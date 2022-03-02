package cn.iichen.quickstudy.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cn.iichen.quickstudy.net.doFailure
import cn.iichen.quickstudy.net.doSuccess
import cn.iichen.quickstudy.pojo.PrismWordsInfo
import cn.iichen.quickstudy.respository.RepositoryImpl
import kotlinx.coroutines.flow.collectLatest

/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.model
 * @ClassName:      OcrImgModel
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 3:06 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 3:06 下午
 * @UpdateRemark:   更新说明：Fuck code, go to hell, serious people who maintain it：
 * @Version:        更新说明: 1.0
┏┓　　　┏┓
┏┛┻━━━┛┻┓
┃　　　　　　　┃
┃　　　━　　　┃
┃　┳┛　┗┳　┃
┃　　　　　　　┃
┃　　　┻　　　┃
┃　　　　　　　┃
┗━┓　　　┏━┛
┃　　　┃   神兽保佑
┃　　　┃   代码无BUG！
┃　　　┗━━━┓
┃　　　　　　　┣┓
┃　　　　　　　┏┛
┗┓┓┏━┳┓┏┛
┃┫┫　┃┫┫
┗┻┛　┗┻┛
 */


class OcrImgModel : ViewModel(){

    private val repository = RepositoryImpl()


    private val _words: MutableLiveData<List<PrismWordsInfo>> = MutableLiveData()
    val words: LiveData<List<PrismWordsInfo>> get() = _words
    suspend fun ocrImage(imgBase64: String) {
        repository.ocrImage(imgBase64).collectLatest {
            it.doFailure {
                _words.value = null
            }
            it.doSuccess { words ->
                if(words.prism_wordsInfo!=null && words.prism_wordsInfo.isNotEmpty()){
                    _words.value = words.prism_wordsInfo
                }
            }
        }
    }
}






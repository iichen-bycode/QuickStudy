package cn.iichen.quickstudy.respository

import cn.iichen.quickstudy.net.ApiResult
import cn.iichen.quickstudy.pojo.OcrBean
import kotlinx.coroutines.flow.Flow

/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.respository
 * @ClassName:      Repository
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 2:39 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 2:39 下午
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


interface Repository {
    suspend fun ocrImage(imgBase64:String) : Flow<ApiResult<OcrBean>>
}
package cn.iichen.quickstudy.respository

import cn.iichen.quickstudy.net.ApiResult
import cn.iichen.quickstudy.net.RetrofitClient
import cn.iichen.quickstudy.pojo.OcrBean
import cn.iichen.quickstudy.pojo.params.OcrParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.respository
 * @ClassName:      RepositoryImpl
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 2:40 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 2:40 下午
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


class RepositoryImpl : Repository{

    override suspend fun ocrImage(imgBase64: String): Flow<ApiResult<OcrBean>> {
        return flow {
            try{
                val ocrBean = RetrofitClient.service.ocrImage("https://english.market.alicloudapi.com/ocrservice/english","APPCODE b8d5c0db45d44741bbdc9efec215d887", OcrParams(imgBase64,false,false,false))
                emit(ApiResult.Success(ocrBean))
            }catch (e:Exception){
                emit(ApiResult.Failure(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

}
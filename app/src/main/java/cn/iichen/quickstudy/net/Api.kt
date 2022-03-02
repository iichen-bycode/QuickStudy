package cn.iichen.quickstudy.net

import cn.iichen.quickstudy.pojo.OcrBean
import cn.iichen.quickstudy.pojo.params.OcrParams
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface Api {
    @POST
    suspend fun ocrImage(@Url url:String, @Header("Authorization") appCode:String,@Body ocr: OcrParams): OcrBean
}
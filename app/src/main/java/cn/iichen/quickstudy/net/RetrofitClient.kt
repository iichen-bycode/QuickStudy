package cn.iichen.quickstudy.net

import android.R.id.message
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit


/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.net
 * @ClassName:      RetrofitClient
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 2:21 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 2:21 下午
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


object RetrofitClient {
    private const val BASE_URL = "http://124.223.95.22:8088/"

    private var retrofit: Retrofit? = null

    val service: Api by lazy {
        getRetrofit().create(Api::class.java)
    }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit!!
    }


    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        builder.run {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor {
                try {
                    val text: String = URLDecoder.decode(it, "utf-8")
                    Log.d("iichen", "------------- $text")
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    Log.d("iichen", "-------------  $it")
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
//            addInterceptor { chain ->
//                val request: Request = chain.request()
//                    .newBuilder()
//                    .addHeader("Authorization", Ext.user?.token?:"")
////                    .addHeader(
////                        "User-Agent",
////                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36"
////                    )
//                    .build()
//                chain.proceed(request)
//            }
            retryOnConnectionFailure(true)//错误重连
        }

        return builder.build()
    }
}
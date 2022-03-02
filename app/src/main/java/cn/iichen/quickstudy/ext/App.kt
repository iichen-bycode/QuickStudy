package cn.iichen.quickstudy.ext

import android.app.Application
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import java.util.*
import kotlin.collections.HashMap


/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.ext
 * @ClassName:      App
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 5:19 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 5:19 下午
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


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        QbSdk.initX5Environment(this,object : QbSdk.PreInitCallback{
            override fun onCoreInitFinished() {

            }

            override fun onViewInitFinished(isX5: Boolean) {

            }
        })
        // wifi下载内核
        QbSdk.setDownloadWithoutWifi(true)

        // 在调用TBS初始化、创建WebView之前进行如下配置
        // 在调用TBS初始化、创建WebView之前进行如下配置
//        val map = HashMap<String,Any>()
//        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
//        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
//        QbSdk.initTbsSettings(map)
    }
}
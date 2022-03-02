package cn.iichen.quickstudy.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import cn.iichen.quickstudy.R
import cn.iichen.quickstudy.base.BaseActivity
import cn.iichen.quickstudy.ext.Ext
import cn.iichen.quickstudy.model.OcrImgModel
import cn.iichen.quickstudy.widget.ImageRegion
import cn.iichen.quickstudy.widget.X5WebView
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_ocr_image.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy.ui
 * @ClassName:      OcrImageActivity
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/2 2:51 下午
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/2 2:51 下午
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


class OcrImageActivity : BaseActivity() {
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0

    private val model: OcrImgModel by viewModels()

    private val ioScope = MainScope()

    companion object{
        fun openOcrImageActivity(context: Context){
            startActivity(Intent(context,OcrImageActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ocr_image)

        screenWidth = resources.displayMetrics.widthPixels
        screenHeight = resources.displayMetrics.heightPixels

        init()

        image.setWordOnClickListener(object : ImageRegion.OnWordClickListener{
            override fun onWordClick(word: String) {
                wordSearch.visibility = View.VISIBLE
                webview.loadUrl("javascript:(function(){  document.getElementById(\"j-textarea\").value = \"${word}\"; document.getElementsByClassName(\"trans-btn\")[0].click();})()")
            }
        })
        btnExit.setOnClickListener {
            wordSearch.visibility = View.INVISIBLE
        }

        Ext.ocrBitmap?.run {
            val baos = ByteArrayOutputStream()
            compress(Bitmap.CompressFormat.JPEG, 100, baos)
            handleImage2View(this)
            val imageBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            ioScope.launch {
                model.ocrImage(imageBase64)
            }
        }


        model.run {
            words.observe(this@OcrImageActivity) {
                if (it != null) {
                    image.setWordPos(it.toMutableList())
                } else {
                    ToastUtils.showLong("当前OCR资源已耗尽，请更换APPCode！")
                }
            }
        }
    }

    private fun handleImage2View(bm : Bitmap) {
        var bitmap = bm

        var width = bitmap.width
        var height = bitmap.height

        Log.d("iichen","########## $width $height")

        if (width >= screenWidth) {
            val radio = width / screenWidth
            val bmHeight = height / radio
            bitmap = ImageUtils.compressByScale(bitmap, screenWidth, bmHeight)
            width = bitmap.width
            height = bitmap.height
        }

        Log.d("iichen","##########2      $width $height")

        val radio = width * 1f / screenWidth
        val scaleHeight = height / radio

        val params = image.layoutParams
        params.width = screenWidth
        params.height = scaleHeight.toInt()
        image.layoutParams = params

        image.setImageBitmap(bitmap)
    }

    fun init() {
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                webview.loadUrl("javascript:(function(){  document.getElementById(\"new-header\").style.display=\"none\";})()")
                if (Build.VERSION.SDK.toInt() >= 16) changGoForwardButton(view)
            }
        }

        webview.loadUrl(mHomeUrl)
    }
    private val mHomeUrl = "https://fanyi.baidu.com/"
    private val disable = 120
    private val enable = 255

    private fun changGoForwardButton(view: WebView) {
        if (view.canGoBack()) btnBack.imageAlpha = enable else btnBack.imageAlpha = disable
        if (view.canGoForward()) btnForward.imageAlpha = enable else btnForward.imageAlpha = disable
        if (view.url != null && view.url.equals(
                mHomeUrl,
                ignoreCase = true
            )
        ) {
            btnHome.imageAlpha = disable
            btnHome.isEnabled = false
        } else {
            btnHome.imageAlpha = enable
            btnHome.isEnabled = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.isVisible){
                if (webview != null && webview.canGoBack()) {
                    webview.goBack()
                    if (Build.VERSION.SDK.toInt() >= 16) changGoForwardButton(webview)
                }else{
                    wordSearch.visibility = View.INVISIBLE
                }
                true
            }else super.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }
}
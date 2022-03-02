package cn.iichen.quickstudy.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.viewModels
import cn.iichen.quickstudy.R
import cn.iichen.quickstudy.base.BaseActivity
import cn.iichen.quickstudy.ext.Ext
import cn.iichen.quickstudy.model.OcrImgModel
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.ToastUtils
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

        if (width >= screenWidth) {
            val radio = width / screenWidth
            val bmHeight = height / radio
            bitmap = ImageUtils.compressByScale(bitmap, screenWidth, bmHeight)
            width = bitmap.width
            height = bitmap.height
        }

        val radio = width * 1f / screenWidth
        val scaleHeight = height / radio

        val params = image.layoutParams
        params.width = screenWidth
        params.height = scaleHeight.toInt()
        image.layoutParams = params

        image.setImageBitmap(bitmap)
    }
}
package cn.iichen.quickstudy

import android.R.attr
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.path
import android.graphics.drawable.Drawable
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        Log.d("iichen","############ $screenWidth $screenHeight")



        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.test,options)
        options.inSampleSize = 1
        options.inJustDecodeBounds = false
        val width = options.outWidth
        val height = options.outHeight

        val radio = width * 1f / screenWidth
        val scaleHeight = height / radio

        val params = image.layoutParams
        params.width = screenWidth
        params.height = scaleHeight.toInt()
        image.layoutParams = params
    }
}
package cn.iichen.quickstudy.ui

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import cn.iichen.quickstudy.R
import cn.iichen.quickstudy.base.BaseActivity
import cn.iichen.quickstudy.ext.Ext
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.ImageUtils
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.ByteArrayOutputStream
import java.io.File


@RuntimePermissions
open class MainActivity : BaseActivity() {
    private lateinit var handleFile: File

    val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()){
        Ext.ocrBitmap = BitmapFactory.decodeFile(handleFile.path)
        OcrImageActivity.openOcrImageActivity(this)
    }

    val pickGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        it.data?.run {
            data?.run {
                val bitmap = BitmapFactory.decodeStream(
                    contentResolver
                        .openInputStream(this)
                )
                Ext.ocrBitmap = bitmap
                OcrImageActivity.openOcrImageActivity(this@MainActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        capture.setOnClickListener {
            openCameraWithPermissionCheck()
        }
        gallery.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = "android.intent.action.GET_CONTENT"
            intent.addCategory("android.intent.category.OPENABLE")
            pickGallery.launch(intent)
        }

    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public fun openCamera(){
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoUri =  getDestinationUri()
        photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(this,"$packageName.fileProvider",File(photoUri.path!!))
        }else{
            getDestinationUri()
        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
        takePicture.launch(photoUri)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun getDestinationUri(): Uri {
        val fileName = String.format("quick_study_%s.jpg",System.currentTimeMillis())
        handleFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),fileName)
        return Uri.fromFile(handleFile)
    }


}








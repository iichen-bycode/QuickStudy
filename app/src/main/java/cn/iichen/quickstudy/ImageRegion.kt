package cn.iichen.quickstudy

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView

/**
 *
 * @ProjectName:    QuickStudy
 * @Package:        cn.iichen.quickstudy
 * @ClassName:      ImageRegion
 * @Description:     java类作用描述
 * @Author:         作者名 qsdiao
 * @CreateDate:     2022/3/1 18:54
 * @UpdateUser:     更新者：qsdiao
 * @UpdateDate:     2022/3/1 18:54
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


class ImageRegion : androidx.appcompat.widget.AppCompatImageView{
    private lateinit var region: Region
    lateinit var mPaint:Paint
    lateinit var mPath:Path

    constructor(context: Context) : super(context){
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.test,options)
        options.inSampleSize = 1
        options.inJustDecodeBounds = false
        val width = options.outWidth
        val height_= options.outHeight

        mPath.run {
            moveTo(101f * screenWidth / width,227f * height / height_)
            lineTo(382f * screenWidth / width,169f * height / height_)
            lineTo(386f * screenWidth / width,192f * height / height_)
            lineTo(105f * screenWidth / width,250f * height / height_)
            close()
        }

        val bounds = RectF()
        mPath.computeBounds(bounds,true)
        region = Region()
        region.setPath(mPath,Region(bounds.left.toInt
        (),bounds.top.toInt(),bounds.right.toInt
        (),bounds.bottom.toInt()))
    }

    private fun init(context: Context) {
        mPaint = Paint()
        mPaint.apply {
            color = Color.RED
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }
        mPath = Path()
    }
/*
        {
            "x": 101,
            "y": 227
        },
        {
            "x": 382,
            "y": 169
        },
        {
            "x": 386,
            "y": 192
        },
        {
            "x": 105,
            "y": 250
        }
 */

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawPath(mPath,mPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                Log.d("iichen","############# ${event.x} ${event.y} ${region.bounds}")
                if(region.contains(event.x.toInt(), event.y.toInt())){
                    Log.d("iichen","############### 包含")
                }else{
                    Log.d("iichen","############### 不包含")
                }
            }

        }
        return super.onTouchEvent(event)
    }
}































package cn.iichen.quickstudy.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import cn.iichen.quickstudy.R
import cn.iichen.quickstudy.ext.Ext
import cn.iichen.quickstudy.pojo.Po
import cn.iichen.quickstudy.pojo.PrismWordsInfo

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
    private lateinit var wordsInfo: MutableList<PrismWordsInfo>
    private var screenWidth: Int = 0
    lateinit var mPaint:Paint
    var mPathList:MutableList<Path> = mutableListOf()
    var mRegionList:MutableList<Region> = mutableListOf()

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

    fun setWordPos(wordsInfo:MutableList<PrismWordsInfo>){
        this.wordsInfo = wordsInfo
        for(wordInfo in wordsInfo){
            var mPath:Path = Path()
            var region: Region = Region()

            Ext.ocrBitmap?.also{
                mPath.run {
                    moveTo(wordInfo.pos[0].x * screenWidth / it.width * 1f,wordInfo.pos[0].y * height / it.height * 1f)
                    lineTo(wordInfo.pos[1].x * screenWidth / it.width * 1f,wordInfo.pos[1].y * height / it.height * 1f)
                    lineTo(wordInfo.pos[2].x * screenWidth / it.width * 1f,wordInfo.pos[2].y * height / it.height * 1f)
                    lineTo(wordInfo.pos[3].x * screenWidth / it.width * 1f,wordInfo.pos[3].y * height / it.height * 1f)
                    close()
                }

                val bounds = RectF()
                mPath.computeBounds(bounds,true)
                region.setPath(mPath,Region(bounds.left.toInt
                    (),bounds.top.toInt(),bounds.right.toInt
                    (),bounds.bottom.toInt()))
            }
            mPathList.add(mPath)
            mRegionList.add(region)
        }
        postInvalidate()
    }

    private fun init(context: Context) {
        screenWidth = resources.displayMetrics.widthPixels
        mPaint = Paint()
        mPaint.apply {
            color = Color.RED
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        for(path in mPathList){
            canvas?.drawPath(path,mPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                var contains = false
                var index = 0
                for (region in mRegionList){
                    if(region.contains(event.x.toInt(), event.y.toInt())){
                        contains = true
                        index = mRegionList.indexOf(region)
                        break
                    }else{
                        contains = false
                    }
                }
                if(contains){
                    this.listener?.run {
                         onWordClick(wordsInfo[index].word)
                    }
                }
            }

        }
        return super.onTouchEvent(event)
    }
    var listener:OnWordClickListener? = null

    interface OnWordClickListener{
        fun onWordClick(word:String)
    }

    fun setWordOnClickListener(listener: OnWordClickListener){
        this.listener = listener
    }
}































package cn.iichen.quickstudy.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

//    应对复杂情况 不会在多线程(网络请求)等产生 无法准确获取当前Fragment的状态而导致 空指针异常
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setDimAmount(0.4f)
        val view: View? = inflater.inflate(setDialogLayoutId(), container,false)
        handleViewEvent(view)
        return view
    }

    open fun setDimAmount(dimAmount: Float){
        dialog?.window?.setDimAmount(dimAmount)
    }

    open fun setCanceledOnTouchOutside(canceled:Boolean){
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onStart() {
        super.onStart()
        initDialogWidth(1f)
    }

    open fun initDialogWidth(radio:Float) {
        if(radio!=1f)
             dialog?.apply {
                val dm = DisplayMetrics()
                requireActivity().windowManager.defaultDisplay.getMetrics(dm)
                window?.setLayout(
                    (dm.widthPixels * radio).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
    }

//    简单场景使用 - 如简单一个提示框等
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
//        val inflater = activity?.layoutInflater
//        val view: View? = inflater?.inflate(setDialogLayoutId(),null)
//        handleViewEvent(view)
//        builder.setView(view)
//        return builder.create()
//    }

    abstract fun handleViewEvent(view: View?)

    abstract fun setDialogLayoutId(): Int
}


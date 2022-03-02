package cn.iichen.quickstudy.net

import android.util.Log

sealed class ApiResult<out T> {
    data class Success<out T>(val value: T) : ApiResult<T>()

    data class Failure(val message: String?) : ApiResult<Nothing>()
}

inline fun <reified T> ApiResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is ApiResult.Success) {
        success(value)
    }
}

inline fun <reified T> ApiResult<T>.doFailure(failure: (String?) -> Unit) {
    if (this is ApiResult.Failure) {
        message?.let { it1 -> Log.d("iichen", "请求失败  $it1") }
        failure(message)
    }
}
package cn.iichen.quickstudy.pojo

data class OcrBean(
    val content: String,
    val error_code: Int,
    val error_msg: String,
    val height: Int,
    val orgHeight: Int,
    val orgWidth: Int,
    val prism_version: String,
    val prism_wnum: Int,
    val prism_wordsInfo: List<PrismWordsInfo>,
    val sid: String,
    val width: Int
)
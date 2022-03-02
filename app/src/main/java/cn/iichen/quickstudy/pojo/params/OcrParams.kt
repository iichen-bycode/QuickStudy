package cn.iichen.quickstudy.pojo.params

data class OcrParams(
    val img: String,
    val prob: Boolean,
    val rotate: Boolean,
    val table: Boolean
)
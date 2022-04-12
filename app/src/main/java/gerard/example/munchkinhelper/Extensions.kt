package gerard.example.munchkinhelper

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat


fun Int.colorInt(context: Context) = ContextCompat.getColor(context, this)
fun Int.colorStateList(context: Context) = ContextCompat.getColorStateList(context, this)
fun Int.colorDrawable(context: Context) = ColorDrawable(ContextCompat.getColor(context, this))

fun Boolean.exec(ontrue: () -> (Unit), onfalse: () -> (Unit)) { if(this) ontrue()  else onfalse()}

inline fun <reified T> checkType(obj: Any) : Boolean {
    return obj is T
}

fun String.toTwoDigitString(): String{
    return if(this.count() == 1) "0$this" else this
}

fun Pair<Int, Int>.toTwoDigitList(): List<String> = mutableListOf<String>().let { list ->
    if(this.first < 0 || this.second < 0 || this.first < this.second) list
    for (i in this.first..this.second) {
        list.add(i.toString().toTwoDigitString())
    }
    list
}
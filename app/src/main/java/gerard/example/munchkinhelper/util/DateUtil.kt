package gerard.example.munchkinhelper.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun dateDDMMYYYYdash() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

}

fun now() = System.currentTimeMillis()
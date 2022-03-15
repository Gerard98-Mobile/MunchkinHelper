package gerard.example.munchkinhelper.util

import java.util.concurrent.TimeUnit

fun Long.msToHHMM() : String = String.format("%02d:%02d",
    TimeUnit.MILLISECONDS.toHours(this),
    TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)))
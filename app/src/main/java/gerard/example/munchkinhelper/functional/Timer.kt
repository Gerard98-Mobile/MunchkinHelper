package gerard.example.munchkinhelper.functional

import android.util.Log
import kotlinx.coroutines.*

class Timer(private val delayTime: Long = 1000, private val onTick: () -> Unit) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var timer: Job? = null

    private fun createTimer() : Job {
        return scope.launch {
            while (true) {
                onTick.invoke()
                delay(delayTime)
            }
        }

    }

    fun startTimer() {
        timer = createTimer()
        timer?.start()
        Log.e("Timer","Start Timer ${timer?.isActive == true}")
    }

    fun cancelTimer() {
        timer?.cancel()
        timer = null
        Log.e("Timer","Cancel Timer ${timer?.isCancelled}")
    }
}
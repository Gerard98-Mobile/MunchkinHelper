package gerard.example.munchkinhelper.util

import android.content.Context
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RawRes
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.R


enum class Sound(@RawRes val resId : Int){
    LVL_UP(R.raw.level_up),
    LVL_DOWN(R.raw.level_down),
    DEATH(R.raw.you_lose)
}

class SoundHelper(
    val context: Context
) {

    private val soundPoll: SoundPool = SoundPool.Builder().setMaxStreams(8).build()
    private val soundsMap = HashMap<Int, Int>()

    private var errors = 0

    fun playSound(sound: Sound) {
        playSound(sound.resId)
    }

    private fun playSound(@RawRes rawResId: Int) {
        if(Cfg.sound.value.get() == false) return

        if(soundsMap[rawResId] == null){
            Log.e("SoundHelper", "Sound loading: $rawResId")
            val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
            val id = soundPoll.load(assetFileDescriptor, 1)
            soundsMap[rawResId] = id
            playSound(rawResId)
            return
        }
        val id = soundsMap[rawResId] ?: return
        val output = soundPoll.play(id, 1.0f, 1.0f, 0, 0, 1.0f)
        Log.e("SoundHelper","Sound played: $output")
        if(output == 0 && errors <= 5) {
            errors++
            Handler(Looper.getMainLooper()).postDelayed({
                playSound(rawResId)
            }, 50)
        } else if(output != 0) errors = 0
    }

    fun playLvlChangeSound(lvlUp: Boolean){
        playSound(if(lvlUp) Sound.LVL_UP else Sound.LVL_DOWN)
    }


}
package gerard.example.munchkinhelper.util

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.annotation.RawRes
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.NotInitializedException
import gerard.example.munchkinhelper.R

class SoundHelper(
    val context: Context
) {

    private val soundPoll: SoundPool = SoundPool.Builder().setMaxStreams(8).build()
    private val soundsMap = HashMap<Int, Int>()

    fun playSound(@RawRes rawResId: Int) {
        if(Cfg.sound.value.get() == false) return

        if(soundsMap.get(rawResId) == null){
            val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
            val id = soundPoll.load(assetFileDescriptor, 1)
            soundsMap.put(rawResId, id)
            playSound(rawResId)
            return
        }
        val id = soundsMap.get(rawResId) ?: return
        soundPoll.play(id, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    fun playLvlChangeSound(lvlUp: Boolean){
        playSound(if(lvlUp) R.raw.level_up else R.raw.level_down)
    }


}
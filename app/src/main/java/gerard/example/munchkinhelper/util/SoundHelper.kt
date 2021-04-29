package gerard.example.munchkinhelper.util

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import gerard.example.munchkinhelper.NotInitializedException
import gerard.example.munchkinhelper.R

class SoundHelper(
    val context: Context
) {

    private val mediaPlayer = MediaPlayer().apply {
        setOnPreparedListener { start() }
        setOnCompletionListener { reset() }
    }

    fun playSound(@RawRes rawResId: Int) {
        val assetFileDescriptor = context.resources.openRawResourceFd(rawResId) ?: return
        mediaPlayer.run {
            reset()
            setDataSource(assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset, assetFileDescriptor.declaredLength)
            prepareAsync()
        }
    }

    fun playLvlChangeSound(application: Context?, goUp: Boolean){
        playSound(if(goUp) R.raw.level_up else R.raw.level_down)
    }


}
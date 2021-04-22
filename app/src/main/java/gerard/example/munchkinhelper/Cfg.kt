package gerard.example.munchkinhelper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import gerard.example.munchkinhelper.fragments.settings.Setting
import gerard.example.munchkinhelper.core.SharedValue

object Cfg{

    var sharedPreferences : SharedPreferences? = null
        get() = if(field == null) throw NotInitializedException("SharedPreference have to be initialized") else field
        private set(value){ field = value}

    fun init(activity: Activity){
        sharedPreferences = activity.getSharedPreferences("Cfg", Context.MODE_PRIVATE)
    }

    private val _autoSaveValue = SharedValue("auto_save",true,Boolean::class.java)
    private val _sound = SharedValue("sound",true,Boolean::class.java)

    val autoSave = Setting(R.string.auto_save, _autoSaveValue, R.drawable.ic_save)
    val sound = Setting(R.string.sound, _sound, R.drawable.ic_audio)

    val settings = listOf(autoSave, sound)
}
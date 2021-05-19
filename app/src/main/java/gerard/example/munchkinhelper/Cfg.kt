package gerard.example.munchkinhelper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import gerard.example.munchkinhelper.ui.fragments.settings.Setting
import gerard.example.munchkinhelper.core.SharedValue
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player

object Cfg{

    var autoOpen = false

    var sharedPreferences : SharedPreferences? = null
        get() = if(field == null) throw NotInitializedException("SharedPreference have to be initialized") else field
        private set(value){ field = value}

    fun init(activity: Activity){
        sharedPreferences = activity.getSharedPreferences("Cfg", Context.MODE_PRIVATE)
    }

    val lastGame = SharedValue("game",null , Game::class.java)

    private val _autoSaveValue = SharedValue("auto_save",true,Boolean::class.java)
    private val _sound = SharedValue("sound",true,Boolean::class.java)
    private val _dice3d = SharedValue("sound",true,Boolean::class.java)
    private val _showDeathCount = SharedValue("show_death_count",true,Boolean::class.java)
    private val _darkMode = SharedValue("dark_mode",false,Boolean::class.java)

    val autoSave = Setting(R.string.auto_save, _autoSaveValue, R.drawable.ic_save)
    val sound = Setting(R.string.sound, _sound, R.drawable.ic_audio)
    val dice3d = Setting(R.string.dice3d, _dice3d, R.drawable.ic_dice_four)
    val showDeathCount = Setting(R.string.death_count_setting, _showDeathCount, R.drawable.ic_skull)
    val darkMode = Setting(R.string.dark_mode, _darkMode, R.drawable.ic_dark_mode)

    val settings = listOf(autoSave, sound, showDeathCount, darkMode)
}
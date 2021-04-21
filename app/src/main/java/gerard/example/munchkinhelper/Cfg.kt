package gerard.example.munchkinhelper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import gerard.example.munchkinhelper.fragments.settings.Setting
import gerard.example.munchkinhelper.core.SharedValue

object Cfg {

    var sharedPreferences : SharedPreferences? = null

    fun init(activity: Activity){
        sharedPreferences = activity.getSharedPreferences("Cfg", Context.MODE_PRIVATE)
    }

    private val _autoSaveValue = SharedValue("auto_save",true,Boolean::class.java)
    val autoSave = Setting(R.string.auto_save, _autoSaveValue)

    val settings = listOf(autoSave)

}
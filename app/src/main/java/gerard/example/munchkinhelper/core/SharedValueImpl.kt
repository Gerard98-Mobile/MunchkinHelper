package gerard.example.munchkinhelper.core

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.model.Game

abstract class SharedValueImpl<T>{

    protected var key : String = ""
    protected var defaultValue : T? = null


    abstract fun getValue() : T?
    abstract fun setValue(value : T)

    fun with(defaultValue: T?, key: String) {
        this.key = key
        this.defaultValue = defaultValue
    }

    class Bool : SharedValueImpl<Boolean>(){
        override fun getValue(): Boolean? {
            val value = Cfg.sharedPreferences?.getBoolean(key, defaultValue ?: false)
            Log.d("SharedValue","Value from " + key + " is " + value)
            return value
        }

        override fun setValue(value: Boolean) {
            with(Cfg.sharedPreferences?.edit()){
                if(this == null) {
                    Log.d("SharedValue", "Setting Value error, obj is null")
                    return
                }
                putBoolean(key, value)
                commit()
            }
        }
    }


    class Integer : SharedValueImpl<Int>(){
        override fun getValue(): Int? {
            return Cfg.sharedPreferences?.getInt(key, defaultValue ?: 0)
        }

        override fun setValue(value: Int) {
            with(Cfg.sharedPreferences?.edit()){
                if(this == null) return
                putInt(key, value)
                commit()
            }
        }
    }

    class Text : SharedValueImpl<String>(){
        override fun getValue(): String? {
            return Cfg.sharedPreferences?.getString(key, defaultValue ?: "")
        }

        override fun setValue(value: String) {
            with(Cfg.sharedPreferences?.edit()){
                if(this == null) return
                putString(key, value)
                commit()
            }
        }
    }

    class SharedGame : SharedValueImpl<Game>(){
        override fun getValue(): Game? {
            val gameJson = Cfg.sharedPreferences?.getString(key, (defaultValue ?: "").toString())
            val type = object: TypeToken<Game>(){}.type
            return Gson().fromJson(gameJson, type)
        }

        override fun setValue(value: Game) {
            val gameJson = Gson().toJson(value)
            with(Cfg.sharedPreferences?.edit()){
                if(this == null) return
                putString(key, gameJson)
                commit()
            }
        }
    }

}
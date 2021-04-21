package gerard.example.munchkinhelper.core

import gerard.example.munchkinhelper.Cfg

abstract class SharedValueImpl<T>{

    protected var key : String = ""
    protected var defaultValue : T? = null


    abstract fun getValue() : T?
    abstract fun setValue(value : T)

    fun with(defaultValue: T, key: String) {
        this.key = key
        this.defaultValue = defaultValue
    }

    class Bool : SharedValueImpl<Boolean>(){
        override fun getValue(): Boolean? {
            return Cfg.sharedPreferences?.getBoolean(key, defaultValue ?: false)
        }

        override fun setValue(value: Boolean) {
            with(Cfg.sharedPreferences?.edit()){
                if(this == null) return
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

}
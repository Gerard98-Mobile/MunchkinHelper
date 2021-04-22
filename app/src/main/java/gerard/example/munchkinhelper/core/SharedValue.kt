package gerard.example.munchkinhelper.core

import android.content.SharedPreferences
import java.lang.UnsupportedOperationException

class SharedValue<T>(val key: String, defaultValue: T, val clazz: Class<T>){

    private var value : T? = null

    private val impl : SharedValueImpl<T>

    init {
        impl = implementationForClass(clazz.name)
        impl.with(defaultValue, key)
    }

    fun get() : T?{
        if(value == null) value = impl.getValue()
        return value
    }

    fun set(value: T){
        this.value = value
        impl.setValue(value)
    }

    private fun implementationForClass(name: String): SharedValueImpl<T> {
        when(name){
            "java.lang.Boolean","boolean" -> return SharedValueImpl.Bool() as SharedValueImpl<T>
            "java.lang.String","string" -> return SharedValueImpl.Text() as SharedValueImpl<T>
            "java.lang.Integer","integer" -> return SharedValueImpl.Integer() as SharedValueImpl<T>
            else -> throw UnsupportedOperationException("This type is unsupported")
        }
    }
}
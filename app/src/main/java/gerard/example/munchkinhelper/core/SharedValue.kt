package gerard.example.munchkinhelper.core

import java.lang.UnsupportedOperationException

class SharedValue<T>(key: String, defaultValue: T, clazz: Class<T>) {
    private val impl : SharedValueImpl<T>

    init {
        impl = implementationForClass(clazz.name)
        impl.with(defaultValue, key)
    }

    fun get() : T?{
        return impl.getValue()
    }

    fun set(value: T){
        impl.setValue(value)
    }

    private fun implementationForClass(name: String): SharedValueImpl<T> {
        when(name){
            "java.lang.Boolean" -> return SharedValueImpl.Bool() as SharedValueImpl<T>
            "java.lang.String" -> return SharedValueImpl.Bool() as SharedValueImpl<T>
            "java.lang.Integer" -> return SharedValueImpl.Bool() as SharedValueImpl<T>
            else -> throw UnsupportedOperationException("This type is unsupported")
        }
    }
}
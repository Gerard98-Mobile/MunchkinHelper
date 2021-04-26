package gerard.example.munchkinhelper.core.views.dice

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

object GLTools {

    fun array2Buffer(array: FloatArray) : FloatBuffer{
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(array.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        return vbb.asFloatBuffer()
    }

    fun array2Buffer(array: ShortArray) : ShortBuffer{
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(array.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        return vbb.asShortBuffer()
    }
}
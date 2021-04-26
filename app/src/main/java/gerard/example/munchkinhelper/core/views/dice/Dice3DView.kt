package gerard.example.munchkinhelper.core.views.dice

import android.R.attr.angle
import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glUseProgram
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import kotlinx.android.synthetic.main.view_dice3d.view.*
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class Dice3DView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : GLSurfaceView(context){

    val renderer : DiceRenderer

    init{
//        inflate(context, R.layout.view_dice3d, this)
        setEGLContextClientVersion(2)
        renderer = DiceRenderer()
        setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }


    class DiceRenderer : GLSurfaceView.Renderer{

        private lateinit var mTriangle: Triangle
        private lateinit var mSquare: Rectangle

        private val mViewMatrix = FloatArray(16)
        private val mMVPMatrix = FloatArray(16)
        private val mProjectionMatrix = FloatArray(16)
        private val mRotationMatrix = FloatArray(16)

        override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
            GLES20.glClearColor(254f, 241f, 213f, 1f)

            mTriangle = Triangle()
            mSquare = Rectangle()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            GLES20.glViewport(0, 0, width, height)

            val ratio = width.toFloat() / height
            // this projection matrix is applied to object coordinates
            // in the onDrawFrame() method
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1F, 1F, 3F, 7F)
        }

        override fun onDrawFrame(gl: GL10?) {
            val time = SystemClock.uptimeMillis() % 4000L;
            val angle = 0.090f * time;

            val scratch = FloatArray(16)

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
            // Set the camera position (View matrix)
            Matrix.setLookAtM(mViewMatrix, 0, -2f, 2f, -6f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

            Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)

            Matrix.setRotateM(mRotationMatrix, 0, 0.5f, 0.5f, 0.5f, 1.0f)

            Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0)

            mSquare.draw(scratch)
        }

        companion object{

            fun loadShader(type: Int, shaderCode: String) : Int{
                // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
                // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
                val shader = GLES20.glCreateShader(type)
                // add the source code to the shader and compile it
                GLES20.glShaderSource(shader, shaderCode)
                GLES20.glCompileShader(shader)
                return shader
            }

            fun checkGlError(glOperation: String) {
                var error: Int
                while (GLES20.glGetError().also { error = it } != GLES20.GL_NO_ERROR) {
                    Log.e("Dice3D", "$glOperation: glError $error")
                    throw RuntimeException("$glOperation: glError $error")
                }
            }

        }
    }

    private val TOUCH_SCALE_FACTOR = 180.0f / 320
    private var mPreviousX = 0f
    private var mPreviousY = 0f

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        val x = e?.x ?: 0f
        val y = e?.y ?: 0f
        Log.e("TAG","X: " + x + " Y: " + y)

        return true
    }
}
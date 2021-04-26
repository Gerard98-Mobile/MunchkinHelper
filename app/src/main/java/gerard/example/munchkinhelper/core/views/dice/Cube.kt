package gerard.example.munchkinhelper.core.views.dice

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer


class Cube{

    private var vertexBuffer : FloatBuffer
    private var indexBuffer: ShortBuffer
    private val numFaces = 6
    private var colorHandle = 0
    private val vertexShaderCode = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}"
    private var MVPMatrixHandle = 0
    private var positionHandle = 0
    private var program = 0

    val COORDS_PER_VERTEX = 3
    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex


    private val colors = arrayOf(
        floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f),
        floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f),
        floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
        floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f),
        floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f),
        floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f)
    )

    private val vertices = floatArrayOf( // Vertices of the 6 faces
        // FRONT
        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        1.0f, 1.0f, 1.0f,  // 3. right-top-front
        // BACK
        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        // LEFT
        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
        // RIGHT
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        1.0f, 1.0f, 1.0f,  // 3. right-top-front
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        // TOP
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        1.0f, 1.0f, 1.0f,  // 3. right-top-front
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        1.0f, 1.0f, -1.0f,  // 7. right-top-back
        -1.0f, 1.0f, -1.0f,  // 5. left-top-back
        -1.0f, 1.0f, 1.0f,  // 2. left-top-front
        // BOTTOM
        -1.0f, -1.0f, -1.0f,  // 4. left-bottom-back
        1.0f, -1.0f, -1.0f,  // 6. right-bottom-back
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        1.0f, -1.0f, 1.0f,  // 1. right-bottom-front
        -1.0f, -1.0f, 1.0f,  // 0. left-bottom-front
        -1.0f, -1.0f, -1.0f // 4. left-bottom-back
    )

    var indeces = shortArrayOf(
        0, 1, 3, 1, 2, 3,
        4, 5, 7, 5, 6, 7,
        8, 9, 11, 9, 10, 11,
        12, 13, 15, 13, 14, 15,
        16, 17, 19, 17, 18, 19,
        20, 21, 23, 21, 22, 23
    )

    init {
        val vbb: ByteBuffer = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        vertexBuffer = vbb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        indexBuffer = ByteBuffer.allocateDirect(indeces.size * 2).order(ByteOrder.nativeOrder())
            .asShortBuffer()
        indexBuffer.put(indeces).position(0)
        val vertexShader: Int = Dice3DView.DiceRenderer.loadShader(
            GLES20.GL_VERTEX_SHADER,
            vertexShaderCode
        )
        val fragmentShader: Int =
            Dice3DView.DiceRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)
        GLES20.glLinkProgram(program)
    }

    // Draw the shape
    fun draw(mvpMatrix: FloatArray?) {
        GLES20.glUseProgram(program)
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(
            positionHandle,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )
        MVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix")
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mvpMatrix, 0)
//        // Render all the faces
//        for (face in 0 until numFaces) {
//            // Set the color for each of the faces
//            colorHandle = GLES20.glGetUniformLocation(program, "vColor")
//            GLES20.glUniform4fv(colorHandle, 1, colors[face], 0)
//        }


        val mColorHandle = GLES20.glGetUniformLocation(program, "vColor");
        // Render all the faces
//        for (face in 0 until numFaces) {
//            // Set the color for each of the faces
//            colorHandle = GLES20.glGetUniformLocation(program, "vColor")
//            GLES20.glUniform4fv(colorHandle, 1, colors[face], 0)
//        }
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 36, GLES20.GL_UNSIGNED_SHORT, indexBuffer)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }
}
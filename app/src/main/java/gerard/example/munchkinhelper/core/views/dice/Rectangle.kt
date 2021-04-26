package gerard.example.munchkinhelper.core.views.dice

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer


class Rectangle {

    private val vertexShaderCode =  // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +  // The matrix must be included as a modifier of gl_Position.
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"

    private val fragmentShaderCode = ("precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}")

    private var vertexBuffer: FloatBuffer
    private var drawListBuffer: ShortBuffer
    private var mProgram = 0
    private var mPositionHandle = 0
    private var mColorHandle = 0
    private var mMVPMatrixHandle = 0

    // number of coordinates per vertex in this array
    val COORDS_PER_VERTEX = 3
    var squareCoords = floatArrayOf(

        0.5f, 0.5f, 0.5f,   // top left     // 4
        0.5f, -0.5f, 0.5f,   // bottom left
        0.5f, -0.5f, -0.5f,   // bottom right
        0.5f, 0.5f, -0.5f,  // top right

        -0.5f, 0.5f, 0.5f,   // top left     // 1
        -0.5f, -0.5f, 0.5f,   // bottom left
        0.5f, -0.5f, 0.5f,   // bottom right
        0.5f, 0.5f, 0.5f,  // top right

        -0.5f, 0.5f, 0.5f,   // top left     // 2
        -0.5f, -0.5f, 0.5f,   // bottom left
        -0.5f, -0.5f, -0.5f,   // bottom right
        -0.5f, 0.5f, -0.5f,  // top right

        -0.5f, 0.5f, 0.5f,   // top left     // up
        0.5f, 0.5f, 0.5f,   // bottom left
        0.5f, 0.5f, -0.5f,   // bottom right
        -0.5f, 0.5f, -0.5f,  // top right

        -0.5f, -0.5f, 0.5f,   // top left     // bottom
        0.5f, -0.5f, 0.5f,   // bottom left
        0.5f, -0.5f, -0.5f,   // bottom right
        -0.5f, -0.5f, -0.5f,  // top right

        0.5f, 0.5f, -0.5f,   // top left     // 3
        0.5f, -0.5f, -0.5f,   // bottom left
        -0.5f, -0.5f, -0.5f,   // bottom right
        -0.5f, 0.5f, -0.5f,  // top right

    )

    private val drawOrders = arrayOf(
        shortArrayOf(0, 1, 2, 0, 2, 3),
        shortArrayOf(4, 5, 6, 4, 6, 7),
        shortArrayOf(8, 9, 10, 8, 10, 11),
        shortArrayOf(12, 13, 14, 12, 14, 15),
        shortArrayOf(16, 17, 18, 16, 18, 19),
        shortArrayOf(20, 21, 22, 20, 22, 23)
    )

    private val drawOrder = shortArrayOf(
        0, 1, 2, 0, 2, 3,
        4, 5, 6, 4, 6, 7,
        8, 9, 10, 8, 10, 11,
        12, 13, 14, 12, 14, 15,
        16, 17, 18, 16, 18, 19,
        20, 21, 22, 20, 22, 23
    ) // order to draw vertices



    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    var color = floatArrayOf(0.2f, 0.709803922f, 0.898039216f, 1.0f)

    var colors = arrayOf(
        floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f),
        floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f),
        floatArrayOf(0.67f, 0.82f, 0.78f, 1.0f),
        floatArrayOf(0.82f, 0.78f, 0.67f, 1.0f),
        floatArrayOf(0.30f, 0.47f, 0.16f, 1.0f),
        floatArrayOf(0.86f, 0.88f, 0.74f, 1.0f)
    )

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */
    constructor() {
        // initialize vertex byte buffer for shape coordinates
        val bb = ByteBuffer.allocateDirect( // (# of coordinate values * 4 bytes per float)
            squareCoords.size * 8
        )
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(squareCoords)
        vertexBuffer.position(0)
        // initialize byte buffer for the draw list
        val dlb = ByteBuffer.allocateDirect( // (# of coordinate values * 2 bytes per short)
            drawOrder.size * 4
        )
        dlb.order(ByteOrder.nativeOrder())
        drawListBuffer = dlb.asShortBuffer()
        drawListBuffer.put(drawOrder)
        drawListBuffer.position(0)
        // prepare shaders and OpenGL program
        val vertexShader: Int = Dice3DView.DiceRenderer.loadShader(
            GLES20.GL_VERTEX_SHADER,
            vertexShaderCode
        )
        val fragmentShader: Int = Dice3DView.DiceRenderer.loadShader(
            GLES20.GL_FRAGMENT_SHADER,
            fragmentShaderCode
        )
        mProgram = GLES20.glCreateProgram() // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader) // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader) // add the fragment shader to program
        GLES20.glLinkProgram(mProgram) // create OpenGL program executables
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    fun draw(mvpMatrix: FloatArray?) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram)
        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle)
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
            mPositionHandle, COORDS_PER_VERTEX,
            GLES20.GL_FLOAT, false,
            vertexStride, vertexBuffer
        )
        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")
        // Set color for drawing the triangle

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")
        Dice3DView.DiceRenderer.checkGlError("glGetUniformLocation")
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)
        Dice3DView.DiceRenderer.checkGlError("glUniformMatrix4fv")
        // Draw the square

        for(i in 0..5){
            GLES20.glUniform4fv(mColorHandle, 1, colors[i], 0)

            val drawOrderTmp = drawOrders[i]

            val dlb = ByteBuffer.allocateDirect( // (# of coordinate values * 2 bytes per short)
                drawOrderTmp.size * 4
            )
            dlb.order(ByteOrder.nativeOrder())
            drawListBuffer = dlb.asShortBuffer()
            drawListBuffer.put(drawOrderTmp)
            drawListBuffer.position(0)

            val tmp = getTextureTarget(i)

            GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrderTmp.size,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer
            )
        }

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

    private fun getTextureTarget(i: Int): Int = when(i){
        0 -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
        1 -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X
        2 -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z
        3 -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X
        4 -> GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y
        5 -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
        else -> GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
    }
}
package gerard.example.munchkinhelper.core.views.dice

import android.opengl.GLES20

class Cube2 {

    val program : Int

    val vPositionLoc : Int
    val mColorLoc : Int

    init{
        program = GLES20.glCreateProgram()

        vPositionLoc = GLES20.glGetAttribLocation(program, "a_Position")
        mColorLoc = GLES20.glGetAttribLocation(program, "a_color")
    }

    private val r: Float = 0.5f
    //Vertex coordinates
    var vertexBuffer = GLTools.array2Buffer(
        floatArrayOf(
            -r, r, r,//0
            -r, -r, r,//1
            r, -r, r,//2
            r, r, r,//3
            r, -r, -r,//4
            r, r, -r,//5
            -r, -r, -r,//6
            -r, r, -r//7
        )
    )

    var mIndices = shortArrayOf(
        0, 1, 2, 0, 2, 3,
        3, 2, 4, 3, 4, 5,
        5, 4, 6, 5, 6, 7,
        7, 6, 1, 7, 1, 0,
        7, 0, 3, 7, 3, 5,
        6, 1, 2, 6, 2, 4
    )
    val mIndicesBuffer = GLTools.array2Buffer(mIndices)

    var colorBuffer =GLTools.array2Buffer(
        floatArrayOf(
            1f,1f,0f,1f,
            1f,1f,0f,1f,
            1f,1f,0f,1f,
            1f,1f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f
        )
    )

    fun draw(){

        GLES20.glUseProgram(program)
        //Set vertex data
        vertexBuffer.position(0)
        GLES20.glEnableVertexAttribArray(vPositionLoc)
        GLES20.glVertexAttribPointer(vPositionLoc, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)
        //Set color data
        colorBuffer.position(0)
        GLES20.glEnableVertexAttribArray(mColorLoc)
        GLES20.glVertexAttribPointer(mColorLoc, 3, GLES20.GL_FLOAT, false, 0, colorBuffer)
        GLES20.glDrawElements(
            GLES20.GL_TRIANGLES,
            mIndices.size,
            GLES20.GL_UNSIGNED_SHORT,
            mIndicesBuffer
        )
    }


}
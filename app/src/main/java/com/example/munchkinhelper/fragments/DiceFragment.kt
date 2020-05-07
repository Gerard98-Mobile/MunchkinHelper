package com.example.munchkinhelper.fragments

import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkinhelper.R
import com.example.munchkinhelper.adapters.PlayerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.security.auth.callback.Callback
import kotlin.random.Random

class DiceFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.dice_fragment, container, false)

        val surface : SurfaceView = v.findViewById(R.id.surface)
        surface.setZOrderOnTop(true)
        surface.holder.setFormat(PixelFormat.TRANSLUCENT)


        val roll : Button = v.findViewById(R.id.roll_button)
        roll.setOnClickListener {
            val canvas : Canvas = surface.holder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            val d = resources.getDrawable(getRandomDiceId(), null)

            val top = Random.nextInt(canvas.height - 150)
            val left = Random.nextInt(canvas.width - 150)

            d.setBounds(left  ,top,left+150,top+150)
            d.draw(canvas)

            surface.holder.unlockCanvasAndPost(canvas)
        }

        return v
    }

    private fun getRandomDiceId() : Int{
        val dice = Random.nextInt(6)
        when(dice){
            0 -> return R.drawable.ic_inverted_dice_1
            1 -> return R.drawable.ic_inverted_dice_2
            2 -> return R.drawable.ic_inverted_dice_3
            3 -> return R.drawable.ic_inverted_dice_4
            4 -> return R.drawable.ic_inverted_dice_5
            5 -> return R.drawable.ic_inverted_dice_6
        }


        return 0
    }


}


package gerard.example.munchkinhelper.fragments

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.model.Game
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
            canvas.drawColor(ContextCompat.getColor(v.context, R.color.colorBackground))
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
            0 -> return R.drawable.ic_dice_one
            1 -> return R.drawable.ic_dice_two
            2 -> return R.drawable.ic_dice_three
            3 -> return R.drawable.ic_dice_four
            4 -> return R.drawable.ic_dice_five
            5 -> return R.drawable.ic_dice_six
        }
        return 0
    }


}

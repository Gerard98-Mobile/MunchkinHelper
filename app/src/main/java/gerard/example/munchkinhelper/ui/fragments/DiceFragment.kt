package gerard.example.munchkinhelper.ui.fragments

import android.graphics.*
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.R
import kotlinx.android.synthetic.main.dice_fragment.view.*
import kotlin.random.Random

class DiceFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.dice_fragment, container, false)

        val surface : SurfaceView = v.findViewById(R.id.surface)
        surface.setZOrderOnTop(true)
        surface.holder.setFormat(PixelFormat.TRANSLUCENT)

        v.roll_button.setOnClickListener {
            val canvas : Canvas = surface.holder.lockCanvas()
            canvas.drawColor(ContextCompat.getColor(v.context, R.color.colorBackground))
            val d = ContextCompat.getDrawable(it.context, getRandomDiceId()) ?: return@setOnClickListener

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


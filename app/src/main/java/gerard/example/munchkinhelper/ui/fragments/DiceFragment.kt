package gerard.example.munchkinhelper.ui.fragments

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.DiceFragmentBinding
import kotlin.random.Random

class DiceFragment : BaseFragment<DiceFragmentBinding>(){

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DiceFragmentBinding
            = DiceFragmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surface.setZOrderOnTop(true)
        binding.surface.holder.setFormat(PixelFormat.TRANSLUCENT)

        binding.rollButton.setOnClickListener {
            val canvas : Canvas = binding.surface.holder.lockCanvas()
            canvas.drawColor(ContextCompat.getColor(it.context, CfgTheme.current.backgroundColor))
            val d = ContextCompat.getDrawable(it.context, getRandomDiceId()) ?: return@setOnClickListener

            d.setTint(CfgTheme.current.primaryColor.colorInt(it.context))

            val top = Random.nextInt(canvas.height - 150)
            val left = Random.nextInt(canvas.width - 150)

            d.setBounds(left  ,top,left+150,top+150)
            d.draw(canvas)

            binding.surface.holder.unlockCanvasAndPost(canvas)
        }
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

    override fun applyThemeColors() {
        //binding.rollButton.applyTheme()
        context?.let {
            binding.frame.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(it))
        }
    }

}


package gerard.example.munchkinhelper.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.core.text.HtmlCompat
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.databinding.DialogDeathBinding

class DeathDialog(
    context: Context,
    val name: String,
    val deathCallback: DeathCallback
) : Dialog(context) {

    private lateinit var binding: DialogDeathBinding

    fun interface DeathCallback{
        fun death()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeathBinding.inflate(layoutInflater)
        val view = binding.root
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(view)

        val htmlText = "<b>" + context.getString(R.string.poor_end) + "</b> " + name + " " + getDeadText()
        binding.deadText.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.btnNo.setOnClickListener { dismiss() }
        binding.btnYes.setOnClickListener {
            deathCallback.death()
            dismiss()
        }

        with(CfgTheme.current.backgroundColor.colorInt(context)){
            binding.root.setCardBackgroundColor(this)
        }

        with(CfgTheme.current.primaryColor.colorInt(context)){
            binding.root.strokeColor = this
            binding.deadText.setTextColor(this)
            binding.btnNo.setTextColor(this)
            binding.btnYes.setTextColor(this)
        }

        with(CfgTheme.current.primaryColor.colorStateList(context)){
            binding.image.imageTintList = this
        }

    }

    fun getDeadText() : String{
        return context.getString(R.string.baked_and_eaten_end)
    }

}
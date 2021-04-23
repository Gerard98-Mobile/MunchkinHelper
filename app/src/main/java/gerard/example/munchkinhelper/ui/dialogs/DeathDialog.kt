package gerard.example.munchkinhelper.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import androidx.core.text.HtmlCompat
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Player
import kotlinx.android.synthetic.main.dialog_death.*

class DeathDialog(
    context: Context,
    val name: String,
    val deathCallback: DeathCallback
) : Dialog(context) {

    fun interface DeathCallback{
        fun death()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_death)

        val htmlText = "<b>" + context.getString(R.string.poor_end) + "</b> " + name + " " + getDeadText()
        dead_text.text = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)

        btn_no.setOnClickListener { dismiss() }
        btn_yes.setOnClickListener {
            deathCallback.death()
            dismiss()
        }
    }

    fun getDeadText() : String{
        return context.getString(R.string.baked_and_eaten_end)
    }

}
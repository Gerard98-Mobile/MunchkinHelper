package gerard.example.munchkinhelper.core.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StringRes
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback
import kotlinx.android.synthetic.main.dialog_custom.*

class YesNoDialog(
    context: Context,
    @StringRes val titleRes: Int,
    @StringRes val bodyRes: Int,
    val callback: Callback<Boolean>
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_custom)
        title_txt.text = context.getString(titleRes)
        body_txt.text = context.getString(bodyRes)
        yes.setOnClickListener {
            callback.execute(true, Action.NONE)
            dismiss()
        }
        cancel.setOnClickListener {
            callback.execute(false, Action.NONE)
            dismiss()
        }
    }
}


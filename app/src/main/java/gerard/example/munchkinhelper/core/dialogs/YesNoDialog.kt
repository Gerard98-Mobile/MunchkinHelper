package gerard.example.munchkinhelper.core.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.StringRes
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.databinding.DialogCustomBinding
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback

class YesNoDialog(
    context: Context,
    @StringRes val titleRes: Int,
    @StringRes val bodyRes: Int,
    val callback: Callback<Boolean>
) : Dialog(context) {

    private lateinit var binding: DialogCustomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomBinding.inflate(layoutInflater)
        val view = binding.root
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(view)

        binding.titleTxt.text = context.getString(titleRes)
        binding.bodyTxt.text = context.getString(bodyRes)
        binding.yes.setOnClickListener {
            callback.execute(true, Action.NONE)
            dismiss()
        }
        binding.cancel.setOnClickListener {
            callback.execute(false, Action.NONE)
            dismiss()
        }
        with(CfgTheme.current.primaryColor.colorInt(context)){
            binding.titleTxt.setBackgroundColor(this)
            binding.cancel.setTextColor(this)
            binding.yes.setTextColor(this)
            binding.bodyTxt.setTextColor(this)
        }
        with(CfgTheme.current.backgroundColor.colorInt(context)){
            binding.root.setCardBackgroundColor(this)
        }

        binding.titleTxt.setTextColor(CfgTheme.current.textColorSecondary.colorInt(context))

    }


}


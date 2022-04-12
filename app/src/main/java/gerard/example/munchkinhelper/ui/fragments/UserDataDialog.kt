package gerard.example.munchkinhelper.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.dialogs.FullScreenDialog
import gerard.example.munchkinhelper.databinding.DialogUserIngameSettingsBinding

class UserDataDialog(context: Context) : FullScreenDialog<DialogUserIngameSettingsBinding>(
    context,
    DialogUserIngameSettingsBinding::inflate,
    R.string.user_data_dialog_title
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.digitSelector?.init()
    }

    override fun applyTheme() {
        super.applyTheme()

    }
}
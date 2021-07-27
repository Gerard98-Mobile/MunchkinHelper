package gerard.example.munchkinhelper.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.dialogs.FullScreenDialog

class UserDataDialog(context: Context, startAnimationView: View) : FullScreenDialog(context, startAnimationView) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_user_ingame_settings)

    }
}
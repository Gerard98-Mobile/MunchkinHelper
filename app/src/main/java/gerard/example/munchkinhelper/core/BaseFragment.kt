package gerard.example.munchkinhelper.core

import android.util.Log
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.Theme

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        super.onStart()

        applyThemeColors()
    }

    abstract fun applyThemeColors()
}
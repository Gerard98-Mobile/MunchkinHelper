package gerard.example.munchkinhelper.core

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        applyThemeColors()
    }

    abstract fun applyThemeColors()
}
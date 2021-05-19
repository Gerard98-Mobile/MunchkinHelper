package gerard.example.munchkinhelper.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.Theme

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    @Suppress("UNCHECKED_CAST")
    val binding get() = _binding!! as VB
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onStart() {
        super.onStart()
        applyThemeColors()
    }

    abstract fun applyThemeColors()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
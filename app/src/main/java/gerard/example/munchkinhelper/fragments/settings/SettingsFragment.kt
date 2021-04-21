package gerard.example.munchkinhelper.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GameActivity

class SettingsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = activity as? GameActivity
        activity?.showBottomBar(false)
    }

    override fun onDetach() {
        super.onDetach()
        val activity = activity as? GameActivity
        activity?.showBottomBar(true)
    }
}
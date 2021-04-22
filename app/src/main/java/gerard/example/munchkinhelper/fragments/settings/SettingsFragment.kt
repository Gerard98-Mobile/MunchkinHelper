package gerard.example.munchkinhelper.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.MainActivity
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GameActivity
import kotlinx.android.synthetic.main.settings_fragment.*

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
        context?.let {
            //settings_recycler.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
            settings_recycler.adapter = SettingAdapter(it, Cfg.settings)
        }

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = activity as? GameActivity
        activity?.hideToolbarButtons()
        activity?.setToolbarTitle("Ustawienia")
    }

    override fun onDetach() {
        super.onDetach()
        val activity = activity as? GameActivity
        activity?.setToolbar()
    }
}
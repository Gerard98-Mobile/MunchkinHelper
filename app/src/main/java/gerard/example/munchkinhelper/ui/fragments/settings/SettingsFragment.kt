package gerard.example.munchkinhelper.ui.fragments.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.dialogs.YesNoDialog
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.ui.fragments.game.GameFragment
import gerard.example.munchkinhelper.util.NavigationHelper
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment(){

    var game : Game? = null

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
            settings_recycler.adapter = SettingAdapter(it, Cfg.settings)
        }

        game = arguments?.get(GAME_KEY) as Game

        reset_game.setOnClickListener {
            context?.let { ctx ->
                YesNoDialog(
                    ctx,
                    R.string.reset_game_title,
                    R.string.reset_game_body
                ){ value, _ ->
                    when(value){
                        true -> {
                            game?.reset()
                            activity?.onBackPressed()
                        }
                    }
                }.show()
            }
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

    companion object{
        fun newInstance(game: Game?) : SettingsFragment {
            return SettingsFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }
}
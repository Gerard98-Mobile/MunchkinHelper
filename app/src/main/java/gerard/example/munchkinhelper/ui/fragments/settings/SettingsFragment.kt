package gerard.example.munchkinhelper.ui.fragments.settings

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.core.dialogs.YesNoDialog
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlin.math.hypot

class SettingsFragment : BaseFragment(){

    var game : Game? = null

    var adapter : SettingAdapter? = null

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
            adapter = SettingAdapter(it, Cfg.settings){ value, _ ->
                if(value.value == Cfg.darkMode.value){
                    CfgTheme.themeChanged()
                    changeTheme()
                }
            }
            settings_recycler.adapter = adapter
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

    override fun applyThemeColors() {
        adapter = context?.let {
            SettingAdapter(it, Cfg.settings){ value, _ ->
                if(value.value == Cfg.darkMode.value){
                    CfgTheme.themeChanged()
                    changeTheme()
                }
            }
        }
        reset_game.applyTheme()
        settings_recycler.adapter = adapter
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

    fun changeTheme(){
        (activity as? GameActivity)?.changeTheme { applyThemeColors() }
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
package gerard.example.munchkinhelper.ui.activity.create

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.model.Player
import com.google.android.material.snackbar.Snackbar
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.databinding.ActivityAddingPlayersBinding
import gerard.example.munchkinhelper.databinding.DialogAddSchemeBinding
import gerard.example.munchkinhelper.model.Scheme
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.Callback
import gerard.example.munchkinhelper.util.NavigationHelper
import gerard.example.munchkinhelper.util.now

const val START_POWER = 0
const val START_LVL = 1

class AddingPlayersActivity : BaseActivity() {

    private lateinit var binding : ActivityAddingPlayersBinding

    val viewmodel by viewModels<AddingPlayersVM>()
    val playerList = mutableListOf<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddingPlayersBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener {
            NavigationHelper.finish(this)
        }


        val adapter = AddedPlayersAdapter(this, playerList)
        binding.recyclerViewNewPlayers.adapter = adapter

        binding.namePlayer.setOnKeyListener { v, keyCode, event ->
            if(event.keyCode == KeyEvent.KEYCODE_ENTER){
                if(event.action == KeyEvent.ACTION_DOWN) binding.addPlayer.performClick()
                return@setOnKeyListener true
            }
            false
        }

        binding.btnStartGame.setOnClickListener {
            if(playerList.size < 2) {
                Snackbar.make(findViewById(android.R.id.content), R.string.player_count_error, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            when(binding.checkboxScheme.isChecked){
                true -> {
                    SchemeNameDialog(this) { name, _ ->
                        viewmodel.insertScheme(Scheme(playerList, name))
                        startGameIntent()
                    }.show()
                }
                false -> startGameIntent()
            }
        }


        binding.addPlayer.setOnClickListener {
            val playerName = binding.namePlayer.text.toString()
            if(playerName.length in 1..20) {
                playerList.add(0, Player(binding.namePlayer.text.toString(), START_POWER, START_LVL))
                (binding.recyclerViewNewPlayers.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0,0)
                adapter.notifyItemInserted(0)
                binding.namePlayer.setText("")
            }else{
                Snackbar.make(findViewById(android.R.id.content), R.string.name_lenght_error, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        NavigationHelper.finish(this)
    }

    override fun applyThemeColors() {
        CfgTheme.current.primaryColor.colorInt(this).let{ color ->
            binding.run {
                toolbar.navigationIcon?.setTint(color)
                toolbar.setTitleTextColor(color)
                checkboxScheme.setTextColor(color)
                separator.setBackgroundColor(color)
                namePlayer.setTextColor(color)
            }
        }

        with(CfgTheme.current.primaryColor.colorStateList(this)){
            binding.checkboxScheme.buttonTintList = this
            binding.addPlayer.imageTintList = this
        }

        binding.toolbar.setBackgroundColor(CfgTheme.current.appBarBackground.colorInt(this))
        binding.root.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))

        binding.namePlayer.setHintTextColor(CfgTheme.current.textLight.colorInt(this))
        //binding.btnStartGame.applyTheme()

        binding.recyclerViewNewPlayers.adapter?.notifyDataSetChanged()
    }

    private fun startGameIntent() {
        NavigationHelper.startActivity(this, GameActivity::class.java, Game(now(), playerList))
    }

    override fun onResume() {
        super.onResume()
        binding.namePlayer.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        binding.namePlayer.clearFocus()
    }

    // function show dialog for get scheme name from user
    class SchemeNameDialog(context: Context, val callback: Callback<String>) : Dialog(context) {

        private lateinit var binding : DialogAddSchemeBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DialogAddSchemeBinding.inflate(layoutInflater)
            val view = binding.root
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(view)

            binding.yes.setOnClickListener {
                if(binding.schemeName.text.toString().length < 4) {
                    binding.schemeName.background = ContextCompat.getDrawable(context, R.drawable.edit_text_error)
                    return@setOnClickListener
                }
                callback.execute(binding.schemeName.text.toString(), Action.NONE)
                dismiss()
            }
            binding.cancel.setOnClickListener { dismiss() }

            applyTheme()
        }

        private fun applyTheme(){
            binding.run {
                CfgTheme.current.primaryColor.colorInt(context).let {
                    title.setBackgroundColor(it)
                    schemeName.setTextColor(it)
                    cancel.setTextColor(it)
                    yes.setTextColor(it)
                }

                CfgTheme.current.backgroundColor.colorInt(context).let {
                    root.setCardBackgroundColor(it)
                }

                schemeName.setHintTextColor(CfgTheme.current.textLight.colorInt(context))
                title.setTextColor(CfgTheme.current.textColorSecondary.colorInt(context))
            }
        }
    }
}


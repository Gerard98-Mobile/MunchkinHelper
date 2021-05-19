package gerard.example.munchkinhelper.ui.activity


import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.core.dialogs.YesNoDialog
import gerard.example.munchkinhelper.databinding.ActivityGameBinding
import gerard.example.munchkinhelper.ui.fragments.home.HomeFragment
import gerard.example.munchkinhelper.ui.fragments.settings.SettingsFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.util.NavigationHelper
import kotlin.math.hypot

val GAME_KEY = "game_key"

class GameActivity : BaseActivity(true) {

    private lateinit var binding: ActivityGameBinding

    var game : Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setToolbar()

        game = intent.getSerializableExtra(GAME_KEY) as Game?
        changeFragment(HomeFragment.newInstance(game))
    }

    fun setToolbar() = binding.run {

        setToolbarTitle(getString(R.string.app_name))
        if(Cfg.autoSave.value.get() == true){
            toolbarAutosave.visibility = View.GONE
        }
        else{
            toolbarAutosave.visibility = View.VISIBLE
        }
        toolbarSettings.visibility = View.VISIBLE
        toolbarBack.visibility = View.GONE
        toolbarSettings.setOnClickListener { changeFragment(SettingsFragment.newInstance(game)) }
        toolbarBack.setOnClickListener { onBackPressed() }
    }

    private fun changeFragment(fragment: Fragment) {
        NavigationHelper.changeFragment(supportFragmentManager, fragment)
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if(count > 1){
            super.onBackPressed()
            return
        }

        YesNoDialog(
            this,
            R.string.back_pressed_title,
            R.string.back_pressed_body
        ){ value, _ ->
            when(value){
                true -> NavigationHelper.finish(this@GameActivity)
            }
        }.show()
    }

    override fun applyThemeColors() = binding.run {
        window.statusBarColor = CfgTheme.current.appBarBackground.colorInt(this@GameActivity)
        container.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this@GameActivity))
        navigation.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this@GameActivity))
        toolbar.setBackgroundColor(CfgTheme.current.appBarBackground.colorInt(this@GameActivity))
        toolbarText.setTextColor(CfgTheme.current.primaryColor.colorInt(this@GameActivity))
        CfgTheme.current.primaryColor.colorStateList(this@GameActivity).let {
            toolbarAutosave.imageTintList = it
            toolbarSettings.imageTintList = it
            toolbarBack.imageTintList = it
        }
    }

    fun changeTheme(a: () -> Unit) = binding.run{

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h.toInt(), Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)
        container.draw(canvas)

        imageView.setImageBitmap(bitmap)
        imageView.isVisible = true

        applyThemeColors()
        a()

        val finalRadius = hypot(w.toDouble(), h * 1.1)

        val anim = ViewAnimationUtils.createCircularReveal(container, w / 2, h / 2, 0f, finalRadius.toFloat())
        anim.interpolator = AccelerateInterpolator()

        anim.duration = 600L
        anim.doOnEnd {
            setStatusBarColors()
            imageView.setImageDrawable(null)
            imageView.isVisible = false
        }
        anim.start()
    }

    fun setToolbarTitle(text: String){
        binding.toolbarText.text = text
    }

    fun hideToolbarButtons() = binding.run {
        toolbarBack.visibility = View.VISIBLE
        toolbarSettings.visibility = View.GONE
        toolbarAutosave.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        game?.let { Cfg.lastGame.set(it) }
    }

}
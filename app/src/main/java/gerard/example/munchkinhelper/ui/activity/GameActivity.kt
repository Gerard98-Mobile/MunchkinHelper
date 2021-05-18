package gerard.example.munchkinhelper.ui.activity


import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.*
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.core.SharedValueImpl
import gerard.example.munchkinhelper.core.dialogs.YesNoDialog
import gerard.example.munchkinhelper.ui.fragments.home.HomeFragment
import gerard.example.munchkinhelper.ui.fragments.settings.SettingsFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.util.NavigationHelper
import gerard.example.munchkinhelper.util.SoundHelper
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.dialog_custom.*
import kotlin.math.hypot

val GAME_KEY = "game_key"

class GameActivity : BaseActivity() {

    var game : Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setToolbar()

        game = intent.getSerializableExtra(GAME_KEY) as Game?
        changeFragment(HomeFragment.newInstance(game))
    }

    fun setToolbar() {
        setToolbarTitle(getString(R.string.app_name))
        if(Cfg.autoSave.value.get() == true){
            toolbar_autosave.visibility = View.GONE
        }
        else{
            toolbar_autosave.visibility = View.VISIBLE
        }
        toolbar_settings.visibility = View.VISIBLE
        toolbar_back.visibility = View.GONE
        toolbar_settings.setOnClickListener { changeFragment(SettingsFragment.newInstance(game)) }
        toolbar_back.setOnClickListener { onBackPressed() }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
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

    override fun applyThemeColors() {
        window.statusBarColor = CfgTheme.current.appBarBackground.colorInt(this)
        container.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))
        navigation.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this))
        toolbar.setBackgroundColor(CfgTheme.current.appBarBackground.colorInt(this))
        toolbar_text.setTextColor(CfgTheme.current.primaryColor.colorInt(this))
        with(CfgTheme.current.primaryColor.colorStateList(this)){
            toolbar_autosave.imageTintList = this
            toolbar_settings.imageTintList = this
            toolbar_back.imageTintList = this
        }
    }

    fun changeTheme(a: () -> Unit){

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
            setStatusBarTextColor()
            imageView.setImageDrawable(null)
            imageView.isVisible = false
        }
        anim.start()
    }

    fun changeTheme2(a: () -> Unit){


        // get the right and bottom side
        // lengths of the reveal layout
        val x: Int = container.right
        val y: Int = container.bottom

        // here the starting radius of the reveal
        // layout is 0 when it is not visible
        val startRadius = 0

        // make the end radius should match
        // the while parent view
        val endRadius = hypot(
            container.width.toDouble(),
            container.height.toDouble()
        ).toInt()

        applyThemeColors()
        a()

        // create the instance of the ViewAnimationUtils to
        // initiate the circular reveal animation
        val anim = ViewAnimationUtils.createCircularReveal(
            container,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        // make the invisible reveal layout to visible
        // so that upon revealing it can be visible to user
        container.visibility = View.VISIBLE
        // now start the reveal animation
        anim.start()

        // set the boolean value to true as the reveal
        // layout is visible to the user
        //isRevealed = true
    }


    fun setToolbarTitle(text: String){
        toolbar_text.setText(text)
    }

    fun hideToolbarButtons() {
        toolbar_back.visibility = View.VISIBLE
        toolbar_settings.visibility = View.GONE
        toolbar_autosave.visibility = View.GONE
    }


    override fun onPause() {
        super.onPause()
        game?.let { Cfg.lastGame.set(it) }
    }

}
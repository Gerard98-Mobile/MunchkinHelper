package gerard.example.munchkinhelper.core

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import gerard.example.munchkinhelper.*


abstract class BaseActivity : AppCompatActivity() {

    abstract fun applyThemeColors()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Cfg.init(this)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setBackgroundDrawable(CfgTheme.current.appBarBackground.colorDrawable(this))
        supportActionBar?.elevation = 0f
        window.statusBarColor = CfgTheme.current.appBarBackground.colorInt(this)
        setStatusBarTextColor()

        applyThemeColors()
    }

    fun setStatusBarTextColor(){
        if(CfgTheme.current is DefaultTheme) setStatusBarTextColorDark() else setStatusBarTextColorLight()
    }

    private fun setStatusBarTextColorLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                0,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

    }

    private fun setStatusBarTextColorDark() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                APPEARANCE_LIGHT_STATUS_BARS,
                APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        }
    }
}
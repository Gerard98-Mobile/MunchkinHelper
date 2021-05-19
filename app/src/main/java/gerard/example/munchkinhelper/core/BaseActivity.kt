package gerard.example.munchkinhelper.core

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import gerard.example.munchkinhelper.*


abstract class BaseActivity(
    val statusBarFloating : Boolean = false
) : AppCompatActivity() {

    var actualTheme: Theme? = null

    abstract fun applyThemeColors()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Cfg.init(this)

        if(statusBarFloating){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("Event","OnResume BaseActivity")

        if(isThemeChanged()){
            Log.e("Event","Theme changed")
            actualTheme = CfgTheme.current
            setStatusBarColors()
            applyThemeColors()
        }
    }

    private fun isThemeChanged() : Boolean {
        return actualTheme != CfgTheme.current
    }

    fun setStatusBarColors(){
        window.statusBarColor = CfgTheme.current.appBarBackground.colorInt(this)

        if(CfgTheme.current is ThemeLight) setStatusBarTextColorDark() else setStatusBarTextColorLight()
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
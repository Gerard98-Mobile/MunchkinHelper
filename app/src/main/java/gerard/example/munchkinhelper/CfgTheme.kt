package gerard.example.munchkinhelper

import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

object CfgTheme {
    var current = selectAppTheme()

    private fun selectAppTheme() = when(Cfg.darkMode.value.get() == false){
        true -> DefaultTheme()
        false -> DarkTheme()
    }

    fun themeChanged(){
        current = selectAppTheme()
    }

    fun actual(): String {
        return when(current){
            is DefaultTheme -> "Default"
            is DarkTheme -> "Dark"
        }
    }

}

fun pickLayout(default: Int, dark: Int) : Int{
    return if(Cfg.darkMode.value.get() == false) default else dark
}

sealed class Theme(
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColorSecondary: Int,
    @ColorRes val textLight: Int,
    @ColorRes val appBarBackground: Int,
    @ColorRes val primaryColor: Int,
    @DrawableRes val stateChecked: Int
)

class DefaultTheme : Theme(
    backgroundColor = R.color.colorBackground,
    textColorSecondary = R.color.colorWhite,
    appBarBackground = R.color.colorBackground,
    textLight = R.color.colorGrey,
    primaryColor = R.color.colorPrimary,
    stateChecked = R.drawable.state_checked
)

class DarkTheme : Theme(
    backgroundColor = R.color.backgroundDark,
    textColorSecondary = R.color.backgroundDark,
    appBarBackground = R.color.backgroundDark,
    textLight = R.color.textDarkLight,
    primaryColor = R.color.colorWhite,
    stateChecked = R.drawable.state_checked_dark
)
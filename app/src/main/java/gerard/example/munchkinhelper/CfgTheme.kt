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

}

sealed class Theme(
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColorSecondary: Int,
    @ColorRes val textLight: Int,
    @ColorRes val appBarBackground: Int,
    @ColorRes val primaryColor: Int,
    @ColorRes val switchState: Int,
    @ColorRes val switchTrack: Int,
    @DrawableRes val stateChecked: Int
)

interface ThemeLight
interface ThemeDark

class DefaultTheme : ThemeLight, Theme(
    backgroundColor = R.color.colorBackground,
    textColorSecondary = R.color.colorWhite,
    appBarBackground = R.color.colorBackground,
    textLight = R.color.textDarkLight,
    primaryColor = R.color.colorPrimary,
    switchState = R.color.switch_state,
    switchTrack = R.color.colorGrey,
    stateChecked = R.drawable.state_checked
)

class DarkTheme : ThemeDark, Theme(
    backgroundColor = R.color.backgroundDark,
    textColorSecondary = R.color.backgroundDark,
    appBarBackground = R.color.backgroundDark,
    textLight = R.color.textDarkLight,
    primaryColor = R.color.colorWhite,
    switchState = R.color.switch_state_dark,
    switchTrack = R.color.colorGrey,
    stateChecked = R.drawable.state_checked_dark
)
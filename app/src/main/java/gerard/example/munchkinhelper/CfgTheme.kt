package gerard.example.munchkinhelper

import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

object CfgTheme {

    private val listeners = mutableSetOf<ThemeChangedListener>()

    var current = selectAppTheme()
        set(value){
            field = value
            listeners.forEach{ listener -> listener.onThemeChanged(value) }
        }

    interface ThemeChangedListener{
        fun onThemeChanged(theme: Theme)
    }

    private fun selectAppTheme() = when(Cfg.darkMode.value.get() == false){
        true -> DefaultTheme()
        false -> DarkTheme()
    }

    fun themeChanged(){
        current = selectAppTheme()
    }

    fun addListener(listener: ThemeChangedListener){
        listeners.add(listener)
    }

    fun removeListener(listener: ThemeChangedListener){
        listeners.remove(listener)
    }

}

class BooleanSetting(
    @ColorRes val textColor: Int,
    @ColorRes val imageTint: Int,
    @ColorRes val thumbTint: Int,
    @ColorRes val trackTint: Int
)

class RoundedButtonTheme(
    @ColorRes val textColor: Int,
    @ColorRes val cardBackgroundColor: Int
)

class TextViewTheme(
    @ColorRes val textColor: Int
)

sealed class Theme(
    @ColorRes val backgroundColor: Int,
    @ColorRes val textColorSecondary: Int,
    @ColorRes val textLight: Int,
    @ColorRes val appBarBackground: Int,
    @ColorRes val primaryColor: Int,
    @ColorRes val switchState: Int,
    @ColorRes val switchTrack: Int,
    @DrawableRes val stateChecked: Int,
    val roundedButtonTheme: RoundedButtonTheme,
    val textViewTheme: TextViewTheme,
    val booleanSetting: BooleanSetting
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
    stateChecked = R.drawable.state_checked,
    roundedButtonTheme = RoundedButtonTheme(
        textColor = R.color.colorWhite,
        cardBackgroundColor = R.color.colorPrimary
    ),
    textViewTheme = TextViewTheme(R.color.colorPrimary),
    booleanSetting = BooleanSetting(
        textColor = R.color.colorPrimary,
        imageTint = R.color.colorPrimary,
        thumbTint = R.color.switch_state,
        trackTint = R.color.colorGrey,
    )
)

class DarkTheme : ThemeDark, Theme(
    backgroundColor = R.color.backgroundDark,
    textColorSecondary = R.color.backgroundDark,
    appBarBackground = R.color.backgroundDark,
    textLight = R.color.textDarkLight,
    primaryColor = R.color.colorWhite,
    switchState = R.color.switch_state_dark,
    switchTrack = R.color.colorGrey,
    stateChecked = R.drawable.state_checked_dark,
    roundedButtonTheme = RoundedButtonTheme(
        textColor = R.color.backgroundDark,
        cardBackgroundColor = R.color.colorWhite
    ),
    textViewTheme = TextViewTheme(R.color.colorWhite),
    booleanSetting = BooleanSetting(
        textColor = R.color.colorWhite,
        imageTint = R.color.colorWhite,
        thumbTint = R.color.switch_state_dark,
        trackTint = R.color.colorGrey,
    )
)
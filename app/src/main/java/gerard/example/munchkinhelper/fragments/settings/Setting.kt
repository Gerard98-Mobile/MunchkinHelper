package gerard.example.munchkinhelper.fragments.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import gerard.example.munchkinhelper.core.SharedValue

class Setting<T>(
    @StringRes val displayName: Int,
    sharedValue: SharedValue<T>,
    @DrawableRes val displayIcon: Int? = null
){
    val value: SharedValue<T> = sharedValue
}
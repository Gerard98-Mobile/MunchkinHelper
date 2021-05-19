package gerard.example.munchkinhelper.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY

object NavigationHelper {

    enum class AnimDestination(@AnimRes val animIn: Int, @AnimRes val animOut: Int){
        RIGHT(R.anim.slide_in_right, R.anim.slide_out_right),
        LEFT(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun <T> startActivity(activity: Activity, clazz: Class<T>, game: Game){
        val bundle = Bundle()
        bundle.putSerializable(GAME_KEY, game)
        startActivity(activity, clazz, bundle)
    }

    fun <T> startActivity(activity: Activity, clazz: Class<T>, params: Bundle? = null, animDestination: AnimDestination = AnimDestination.LEFT){
        val intent = Intent(activity, clazz)
        params?.let { intent.putExtras(params) }
        activity.startActivity(intent)
        activity.overridePendingTransition(animDestination.animIn, animDestination.animOut)
    }

    fun changeFragment(fragmentManager: FragmentManager, fragment: Fragment){
        fragmentManager
            .beginTransaction()
            .setCustomAnimations(AnimDestination.LEFT.animIn, AnimDestination.LEFT.animOut, AnimDestination.RIGHT.animIn, AnimDestination.RIGHT.animOut)
            .replace(R.id.frame, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun finish(activity: Activity){
        with(activity){
            this.finish()
            // enter, exit
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }
}
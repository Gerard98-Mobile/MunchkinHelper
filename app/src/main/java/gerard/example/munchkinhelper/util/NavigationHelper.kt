package gerard.example.munchkinhelper.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY

object NavigationHelper {

    fun <T> startActivity(activity: Activity, clazz: Class<T>, game: Game){
        val bundle = Bundle()
        bundle.putSerializable(GAME_KEY, game)
        startActivity(activity, clazz, bundle)
    }

    fun <T> startActivity(activity: Activity, clazz: Class<T>, params: Bundle? = null){
        val intent = Intent(activity, clazz)
        params?.let { intent.putExtras(params) }
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun finish(activity: Activity){
        with(activity){
            this.finish()
            // enter, exit
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
        }
    }
}
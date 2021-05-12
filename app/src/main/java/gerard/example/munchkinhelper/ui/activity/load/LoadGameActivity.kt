package gerard.example.munchkinhelper.ui.activity.load

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import kotlinx.android.synthetic.main.activity_load_game_with_vp.*

class LoadGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_game_with_vp)

        load_game_viewpager.adapter = LoadGameVPAdapter(this)

        TabLayoutMediator(load_game_tab_layout, load_game_viewpager) { tab, position ->
            tab.text = if(position == 0) getString(R.string.history) else getString(R.string.schemes)
        }.attach()

        btn_startNewGame.setOnClickListener {
            val intent = Intent(this, AddingPlayersActivity::class.java)
            startActivity(intent)
        }

        load_game_viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btn_startNewGame.animate().translationY(0f)
            }
        })
    }

    var startPostion : Float? = null

    fun changePositionOfBtn(newPosition: Float){
        if(startPostion == null) startPostion = btn_startNewGame.y

        startPostion?.let {
            if(btn_startNewGame.y + newPosition < it) return
        }

        btn_startNewGame.y = btn_startNewGame.y + newPosition
    }


}
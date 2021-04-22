package gerard.example.munchkinhelper.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.fragments.GameFragment
import gerard.example.munchkinhelper.model.Game
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = arguments?.getSerializable(GAME_KEY) as Game

        home_view_pager.adapter = ViewPagerAdapter(game, this)

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_game -> home_view_pager.setCurrentItem(0,true)
                R.id.menu_dice -> home_view_pager.setCurrentItem(1,true)
                R.id.menu_fight -> home_view_pager.setCurrentItem(2,true)
            }
            true
        }

        home_view_pager.registerOnPageChangeCallback(object : OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0 -> navigation.selectedItemId = R.id.menu_game
                    1 -> navigation.selectedItemId = R.id.menu_dice
                    2 -> navigation.selectedItemId = R.id.menu_fight
                }
            }
        })
    }


    companion object{
        fun newInstance(game: Game?) : HomeFragment {
            return HomeFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }
}
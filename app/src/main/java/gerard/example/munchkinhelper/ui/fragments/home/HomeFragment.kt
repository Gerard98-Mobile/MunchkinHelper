package gerard.example.munchkinhelper.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.colorStateList
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.HomeFragmentBinding
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.model.Game

class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding
            = HomeFragmentBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = arguments?.getSerializable(GAME_KEY) as Game

        binding.homeViewPager.adapter = ViewPagerAdapter(game, this)
        binding.homeViewPager.isUserInputEnabled = false


        binding. navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_game -> binding.homeViewPager.setCurrentItem(0,true)
                R.id.menu_dice -> binding.homeViewPager.setCurrentItem(1,true)
                R.id.menu_fight -> binding.homeViewPager.setCurrentItem(2,true)
            }
            true
        }

        binding.homeViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when(position){
                    0 -> binding.navigation.selectedItemId = R.id.menu_game
                    1 -> {
                        binding.homeViewPager.isUserInputEnabled = false
                        binding.navigation.selectedItemId = R.id.menu_dice
                    }
                    2 -> binding.navigation.selectedItemId = R.id.menu_fight
                }
            }
        })
    }

    override fun applyThemeColors() : Unit = binding.run{
        context?.let {
            navigation.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(it))
            navigation.itemIconTintList = CfgTheme.current.stateChecked.colorStateList(it)
            navigation.itemTextColor = CfgTheme.current.stateChecked.colorStateList(it)
        }
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
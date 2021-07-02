package gerard.example.munchkinhelper.ui.fragments.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import gerard.example.munchkinhelper.Cfg
import gerard.example.munchkinhelper.ui.fragments.DiceFragment
import gerard.example.munchkinhelper.ui.fragments.game.GameFragment
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.fragments.fight.FightContainerFragment

class ViewPagerAdapter(
    val game: Game,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> DiceFragment()
            2 -> FightContainerFragment.newInstance(game)
            else -> GameFragment.newInstance(game)
        }
    }
}
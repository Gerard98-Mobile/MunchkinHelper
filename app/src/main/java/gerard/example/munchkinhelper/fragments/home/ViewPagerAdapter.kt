package gerard.example.munchkinhelper.fragments.home

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import gerard.example.munchkinhelper.fragments.DiceFragment
import gerard.example.munchkinhelper.fragments.FightFragment
import gerard.example.munchkinhelper.fragments.GameFragment
import gerard.example.munchkinhelper.model.Game

class ViewPagerAdapter(
    val game: Game,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            1 -> return DiceFragment()
            2 -> return FightFragment.newInstance(game)
            else -> return GameFragment.newInstance(game)
        }
    }
}
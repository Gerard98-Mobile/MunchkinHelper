package gerard.example.munchkinhelper.ui.activity.load

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import gerard.example.munchkinhelper.ui.fragments.fight.FightFragment
import gerard.example.munchkinhelper.ui.fragments.fight.SelectFightersFragment

class LoadGameVPAdapter(parent: FragmentActivity) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> HistoryGamesFragment()
            else -> SchemeFragment()
        }
    }
}
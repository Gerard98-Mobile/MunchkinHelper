package gerard.example.munchkinhelper.ui.fragments.fight

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

val FIGHTERS_KEY = "fighters_key"

class FightViewPagerAdapter(
    val fighters: SelectFightersFragment.FighterModelParcelable,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return SelectFightersFragment.newInstance(fighters)
            else -> return FightFragment.newInstance()
        }
    }
}
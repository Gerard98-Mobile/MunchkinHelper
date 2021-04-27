package gerard.example.munchkinhelper.ui.fragments.fight

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

val FIGHTERS_KEY = "fighters_key"

class FightViewPagerAdapter(
    val fighters: SelectFightersFragment.FighterModelParcelable,
    parent: Fragment
) : FragmentStateAdapter(parent) {

    val selectFightersFragment = SelectFightersFragment.newInstance(fighters)
    val fightFragment = FightFragment2.newInstance()

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return selectFightersFragment
            else -> return fightFragment
        }
    }

    fun reset(){
        selectFightersFragment.reset()
        fightFragment.reset()
    }

    fun fightersPowerChanged(newPower: Int) {
        fightFragment.fightersPowerChanged(newPower)
    }
}
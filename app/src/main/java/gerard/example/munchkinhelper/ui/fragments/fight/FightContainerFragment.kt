package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.util.AnimationUtil
import kotlinx.android.synthetic.main.fragment_fight_container.*

class FightContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fight_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = arguments?.getSerializable(GAME_KEY) as Game
        val fighters = mutableListOf<SelectFightersFragment.FighterModel>()
        game.players.forEach { fighters.add(SelectFightersFragment.FighterModel(it, false)) }

        val adapter = FightViewPagerAdapter(
            SelectFightersFragment.FighterModelParcelable(fighters),
            this
        )

        fight_view_pager.adapter = adapter

        fight_view_pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(position == 1){
                    AnimationUtil.animateAlpha(1.0f, 300, back)
                }
                else{
                    AnimationUtil.animateAlpha(0.0f, 300, back)
                }
            }
        })

        reset.setOnClickListener {
            adapter.reset()
        }

        back.setOnClickListener {
            changeFragment(0)
        }
    }

    fun changeFragment(i: Int) {
        fight_view_pager.setCurrentItem(i, true)
    }

    fun fightersPowerChanged(newPower: Int){
        (fight_view_pager.adapter as? FightViewPagerAdapter)?.fightersPowerChanged(newPower)
    }

    companion object{
        fun newInstance(game: Game) : FightContainerFragment {
            return FightContainerFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }
}
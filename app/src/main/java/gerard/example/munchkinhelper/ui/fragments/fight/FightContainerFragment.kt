package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = arguments?.getSerializable(GAME_KEY) as Game
        val fighters = mutableListOf<SelectFightersFragment.FighterModel>()
        game.players.forEach { fighters.add(SelectFightersFragment.FighterModel(it, false)) }

        model.fighters = fighters
        val adapter = FightViewPagerAdapter(this)

        fight_view_pager.adapter = adapter

        fight_view_pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                AnimationUtil.animateAlpha(back, position.toFloat(), 300)
            }
        })

        reset.setOnClickListener {
            changeFragment(0)
            model.reset()
        }

        back.setOnClickListener {
            changeFragment(0)
        }

    }

    fun changeFragment(i: Int) {
        fight_view_pager.setCurrentItem(i, true)
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
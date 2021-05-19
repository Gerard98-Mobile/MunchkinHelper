package gerard.example.munchkinhelper.ui.fragments.fight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.FragmentFightContainerBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.util.AnimationUtil


class FightContainerFragment : BaseFragment<FragmentFightContainerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFightContainerBinding
            = FragmentFightContainerBinding::inflate

    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = arguments?.getSerializable(GAME_KEY) as Game
        val fighters = mutableListOf<SelectFightersFragment.FighterModel>()
        game.players.forEach { fighters.add(SelectFightersFragment.FighterModel(it, false)) }

        model.fighters = fighters
        val adapter = FightViewPagerAdapter(this)

        binding.fightViewPager.adapter = adapter

        binding.fightViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                AnimationUtil.animateAlpha(binding.back, position.toFloat(), 300)
            }
        })

        binding.reset.setOnClickListener {
            changeFragment(0)
            model.reset()
        }

        binding.back.setOnClickListener {
            changeFragment(0)
        }

    }

    override fun applyThemeColors() {
        context?.let {
            with(CfgTheme.current.primaryColor.colorInt(it)){
                binding.back.setTextColor(this)
                binding.reset.setTextColor(this)
            }
        }

    }

    fun changeFragment(i: Int) {
        binding.fightViewPager.setCurrentItem(i, true)
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
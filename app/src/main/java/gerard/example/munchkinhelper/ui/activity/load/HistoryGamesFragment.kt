package gerard.example.munchkinhelper.ui.activity.load

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.FragmentGamesHistoryBinding
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.NavigationHelper

class HistoryGamesFragment : BaseFragment<FragmentGamesHistoryBinding>(){

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGamesHistoryBinding
            = FragmentGamesHistoryBinding::inflate

    private val viewmodel by viewModels<HistoryGamesVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.loadAllObjects()

        viewmodel.games.observe(viewLifecycleOwner, {
            Log.e("TAG", "History games live data")
            val adapter = HistoryGameAdapter(view.context, it) { game, action ->
                when(action){
                    HistoryGameAdapter.Action.DELETE -> {
                        viewmodel.removeGame(game)
                    }
                    HistoryGameAdapter.Action.OPEN -> {
                        activity?.let {
                            NavigationHelper.startActivity(it, GameActivity::class.java, game)
                        }

                    }
                }



            }
            binding.historyRecycler.adapter = adapter
        })

        binding.historyRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (activity as? LoadGameActivity)?.changePositionOfBtn(dy.toFloat())
            }
        })
    }

    override fun applyThemeColors() {
        binding.historyRecycler.adapter?.notifyDataSetChanged()
    }

}
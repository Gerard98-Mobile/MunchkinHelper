package gerard.example.munchkinhelper.ui.activity.load

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.ui.activity.GAME_KEY
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.NavigationHelper
import kotlinx.android.synthetic.main.fragment_games_history.*

class HistoryGamesFragment : Fragment(){

    private val viewmodel by viewModels<HistoryGamesVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_games_history, container, false)
    }

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
            history_recycler.adapter = adapter
        })

        history_recycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (activity as? LoadGameActivity)?.changePositionOfBtn(dy.toFloat())
            }
        })
    }
}
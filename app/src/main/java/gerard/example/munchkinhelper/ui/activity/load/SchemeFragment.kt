package gerard.example.munchkinhelper.ui.activity.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.NavigationHelper
import gerard.example.munchkinhelper.util.now
import kotlinx.android.synthetic.main.fragment_scheme.*

class SchemeFragment : Fragment(){

    val viewmodel by viewModels<SchemeVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scheme, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.loadAllObjects()

        viewmodel.schemes.observe(viewLifecycleOwner, {
            val adapter = SchemesAdapter(view.context, it) { scheme, action ->
                when(action){
                    Action.DELETE -> viewmodel.removeScheme(scheme)
                    Action.OPEN -> {
                        activity?.let {
                            NavigationHelper.startActivity(it, GameActivity::class.java, Game(now(), scheme.players))
                        }
                    }
                }
            }
            schemes_recycler_view.adapter = adapter
        })

        schemes_recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (activity as? LoadGameActivity)?.changePositionOfBtn(dy.toFloat())
            }
        })
    }
}
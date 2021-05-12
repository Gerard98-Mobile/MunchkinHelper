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
            val adapter = SchemesAdapter(view.context, it) { scheme -> viewmodel.removeScheme(scheme) }
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
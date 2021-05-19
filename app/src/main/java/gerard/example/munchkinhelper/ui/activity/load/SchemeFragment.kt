package gerard.example.munchkinhelper.ui.activity.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.core.BaseFragment
import gerard.example.munchkinhelper.databinding.FragmentSchemeBinding
import gerard.example.munchkinhelper.model.Game
import gerard.example.munchkinhelper.ui.activity.GameActivity
import gerard.example.munchkinhelper.util.Action
import gerard.example.munchkinhelper.util.NavigationHelper
import gerard.example.munchkinhelper.util.now

class SchemeFragment : BaseFragment(){

    private var _binding: FragmentSchemeBinding? = null
    private val binding get() = _binding!!

    val viewmodel by viewModels<SchemeVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSchemeBinding.inflate(inflater, container, false)
        return binding.root
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
            binding.schemesRecyclerView.adapter = adapter
        })

        binding.schemesRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (activity as? LoadGameActivity)?.changePositionOfBtn(dy.toFloat())
            }
        })
    }

    override fun applyThemeColors() {
        binding.schemesRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
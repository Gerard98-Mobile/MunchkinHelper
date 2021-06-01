package gerard.example.munchkinhelper.ui.activity.load

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import gerard.example.munchkinhelper.CfgTheme
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.colorInt
import gerard.example.munchkinhelper.core.BaseActivity
import gerard.example.munchkinhelper.databinding.ActivityLoadGameBinding
import gerard.example.munchkinhelper.ui.activity.create.AddingPlayersActivity
import gerard.example.munchkinhelper.util.NavigationHelper

class LoadGameActivity : BaseActivity() {

    private lateinit var binding : ActivityLoadGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener {
            NavigationHelper.finish(this)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.loadGameViewpager.adapter = LoadGameVPAdapter(this)

        TabLayoutMediator(binding.loadGameTabLayout, binding.loadGameViewpager) { tab, position ->
            tab.text = if(position == 0) getString(R.string.history) else getString(R.string.schemes)
        }.attach()

        binding.btnStartNewGame.setOnClickListener {
            NavigationHelper.startActivity(this, AddingPlayersActivity::class.java)
        }

        binding.loadGameViewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnStartNewGame.animate().translationY(0f)
            }
        })
    }

    override fun onBackPressed() {
        NavigationHelper.finish(this)
    }

    override fun applyThemeColors() {
        binding.run {
            CfgTheme.current.primaryColor.colorInt(this@LoadGameActivity).let {
                toolbar.navigationIcon?.setTint(it)
                toolbar.setTitleTextColor(it)
            }

            toolbar.setBackgroundColor(CfgTheme.current.appBarBackground.colorInt(this@LoadGameActivity))

            CfgTheme.current.primaryColor.colorInt(this@LoadGameActivity).let{
                loadGameTabLayout.setSelectedTabIndicatorColor(it)
                loadGameTabLayout.setTabTextColors(R.color.darkGrey.colorInt(this@LoadGameActivity), it)
            }
            root.setBackgroundColor(CfgTheme.current.backgroundColor.colorInt(this@LoadGameActivity))
            //btnStartNewGame.applyTheme()
        }

    }

    var startPostion : Float? = null
    fun changePositionOfBtn(newPosition: Float){
        if(startPostion == null) startPostion = binding.btnStartNewGame.y

        startPostion?.let {
            if(binding.btnStartNewGame.y + newPosition < it) return
        }

        binding.btnStartNewGame.y = binding.btnStartNewGame.y + newPosition
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> NavigationHelper.finish(this)
        }
        return super.onOptionsItemSelected(item)
    }


}
package com.example.munchkinhelper

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkinhelper.adapters.PlayerAdapter
import com.example.munchkinhelper.fragments.DiceFragment
import com.example.munchkinhelper.fragments.FightFragment
import com.example.munchkinhelper.fragments.GameFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class GameActivity : AppCompatActivity() {

    var navigation : BottomNavigationView? = null
    var actuallyFragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val withPower = this.intent.getBooleanExtra(KEY_EXTRA_WITH_POWER, false)

        actuallyFragment = GameFragment(withPower)
        actuallyFragment?.let {
            val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, it)
            transaction.commit()
        }

        navigation = findViewById(R.id.navigation)
        navigation!!.setOnNavigationItemSelectedListener{
            val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.menu_game -> {
                    actuallyFragment = GameFragment(withPower)
                    actuallyFragment?.let {
                        transaction.replace(R.id.frame, it)
                        transaction.commit()
                    }


                }
                R.id.menu_dice -> {
                    actuallyFragment = DiceFragment()
                    actuallyFragment?.let {
                        transaction.replace(R.id.frame, it)
                        transaction.commit()
                    }
                }
                R.id.menu_fight -> {
                    actuallyFragment = FightFragment()
                    actuallyFragment?.let {
                        transaction.replace(R.id.frame, it)
                        transaction.commit()
                    }
                }
            }
            true
        }

        // stare



    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)

        with(builder){
            setTitle(R.string.back_pressed_title)
            setPositiveButton(R.string.yes, { dialog,which ->
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            })
            setNegativeButton(R.string.no,{ dialog,which ->
                // nothing
            })

            show()
        }
    }

}
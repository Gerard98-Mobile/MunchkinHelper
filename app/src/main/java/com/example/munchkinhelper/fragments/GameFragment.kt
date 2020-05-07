package com.example.munchkinhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.munchkinhelper.R
import com.example.munchkinhelper.adapters.PlayerAdapter

class GameFragment(val withPower: Boolean) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.game_fragment, container, false)

        val rightTextView: TextView = v.findViewById(R.id.rightTextView)
        val leftTextView: TextView = v.findViewById(R.id.leftTextView)

        if(withPower){
            rightTextView.setText(R.string.power)
            leftTextView.setText(R.string.level)
        }
        else{
            rightTextView.setText(R.string.level)
            leftTextView.text = ""
        }

        val recyclerView : RecyclerView = v.findViewById(R.id.recycler_view)
        this.context?.let {
            recyclerView.layoutManager = LinearLayoutManager(it)
            recyclerView.adapter = PlayerAdapter(it, withPower)
        }

        return v
    }
}
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
import com.example.munchkinhelper.adapters.PlayerPowerAdapter

class GameFragment(val withPower: Boolean) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.game_fragment, container, false)

        val recyclerView : RecyclerView = v.findViewById(R.id.recycler_view)
        if(withPower){
            this.context?.let {
                recyclerView.layoutManager = LinearLayoutManager(it)
                recyclerView.adapter = PlayerPowerAdapter(it)
            }
        }
        else{
            this.context?.let {
                recyclerView.layoutManager = LinearLayoutManager(it)
                recyclerView.adapter = PlayerAdapter(it)
            }
        }




        return v
    }
}
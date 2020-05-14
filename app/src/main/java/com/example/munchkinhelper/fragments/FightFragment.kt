package com.example.munchkinhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.munchkinhelper.LocalBase
import com.example.munchkinhelper.Player
import com.example.munchkinhelper.R

class FightFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fight_fragment, container, false)

        val firstPlayer : Spinner = v.findViewById(R.id.first_player)

        val players = LocalBase.instance.players
        val playersNames = mutableListOf<String>()

        players.forEach({
            playersNames.add(it.name)
        })

        context?.let {
            val adapter = ArrayAdapter<String>(it,android.R.layout.simple_list_item_1, playersNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            firstPlayer.adapter = adapter
        }





        return v
    }
}
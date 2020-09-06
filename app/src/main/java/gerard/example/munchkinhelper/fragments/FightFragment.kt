package gerard.example.munchkinhelper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import gerard.example.munchkinhelper.model.Player
import gerard.example.munchkinhelper.R
import gerard.example.munchkinhelper.activity.GAME_KEY
import gerard.example.munchkinhelper.model.Game

class FightFragment : Fragment() {

    private var playerPower = 1
    private var monsterPower = 1
    private var player : Player? = null
    private var assistant : Player? = null

    companion object{
        fun newInstance(game: Game) : FightFragment{
            return FightFragment().apply {
                arguments = Bundle().apply{
                    putSerializable(GAME_KEY, game)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.fight_fragment, container, false)

        val firstPlayer : Spinner = v.findViewById(R.id.first_player_spinner)
        val assistantPlayer : Spinner = v.findViewById(R.id.assistant_player_spinner)

        val game = arguments?.getSerializable(GAME_KEY) as Game
        val players = game.players

        val playersNames = mutableListOf<String>()
        playersNames.add(0,getString(R.string.nobody))
        players.forEach({
            playersNames.add(it.name)
        })

        context?.let {
            val adapter = ArrayAdapter<String>(it,android.R.layout.simple_list_item_1, playersNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            firstPlayer.adapter = adapter

            val adapterAssistants = ArrayAdapter<String>(it,android.R.layout.simple_list_item_1, playersNames)
            adapterAssistants.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            assistantPlayer.adapter = adapterAssistants

        }

        val playerPowerET : EditText = v.findViewById(R.id.player_power_fight)
        val monsterPowerET : EditText = v.findViewById(R.id.monster_power_fight)

        firstPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0){
                    player = null
                    assistant = null
                    assistantPlayer.setSelection(0)
                }
                else{
                    player = players.get(position-1)
                }

                setPlayerPower(playerPowerET)
            }
        }

        assistantPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if(player == null){
                    Toast.makeText(context, getString(R.string.choose_fighter),Toast.LENGTH_SHORT).show()
                    assistantPlayer.setSelection(0)
                    return
                }

                if(position == 0){
                    assistant = null
                }
                else{
                    assistant = players.get(position-1)
                }
                setPlayerPower(playerPowerET)
            }
        }

        // Buttons which increase power for player

        val player_add_1 : Button = v.findViewById(R.id.player_add_1)
        player_add_1.setOnClickListener {
            playerPower += 1
            playerPowerET.setText(playerPower.toString())
        }
        val player_add_2 : Button = v.findViewById(R.id.player_add_2)
        player_add_2.setOnClickListener {
            playerPower += 2
            playerPowerET.setText(playerPower.toString())
        }
        val player_add_3 : Button = v.findViewById(R.id.player_add_3)
        player_add_3.setOnClickListener {
            playerPower += 3
            playerPowerET.setText(playerPower.toString())
        }
        val player_add_5 : Button = v.findViewById(R.id.player_add_5)
        player_add_5.setOnClickListener {
            playerPower += 5
            playerPowerET.setText(playerPower.toString())
        }
        val player_add_10 : Button = v.findViewById(R.id.player_add_10)
        player_add_10.setOnClickListener {
            playerPower += 10
            playerPowerET.setText(playerPower.toString())
        }

        // Buttons which reduce power for player

        val player_minus_1 : Button = v.findViewById(R.id.player_minus_1)
        player_minus_1.setOnClickListener {
            playerPower -= 1
            playerPowerET.setText(playerPower.toString())
        }
        val player_minus_2 : Button = v.findViewById(R.id.player_minus_2)
        player_minus_2.setOnClickListener {
            playerPower -= 2
            playerPowerET.setText(playerPower.toString())
        }
        val player_minus_3 : Button = v.findViewById(R.id.player_minus_3)
        player_minus_3.setOnClickListener {
            playerPower -= 3
            playerPowerET.setText(playerPower.toString())
        }
        val player_minus_5 : Button = v.findViewById(R.id.player_minus_5)
        player_minus_5.setOnClickListener {
            playerPower -= 5
            playerPowerET.setText(playerPower.toString())
        }
        val player_minus_10 : Button = v.findViewById(R.id.player_minus_10)
        player_minus_10.setOnClickListener {
            playerPower -= 10
            playerPowerET.setText(playerPower.toString())
        }

        // Buttons which increase power for monster

        val monster_add_1 : Button = v.findViewById(R.id.monster_add_1)
        monster_add_1.setOnClickListener {
            monsterPower += 1
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_add_2 : Button = v.findViewById(R.id.monster_add_2)
        monster_add_2.setOnClickListener {
            monsterPower += 2
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_add_3 : Button = v.findViewById(R.id.monster_add_3)
        monster_add_3.setOnClickListener {
            monsterPower += 3
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_add_5 : Button = v.findViewById(R.id.monster_add_5)
        monster_add_5.setOnClickListener {
            monsterPower += 5
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_add_10 : Button = v.findViewById(R.id.monster_add_10)
        monster_add_10.setOnClickListener {
            monsterPower += 10
            monsterPowerET.setText(monsterPower.toString())
        }

        // Buttons which reduce power for monster

        val monster_minus_1 : Button = v.findViewById(R.id.monster_minus_1)
        monster_minus_1.setOnClickListener {
            monsterPower -= 1
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_minus_2 : Button = v.findViewById(R.id.monster_minus_2)
        monster_minus_2.setOnClickListener {
            monsterPower -= 2
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_minus_3 : Button = v.findViewById(R.id.monster_minus_3)
        monster_minus_3.setOnClickListener {
            monsterPower -= 3
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_minus_5 : Button = v.findViewById(R.id.monster_minus_5)
        monster_minus_5.setOnClickListener {
            monsterPower -= 5
            monsterPowerET.setText(monsterPower.toString())
        }
        val monster_minus_10 : Button = v.findViewById(R.id.monster_minus_10)
        monster_minus_10.setOnClickListener {
            monsterPower -= 10
            monsterPowerET.setText(monsterPower.toString())
        }



        return v
    }

    private fun setPlayerPower(playerPowerET: EditText) {
        if(player == null){
            playerPowerET.setText("0")
        }
        else{
            if(assistant == null){
                playerPowerET.setText(player!!.getAbsolutePowerInt().toString())
            }
            else{
                val playerPower = player!!.getAbsolutePowerInt() + assistant!!.getAbsolutePowerInt()
                playerPowerET.setText(playerPower.toString())
            }
        }
    }


}
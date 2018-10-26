package com.gameapp.android.wizardcounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


/* Wizard Card Game Counter */

public class HomeScreen extends AppCompatActivity {

    protected SharedPreferences user_state;

    //boolean variable if LOAD GAME is clicked
    protected boolean clicked=false;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_screen);

            user_state= getDefaultSharedPreferences(getApplicationContext());

        }

    //Proceed to PlayerSelection Activity
    public void NewGame(View view) {
        Intent launch_NewGame = new Intent(HomeScreen.this, PlayerSelection.class);
        startActivity(launch_NewGame);
    }


    //load only the last saved game of the user(via the save button in GameMenu)
    public void LoadGame(View view)
    {
        if(user_state.contains("initialized_load_game")) //if Save button is clicked in GameMenu
        {
            clicked = true;
            String player_1 = user_state.getString("Player_1", "");
            String player_2 = user_state.getString("Player_2", "");
            String player_3 = user_state.getString("Player_3", "");
            String player_4 = user_state.getString("Player_4", "");
            String player_5 = user_state.getString("Player_5", "");
            String player_6 = user_state.getString("Player_6", "");
            int count_info = user_state.getInt("Counter",0);

            Bundle extras = new Bundle();
            extras.putString("UserInput1", player_1);
            extras.putString("UserInput2", player_2);
            extras.putString("UserInput3", player_3);
            extras.putString("UserInput4", player_4);
            extras.putString("UserInput5", player_5);
            extras.putString("UserInput6", player_6);
            extras.putBoolean("isClicked_Load", clicked);
            extras.putInt("COUNTER",count_info);

            Intent Load_game = new Intent(HomeScreen.this, GameMenu.class);
            Load_game.putExtras(extras);
            startActivity(Load_game);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Game Save not found",Toast.LENGTH_SHORT).show();
        }

    }

    //View the Instructions of the Wizard Card Game via a Text File
    public void Instructions(View view)
    {
        Intent launch_GameInstructions=new Intent(HomeScreen.this,GameInstructions.class);
        startActivity(launch_GameInstructions);

    }

}














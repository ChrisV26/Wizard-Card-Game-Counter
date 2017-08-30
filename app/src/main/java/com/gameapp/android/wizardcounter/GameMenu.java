package com.gameapp.android.wizardcounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class GameMenu extends AppCompatActivity {

    //TextViews for player names
    protected TextView txt1,txt2,txt3,txt4,txt5,txt6;

    //Score text views for Player Names
    protected TextView score_txt1,score_txt2,score_txt3,score_txt4,score_txt5,score_txt6;

    //Bids EditTexts
    protected EditText bid_edt1,bid_edt2,bid_edt3,bid_edt4,bid_edt5,bid_edt6;

    //Tricks EdiTexts
    protected EditText trick_edt1,trick_edt2,trick_edt3,trick_edt4,trick_edt5,trick_edt6;

    //Buttons(bottom of the screen)
    protected Button calcBtn;
    protected Button newRoundBtn;
    protected Button saveBtn;


    //variables for storing each user input(Bids,Tricks, and Scores)
    protected  int bid_num1 = 0,bid_num2=0,bid_num3=0,bid_num4=0,bid_num5=0,bid_num6=0;

    protected  int trick_num1 = 0,trick_num2=0,trick_num3=0,trick_num4=0,trick_num5=0,trick_num6=0;

    protected   int score_1=0,score_2=0,score_3=0,score_4=0,score_5=0,score_6=0;

    //Number TextViews for Round counter
    protected TextView FirstNumCountTxt;
    protected TextView FinalNumCountTxt;

    //var for increasing the Round Number TextView
    protected int RoundCounter = 1;

    //variable for the Name of the winner
    String winner_name;

    //Array max_score which holds the scores of players
    int max_score[]=new int[6];

    //Array for bids/tricks boxes
    protected EditText bids_tricks[]=new EditText[12];

    //variable for the method toggle_buttons
    protected boolean clicked=false;

    //save user's activity state(Player Names,scores) in SharedPreferences
    protected SharedPreferences user_state;

    /* Double press to go back in PlayerSelection Class from GameMenu */
    boolean doubleBackToExitPressedOnce = false; //bool button for checking if back it's pressed

    //boolean variable checks if the save button is pressed
    protected boolean initialized=false;

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this,"Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        user_state=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //Handler for delay within newRoundBtn Button
        final Handler handler = new Handler();

        //Transition when Rounds are over(from GameMenu to PlayerSelection)
        final Intent launch_player_selection=new Intent(GameMenu.this,PlayerSelection.class);

        //Receive Player_names and number of players input from PlayerSelection Class
        Bundle extras = getIntent().getExtras();
        final String userData1 = extras.getString("UserInput1");
        final String userData2 = extras.getString("UserInput2");
        final String userData3 = extras.getString("UserInput3");
        final String userData4 = extras.getString("UserInput4");
        final String userData5 = extras.getString("UserInput5");
        final String userData6 = extras.getString("UserInput6");

        final int getCountInfo = extras.getInt("COUNTER");
        SharedPreferences.Editor editor=user_state.edit();
        editor.putInt("Counter",getCountInfo); //send the getCountInfo to HomeScreen(LOAD GAME)
        editor.commit();

        boolean isClicked=extras.getBoolean("isClicked_Load");

        //binding variables for viewing bids,tricks and score views based on players
        txt1 = (TextView) findViewById(R.id.txt1);
        txt1.setText(userData1);

        txt2 = (TextView) findViewById(R.id.txt2);
        txt2.setText(userData2);

        txt3 = (TextView) findViewById(R.id.txt3);
        txt3.setText(userData3);

        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);

        bid_edt1 = (EditText) findViewById(R.id.edt1);
        bid_edt2 = (EditText) findViewById(R.id.edt2);
        bid_edt3 = (EditText) findViewById(R.id.edt3);
        bid_edt4 = (EditText) findViewById(R.id.edt4);
        bid_edt5 = (EditText) findViewById(R.id.edt5);
        bid_edt6 = (EditText) findViewById(R.id.edt6);

        trick_edt1 = (EditText) findViewById(R.id.trick_edt1);
        trick_edt2 = (EditText) findViewById(R.id.trick_edt2);
        trick_edt3 = (EditText) findViewById(R.id.trick_edt3);
        trick_edt4 = (EditText) findViewById(R.id.trick_edt4);
        trick_edt5 = (EditText) findViewById(R.id.trick_edt5);
        trick_edt6 = (EditText) findViewById(R.id.trick_edt6);

        score_txt1 = (TextView) findViewById(R.id.score_txt1);
        score_txt2 = (TextView) findViewById(R.id.score_txt2);
        score_txt3 = (TextView) findViewById(R.id.score_txt3);
        score_txt4 = (TextView) findViewById(R.id.score_txt4);
        score_txt5 = (TextView) findViewById(R.id.score_txt5);
        score_txt6 = (TextView) findViewById(R.id.score_txt6);

        FirstNumCountTxt = (TextView) findViewById(R.id.num_round_txt1);
        FinalNumCountTxt = (TextView) findViewById(R.id.num_round_txt2);

        //Buttons Calculate Round,Next Round and Save at the bottom of the screen
        calcBtn = (Button) findViewById(R.id.calc_btn);
        newRoundBtn = (Button) findViewById(R.id.new_round);
        saveBtn=(Button) findViewById(R.id.button3);

        //enable button Next Round when button Calculate Round is pressed
        newRoundBtn.setEnabled(false);

        //Save button via method Save_State
        saveBtn.setOnClickListener(Save_State());

        //Shows number 1 as First Round
        FirstNumCountTxt.setText(Integer.toString(RoundCounter));

        //20_Rounds for 3 players
        FinalNumCountTxt.setText(Integer.toString(20));

        //when button Calculate Round is clicked
        calcBtn.setOnClickListener(Calculate_Score_3players());

        if(isClicked)
        {
            score_1=user_state.getInt("score_1",0);
            score_txt1.setText(Integer.toString(score_1));
            score_2=user_state.getInt("score_2",0);
            score_txt2.setText(Integer.toString(score_2));
            score_3=user_state.getInt("score_3",0);
            score_txt3.setText(Integer.toString(score_3));
            RoundCounter=user_state.getInt("First_txt_Round",RoundCounter);
            FirstNumCountTxt.setText(Integer.toString(RoundCounter));
        }

        //views for bids and tricks based on number of players from PlayerSelection class
          if (getCountInfo == 1) {
              txt4.setText(userData4);
              bid_edt4.setVisibility(View.VISIBLE);
              trick_edt4.setVisibility(View.VISIBLE);
              score_txt4.setVisibility(View.VISIBLE);
              FinalNumCountTxt.setText(Integer.toString(15)); // 15_Rounds for 4 players
              calcBtn.setOnClickListener(Calculate_Score_4players()); //calculate round for 4 players
              if(isClicked)
              {
                  score_4=user_state.getInt("score_4",0);
                  score_txt4.setText(Integer.toString(score_4));
              }
        } else if (getCountInfo == 2) {
              txt4.setText(userData4);
            bid_edt4.setVisibility(View.VISIBLE);
            trick_edt4.setVisibility(View.VISIBLE);
            score_txt4.setVisibility(View.VISIBLE);
              txt5.setText(userData5);
            bid_edt5.setVisibility(View.VISIBLE);
            trick_edt5.setVisibility(View.VISIBLE);
            score_txt5.setVisibility(View.VISIBLE);
              FinalNumCountTxt.setText(Integer.toString(12)); //12_Rounds for 5 players
            calcBtn.setOnClickListener(Calculate_Score_5players()); //calculate round for 5 players
              if(isClicked)
              {
                  score_4=user_state.getInt("score_4",0);
                  score_txt4.setText(Integer.toString(score_4));
                  score_5=user_state.getInt("score_5",0);
                  score_txt5.setText(Integer.toString(score_5));
              }
        } else if(getCountInfo==3){
              txt4.setText(userData4);
            bid_edt4.setVisibility(View.VISIBLE);
            trick_edt4.setVisibility(View.VISIBLE);
            score_txt4.setVisibility(View.VISIBLE);
              txt5.setText(userData5);
            bid_edt5.setVisibility(View.VISIBLE);
            trick_edt5.setVisibility(View.VISIBLE);
            score_txt5.setVisibility(View.VISIBLE);
              txt6.setText(userData6);
            bid_edt6.setVisibility(View.VISIBLE);
            trick_edt6.setVisibility(View.VISIBLE);
            score_txt6.setVisibility(View.VISIBLE);
              FinalNumCountTxt.setText(Integer.toString(10)); //10_Rounds for 6 players
            calcBtn.setOnClickListener(Calculate_Score_6players()); //calculate round for 6 players
              if(isClicked)
              {
                  score_4=user_state.getInt("score_4",0);
                  score_txt4.setText(Integer.toString(score_4));
                  score_5=user_state.getInt("score_5",0);
                  score_txt5.setText(Integer.toString(score_5));
                  score_6=user_state.getInt("score_6",0);
                  score_txt6.setText(Integer.toString(score_6));
              }
        }

        //when button Next Round is clicked
          newRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bid_edt1.setText("");
                bid_edt2.setText("");
                bid_edt3.setText("");
                bid_edt4.setText("");
                bid_edt5.setText("");
                bid_edt6.setText("");

                trick_edt1.setText("");
                trick_edt2.setText("");
                trick_edt3.setText("");
                trick_edt4.setText("");
                trick_edt5.setText("");
                trick_edt6.setText("");


                if (getCountInfo == 0) {
                    if (RoundCounter < 20)
                    {
                        RoundCounter++;
                        FirstNumCountTxt.setText(Integer.toString(RoundCounter));
                    }
                    else
                    {
                        //retrieving the winner score from the method find_max_score
                        max_score=new int[]{score_1,score_2,score_3,score_4,score_5,score_6};
                        final int winner_score=find_max_score(max_score);
                        if(winner_score==score_1)
                        {
                            winner_name=userData1;
                        }
                        else if(winner_score==score_2)
                        {
                            winner_name=userData2;
                        }
                        else if(winner_score==score_3)
                        {
                            winner_name=userData3;
                        }
                        else if(winner_score==score_4)
                        {
                            winner_name=userData4;
                        }
                        else if(winner_score==score_5)
                        {
                            winner_name=userData5;
                        }
                        else
                        {
                            winner_name=userData6;
                        }
                        Toast.makeText(getApplicationContext(),"Winner is:"+winner_name,Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                // Delay Transition from GameMenu class to PlayerSelection
                                startActivity(launch_player_selection);
                            }
                        }, 2000);
                    }
                }

                if (getCountInfo == 1) {
                    if (RoundCounter < 15)
                    {
                        RoundCounter++;
                        FirstNumCountTxt.setText(Integer.toString(RoundCounter));
                    }
                    else
                    {
                        //retrieving the winner score from the method find_max_score
                        max_score=new int[]{score_1,score_2,score_3,score_4,score_5,score_6};
                        final int winner_score=find_max_score(max_score);
                        if(winner_score==score_1)
                        {
                            winner_name=userData1;
                        }
                        else if(winner_score==score_2)
                        {
                            winner_name=userData2;
                        }
                        else if(winner_score==score_3)
                        {
                            winner_name=userData3;
                        }
                        else if(winner_score==score_4)
                        {
                            winner_name=userData4;
                        }
                        else if(winner_score==score_5)
                        {
                            winner_name=userData5;
                        }
                        else
                        {
                            winner_name=userData6;
                        }
                        Toast.makeText(getApplicationContext(),"Winner is:"+winner_name,Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                // Delay Transition from GameMenu class to PlayerSelection
                                startActivity(launch_player_selection);
                            }
                        }, 2000);
                    }
                }

                if (getCountInfo == 2) {
                    if (RoundCounter < 12)
                    {
                        RoundCounter++;
                        FirstNumCountTxt.setText(Integer.toString(RoundCounter));
                    }
                    else
                    {
                        //retrieving the winner score from the method find_max_score
                        max_score=new int[]{score_1,score_2,score_3,score_4,score_5,score_6};
                        final int winner_score=find_max_score(max_score);
                        if(winner_score==score_1)
                        {
                            winner_name=userData1;
                        }
                        else if(winner_score==score_2)
                        {
                            winner_name=userData2;
                        }
                        else if(winner_score==score_3)
                        {
                            winner_name=userData3;
                        }
                        else if(winner_score==score_4)
                        {
                            winner_name=userData4;
                        }
                        else if(winner_score==score_5)
                        {
                            winner_name=userData5;
                        }
                        else
                        {
                            winner_name=userData6;
                        }
                        Toast.makeText(getApplicationContext(),"Winner is:"+winner_name,Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                // Delay Transition from GameMenu class to PlayerSelection
                                startActivity(launch_player_selection);
                            }
                        }, 2000);

                    }
                }

                if (getCountInfo == 3) {
                    if (RoundCounter <10)
                    {
                        RoundCounter++;
                        FirstNumCountTxt.setText(Integer.toString(RoundCounter));

                    }
                    else
                    {
                        //retrieving the winner score from the method find_max_score
                        max_score=new int[]{score_1,score_2,score_3,score_4,score_5,score_6};
                        final int winner_score=find_max_score(max_score);
                        if(winner_score==score_1)
                        {
                            winner_name=userData1;
                        }
                        else if(winner_score==score_2)
                        {
                            winner_name=userData2;
                        }
                        else if(winner_score==score_3)
                        {
                            winner_name=userData3;
                        }
                        else if(winner_score==score_4)
                        {
                            winner_name=userData4;
                        }
                        else if(winner_score==score_5)
                        {
                            winner_name=userData5;
                        }
                        else
                        {
                            winner_name=userData6;
                        }
                        Toast.makeText(getApplicationContext(),"Winner is:"+winner_name,Toast.LENGTH_SHORT).show();

                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run() {
                                // Delay Transition from GameMenu class to PlayerSelection
                                startActivity(launch_player_selection);
                            }
                        }, 2000);

                    }
                }
                clicked=false;
                toggle_buttons(clicked);
            }
        });



    } //end of onCreate method

    protected View.OnClickListener Calculate_Score_3players() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialize the array bids_tricks for 3_players
                bids_tricks=new EditText[]{ bid_edt1,bid_edt2,bid_edt3,
                        trick_edt1,trick_edt2,trick_edt3};
                boolean validate_edtxs=validate_bids_tricks(bids_tricks); //check bids/trick boxes if they are empty
                clicked=true;
                if(validate_edtxs)
                {
                    bid_num1 = Integer.parseInt(bid_edt1.getText().toString().trim());
                    bid_num2 = Integer.parseInt(bid_edt2.getText().toString().trim());
                    bid_num3 = Integer.parseInt(bid_edt3.getText().toString().trim());

                    trick_num1 = Integer.parseInt(trick_edt1.getText().toString().trim());
                    trick_num2 = Integer.parseInt(trick_edt2.getText().toString().trim());
                    trick_num3 = Integer.parseInt(trick_edt3.getText().toString().trim());

                        if(bid_num1 == trick_num1)
                        {
                            score_1 = score_1+20 + (10 * trick_num1);
                        }
                         else {
                            if (bid_num1 > trick_num1)
                                score_1 = score_1 - ((bid_num1 - trick_num1) * 10);
                            else
                                score_1 = score_1 + ((bid_num1 - trick_num1) * 10);
                            }
                            score_txt1.setText(Integer.toString(score_1));

                        if (bid_num2 == trick_num2) {
                            score_2 =score_2 +20 + (10 * trick_num2);
                        } else {
                            if (bid_num2 > trick_num2)
                            score_2 = score_2 - ((bid_num2 - trick_num2) * 10);
                            else
                               score_2 = score_2 + ((bid_num2 - trick_num2) * 10);
                        }
                        score_txt2.setText(Integer.toString(score_2));

                        if (bid_num3 == trick_num3) {
                            score_3 =score_3+ 20 + (10 * trick_num3);
                        } else {
                            if (bid_num3 > trick_num3)
                                score_3 = score_3 - ((bid_num3 - trick_num3) * 10);
                            else
                                score_3 = score_3 + ((bid_num3 - trick_num3) * 10);
                        }
                        score_txt3.setText(Integer.toString(score_3));
                        toggle_buttons(clicked);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You forgot bid or trick",Toast.LENGTH_SHORT).show();
                }


            }
        }; //end of onClick method
    }//end of anonymous inner class


    protected View.OnClickListener Calculate_Score_4players() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //initialize the array bids_tricks for 4_players
                bids_tricks=new EditText[]{ bid_edt1,bid_edt2,bid_edt3,bid_edt4,
                        trick_edt1,trick_edt2,trick_edt3,trick_edt4};
                boolean validate_edtxs=validate_bids_tricks(bids_tricks); //check bids/trick boxes if they are empty
                clicked=true;

                if(validate_edtxs)
                {
                    bid_num1 = Integer.parseInt(bid_edt1.getText().toString());
                    bid_num2 = Integer.parseInt(bid_edt2.getText().toString());
                    bid_num3 = Integer.parseInt(bid_edt3.getText().toString());
                    bid_num4 = Integer.parseInt(bid_edt4.getText().toString());

                    trick_num1 = Integer.parseInt(trick_edt1.getText().toString());
                    trick_num2 = Integer.parseInt(trick_edt2.getText().toString());
                    trick_num3 = Integer.parseInt(trick_edt3.getText().toString());
                    trick_num4 = Integer.parseInt(trick_edt4.getText().toString());

                    if (bid_num1==trick_num1)
                    {

                        score_1 = score_1+20+(10 * trick_num1);
                    } else {
                        if (bid_num1 > trick_num1)
                            score_1 = score_1 - ((bid_num1 - trick_num1) * 10);
                        else
                            score_1 = score_1 + ((bid_num1 - trick_num1) * 10);
                    }
                    score_txt1.setText(Integer.toString(score_1));

                    if (bid_num2 == trick_num2) {
                        score_2 =score_2+ 20 + (10 * trick_num2);
                    } else {
                        if (bid_num2 > trick_num2)
                            score_2 = score_2 - ((bid_num2 - trick_num2) * 10);
                        else
                            score_2 = score_2 + ((bid_num2 - trick_num2) * 10);
                    }
                    score_txt2.setText(Integer.toString(score_2));

                    if (bid_num3 == trick_num3) {
                        score_3 = score_3+20 + (10 * trick_num3);
                    } else {
                        if (bid_num3 > trick_num3)
                            score_3 = score_3 - ((bid_num3 - trick_num3) * 10);
                        else
                            score_3 = score_3 + ((bid_num3 - trick_num3) * 10);
                    }
                    score_txt3.setText(Integer.toString(score_3));

                    if (bid_num4 == trick_num4) {
                        score_4 = score_4+20 + (10 * trick_num4);
                    } else {
                        if (bid_num4 > trick_num4)
                            score_4 = score_4 - ((bid_num4 - trick_num4) * 10);
                        else
                            score_4 = score_4 + ((bid_num4 - trick_num4) * 10);
                    }
                    score_txt4.setText(Integer.toString(score_4));
                    toggle_buttons(clicked);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You forgot bid or trick",Toast.LENGTH_SHORT).show();

                }

            }
        };

    }


    protected View.OnClickListener Calculate_Score_5players() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //initialize the array bids_tricks for 5_players
                bids_tricks=new EditText[]{ bid_edt1,bid_edt2,bid_edt3,bid_edt4,bid_edt5,
                        trick_edt1,trick_edt2,trick_edt3,trick_edt4,trick_edt5};
                boolean validate_edtxs=validate_bids_tricks(bids_tricks); //check bids/trick boxes if they are empty
                clicked=true;

                if(validate_edtxs)
                {
                    bid_num1 = Integer.parseInt(bid_edt1.getText().toString());
                    bid_num2 = Integer.parseInt(bid_edt2.getText().toString());
                    bid_num3 = Integer.parseInt(bid_edt3.getText().toString());
                    bid_num4 = Integer.parseInt(bid_edt4.getText().toString());
                    bid_num5 = Integer.parseInt(bid_edt5.getText().toString());

                    trick_num1 = Integer.parseInt(trick_edt1.getText().toString());
                    trick_num2 = Integer.parseInt(trick_edt2.getText().toString());
                    trick_num3 = Integer.parseInt(trick_edt3.getText().toString());
                    trick_num4 = Integer.parseInt(trick_edt4.getText().toString());
                    trick_num5 = Integer.parseInt(trick_edt5.getText().toString());

                    if (bid_num1 == trick_num1) {

                        score_1 = score_1+ 20 + (10 * trick_num1);
                    } else {
                        if (bid_num1 > trick_num1)
                            score_1 = score_1 - ((bid_num1 - trick_num1) * 10);
                        else
                            score_1 = score_1 + ((bid_num1 - trick_num1) * 10);
                    }
                    score_txt1.setText(Integer.toString(score_1));

                    if (bid_num2 == trick_num2) {
                        score_2 = score_2+20 + (10 * trick_num2);
                    } else {
                        if (bid_num2 > trick_num2)
                            score_2 = score_2 - ((bid_num2 - trick_num2) * 10);
                        else
                            score_2 = score_2 + ((bid_num2 - trick_num2) * 10);
                    }
                    score_txt2.setText(Integer.toString(score_2));

                    if (bid_num3 == trick_num3) {
                        score_3 = score_3+20 + (10 * trick_num3);
                    } else {
                        if (bid_num3 > trick_num3)
                            score_3 = score_3 - ((bid_num3 - trick_num3) * 10);
                        else
                            score_3 = score_3 + ((bid_num3 - trick_num3) * 10);
                    }
                    score_txt3.setText(Integer.toString(score_3));

                    if (bid_num4 == trick_num4) {
                        score_4 = score_4+20 + (10 * trick_num4);
                    } else {
                        if (bid_num4 > trick_num4)
                            score_4 = score_4 - ((bid_num4 - trick_num4) * 10);
                        else
                            score_4 = score_4 + ((bid_num4 - trick_num4) * 10);
                    }
                    score_txt4.setText(Integer.toString(score_4));

                    if (bid_num5 == trick_num5) {
                        score_5 = score_5+20 + (10 * trick_num5);
                    } else {
                        if (bid_num5 > trick_num5)
                            score_5 = score_5 - ((bid_num5 - trick_num5) * 10);
                        else
                            score_5 = score_5 + ((bid_num5 - trick_num5) * 10);
                    }
                    score_txt5.setText(Integer.toString(score_5));
                    toggle_buttons(clicked);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You forgot bid or trick",Toast.LENGTH_SHORT).show();

                }
            }
        };

    }

    protected View.OnClickListener Calculate_Score_6players() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialize the array bids_tricks for 6_players
                bids_tricks=new EditText[]{ bid_edt1,bid_edt2,bid_edt3,bid_edt4,bid_edt5,bid_edt6,
                        trick_edt1,trick_edt2,trick_edt3,trick_edt4,trick_edt5,trick_edt6};
                boolean validate_edtxs=validate_bids_tricks(bids_tricks); //check bids/trick boxes if they are empty
                clicked=true;

                if(validate_edtxs)
                {
                    bid_num1 = Integer.parseInt(bid_edt1.getText().toString());
                    bid_num2 = Integer.parseInt(bid_edt2.getText().toString());
                    bid_num3 = Integer.parseInt(bid_edt3.getText().toString());
                    bid_num4 = Integer.parseInt(bid_edt4.getText().toString());
                    bid_num5 = Integer.parseInt(bid_edt5.getText().toString());
                    bid_num6 = Integer.parseInt(bid_edt6.getText().toString());

                    trick_num1 = Integer.parseInt(trick_edt1.getText().toString());
                    trick_num2 = Integer.parseInt(trick_edt2.getText().toString());
                    trick_num3 = Integer.parseInt(trick_edt3.getText().toString());
                    trick_num4 = Integer.parseInt(trick_edt4.getText().toString());
                    trick_num5 = Integer.parseInt(trick_edt5.getText().toString());
                    trick_num6 = Integer.parseInt(trick_edt6.getText().toString());

                    if (bid_num1 == trick_num1) {

                        score_1 = score_1+20 + (10 * trick_num1);
                    } else {
                        if (bid_num1 > trick_num1)
                            score_1 = score_1 - ((bid_num1 - trick_num1) * 10);
                        else
                            score_1 = score_1 + ((bid_num1 - trick_num1) * 10);
                    }
                    score_txt1.setText(Integer.toString(score_1));

                    if (bid_num2 == trick_num2) {
                        score_2 = score_2+20 + (10 * trick_num2);
                    } else {
                        if (bid_num2 > trick_num2)
                            score_2 = score_2 - ((bid_num2 - trick_num2) * 10);
                        else
                            score_2 = score_2 + ((bid_num2 - trick_num2) * 10);
                    }
                    score_txt2.setText(Integer.toString(score_2));

                    if (bid_num3 == trick_num3) {
                        score_3 = score_3+20 + (10 * trick_num3);
                    } else {
                        if (bid_num3 > trick_num3)
                            score_3 = score_3 - ((bid_num3 - trick_num3) * 10);
                        else
                            score_3 = score_3 + ((bid_num3 - trick_num3) * 10);
                    }
                    score_txt3.setText(Integer.toString(score_3));

                    if (bid_num4 == trick_num4) {
                        score_4 =score_4+ 20 + (10 * trick_num4);
                    } else {
                        if (bid_num4 > trick_num4)
                            score_4 = score_4 - ((bid_num4 - trick_num4) * 10);
                        else
                            score_4 = score_4 + ((bid_num4 - trick_num4) * 10);
                    }
                    score_txt4.setText(Integer.toString(score_4));

                    if (bid_num5 == trick_num5) {
                        score_5 = score_5+20 + (10 * trick_num5);
                    } else {
                        if (bid_num5 > trick_num5)
                            score_5 = score_5 - ((bid_num5 - trick_num5) * 10);
                        else
                            score_5 = score_5 + ((bid_num5 - trick_num5) * 10);
                    }
                    score_txt5.setText(Integer.toString(score_5));

                    if (bid_num6 == trick_num6) {
                        score_6 = score_6+20 + (10 * trick_num6);
                    } else {
                        if (bid_num6 > trick_num6)
                            score_6 = score_6 - ((bid_num6 - trick_num6) * 10);
                        else
                            score_6 = score_6 + ((bid_num6 - trick_num6) * 10);
                    }
                    score_txt6.setText(Integer.toString(score_6));
                    toggle_buttons(clicked);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You forgot bid or trick",Toast.LENGTH_SHORT).show();

                }

            }
        }; //end of onClick method
    }//end of anonymous inner class

    //find the max score for variables score_1,2.. etc
    protected int find_max_score(int max_score[])
    {
        int count_max=0;
        int winner=max_score[count_max]; //winner<=player with the max score
        for(count_max=0; count_max<max_score.length; count_max++)
        {
            if(max_score[count_max]>winner)
            {
                winner=max_score[count_max];
            }
        }
        return winner;
    }

    //enable/disable Calculate Round/Next Round for user purpose
    protected void toggle_buttons(boolean clicked)
    {
        if(clicked)
        {
            newRoundBtn.setEnabled(true);
            calcBtn.setEnabled(false);
        }
        else
        {
            newRoundBtn.setEnabled(false);
            calcBtn.setEnabled(true);
        }
    }
    //check if the bids/tricks Edit boxes are empty
    protected boolean validate_bids_tricks(EditText bids_tricks[])
    {
        for(int i=0; i<bids_tricks.length; i++)
        {
            EditText currentField=bids_tricks[i];
            if(currentField.getText().toString().trim().equals(""))
            {
                return false;
            }
        }
        return true;
    }

    //save user's activity state
    protected View.OnClickListener Save_State() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor=user_state.edit();
                editor.putString("Player_1",txt1.getText().toString().trim());
                editor.putString("Player_2",txt2.getText().toString().trim());
                editor.putString("Player_3",txt3.getText().toString().trim());
                editor.putString("Player_4",txt4.getText().toString().trim());
                editor.putString("Player_5",txt5.getText().toString().trim());
                editor.putString("Player_6",txt6.getText().toString().trim());
                editor.putInt("score_1",score_1);
                editor.putInt("score_2",score_2);
                editor.putInt("score_3",score_3);
                editor.putInt("score_4",score_4);
                editor.putInt("score_5",score_5);
                editor.putInt("score_6",score_6);
                editor.putInt("First_txt_Round",RoundCounter);
                initialized=true;
                editor.putBoolean("initialized_load_game",initialized);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Game Saved Successfully",Toast.LENGTH_SHORT).show();

            }


        }; //end of onClick method
    } //end of anonymous inner class



} //end of class
package com.gameapp.android.wizardcounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class PlayerSelection extends AppCompatActivity {

    //to be considered
    RelativeLayout.LayoutParams layoutParams;
    RelativeLayout Rl;

    //TextViews for players 4,5,6 that are invisible
    protected TextView txv4;
    protected TextView txv5;
    protected TextView txv6;

    //EditText for players 1,2,3,4,5,6
    protected EditText et1;
    protected EditText et2;
    protected EditText et3;
    protected EditText et4;
    protected EditText et5;
    protected EditText et6;

    //counter variable to count number of players
    protected int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);

         txv4=(TextView)findViewById(R.id.textView20);
         txv5= (TextView)findViewById(R.id.textView21);
         txv6=(TextView)findViewById(R.id.textView22);

        et1=(EditText)findViewById(R.id.inputSearchEditText);
        et2=(EditText)findViewById(R.id.inputSearchEditText2);
        et3=(EditText)findViewById(R.id.inputSearchEditText3);
        et4=(EditText)findViewById(R.id.inputSearchEditText4);
        et5=(EditText)findViewById(R.id.inputSearchEditText5);
        et6=(EditText)findViewById(R.id.inputSearchEditText6);


        Button bplus=(Button)findViewById(R.id.button6);
        bplus.setOnClickListener(onClickPlus());

        Button bminus=(Button)findViewById(R.id.button5);
        bminus.setOnClickListener(onClickMinus());


        Rl = (RelativeLayout)findViewById(R.id.Rl);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    }

    //Method for adding a player
    private  View.OnClickListener onClickPlus(){
        return new View.OnClickListener() {

             public void onClick(View v)
             {
                 if (counter >= 0 && counter<=2)
                 {
                     if (counter == 0) {
                         txv4.setVisibility(View.VISIBLE);
                         et4.setVisibility(View.VISIBLE);

                     } else if (counter == 1) {
                         txv5.setVisibility(View.VISIBLE);
                         et5.setVisibility(View.VISIBLE);

                     } else {
                         txv6.setVisibility(View.VISIBLE);
                         et6.setVisibility(View.VISIBLE);
                     }
                    counter++;
                 }

             }


        };
    }

    //Method for removing a player
    private View.OnClickListener onClickMinus()
    {
        return new View.OnClickListener(){
            public void onClick(View v) {
                if (counter >= 1 && counter<=3)
                {
                    if (counter == 1) {
                    txv4.setVisibility(View.INVISIBLE);
                    et4.setVisibility(View.INVISIBLE);

                    } else if (counter == 2) {

                    txv5.setVisibility(View.INVISIBLE);
                    et5.setVisibility(View.INVISIBLE);
                    } else {

                    txv6.setVisibility(View.INVISIBLE);
                    et6.setVisibility(View.INVISIBLE);
                    }
                  counter--;
                }
            }
        };
    }

    //From PlayerSelection go to GameMenu when Continue button is clicked
        public void ContinueOn(View view)
        {
            String str1=et1.getText().toString().trim();
            String str2=et2.getText().toString().trim();
            String str3=et3.getText().toString().trim();
            String str4=et4.getText().toString().trim();
            String str5=et5.getText().toString().trim();
            String str6=et6.getText().toString().trim();

            Intent launchGameMenu = new Intent(PlayerSelection.this,GameMenu.class);

            Bundle extras=new Bundle();
            extras.putString("UserInput1", str1);
            extras.putString("UserInput2", str2);
            extras.putString("UserInput3", str3);
            extras.putString("UserInput4", str4);
            extras.putString("UserInput5", str5);
            extras.putString("UserInput6", str6);
            extras.putInt("COUNTER",counter);

            launchGameMenu.putExtras(extras);

            if(str1.equals("") || str2.equals("") || str3.equals(""))
            {
                Toast.makeText(this,"Please,enter your names",Toast.LENGTH_SHORT).show();
            }
            else
            {
                startActivity(launchGameMenu);
            }
        }

}

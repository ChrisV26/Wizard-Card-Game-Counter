package com.gameapp.android.wizardcounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameInstructions extends AppCompatActivity {

    protected TextView GameInstruct;
    protected StringBuilder DataString = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_instructions);

        GameInstruct=(TextView)findViewById(R.id.instruct_txt);

        BufferedReader reader = null;
        try
        {
            //read from the text GameInstructions.txt
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("GameInstructions.txt")));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                DataString.append(mLine);
                DataString.append('\n');

            }
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {

                }
            }
            GameInstruct.setText((CharSequence) DataString);
        }
    } //end of onCreate Method
} //end of Class

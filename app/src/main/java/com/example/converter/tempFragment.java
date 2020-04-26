package com.example.converter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class tempFragment extends Fragment {


    public tempFragment() {
        // Required empty public constructor
    }

    //Declarations
    TextView result;
    RadioGroup conversionGroup;
    RadioButton conversionChoice;
    Button Calculate;
    private EditText inputValue;
    double solution = 0.0;
    double userInput = 0.0;
    final String PREF_KEY = "SAVE_PREF";
    String inputText;
    int selectedID;
    View tempFragment;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tempFragment = inflater.inflate(R.layout.fragment_temp, container, false);

        //Associate widgets with variables
        result = tempFragment.findViewById(R.id.tempResult);
        inputValue = (EditText) tempFragment.findViewById(R.id.tempUserInput);
        conversionGroup = (RadioGroup) tempFragment.findViewById(R.id.tempConvGroup);

        //Calculate Button Listener
        Calculate = (Button) tempFragment.findViewById(R.id.tempCalculateButton);
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get radio button choice
                selectedID = conversionGroup.getCheckedRadioButtonId();
                conversionChoice = (RadioButton) tempFragment.findViewById(selectedID);

                //Get User Input
                inputText = inputValue.getText().toString();
                userInput = Double.parseDouble(inputText);

                //Computation based on their choice
                if(conversionChoice.getText().equals("Celsius to Fahrenheit"))
                {
                    //Actual calculation formula
                    solution = (userInput * (9.0/5)) + 32;

                    //Displays result on screen
                    result.setText(String.format ("%.2f", userInput) + " \u2103 is:   " + String.format ("%.2f", solution) + " \u2109");

                    //Color Change based on temperature being Cold, Hot, or Mild (between 0F-100F)
                    if(solution > 100.0)
                    {
                        tempFragment.setBackgroundColor(Color.RED);
                    }
                    else if(solution < 0)
                    {
                        tempFragment.setBackgroundColor(Color.rgb(42,106,255));
                    }
                    else
                    {
                        tempFragment.setBackgroundColor(Color.rgb(80,220,100));
                    }
                }
                if(conversionChoice.getText().equals("Fahrenheit to Celsius"))
                {
                    //Actual calculation formula
                    solution = (userInput - 32) * (5.0/9);

                    //Displays result on screen
                    result.setText(String.format ("%.2f", userInput) + " \u2109 is:   " + String.format ("%.2f", solution) + "\u2103");

                    //Color change based on temperature being cold, hot, or mild
                    if(solution > 37.0)
                    {
                        tempFragment.setBackgroundColor(Color.RED);
                    }
                    else if(solution < -17)
                    {
                        tempFragment.setBackgroundColor(Color.rgb(42,106,255));
                    }
                    else
                    {
                        tempFragment.setBackgroundColor(Color.rgb(80,220,100));
                    }
                }
            }
        });
        //Return the inflated fragment
        return tempFragment;
    }
    //This is invoked when the fragment is popped off the stack
    public void onPause() {
        super.onPause();
        //On Pause, meaning while this fragment is not in display do the following..
        selectedID = conversionGroup.getCheckedRadioButtonId();
        sharedPreferences = this.getActivity().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);

        //Make an editor to put values into shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Put values into shared preference which will be accessed later
        editor.putString("ValueTemp", String.valueOf(userInput));
        editor.putString("SolutionTemp", String.valueOf(solution));
        editor.putInt("checkIDTemp", selectedID);

        //Commit changes, then apply
        editor.commit();
        editor.apply();
    }

    @Override
    //This is invoked when the fragment is brought back into stack
    public void onResume() {
        super.onResume();
        //Get preferences set
        sharedPreferences = this.getActivity().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);

        //Get saved preferences and store them into appropriate variables
        userInput = Double.parseDouble(sharedPreferences.getString("ValueTemp", "0.0"));
        solution = Double.parseDouble(sharedPreferences.getString("SolutionTemp", "0.0"));
        //Set the edittext
        inputValue.setText(String.valueOf(userInput));
        //Set selected id of radio button
        selectedID = sharedPreferences.getInt("checkIDTemp", R.id.cTof);

        //Based on the id fetched, perform the appropriate task
        if(selectedID == R.id.cTof)
        {
            //Make this radio button checked
            conversionGroup.check(sharedPreferences.getInt("checkIDTemp", R.id.cTof));
            //Display result to the UI
            result.setText(String.format ("%.2f", userInput) + " \u2103 is:   " + String.format ("%.2f", solution) + " \u2109");
            //Change color based on the previous saved temperature gotten from shared preferences
            if(solution > 100.0)
            {
                tempFragment.setBackgroundColor(Color.RED);
            }
            else if(solution < 0)
            {
                tempFragment.setBackgroundColor(Color.rgb(42,106,255));
            }
            else
            {
                tempFragment.setBackgroundColor(Color.rgb(80,220,100));
            }
        }
        else
        {
            //Make this radio button checked
            conversionGroup.check(sharedPreferences.getInt("checkIDTemp", R.id.cTof));
            //Display result to the UI
            result.setText(String.format ("%.2f", userInput) + " \u2109 is:   " + String.format ("%.2f", solution) + "\u2103");
            //Change color based on the previous saved temperature gotten from shared preferences
            if(solution > 37.0)
            {
                tempFragment.setBackgroundColor(Color.RED);
            }
            else if(solution < -17)
            {
                tempFragment.setBackgroundColor(Color.rgb(42,106,255));
            }
            else
            {
                tempFragment.setBackgroundColor(Color.rgb(80,220,100));
            }
        }
    }
}

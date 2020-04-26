package com.example.converter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class distFragment extends Fragment {


    public distFragment() {
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
    String resultText;
    int selectedID;
    View distFragment;
    SharedPreferences sharedPreferences;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        distFragment =  inflater.inflate(R.layout.fragment_dist, container, false);

        //Accessing the widgets
        inputValue = (EditText) distFragment.findViewById(R.id.distUserInput);
        result = distFragment.findViewById(R.id.distResult);
        conversionGroup = (RadioGroup) distFragment.findViewById(R.id.distConvGroup);


        //Calculate Button Listener
        Calculate = (Button) distFragment.findViewById(R.id.distCalculateButton);
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Find selected radio button
                selectedID = conversionGroup.getCheckedRadioButtonId();
                conversionChoice = (RadioButton) distFragment.findViewById(selectedID);

                //Get value from edittext widget
                inputText = inputValue.getText().toString();
                userInput = Double.parseDouble(inputText);

                //Based on selection of radio button, convert appropriately
                if(conversionChoice.getText().equals("Kilometers to Miles"))
                {
                    //Solution contains actual result of the conversion
                    solution = (userInput * 0.62137119);

                    //Display the result on UI
                    resultText = String.format ("%.2f", userInput) + " km is:   " + String.format ("%.2f", solution) + " mi";
                    result.setText(resultText);
                }
                if(conversionChoice.getText().equals("Miles to Kilometers"))
                {
                    //Solution contains actual result of the conversion
                    solution = (userInput * 1.60934);

                    //Display the result on UI
                    resultText = String.format ("%.2f", userInput) + " mi is:   " + String.format ("%.2f", solution) + " km";
                    result.setText(resultText);
                }
            }
        });
        //Return the inflated fragment
        return distFragment;
    }

    @Override
    //This is invoked when the fragment is popped off stack
    public void onPause() {
        super.onPause();
        //Save the following things when this fragment is popped off stack
        selectedID = conversionGroup.getCheckedRadioButtonId();
        sharedPreferences = this.getActivity().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        //Editor declaration & initialization
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Save these key pair values for future accessing
        editor.putString("Value", String.valueOf(userInput));
        editor.putString("Solution", String.valueOf(solution));
        editor.putInt("checkID", selectedID);

        //Commit changes to editor & apply
        editor.commit();
        editor.apply();
    }

    @Override
    //This is invoked when the fragment is brought back into stack
    public void onResume() {
        super.onResume();
        //These are the things we would like to bring back to the fragment as per saved state of each
        sharedPreferences = this.getActivity().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);

        //Get saved preference values and store them to variables
        userInput = Double.parseDouble(sharedPreferences.getString("Value", "0.0"));
        solution = Double.parseDouble(sharedPreferences.getString("Solution", "0.0"));
        inputValue.setText(String.valueOf(userInput));
        selectedID = sharedPreferences.getInt("checkID", R.id.kmTomi);

        //Based on the saved pref id from last time, display results
        if(selectedID == R.id.miTokm)
        {
            //Make sure the selected radio button is actually checked
            conversionGroup.check(sharedPreferences.getInt("checkID", R.id.kmTomi));
            //Display result
            result.setText(String.format ("%.2f", userInput) + " mi is:   " + String.format ("%.2f", solution) + " km");
        }
        else
        {
            //Make sure the selected radio button is actually checked
            conversionGroup.check(sharedPreferences.getInt("checkID", R.id.kmTomi));
            //Display result
            result.setText(String.format ("%.2f", userInput) + " km is:   " + String.format ("%.2f", solution) + " mi");
        }
    }
}

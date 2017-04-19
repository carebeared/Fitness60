package shivamdh.com.fitness60;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Timer;

/*
The java file that coordinates the functionality of the options file within the app. The app regulates user selections through the 
SharedPreferences feature of Android and records and give user options regarding their app functionality
*/

public class Options extends Fragment {

	//boolean data to be collected from the radio buttons and stored for other parts of the app to use
    public static Boolean distanceC, weightC, timerC;
    RadioButton TimerYes, TimerNo, DistanceKm, DistanceMiles, WeightLbs, WeightKg;
    private static SharedPreferences optionsSelected; //main unit where user options are stored across the app

    public Options() {//empty constructor needed
    }

    public static void backPressed(Context appContext) { //if back is pressed from options file, show user that options have been saved
        Toast aToast = Toast.makeText(appContext, R.string.options_saved, Toast.LENGTH_SHORT);
        aToast.show();
    }

    @Override
    public void onPause() { //save the preferences when app activity goes on pause mode
        super.onPause();
        SharedPreferences.Editor editOptionsSaved = optionsSelected.edit(); 
        editOptionsSaved.putBoolean(getString(R.string.timer_option), timerC); //put in key-value pairs for the user choices
        editOptionsSaved.putBoolean(getString(R.string.distance_option), distanceC);
        editOptionsSaved.putBoolean(getString(R.string.weight_option), weightC);
        editOptionsSaved.apply(); //push changes in the shared preferences
    }

    public static void setVariables(Activity givenActivity) { //set the variables from previous recorded data
		//get access to the shared preferences
        optionsSelected = givenActivity.getSharedPreferences(givenActivity.getString(R.string.options_data_filename), Context.MODE_PRIVATE);

		//set variables to values from previous user selections, or set them to true if no data exists
        timerC = optionsSelected.getBoolean(givenActivity.getString(R.string.timer_option), true);
        distanceC = optionsSelected.getBoolean(givenActivity.getString(R.string.distance_option), true);
        weightC = optionsSelected.getBoolean(givenActivity.getString(R.string.weight_option), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.options, container, false);

        setVariables(getActivity()); //set the variables initally

		//toast to be used when user scrolls selections, set to random toast message because that message is going to be changed later anyways
        final Toast myToast = Toast.makeText(getContext(), R.string.random_toast_message, Toast.LENGTH_SHORT);

		//set radio buttons to true/false according to previous data or default data
        TimerYes = (RadioButton)myView.findViewById(R.id.TimerYes);
        TimerNo = (RadioButton)myView.findViewById(R.id.TimerNo);
        if (timerC) {
            TimerYes.setChecked(true);
        } else {
            TimerNo.setChecked(true);
        }
        DistanceKm = (RadioButton)myView.findViewById(R.id.km_unit);
        DistanceMiles = (RadioButton)myView.findViewById(R.id.miles_unit);
        if (distanceC) {
            DistanceKm.setChecked(true);
        } else {
            DistanceMiles.setChecked(true);
        }
        WeightLbs = (RadioButton)myView.findViewById(R.id.lbs_unit);
        WeightKg = (RadioButton)myView.findViewById(R.id.kg_unit);
        if (weightC) {
            WeightLbs.setChecked(true);
        } else {
            WeightKg.setChecked(true);
        }

        final RadioGroup timer = (RadioGroup) myView.findViewById(R.id.timer_buttons);

		//show user a toast everytime an option is changed to demonstrate that changes are going to be applied
        timer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View selectedButton = timer.findViewById(checkedId);
                int checked = timer.indexOfChild(selectedButton);

                switch(checked) {
                    case 0: //first button is selected
                        myToast.setText(R.string.timer_on);
                        timerC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText(R.string.timer_off);
                        timerC = false;
                        myToast.show();
                        break;
                }
            }
        });

        final RadioGroup distanceUnits = (RadioGroup) myView.findViewById(R.id.units1_check);
		//show user a toast everytime an option is changed to demonstrate that changes are going to be applied
        distanceUnits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View selectedButton = distanceUnits.findViewById(checkedId);
                int checked = distanceUnits.indexOfChild(selectedButton);

                switch(checked) {
                    case 0: //first button is selected
                        myToast.setText(R.string.km_unit_on);
                        distanceC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText(R.string.miles_unit_on);
                        distanceC = false;
                        myToast.show();
                        break;
                }
            }
        });

        final RadioGroup weightUnits = (RadioGroup) myView.findViewById(R.id.units2_check);
		//show user a toast everytime an option is changed to demonstrate that changes are going to be applied
        weightUnits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View selectedButton = weightUnits.findViewById(checkedId);
                int checked = weightUnits.indexOfChild(selectedButton);

                switch(checked) {
                    case 0: //first button is selected
                        myToast.setText(R.string.lbs_unit_on);
                        weightC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText(R.string.kg_unit_on);
                        weightC = false;
                        myToast.show();
                        break;
                }
            }
        });

        // Inflate the layout for this fragment
        return myView;
    }


}

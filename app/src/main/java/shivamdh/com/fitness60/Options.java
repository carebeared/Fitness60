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

public class Options extends Fragment {

    public static Boolean distanceC;
    public static Boolean weightC;
    public static Boolean timerC;
    RadioButton TimerYes, TimerNo, DistanceKm, DistanceMiles, WeightLbs, WeightKg;
    private static SharedPreferences optionsSelected;


    public Options() {
    }

    public static void backPressed(Context appContext) {
        Toast aToast = Toast.makeText(appContext, R.string.options_saved, Toast.LENGTH_SHORT);
        aToast.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editOptionsSaved = optionsSelected.edit();
        editOptionsSaved.putBoolean(getString(R.string.timer_option), timerC);
        editOptionsSaved.putBoolean(getString(R.string.distance_option), distanceC);
        editOptionsSaved.putBoolean(getString(R.string.weight_option), weightC);
        editOptionsSaved.apply();
    }

    public static void setVariables(Activity givenActivity) {

        optionsSelected = givenActivity.getSharedPreferences(givenActivity.getString(R.string.options_data_filename), Context.MODE_PRIVATE);

        timerC = optionsSelected.getBoolean(givenActivity.getString(R.string.timer_option), true);
        distanceC = optionsSelected.getBoolean(givenActivity.getString(R.string.distance_option), true);
        weightC = optionsSelected.getBoolean(givenActivity.getString(R.string.weight_option), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.options, container, false);

        setVariables(getActivity());

        final Toast myToast = Toast.makeText(getContext(), R.string.random_toast_message, Toast.LENGTH_SHORT);

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

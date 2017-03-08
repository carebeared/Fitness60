package shivamdh.com.fitness60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class Options extends Fragment {

    public Boolean distanceC;
    public Boolean weightC;
    public Boolean timerC;

    public Options() {
        // Required empty public constructor

        timerC = true; //timer is default to be on
        //false reading = 1st option on radio buttons
        distanceC = false; //default km
        weightC = false; //default lbs
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.options, container, false);

        final Toast myToast = Toast.makeText(getContext(), "A toast to type", Toast.LENGTH_SHORT);

        RadioButton TimerYes = (RadioButton)myView.findViewById(R.id.TimerYes);
        TimerYes.setChecked(true);
        RadioButton TimerKm = (RadioButton)myView.findViewById(R.id.km_unit);
        TimerKm.setChecked(true);
        RadioButton TimerLbs = (RadioButton)myView.findViewById(R.id.lbs_unit);
        TimerLbs.setChecked(true);

        final RadioGroup timer = (RadioGroup) myView.findViewById(R.id.timer_buttons);

        timer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View selectedButton = timer.findViewById(checkedId);
                int checked = timer.indexOfChild(selectedButton);

                switch(checked) {
                    case 0: //first button is selected
                        myToast.setText("Timer On");
                        timerC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText("Timer Off");
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
                        myToast.setText("Using Kilometers as Distance Units");
                        distanceC = false;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText("Using Miles as Distance Units");
                        distanceC = true;
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
                        myToast.setText("Measuring in Pounds (lbs)");
                        weightC = false;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText("Measuring in Kilograms (kg)");
                        weightC = true;
                        myToast.show();
                        break;
                }
            }
        });

        // Inflate the layout for this fragment
        return myView;
    }


}

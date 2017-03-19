package shivamdh.com.fitness60;

import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Options extends Fragment {

    public static Boolean distanceC = true;
    public static Boolean weightC = true;
    public static Boolean timerC = true;

    public Options() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//

    public static void backPressed(Context appContext) {
        Toast aToast = Toast.makeText(appContext, "Options saved", Toast.LENGTH_SHORT);
        aToast.show();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.options, container, false);

        final Toast myToast = Toast.makeText(getContext(), "A toast to type", Toast.LENGTH_SHORT);

        RadioButton TimerYes = (RadioButton)myView.findViewById(R.id.TimerYes);
        RadioButton TimerNo = (RadioButton)myView.findViewById(R.id.TimerNo);
        if (timerC) {
            TimerYes.setChecked(true);
        } else {
            TimerNo.setChecked(true);
        }
        RadioButton TimerKm = (RadioButton)myView.findViewById(R.id.km_unit);
        RadioButton TimerMiles = (RadioButton)myView.findViewById(R.id.miles_unit);
        if (distanceC) {
            TimerKm.setChecked(true);
        } else {
            TimerMiles.setChecked(true);
        }
        RadioButton TimerLbs = (RadioButton)myView.findViewById(R.id.lbs_unit);
        RadioButton TimerKgs = (RadioButton)myView.findViewById(R.id.kg_unit);
        if (weightC) {
            TimerLbs.setChecked(true);
        } else {
            TimerKgs.setChecked(true);
        }

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
                        distanceC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText("Using Miles as Distance Units");
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
                        myToast.setText("Measuring in Pounds (lbs)");
                        weightC = true;
                        myToast.show();
                        break;
                    case 1: //second button is selected
                        myToast.setText("Measuring in Kilograms (kg)");
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

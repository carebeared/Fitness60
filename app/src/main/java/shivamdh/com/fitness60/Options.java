package shivamdh.com.fitness60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 */
public class Options extends Fragment {


    public Options() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.options, container, false);

//        RadioGroup timer = (RadioGroup)getView().findViewById(R.id.timer_buttons);
        RadioButton TimerYes = (RadioButton)myView.findViewById(R.id.TimerYes);
        TimerYes.setChecked(true);
        RadioButton TimerKm = (RadioButton)myView.findViewById(R.id.km_unit);
        TimerKm.setChecked(true);
        RadioButton TimerLbs = (RadioButton)myView.findViewById(R.id.lbs_unit);
        TimerLbs.setChecked(true);

//        final RadioGroup distanceUnits = (RadioGroup)getView().findViewById(R.id.units1_check);
//        distanceUnits.check(R.id.km_unit);
//        final RadioGroup weightUnits = (RadioGroup)getView().findViewById(R.id.units2_check);



//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                View selectedButton = radioGroup.findViewById(checkedId);
//                int checked = radioGroup.indexOfChild(selectedButton);
//
//                switch(checked) {
//                    case 0: //first button is selected
//
//                        break;
//                    case 1: //second button is selected
//
//                        break;
//                }
//            }
//        });


        // Inflate the layout for this fragment
        return myView;
    }


}

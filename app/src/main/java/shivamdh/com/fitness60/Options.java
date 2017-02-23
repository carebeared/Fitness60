package shivamdh.com.fitness60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

//        final RadioGroup radioGroup = (RadioGroup)getView().findViewById(R.id.timer_buttons);
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
        return inflater.inflate(R.layout.options, container, false);
    }


}

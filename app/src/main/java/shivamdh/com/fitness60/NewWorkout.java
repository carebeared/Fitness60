package shivamdh.com.fitness60;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewWorkout extends Fragment {


    public NewWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View theView = inflater.inflate(R.layout.new_workout, container, false);

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        Calendar theTime = Calendar.getInstance();
        String displayTime = time.format(theTime.getTime());
        String finalTime = getString(R.string.starting_workout) + " " + displayTime;

        TextView toStartWorkout = (TextView) theView.findViewById(R.id.time_start);
        toStartWorkout.setText(finalTime);




//        ViewGroup myView = (ViewGroup) theView.findViewById(R.id.layout);
        //.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

//        View n;
//
//        n = inflater.inflate(R.layout.activity_tables, container, false);
//        myView.addView(n);


        Activities one = new Activities(container, theView, inflater, getContext());
        Activities two = new Activities(container, theView, inflater, getContext());

//        final Button addSet = (Button) theView.findViewById(R.id.add_more_sets);
//        addSet.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        return theView;
    }

}

class Activities {
//    public static View myView;

    Activities(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context theContext) {
        LinearLayout myView = (LinearLayout) theLayout.findViewById(R.id.layout);
        View n = theInflate.inflate(R.layout.activity_tables, mainContainer, false);
        myView.addView(n);


        LinearLayout newView = new LinearLayout(theContext);
        newView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        myView.addView(newView);

        ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) newView.getLayoutParams();
        int top = 100;
        margins.setMargins(0, 100, 0, 0);

    }



}

package shivamdh.com.fitness60;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewWorkout extends Fragment {

    private int activities = 0;

    public NewWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View theView = inflater.inflate(R.layout.new_workout, container, false);

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
        Calendar theTime = Calendar.getInstance();
        String displayTime = time.format(theTime.getTime());
        String finalTime = getString(R.string.starting_workout) + " " + displayTime;

        TextView toStartWorkout = (TextView) theView.findViewById(R.id.time_start);
        toStartWorkout.setText(finalTime);
        toStartWorkout.setTextSize(23.0f);

        toStartWorkout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));



//        ViewGroup myView = (ViewGroup) theView.findViewById(R.id.layout);
        //.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

//        View n;
//
//        n = inflater.inflate(R.layout.activity_tables, container, false);
//        myView.addView(n);

        final ArrayList<Activities> workoutActivities = new ArrayList<Activities>();
        workoutActivities.add(new Activities(container, theView, inflater, getContext(), ++activities));
        activities++;
        workoutActivities.add(new Activities(container, theView, inflater, getContext(), ++activities));
        activities++;

        Button addActivities = (Button) theView.findViewById(R.id.new_activity);
        addActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutActivities.add(new Activities(container, theView, inflater, getContext(), ++activities));
                activities++;
            }
        });

        return theView;
    }

//    View.OnClickListener onClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.delete_activity:
//
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
}


class Activities implements View.OnClickListener{
    private Button addSets, removeSets;
//    public Button deleteActivity;
    private TableRow newRow;
    private Context theContext;
    private EditText sets, reps, weight;
    private TextView time;
    private String finalTime;
    private int setNumber, firstWeight, firstReps;
    private TableLayout table;

    Activities(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum) {
        theContext = aContext;
        LinearLayout myView = (LinearLayout) theLayout.findViewById(R.id.layout);

        View c = theInflate.inflate(R.layout.cancel_activity, mainContainer, false);
        myView.addView(c, activityNum++);
        View n = theInflate.inflate(R.layout.activity_tables, mainContainer, false);
        myView.addView(n, activityNum);

        LinearLayout newView = new LinearLayout(theContext);
        newView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        myView.addView(newView);

        ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) newView.getLayoutParams();
        margins.setMargins(0, 100, 0, 0);

        addSets = (Button) n.findViewById(R.id.add_more_sets);
        addSets.setOnClickListener(this);
        removeSets = (Button) n.findViewById(R.id.remove_sets);
        removeSets.setOnClickListener(this);
//        deleteActivity= (Button) n.findViewById(R.id.delete_activity);
//        onClick(n);

        table = (TableLayout) n.findViewById(R.id.activity1);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);
        table.setColumnStretchable(3, true);
        setNumber = 0;

        //creating first row
        createRow();
    }

    private void createRow() {
        newRow = new TableRow(theContext);
        sets = new EditText(theContext);
        weight = new EditText(theContext);
        reps = new EditText(theContext);
        time = new TextView(theContext);
        setNumber++;
        sets.setText(Integer.toString(setNumber));
        if (setNumber == 1) {
            createFirstRowOnly();
        }
        time.setText("00:00");
        sets.setGravity(Gravity.CENTER);
        weight.setGravity(Gravity.CENTER);
        time.setGravity(Gravity.CENTER);
        reps.setGravity(Gravity.CENTER);
        newRow.addView(sets);
        newRow.addView(weight);
        newRow.addView(reps);
        newRow.addView(time);
        table.addView(newRow,setNumber);
    }

    private void createFirstRowOnly() {
        weight.setHint("Weight"); //only for 1st one
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    firstWeight = Integer.parseInt(s.toString());
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        reps.setHint("Reps"); //only for 1st one
        reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    firstReps = Integer.parseInt(s.toString());
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_more_sets:
                createRow();
                //extra applicable only to 2nd row onwards
                if (firstWeight == 0) {
                    weight.setHint("Weight");
                } else {
                    weight.setText(Integer.toString(firstWeight));
                }
                if (firstReps == 0) {
                    reps.setHint("Reps");
                } else {
                    reps.setText(Integer.toString(firstReps));
                }
                break;
            case R.id.remove_sets:
                if (setNumber > 0) { //check to not remove the header row
                    table.removeViewAt(setNumber);
                    setNumber--;
                }
                break;
            default:
                break;
        }


    }

//
//    @Override
//    public void run() {
//        SimpleDateFormat time = new SimpleDateFormat("mm:ss", Locale.CANADA);
//        Calendar theTime = Calendar.getInstance();
//        finalTime = time.format(theTime.getTime());
//    }
}


package shivamdh.com.fitness60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Timer;

import static shivamdh.com.fitness60.Options.timerC;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewWorkout extends Fragment implements View.OnClickListener {

    private ArrayList<Activities> workoutActivities = new ArrayList<Activities>();
    private LayoutInflater currInflate;
    private ViewGroup currContainer;
    private View theView;
    LinearLayout workoutTab;

    public NewWorkout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        currContainer = container;
        currInflate = inflater;
        theView = currInflate.inflate(R.layout.new_workout, currContainer, false);

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


        workoutActivities.add(new Activities(container, theView, inflater, getContext(), workoutActivities.size()+1, getActivity()));

        Button addActivities = (Button) theView.findViewById(R.id.new_activity);
        addActivities.setOnClickListener(this);

        Button removeActivities = (Button) theView.findViewById(R.id.remove_last_activity);
        removeActivities.setOnClickListener(this);

        Button distanceActivity = (Button) theView.findViewById(R.id.distance_activity);
        distanceActivity.setOnClickListener(this);

        Button bodyweight = (Button) theView.findViewById(R.id.bodyweight_exercise);
        bodyweight.setOnClickListener(this);

        workoutTab = (LinearLayout) theView.findViewById(R.id.layout);

        return theView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_activity:
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new Activities(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity()));
                break;
            case R.id.remove_last_activity:
                if (workoutActivities.size() > 0) {
                    if (timerC) {
                        if (workoutActivities.size() > 1) {

                            Activities nowLastActivity = workoutActivities.get(workoutActivities.size() - 2);

                            TableLayout getTable = (TableLayout) nowLastActivity.getTable();
                            TableRow wantedRow = (TableRow) getTable.getChildAt(nowLastActivity.getSetNumber());
                            TextView resumedTimer = (TextView) wantedRow.getChildAt(3);

                            int previousM = nowLastActivity.getPreviousMins(resumedTimer.getText());
                            int previousS = nowLastActivity.getPreviousSecs(resumedTimer.getText());

                            nowLastActivity.setTime(resumedTimer);
                            nowLastActivity.start = System.currentTimeMillis() - ((previousM * 60) + previousS) * 1000;
                            nowLastActivity.setTimer = new Timer();
                            nowLastActivity.setTimer.schedule(new Activities.runTimer(getActivity()), 1000, 1000);
                        }
                    }
                    workoutTab.removeViewAt(workoutActivities.size());
                    workoutActivities.remove(workoutActivities.size() - 1);
                }
                break;
            case R.id.distance_activity:
//                workoutActivities.add(new DistanceActivity(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1));
                break;
            case R.id.bodyweight_exercise:

                break;
            default:
                break;
        }
    }
}


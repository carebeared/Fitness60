package shivamdh.com.fitness60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.Locale;
import java.util.Timer;

import static shivamdh.com.fitness60.Options.timerC;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewWorkout extends Fragment implements View.OnClickListener {

    private ArrayList<Activities> workoutActivities = new ArrayList<>();
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

        SimpleDateFormat time = new SimpleDateFormat(getString(R.string.dateFormat), Locale.CANADA);
        Calendar theTime = Calendar.getInstance();
        String displayTime = time.format(theTime.getTime());
        String finalTime = getString(R.string.starting_workout) + " " + displayTime;

        TextView toStartWorkout = (TextView) theView.findViewById(R.id.time_start);
        toStartWorkout.setText(finalTime);

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
                        Activities nowLastActivity = workoutActivities.get(workoutActivities.size() - 2); // about to become the last activity now
                        if (workoutActivities.size() > 1 && nowLastActivity.setNumber > 0) { //start previous activities timer now, only if there was a timer left

                            TableLayout getTable =  nowLastActivity.getTable();
                            TableRow wantedRow = (TableRow) getTable.getChildAt(nowLastActivity.getSetNumber());
                            TextView resumedTimer = (TextView) wantedRow.getChildAt(3); //get the desired timer textview

                            int previousM = nowLastActivity.getPreviousMins(resumedTimer.getText()); //get the previous minutes on timer
                            int previousS = nowLastActivity.getPreviousSecs(resumedTimer.getText()); //get the previous seconds form the timer

                            nowLastActivity.setTime(resumedTimer); //set the class's timer to the timer last left off
                            Activities.start = System.currentTimeMillis() - ((previousM * 60) + previousS) * 1000; //set the time of the 'start' var according to previous timer's time
                            nowLastActivity.setTimer = new Timer(); //have to create a brand new time since last timer was canceled and deleted
                            nowLastActivity.setTimer.schedule(new Activities.runTimer(getActivity()), 1000, 1000);
                        }
                    }
                    workoutTab.removeViewAt(workoutActivities.size()); //safely remove the lass activity
                    workoutActivities.remove(workoutActivities.size() - 1); //take out the activity from the linkedlist
                }
                break;
            case R.id.distance_activity:
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new DistanceActivity(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity()));
                break;
            case R.id.bodyweight_exercise:
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new BodyweightExercise(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity()));
                break;
            default:
                break;
        }
    }
}


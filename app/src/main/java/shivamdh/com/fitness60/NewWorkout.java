package shivamdh.com.fitness60;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class NewWorkout extends Fragment implements View.OnClickListener {
    DatabaseHelper db;

    private ArrayList<Activities> workoutActivities = new ArrayList<>();
    private LayoutInflater currInflate;
    private ViewGroup currContainer;
    private View theView;
    LinearLayout workoutTab;
    Context appContext;
    static WorkoutActivityDatabase myData;
    public static boolean done = false;
    public static boolean Save = false;

    public NewWorkout() {
        // Required empty public constructor
        done = true;
    }

    public void setTheContext (Context aContext) {
        appContext = aContext;
//        db = new DatabaseHelper(appContext, 0);
    }

    public static class MyDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

        public MyDialogFragment() {
            super();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Workout closed. Would you like to save it?").setPositiveButton("Yes", this)
                    .setNegativeButton("No", this).create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Save = true;
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Save = false;
                        break;
                    default:
                        break;
                }
            }
        }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("About to", "DESTORRYYYYY");

        //THIS IS ONLY TAKING THE DATA FROM THE FIRST WORKOUT ACTIVITY, NEED IT TO DO ALL ACTIVITIES
//        Cursor previousData;
//        String buffer;
//        if (myData != null) {
//            previousData = myData.getAllData();
//            previousData.moveToFirst();
//            if (!previousData.isAfterLast()) {
//                buffer = previousData.getString(0);
//            } else {
//                buffer = "";
//            }
//        } else {
//            buffer = "";
//        }
//        int previousSets;
//        try {
//            previousSets = Integer.parseInt(buffer);
//        } catch (NumberFormatException e) {
//            previousSets = 0;
//        }

        if (workoutActivities.get(0).getClass() == DistanceActivity.class)  { //distance activity
            myData = new WorkoutActivityDatabase(appContext, false, workoutActivities.get(0).table); //create normal database
        } else { //normal activity
            myData = new WorkoutActivityDatabase(appContext, true, workoutActivities.get(0).table); //create normal database
        }
        Log.d("Cal", "ed");
        myData.addData();
        Log.d("Cal", "DONE");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

        workoutActivities.add(new Activities(container, theView, inflater, getContext(), workoutActivities.size()+1, getActivity(), true));

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
                workoutActivities.add(new Activities(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity(), true));
                break;
            case R.id.bodyweight_exercise:
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new Activities(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity(), false));
                break;
            case R.id.remove_last_activity:
                if (workoutActivities.size() > 0) {
                    if (timerC && workoutActivities.size() > 1) {
                        Activities nowLastActivity = workoutActivities.get(workoutActivities.size() - 2); // about to become the last activity now
                        if (workoutActivities.size() > 1 && nowLastActivity.setNumber > 0) { //start previous activities timer now, only if there was a timer left

                            TableLayout getTable =  nowLastActivity.table;
                            TableRow wantedRow = (TableRow) getTable.getChildAt(nowLastActivity.getSetNumber());
                            TextView resumedTimer = (TextView) wantedRow.getChildAt(wantedRow.getChildCount()-1); //get the desired timer textview

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
            default:
                break;
        }
    }
}


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

/*
This class is used to by the user when creating a new workout. This class serves to handle all the renderings and
database manipulations. This class keeps all the individual activities within a workout in a list and keeps track of all their 
data and properly packages it into the database. The class also handles activity deletion and addition through multiple on click listeners
and access to constructing the proper activity classes on the user's discretion.
*/

public class NewWorkout extends Fragment implements View.OnClickListener {
    DatabaseHelper db;

    private ArrayList<Activities> workoutActivities = new ArrayList<>();
    private LayoutInflater currInflate;
    private ViewGroup currContainer;
    private View theView;
    LinearLayout workoutTab;
    Context appContext;
    static WorkoutActivityDatabase myData;
    public static boolean done = false; //default booleans
    public static boolean Save = false;

    public NewWorkout() {
        // Required empty public constructor
        done = true; //test the true boolean
    }

    public void setTheContext (Context aContext) {
        appContext = aContext;
//        db = new DatabaseHelper(appContext, 0);
    }

	//class within the class to help with the Dialog Fragment that gets called on when the app exit the fragment
    public static class MyDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

        public MyDialogFragment() {
            super();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity()) //send out the appropriate dialog fragment to user for workout save feature
                    .setMessage("Workout closed. Would you like to save it?").setPositiveButton("Yes", this)
                    .setNegativeButton("No", this).create();
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
                switch (which) { //database actions depend on what the user selects
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

		//Code below can be used to insert data into the database and allow the database to grow, still incomplete because
		//the code only takes information of the first activity from the workout (which works well, just need to expand to all activites)
		//in the workout
		
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
        myData.addData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
		//create local variables with inserted paramters
        currContainer = container;
        currInflate = inflater;
        theView = currInflate.inflate(R.layout.new_workout, currContainer, false);

		//used to display the time that the workout starts at
        SimpleDateFormat time = new SimpleDateFormat(getString(R.string.dateFormat), Locale.CANADA);
        Calendar theTime = Calendar.getInstance();
        String displayTime = time.format(theTime.getTime());
        String finalTime = getString(R.string.starting_workout) + " " + displayTime;

        TextView toStartWorkout = (TextView) theView.findViewById(R.id.time_start);
        toStartWorkout.setText(finalTime);

		//test with one activity inserted as a default
        workoutActivities.add(new Activities(container, theView, inflater, getContext(), workoutActivities.size()+1, getActivity(), true));

		//set the button on click listeners for various tasks
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
            case R.id.new_activity: //user asks for a new basic activity
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new Activities(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity(), true));
                break;
            case R.id.bodyweight_exercise: //user asks for a new bodyweight exercise activity
                if (timerC && workoutActivities.size() > 0) {
                    workoutActivities.get(workoutActivities.size()-1).setTimer.cancel();
                }
                workoutActivities.add(new Activities(currContainer, theView, currInflate, getContext(), workoutActivities.size()+1, getActivity(), false));
                break;
            case R.id.remove_last_activity: //user wishes to remove the last activity in the workout
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
            case R.id.distance_activity: //user opts to start a distance activity
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


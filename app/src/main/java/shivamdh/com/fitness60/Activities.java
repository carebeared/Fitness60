package shivamdh.com.fitness60;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shivamdh.com.fitness60.Options.timerC;
import static shivamdh.com.fitness60.Options.weightC;
import static shivamdh.com.fitness60.Options.distanceC;


/*
This class pertains to the Activites tab within the app, used to communicate with the internal database as well as UI to provide
a smooth rendering of how a user sees their data recorded and stored during workouts. The main fragment that this class runs on is the 
activity_main layout xml file. This class pertains to the standard activity a user may use while DistanceActivity (a subclass of this)
is for specific distance related activities within workouts
*/

public class Activities implements View.OnClickListener{
    Context theContext; //the provided app context within which the class runs on 
    protected LinearLayout myView; //provided linear layout under which the app and fragment operates within
    Activity ourActivity; //the app activity
    View n; //the app view, used to inflate the layout and table
    protected Button addSets, removeSets; //buttons at the bottom of each tab for set manipulation
    TableLayout table; //each set's table 
    TableRow newRow; 
    EditText sets, reps, weight; //each row's column 
    static TextView time; //each row may contain a timer (depending on settings)
    private static TextView weights; //the weights header column (changes display units depending on user options)
    int setNumber, firstReps;
    private int firstWeight; //keep a track of the first set's weight, used to copy that into other sets
    static long start; //long number for timer start and stop 
    Timer setTimer;
    private SharedPreferences optionsSelected; //user preferences from the options tab in the app
    private Boolean activityType; //what kind of activity is selected by the user to deploy

    int getSetNumber() { 
        return setNumber;
    }

    void setTime(TextView givenTime) {
        time = givenTime;
    }

    Activities() { } //default constructor used by child classes

    void defaultSetup(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum, Activity currActivity) {
        theContext = aContext;
        ourActivity = currActivity;
        myView = (LinearLayout) theLayout.findViewById(R.id.layout);

		//inflate the layout for the table and set boundary paramters
        n = theInflate.inflate(R.layout.activity_tables, mainContainer, false);
        myView.addView(n, activityNum);
		
        LinearLayout newView = new LinearLayout(theContext);
        newView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        myView.addView(newView);

        ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) newView.getLayoutParams();
        margins.setMargins(0, 100, 0, 0);

        //set the onclick handlers for the buttons
        addSets = (Button) n.findViewById(R.id.add_more_sets);
        addSets.setOnClickListener(this);
        removeSets = (Button) n.findViewById(R.id.remove_sets);
        removeSets.setOnClickListener(this);

        //set some object fields common to all similar classes
        table = (TableLayout) n.findViewById(R.id.activity1);
        setNumber = 1;
    }

	//custom constructor, used when deploying the basic activity type
    Activities(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum, Activity currActivity, Boolean type) {
        defaultSetup(mainContainer, theLayout, theInflate, aContext, activityNum, currActivity); //user a default setup function that all types of activities use
        activityType = type;

        weights = (TextView) n.findViewById(R.id.weight_text);

        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);

        Options.setVariables(ourActivity);

        if (!timerC) { //remove table column if option is selected
            TableRow header = (TableRow) n.findViewById(R.id.new_workout_header);
            header.removeViewAt(3);
        } else { //keep timer, stretch the column to right size continue timer
            table.setColumnStretchable(3, true);
            start = System.currentTimeMillis();

            setTimer = new Timer();
            setTimer.schedule(new runTimer(ourActivity), 1000, 1000);
        }

        optionsSelected = ourActivity.getSharedPreferences(ourActivity.getString(R.string.options_data_filename), Context.MODE_PRIVATE);

		//get user 'shered preferences' from the options tab, otherwise set all booleans to true
        timerC = optionsSelected.getBoolean(ourActivity.getString(R.string.timer_option), true);
        distanceC = optionsSelected.getBoolean(ourActivity.getString(R.string.distance_option), true);
        weightC = optionsSelected.getBoolean(ourActivity.getString(R.string.weight_option), true);

        //creating first row
        createRow();
    }

    static class runTimer extends TimerTask { //class used to handle timer handler
        private Activity theActivity;

        runTimer(Activity givenActivity){
            theActivity = givenActivity;
        }

        @Override
        public void run() { //timer handler
            theActivity.runOnUiThread(new Runnable() { //ensure timer runs on UI thread, to allow for multi-core processing with the timer being shown on UI
                @Override
                public void run() {
                    if (timerC) {
                        long mill = System.currentTimeMillis() - start; //calc milliseconds from when the timer was started
                        int seconds = (int) (mill / 1000 + 1); 
                        int minutes = seconds / 60;
                        seconds = seconds % 60;
                        Log.d(String.valueOf(minutes), String.valueOf(seconds));
                        time.setText(String.format(Locale.getDefault(), "%d:%02d", minutes, seconds)); //convert and show the final times
                    }
                }
            });
        }
    }

    private void createRow() { //function for creating rows for the activity

        newRow = new TableRow(theContext);
        sets = new EditText(theContext);
        weight = new EditText(theContext);
        reps = new EditText(theContext);
        setNumber++;
        sets.setText(String.format(Locale.getDefault(), "%d", setNumber-1));
        if (setNumber == 2) {
            createFirstRowOnly(); //special syntax for first row, regarding edit text listeners
        }

		//edit text setup and new row rendering code
        sets.setGravity(Gravity.CENTER);
        weight.setGravity(Gravity.CENTER);

        if (!weightC){
            weights.setText(R.string.kgchoice);
        }
        reps.setGravity(Gravity.CENTER);
        newRow.addView(sets);
        newRow.addView(weight);
        newRow.addView(reps);

        if (timerC) { //check if timer option has been selected by the user
            time = new TextView(theContext);
            time.setText(R.string.defaultTime);
            start = System.currentTimeMillis();
            time.setGravity(Gravity.CENTER);
            newRow.addView(time);
        }
        table.addView(newRow,setNumber);
    }

    private void createFirstRowOnly() {
        if (!activityType) { //check for activity type, change edit text hints accordingly
            weight.setHint(R.string.addtl_weight);
        } else {
            weight.setHint(R.string.weight); //only for 1st one
        }
		
		//Edit Text Listeners used to predict user's second set and onwards setting by finding out what oarameters they entered in the first set
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    firstWeight = Integer.parseInt(s.toString());
                } catch (Exception ignored) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        reps.setHint(R.string.reps_text); //only for 1st one
        reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    firstReps = Integer.parseInt(s.toString());
                } catch (Exception ignored) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) { //button on click listeners for add/remove sets
        switch (v.getId()) {
            case R.id.add_more_sets:
                if (this.getClass() == Activities.class) { //polymorphism adaptation, check what kind of class so that appropriate method is run
                    createRow();
                } else if (this.getClass() == DistanceActivity.class) {
                    ((DistanceActivity)this).createDistanceRow();
                }
                //extra applicable only to 2nd row onwards
                if (firstWeight == 0 && this.getClass() == Activities.class) {
                    weight.setHint(R.string.weight);
                } else if (this.getClass() == Activities.class) {
                    weight.setText(String.format(Locale.getDefault(), "%d", firstWeight));
                }
                if (firstReps == 0 && this.getClass() == Activities.class) { //polymorphism, base class activity
                    reps.setHint(R.string.reps_text);
                } else if (firstReps == 0 && this.getClass() == DistanceActivity.class) { //polymorphism, distance class activity
                    reps.setHint(R.string.distance_text);
                } else { //reps/distance were defined, not 0, use that
                    reps.setText(String.format(Locale.getDefault(), "%d", firstReps));
                }
                break;
            case R.id.remove_sets:
                if (setNumber > 0) { //check to make sure to not remove the header row
                    table.removeViewAt(setNumber);
                    setNumber--;
                    if (timerC && setNumber > 0) { //resume last set timer if timers are selected
                        TableRow desiredRow = (TableRow) table.getChildAt(setNumber);
                        TextView theTimer;
                        if (this.getClass() == DistanceActivity.class) {
                            theTimer = (TextView) desiredRow.getChildAt(2); //1 less column in distance activity
                        } else {
                            theTimer = (TextView) desiredRow.getChildAt(3); //timer in 4th column for base activity
                        }

                        int prevM = getPreviousMins(theTimer.getText()); //previous timer's minutes 
                        int prevS = getPreviousSecs(theTimer.getText()); //previous timer's seconds

                        time = theTimer;
                        start = System.currentTimeMillis() - ((prevM*60)+prevS)*1000; //adjust the timer start accordingly
                        setTimer = new Timer();
                        setTimer.schedule(new runTimer(ourActivity), 1000, 1000);
                    }
                }
                break;
            default:
                break;
        }

    }

    int getPreviousMins(CharSequence oldTimerM) { //get the previous timer's minutes using regex expressions
        Pattern p = Pattern.compile("[0-5]?[0-9]:");
        Matcher m = p.matcher(oldTimerM);
        if (!m.find()) {
            return 0;
        }
        String previousTime = m.group();
        String minutes = previousTime.substring(0, previousTime.length()-1);
        return Integer.valueOf(minutes);
    }

    int getPreviousSecs(CharSequence oldTimerS) {//get the previous timer's seconds using regex expressions
        Pattern s = Pattern.compile(":[0-9][0-9]");
        Matcher n = s.matcher(oldTimerS);
        if (!n.find()) {
            return 0;
        }
        String prevTimes = n.group();
        String seconds = prevTimes.substring(1, prevTimes.length());
        return Integer.valueOf(seconds);
    }

}

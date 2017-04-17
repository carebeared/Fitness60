package shivamdh.com.fitness60;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Timer;

import static shivamdh.com.fitness60.Options.distanceC;
import static shivamdh.com.fitness60.Options.timerC;
import static shivamdh.com.fitness60.Options.weightC;


/*
The main java file used to correspond with the advanced distance activity, this activity is based on a distance-related activity and thus
contains different parameters for user to input, record and store. A similar layout is still used for the tables yet the data is stored 
differenty within the database 
 */

public class DistanceActivity extends Activities {
    private TextView repHeader;

    DistanceActivity(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum, Activity currActivity) {
        super(); //just call on a constructor from above
        defaultSetup(mainContainer, theLayout, theInflate, aContext, activityNum, currActivity);

        //replace reps from default activity with distance
        repHeader = (TextView) n.findViewById(R.id.reps_text);
        if (this.getClass() == DistanceActivity.class) { //check in fact that this is a distance activty
            if (distanceC) {
                repHeader.setText(R.string.kmC);
            } else {
                repHeader.setText(R.string.milesC);
            }
        } else { //meaning this is a bodyweight exercise activity
            if (weightC) {
                repHeader.setText(R.string.kgchoice);
            } else {
                repHeader.setText(R.string.lbsC);
            }
        }

        TableRow header = (TableRow) n.findViewById(R.id.new_workout_header);
        header.removeViewAt(1); //remove weight column

        //set the columns to correct widths
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);

        if (!timerC) { //remove table column if option is selected
            header.removeViewAt(2);
        } else { //keep timer, stretch the column to right size continue timer
            table.setColumnStretchable(2, true);
            start = System.currentTimeMillis();

            start = System.currentTimeMillis();
            setTimer = new Timer();
            setTimer.schedule(new runTimer(ourActivity), 1000, 1000);
        }
        createDistanceRow();
    }

    void createDistanceRow() {
        newRow = new TableRow(theContext);
        sets = new EditText(theContext);
        reps = new EditText(theContext);
        setNumber++;
        sets.setText(String.format(Locale.getDefault(), "%d", setNumber-1));
        if (setNumber == 2) {
            createFirstDistanceRowOnly(); //special syntax for first row only
        }

        sets.setGravity(Gravity.CENTER);
        reps.setGravity(Gravity.CENTER);
        newRow.addView(sets);
        newRow.addView(reps);

        if (timerC) { //add the timer column if user options selected permits
            time = new TextView(theContext);
            time.setText(R.string.defaultTime);
            start = System.currentTimeMillis();
            time.setGravity(Gravity.CENTER);
            newRow.addView(time);
        }
        table.addView(newRow,setNumber);

    }
	
	//creating the first row is different than other rows as user input is collected for faster use when creating multiple rows
    private void createFirstDistanceRowOnly() { 
        reps.setHint(R.string.distance_text);
		
		//text watchers for the edit text regarding reps only (no weights in distance related activity)
		//recorded and then displayed for future rows in case same info applies
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

}

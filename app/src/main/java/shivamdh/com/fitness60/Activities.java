package shivamdh.com.fitness60;

import android.app.Activity;
import android.content.Context;
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

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shivamdh.com.fitness60.Options.timerC;
import static shivamdh.com.fitness60.Options.weightC;


public class Activities implements View.OnClickListener{
    protected Button addSets, removeSets;
//    public Button deleteActivity;
    protected TableRow newRow;
    protected Context theContext;
    protected EditText sets, reps, weight;
    protected static TextView time, weights;
    protected int setNumber, firstWeight, firstReps;
    protected TableLayout table;
    public static long start;
    public Timer setTimer;
    private Activity ourActivity;

    public TableLayout getTable () {
        return table;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setTime(TextView givenTime) {
        time = givenTime;
    }

    Activities(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum, Activity currActivity) {
        theContext = aContext;
        ourActivity = currActivity;
        LinearLayout myView = (LinearLayout) theLayout.findViewById(R.id.layout);

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

        table = (TableLayout) n.findViewById(R.id.activity1);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);

        if (!timerC) {
            TableRow header = (TableRow) n.findViewById(R.id.new_workout_header);
            header.removeViewAt(3);
        } else {
            table.setColumnStretchable(3, true);
            start = System.currentTimeMillis();

            setTimer = new Timer();
            setTimer.schedule(new runTimer(ourActivity), 1000, 1000);
        }

        setNumber = 0;

        weights = (TextView) n.findViewById(R.id.weight_text);
        //creating first row
        createRow();

    }

    public static class runTimer extends TimerTask {
        private Activity theActivity;

        runTimer(Activity givenActivity){
            theActivity = givenActivity;
        }

        @Override
        public void run() {
            theActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    long mill = System.currentTimeMillis() - start;
                    int seconds = (int) (mill/1000 + 1); //account for lag from other methods
                    int minutes = seconds/60;
                    seconds = seconds % 60;
                    Log.d(String.valueOf(minutes), String.valueOf(seconds));
                    Log.d("FFF", (String) time.getText());
                    time.setText(String.format("%d:%02d", minutes, seconds));
                }
            });
        }
    }

    private void createRow() {
        newRow = new TableRow(theContext);
        sets = new EditText(theContext);
        weight = new EditText(theContext);
        reps = new EditText(theContext);
        setNumber++;
        sets.setText(Integer.toString(setNumber));
        if (setNumber == 1) {
            createFirstRowOnly();
        }

        sets.setGravity(Gravity.CENTER);
        weight.setGravity(Gravity.CENTER);

        if (!weightC){
            weights.setText(R.string.kgchoice);
        }
        reps.setGravity(Gravity.CENTER);
        newRow.addView(sets);
        newRow.addView(weight);
        newRow.addView(reps);

        if (timerC) {
            time = new TextView(theContext);
            time.setText(R.string.defaultTime);
            start = System.currentTimeMillis();
            time.setGravity(Gravity.CENTER);
            newRow.addView(time);
        }
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
                    if (timerC && setNumber > 0) {
                        TableRow desiredRow = (TableRow) table.getChildAt(setNumber);
                        TextView theTimer = (TextView) desiredRow.getChildAt(3);

                        int prevM = getPreviousMins(theTimer.getText());
                        int prevS = getPreviousSecs(theTimer.getText());

                        time = theTimer;
                        start = System.currentTimeMillis() - ((prevM*60)+prevS)*1000;
                        setTimer = new Timer();
                        setTimer.schedule(new runTimer(ourActivity), 1000, 1000);
                    }
                }
                break;
            default:
                break;
        }

    }

    public int getPreviousMins (CharSequence oldTimerM) {
        Pattern p = Pattern.compile("[0-5]?[0-9]:");
        Matcher m = p.matcher(oldTimerM);
        m.find();
        String previousTime = m.group();
        String minutes = previousTime.substring(0, previousTime.length()-1);
        return Integer.valueOf(minutes);
    }

    public int getPreviousSecs (CharSequence oldTimerS) {
        Pattern s = Pattern.compile(":[0-9][0-9]");
        Matcher n = s.matcher(oldTimerS);
        n.find();
        String prevTimes = n.group();
        String seconds = prevTimes.substring(1, prevTimes.length());
        return Integer.valueOf(seconds);
    }

//
//    @Override
//    public void run() {
//        SimpleDateFormat time = new SimpleDateFormat("mm:ss", Locale.CANADA);
//        Calendar theTime = Calendar.getInstance();
//        finalTime = time.format(theTime.getTime());
//    }
}

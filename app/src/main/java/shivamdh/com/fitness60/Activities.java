package shivamdh.com.fitness60;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
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

import static shivamdh.com.fitness60.Options.timerC;
import static shivamdh.com.fitness60.Options.weightC;


public class Activities implements View.OnClickListener{
    private Button addSets, removeSets;
//    public Button deleteActivity;
    private TableRow newRow;
    private Context theContext;
    private EditText sets, reps, weight;
    private TextView time, weights;
    private String finalTime;
    private int setNumber, firstWeight, firstReps;
    private TableLayout table;
    private static int howMany = 0;

    Activities(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum) {
        howMany++;
        theContext = aContext;
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
        }

        setNumber = 0;

        weights = (TextView) n.findViewById(R.id.weight_text);
        //creating first row
        createRow();
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
            time.setText("00:00");
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

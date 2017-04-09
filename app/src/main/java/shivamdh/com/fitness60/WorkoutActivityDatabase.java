package shivamdh.com.fitness60;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WorkoutActivityDatabase {

    private Context theContext;
    private SQLiteDatabase mDatabase;
    private DatabaseHelper DbHelper;

    private String[] ActivityColumns1 = {
            DatabaseHelper.COL6,
            DatabaseHelper.COL2_1,
            DatabaseHelper.COL3,
            DatabaseHelper.COL4,
            DatabaseHelper.COL_ACT
    };

    TableLayout theTable;
    TableRow theRow;
    EditText weight_or_distance, reps_or_nothing, workoutName;
    TextView timer;
    Boolean TableType;

    public WorkoutActivityDatabase(Context givenContext, Boolean type, TableLayout givenTable) {
        theContext = givenContext;
        DbHelper = new DatabaseHelper(theContext);
        theTable = givenTable;
        TableType = type;

        try {
            mDatabase = DbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.d("Database Opening", "Unable to open activity database");
        }
    }

    public void insertDataType (int weight, int reps, String timer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL2_1, weight);
        contentValues.put(DatabaseHelper.COL3, reps);
        contentValues.put(DatabaseHelper.COL4, timer);
        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public void addData () {
        int length = theTable.getChildCount() - 3; //accounts for the header row not having any data
        if (length < 0) {
            length = 0;
        }
        Log.d("LENGTH", String.valueOf(length));

        if (length >= 0) {
            TableRow aworkoutName = (TableRow) theTable.getChildAt(0);
            workoutName = (EditText) aworkoutName.getChildAt(0); //gets the workout name, but not utilized anywhere
            //TODO: use this when creating the table of sets for each activity
        }
        for (int i = 0; i < length; i++) {
            //TODO: Fix any possiblitity of errors within this section
            theRow = (TableRow) theTable.getChildAt(i + 2);
            weight_or_distance = (EditText) theRow.getChildAt(1);
            Log.d("FSM", String.valueOf(weight_or_distance.getText().toString()));
            String weight_or_distance_text = weight_or_distance.getText().toString();
            String reps_or_nothing_text;

            if (TableType) {
                reps_or_nothing = (EditText) theRow.getChildAt(2);
                reps_or_nothing_text = reps_or_nothing.getText().toString();
                timer = (TextView) theRow.getChildAt(3);
            } else {
                reps_or_nothing_text = "-1";
                timer = (TextView) theRow.getChildAt(2);
            }

            String timerText = timer.getText().toString();
            int weightORtextNUM, repsORnothingNUM;
            try {
                weightORtextNUM = Integer.parseInt(weight_or_distance_text);
            } catch (NumberFormatException e) {
                weightORtextNUM = 0;
            }
            try {
                repsORnothingNUM  = Integer.parseInt(reps_or_nothing_text);
            } catch (NumberFormatException e) {
                repsORnothingNUM = 0;
            }

            insertDataType(weightORtextNUM, repsORnothingNUM , timerText);
        }

    }

    public Cursor getAllData() {
        Cursor resultTable;
        resultTable = mDatabase.rawQuery("select * from " + DatabaseHelper.TABLE_NAME, null);
        return resultTable;
    }

}

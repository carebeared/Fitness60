package shivamdh.com.fitness60;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

public class AppDatabase extends SQLiteOpenHelper {

    public static int countWorkouts = 1;
    public String DATABASE_NAME;
    public static final String TABLE_NAME = "user_workouts";
    public static final String COL1 = "SETS";
    public static final String COL2 = "REPS";
    public static final String COL3 = "user_workouts";
    public static final String COL4 = "user_workouts";
    private Boolean TableType;
    TableLayout theTable;

    public AppDatabase(Context context, Boolean type) {
        super(context, String.format(Locale.getDefault(), "workout%d.db", ++countWorkouts), null, 1);
        DATABASE_NAME = String.format(Locale.getDefault(), "workout%d.db", ++countWorkouts);
        TableType = type;
        // theTable = givenTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (TableType) {
            db.execSQL("create table " + TABLE_NAME + " ( SETS_ID INTEGER PRIMARY KEY AUTOINCREMENT, LBS INTEGER, REPS INTEGER, TIMER TEXT )");
            Log.d("Normal", "Database");
        } else {
            db.execSQL("create table" + TABLE_NAME + " ( SETS INTEGER PRIMARY KEY AUTOINCREMENT, LBS INTEGER, REPS INTEGER, TIMER TEXT )");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData (int weight, int reps, String timer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, weight);
        contentValues.put(COL3, reps);
        contentValues.put(COL4, timer);
        db.insert(TABLE_NAME, null, contentValues);
    }

    public void addData () {
        int length = theTable.getChildCount() - 1; //accounts for the header row not having any data
        for (int i = 0; i < length; i++) {
            TableRow theRow = (TableRow) theTable.getChildAt(i+1);
            EditText weight, distance;
            weight = (EditText) theRow.getChildAt(1);
            distance = (EditText) theRow.getChildAt(2);
            TextView timer = (TextView) theRow.getChildAt(3);
            insertData(Integer.valueOf(weight.getText().toString()), Integer.valueOf(distance.getText().toString()), timer.getText().toString());
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_NAME, null);
        return result;
    }

}

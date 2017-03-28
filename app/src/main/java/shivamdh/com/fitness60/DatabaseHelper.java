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

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int countWorkouts = 1;
    public static final String TABLE_NAME = "user_workouts";
    public static final String TABLE_NAME_MAIN = "user_data";
    static final String COL2_1 = "LBS";
    static final String COL2_2 = "KGS";
    static final String COL3 = "REPS";
    static final String COL4 = "TIMER";
    static final String COL5_1 = "MILES";
    static final String COL5_2 = "KMS";
    static final String COL6 = "SETS";
    static final String COL7 = "TYPE";
    static final String COL_ACT = "ACTIVITIY";
    private static final String DATABASE_NAME = "fitness60app.db";
    Context theContext;


    private static final String SQL_CREATE_ACTIVITES_TABLE1 =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "SETS_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL2_1 + " INTEGER," +
                    COL3 + " INTEGER," +
                    COL3 + " TEXT," +
                    COL_ACT + " TEXT)";

    private static final String SQL_CREATE_DATABASE_LOG =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ACTIVITY_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL6 + " INTEGER," +
                    COL7 + " TEXT)";

    public DatabaseHelper(Context context, int previousSets) {
        super(context, DATABASE_NAME, null, 2);
        if (previousSets > 1) {
            countWorkouts = previousSets;
        }
        theContext = context;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ACTIVITES_TABLE1);
        db.execSQL(SQL_CREATE_DATABASE_LOG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MAIN);
        onCreate(db);
    }

}

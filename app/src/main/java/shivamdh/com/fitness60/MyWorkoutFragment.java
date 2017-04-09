package shivamdh.com.fitness60;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWorkoutFragment extends Fragment {
    Context theContext;

    public MyWorkoutFragment() {
        // Required empty public constructor
    }

    public void setTheContext (Context givenContext) {
        theContext = givenContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static void pressBack(Context givenContext) {
        Toast aToast = Toast.makeText(givenContext, R.string.return_home, Toast.LENGTH_SHORT);
        aToast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.myworkouts, container, false);

        final TextView myText = (TextView) myView.findViewById(R.id.workout_text1);
        myText.setText("List of activities underneath");

        LinearLayout workoutTab = (LinearLayout) myView.findViewById(R.id.myworkout_tab);

        if (NewWorkout.done && NewWorkout.Save) {
            TextView aText = new TextView(getContext());
            Cursor theData;
            if (NewWorkout.myData != null) {
                theData = NewWorkout.myData.getAllData();
                StringBuffer buffer = new StringBuffer();
                if (theData.getCount() > 0) {
                    while (theData.moveToNext()) {
                        buffer.append("\n" + "Set " + theData.getString(0) + "\n");
                        buffer.append("Weight " + theData.getString(1) + "\n");
                        buffer.append("Reps " + theData.getString(2) + "\n");
                        buffer.append("Timer " + theData.getString(3) + "\n");
                    }
                }
                aText.setText(buffer.toString());
                workoutTab.addView(aText);
            }
        }

        Button new_workout;
        new_workout = (Button) myView.findViewById(R.id.button_new_workout);

        new_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWorkout new_workout = new NewWorkout();
                new_workout.setTheContext(theContext);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, new_workout);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return myView;
    }

}

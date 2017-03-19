package shivamdh.com.fitness60;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWorkoutFragment extends Fragment {


    public MyWorkoutFragment() {
        // Required empty public constructor
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

        Button new_workout;
        new_workout = (Button) myView.findViewById(R.id.button_new_workout);

        new_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewWorkout new_workout = new NewWorkout();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_container, new_workout);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });

        return myView;
    }

}

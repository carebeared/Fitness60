package shivamdh.com.fitness60;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/*
A class to be utlized later for displaying the user's past activity and giving some feedback and analysis on that
Currently, it is still in progress and serves only as a filler app page for what is to come
 */
public class WorkoutAnalysisFragment extends Fragment {


    public WorkoutAnalysisFragment() {
        // Required empty public constructor
    }

    public static void backisPressed(Context getContext) {
        Toast aToast = Toast.makeText(getContext, R.string.return_home, Toast.LENGTH_SHORT);
        aToast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.workout_analysis, container, false);

        return myView;
    }

}

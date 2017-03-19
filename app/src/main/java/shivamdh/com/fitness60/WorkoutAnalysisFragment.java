package shivamdh.com.fitness60;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
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

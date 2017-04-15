package shivamdh.com.fitness60;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/*
This is the home page of the app, future designs include some functionality regarding past activity and preferences
Currently, it is a filler app page that is not used to its full extent
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_home_fragement, container, false);

        FrameLayout theHome = (FrameLayout) myView.findViewById(R.id.home_page);

        // Inflate the layout for this fragment
        return myView;
    }

}

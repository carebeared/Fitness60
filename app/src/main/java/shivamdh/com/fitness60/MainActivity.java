package shivamdh.com.fitness60;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("Ented", "Entered");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            View optionFrag = this.findViewById(R.id.options_tab);
            View workoutFrag = this.findViewById(R.id.myworkout_tab);
            View analysisFrag = this.findViewById(R.id.analysis_page);
            View newWorkoutFrag = this.findViewById(R.id.layout);
            if (optionFrag != null && optionFrag.getVisibility() == View.VISIBLE) {
                Options.backPressed(getApplicationContext());
                openHome();
                return true;
            } else if (workoutFrag != null && workoutFrag.getVisibility() == View.VISIBLE){
                MyWorkoutFragment.pressBack(getApplicationContext());
                openHome();
                return true;
            } else if (analysisFrag != null && analysisFrag.getVisibility() == View.VISIBLE) {
                WorkoutAnalysisFragment.backisPressed(getApplicationContext());
                openHome();
                return true;
            } else if (newWorkoutFrag != null && newWorkoutFrag.getVisibility() == View.VISIBLE) {
                NewWorkout.MyDialogFragment exitWorkout = new NewWorkout.MyDialogFragment();
                exitWorkout.show(getSupportFragmentManager(), "M");
                openHome();
            }
        }
        Log.d("CA", "LLED");
        return super.onKeyDown(keyCode, event);
    }

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the fragment initally
        HomeFragment fragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fitness60");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_home) {
            openHome();
        } else if (id == R.id.nav_workouts) {
            MyWorkoutFragment fragment = new MyWorkoutFragment();
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "WorkoutFrag");
            fragmentTransaction.commit();
            toolbar.setTitle("My Workouts");
        } else if (id == R.id.nav_analysis) {
            WorkoutAnalysisFragment fragment = new WorkoutAnalysisFragment();
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "AnalysisFrag");
            fragmentTransaction.commit();
            toolbar.setTitle("Workout Analysis");
        } else if (id == R.id.nav_options) {
            Options fragment = new Options();
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "OptionFrag");
            fragmentTransaction.commit();
            toolbar.setTitle("Your Options");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openHome() {
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "HomeFrag");
        fragmentTransaction.commit();
        toolbar.setTitle("Fitness60");
    }
}

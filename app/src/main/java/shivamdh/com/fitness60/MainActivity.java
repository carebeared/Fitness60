package shivamdh.com.fitness60;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

/*
This is the main java file that is used in the app to coordinate the multiple app fragments as well as different sections and
functionalities of the app. This file coordinates which fragment to open, and manages the top toolbar as well as the navigation drawer present on 
the left side of the app rendering. The code also ensures all the renderings and fragment are smoothly operated by the UI when the user uses the app
*/

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

	//override method for configuring custom actions for back button pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            View optionFrag = this.findViewById(R.id.options_tab);
            View workoutFrag = this.findViewById(R.id.myworkout_tab);
            View analysisFrag = this.findViewById(R.id.analysis_page);
            View newWorkoutFrag = this.findViewById(R.id.layout);
            if (optionFrag != null && optionFrag.getVisibility() == View.VISIBLE) { 
			//back button pressed from options menu
                Options.backPressed(getApplicationContext());
                openHome();
                return true;
            } else if (workoutFrag != null && workoutFrag.getVisibility() == View.VISIBLE){
			//back button pressed from workout fragment
                MyWorkoutFragment.pressBack(getApplicationContext());
                openHome();
                return true;
            } else if (analysisFrag != null && analysisFrag.getVisibility() == View.VISIBLE) {
			//back button pressed from workout analysis fragment
                WorkoutAnalysisFragment.backisPressed(getApplicationContext());
                openHome();
                return true;
            } else if (newWorkoutFrag != null && newWorkoutFrag.getVisibility() == View.VISIBLE) {
			//back button pressed from new workout fragment
                NewWorkout.MyDialogFragment exitWorkout = new NewWorkout.MyDialogFragment();
                exitWorkout.show(getSupportFragmentManager(), "M");
                openHome();
            }
        }
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

		//get the top toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

		//navigation drawer and its layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() { //what happens when the back button is pressed when the side toolbar is opened
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) { //home selected
            openHome();
        } else if (id == R.id.nav_workouts) { //workouts tab selected from menu
            MyWorkoutFragment fragment = new MyWorkoutFragment();
            fragment.setTheContext(getApplicationContext()); //set fragment background
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment); //replace existing container with new fragment
            fragmentTransaction.commit(); //commit or push the action
            toolbar.setTitle(R.string.my_workouts); //change the toolbar at the top to display correct name
        } else if (id == R.id.nav_analysis) { //workout analysis tab selected from menu
            WorkoutAnalysisFragment fragment = new WorkoutAnalysisFragment();
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            toolbar.setTitle(R.string.workout_analysis);
        } else if (id == R.id.nav_options) { //options tab selected from menu
            Options fragment = new Options();
            fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            toolbar.setTitle(R.string.options);
        }

		//close the drawer once the user selects an option from it
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 
    private void openHome() { //private method used to render the home page, used often on back button pressed or home selection
        HomeFragment fragment = new HomeFragment();
        fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        toolbar.setTitle(R.string.app_name);
    }
}
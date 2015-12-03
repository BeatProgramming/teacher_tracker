package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.exception.CSVException;
import beatprogramming.github.com.teacker_tracker.fragments.ReviewFragment;
import beatprogramming.github.com.teacker_tracker.fragments.StudentFragment;
import beatprogramming.github.com.teacker_tracker.fragments.SubjectFragment;
import beatprogramming.github.com.teacker_tracker.fragments.TaskFragment;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.util.CSVManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment frag;
        final Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            //uri = intent.getStringExtra("URI");
            Uri uri = intent.getData();

            List<Student> newStudents = CSVManager.getInstance(this).importStudents(uri.getEncodedPath());

            if (newStudents != null) {
                // Add students to database.
            }

            frag = new StudentFragment();

        } else {

            frag = new TaskFragment();

        }

        frag.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, frag).commit();
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
        getMenuInflater().inflate(R.menu.fragment_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment frag;

        if (id == R.id.nav_subject) {

            frag = new SubjectFragment();
            replaceFragment(frag);

        } else if (id == R.id.nav_score) {

            frag = new ReviewFragment();
            replaceFragment(frag);

        } else if (id == R.id.nav_export_students) {

            exportStudentList();

        } else if (id == R.id.nav_manage_students) {

            frag = new StudentFragment();
            replaceFragment(frag);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exportStudentList() {

        List<Student> studentsExample = new ArrayList<Student>();
        studentsExample.add(new Student("Juan Carlos", "González"));
        File dir = getExternalFilesDir(null);
        String outputMessage = null;
        try {
            CSVManager.getInstance(this).exportStudents(dir, studentsExample);
            outputMessage = "Alumnos exportados a " + dir.getAbsolutePath();
        } catch (CSVException e) {
            outputMessage = e.getMessage();
        }
        Toast.makeText(this, outputMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStackImmediate();
    }

}

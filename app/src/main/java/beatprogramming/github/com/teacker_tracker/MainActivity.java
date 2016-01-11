package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.exception.CSVException;
import beatprogramming.github.com.teacker_tracker.fragments.ImportStudentsFragment;
import beatprogramming.github.com.teacker_tracker.fragments.ReviewFragment;
import beatprogramming.github.com.teacker_tracker.fragments.StudentTabFragment;
import beatprogramming.github.com.teacker_tracker.fragments.SubjectFragment;
import beatprogramming.github.com.teacker_tracker.fragments.TaskFragment;
import beatprogramming.github.com.teacker_tracker.util.CSVManager;
import beatprogramming.github.com.teacker_tracker.util.HelpUtil;

/**
 * - Activity principal de la app
 *   Carga la BDD, las preferencias, el modo ayuda y los fragmentos
 *   Implementa los metodos de selección del menú de configuración y navegación
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {

    private static final String TAG = MainActivity.class.getName();
    private boolean help_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //- Valores predefinidos de las preferencias de la app
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        //- Carga de la BDD
        BDHelper.init(this);
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

        //- Obtenemos los valores de las preferencias
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(header);
        TextView text = (TextView) header.findViewById(R.id.textView3);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String user_name = pref.getString("user_name", "?");
        String user_mail = pref.getString("user_mail", "?");
        help_mode = pref.getBoolean("help",true);
        if (text!=null){
            text.setText(user_name + " (" + user_mail + ")");
        }
        Log.d("debug", "onCrete de main");
        //- Activado el modo ayuda
        if(help_mode)
            HelpUtil.showIndexHelp(this, new String[] {getResources().getString(R.string.helpIndex1),
                    getResources().getString(R.string.helpIndex2),getResources().getString(R.string.helpIndex3),
                    getResources().getString(R.string.helpIndex4)
            });


        Fragment frag;
        final Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();

            List<Student> newStudents = CSVManager.getInstance(this).importStudents(uri);

            for(Student student: newStudents) {
                Log.d(TAG, "onCreate, student: " + student.toString());
            }

            frag = ImportStudentsFragment.newInstance(newStudents);

            if (newStudents != null) {
                Bundle extras = new Bundle();
                extras.putSerializable(ImportStudentsFragment.STUDENTS, (Serializable) newStudents);
                getIntent().putExtras(extras);
            }

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
            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);
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

        if(id == R.id.nav_task) {

            frag = new TaskFragment();
            replaceFragment(frag);
            //- Activado el modo ayuda
            if(help_mode)
                HelpUtil.showTaskHelp(this, new String[] {getResources().getString(R.string.helpTask1),
                        getResources().getString(R.string.helpTask2),getResources().getString(R.string.helpTask3)
                });
        }
        if (id == R.id.nav_subject) {

            frag = new SubjectFragment();
            replaceFragment(frag);
            //- Activado el modo ayuda
            if(help_mode)
                HelpUtil.showSubjectHelp(this,new String[] {getResources().getString(R.string.helpSubject1),
                    getResources().getString(R.string.helpSubject2),getResources().getString(R.string.helpSubject3)
            });

        } else if (id == R.id.nav_score) {

            frag = new ReviewFragment();
            replaceFragment(frag);
            //- Activado el modo ayuda
            if(help_mode)
                HelpUtil.showScoreHelp(this, new String[] {getResources().getString(R.string.helpReview1),
                        getResources().getString(R.string.helpReview2),getResources().getString(R.string.helpReview3)
                });

        } else if (id == R.id.nav_export_students) {

            exportStudentList();

        } else if (id == R.id.nav_manage_students) {

            frag = new StudentTabFragment();
            replaceFragment(frag);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exportStudentList() {

        File dir = getExternalFilesDir(null);
        try {
            Intent intent = CSVManager.getInstance(this).exportStudents(dir);
            startActivity(Intent.createChooser(intent, "Send Mail"));
        } catch (CSVException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void showDialog(DialogFragment fragment) {
        fragment.show(getSupportFragmentManager(), null);
    }

}

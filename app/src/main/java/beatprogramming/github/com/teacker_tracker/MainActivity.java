package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.exception.CSVException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lista_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ScriptSQL sql = new ScriptSQL(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubjectActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instancia del ListView
        lista_main = (ListView) findViewById(R.id.listViewMain);

        Cursor c = getTask();

        //- Creación de la lista
        String[] colSubjects = new String[]{"_id","hora","aula","nombreAsignatura"};
        MatrixCursor cursor = new MatrixCursor(colSubjects);
        if(c.moveToFirst()){
            do{
                cursor.addRow(new Object[]{c.getString(0),c.getString(1),
                        c.getString(3),c.getString(4)});
            }while(c.moveToNext());
        }
        String[] cols = {"hora","aula","nombreAsignatura"};
        int[] viewSubjects = {R.id.hour_main,R.id.classroom_main,R.id.subject_main};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.listview_main_row,cursor,cols,viewSubjects,0);
        lista_main.setAdapter(adapter);
        lista_main.setOnItemClickListener(itemClickListener);

    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView nombre_asignatura = (TextView) view.findViewById(R.id.subject_main);
            String nombreAsignatura = nombre_asignatura.getText().toString();
            Intent intent = new Intent(MainActivity.this,NotesActivity.class);
            intent.putExtra("asignatura",nombreAsignatura);
            Log.i("NOTE-ACTIVITY",nombreAsignatura);
            startActivity(intent);
        }
    };

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

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow){
            Intent intent = new Intent(MainActivity.this,EditSubjectActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_score) {
            Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this,TaskActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_export_students) {
            exportStudentList();
        } else if (id == R.id.nav_manage_students) {
            Intent intent = new Intent(this, NewStudentsActivity.class);
            startActivity(intent);
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

    public Cursor getTask(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {"_id","hora","descripcion","aula","nombreAsignatura"};

        //- Ejecución de la query
        Cursor c = db.query("Tarea", campos, null, null, null, null, null);

        //- Devolvemos el conjunto de Asignaturas
        return c;
    }
}

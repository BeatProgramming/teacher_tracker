package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AddScoreActivity extends AppCompatActivity {

    private final static String ID = "_id";
    private final static String NOMBRE_EXAMEN = "nombre";
    private final static String FECHA = "fecha";
    private final static String ALUMNO = "alumno";
    private final static String EXAMEN = "Examen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        //- Creación de la lista de exámenes
        createList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button_create_exam);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddScoreActivity.this, SubmitScoreActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * - Método que genera la lista de exámenes por asignatura listos para calificar.
     */
    public void createList(){
        //- Obtención de la lista
        ListView listview = (ListView) findViewById(R.id.subject_add_score_list_view);

        //- Obtención de los exámenes
        Cursor c = getExams();

        //- Generación de la lista con los exámenes correspondientes
        String[] colSubjects = new String[]{ID,NOMBRE_EXAMEN,FECHA};
        MatrixCursor cursor = new MatrixCursor(colSubjects);
        if(c.moveToFirst()){
            do{
                cursor.addRow(new Object[]{c.getString(0),c.getString(1),c.getString(2)});
            }while(c.moveToNext());
        }
        String[] cols = {NOMBRE_EXAMEN,FECHA};
        int[] viewSubjects = {R.id.item_name,R.id.item_first_name};
        SimpleCursorAdapter adapter =  new SimpleCursorAdapter(this,R.layout.pupils_listview_entry,cursor,cols,viewSubjects,0);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(AddScoreActivity.this,SubmitScoreActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_score, menu);
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

    /**
     * - Método que obtiene los exámenes de una determinada asignatura
     * @return Exámenes
     */
    public Cursor getExams(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {ID,NOMBRE_EXAMEN,FECHA};
        String asignatura = getIntent().getExtras().getString("NombreAsignatura");
        String[] asignatura_value = new String[]{asignatura};

        //- Ejecución de la query
        Cursor c = db.query(EXAMEN, campos, "nombreAsignatura=?", asignatura_value, null, null, null);

        //- Devolvemos el conjunto de Asignaturas
        return c;
    }
}

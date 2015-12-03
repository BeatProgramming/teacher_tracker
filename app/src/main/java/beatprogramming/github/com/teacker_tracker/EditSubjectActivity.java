package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class EditSubjectActivity extends AppCompatActivity {

    private final static String ID = "_id";
    private final static String ASIGNATURA = "Asignatura";
    private final static String CURSO = "Curso";
    private final static String NOMBRE_ASIGNATURA = "nombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        createList();
    }

    /**
     * - Método que muestra las asignaturas del usuario listas para ser editadas.
     */
    public void createList(){
        //- Obtención del identificador de la lista
        ListView listview = (ListView) findViewById(R.id.subject_listview);

        //- Obtención de las asignaturas
        Cursor c = getAsignaturas();

        //- Creación de la lista
        String[] colSubjects = new String[]{ID,ASIGNATURA,CURSO};
        MatrixCursor cursor = new MatrixCursor(colSubjects);
        if(c.moveToFirst()){
            do{
                cursor.addRow(new Object[]{c.getString(0),c.getString(1),c.getString(2)});
            }while(c.moveToNext());
        }
        String[] cols = {ASIGNATURA,CURSO};
        int[] viewSubjects = {R.id.item_subject,R.id.item_edit};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.subject_listview_entry,cursor,cols,viewSubjects,0);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textoTitulo = (TextView) view.findViewById(R.id.item_subject);
            TextView textoCurso = (TextView) view.findViewById(R.id.item_edit);
            Intent intent = new Intent(EditSubjectActivity.this,ModifySubjectActivity.class);
            intent.putExtra("asignatura",textoTitulo.getText());
            intent.putExtra("curso",textoCurso.getText());
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_subject, menu);
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
     * - Método que obtiene todas las asignaturas del usuario
     * - @return Asignaturas
     */
    public Cursor getAsignaturas(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {ID,NOMBRE_ASIGNATURA,CURSO,};

        //- Ejecución de la query
        Cursor c = db.query(ASIGNATURA, campos, null, null, null, null, null);

        //- Devolvemos el conjunto de Asignaturas
        return c;
    }

}

package beatprogramming.github.com.teacker_tracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifySubjectActivity extends AppCompatActivity {

    private final static String ASIGNATURA = "asignatura";
    private final static String CURSO = "curso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_subject);

        //- Obtenemos los datos de la asignatura seleccionada en la activity anterior
        EditText subject = (EditText) findViewById(R.id.subjectName);
        EditText curseSubject = (EditText) findViewById(R.id.subjectCurse);
        String asignatura = getIntent().getExtras().getString(ASIGNATURA);
        String curso = getIntent().getExtras().getString(CURSO);
        subject.setHint(asignatura);
        curseSubject.setHint(curso);

        //- Actualización de la asignatura
        Button editar = (Button) findViewById(R.id.button);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editSubject();
                Intent intent = new Intent(ModifySubjectActivity.this, EditSubjectActivity.class);
                startActivity(intent);
            }
        });

        //- Eliminación de una asignatura
        Button borrar = (Button) findViewById(R.id.button_delete);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSubject();
                Intent intent = new Intent(ModifySubjectActivity.this, EditSubjectActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify_subject, menu);
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
     * - Método que modifica los datos de una asignatura
     */
    public void editSubject(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Obtención de los datos nuevos a modificar de la asignatura
        EditText nombreAsignatura = (EditText) findViewById(R.id.subjectName);
        EditText descripcionAsignatura = (EditText) findViewById(R.id.subjectDescription);
        EditText cursoAsignatura = (EditText) findViewById(R.id.subjectCurse);
        String nombre_asignatura = nombreAsignatura.getText().toString();
        String descripcion_asignatura = descripcionAsignatura.getText().toString();
        String curso_asignatura = cursoAsignatura.getText().toString();
        ContentValues values = new ContentValues();
        values.put("nombre",nombre_asignatura);
        values.put("descripcion",descripcion_asignatura);
        values.put("curso",curso_asignatura);
        String[] name = new String[]{nombre_asignatura};

        //- Actualización de la asignatura
        db.update("Asignatura",values,"nombre=?",name);
        db.close();

        //- Mensaje de confirmación de actualización
        Toast.makeText(getApplicationContext(),"ASIGNATURA EDITADA CON ÉXITO",Toast.LENGTH_LONG).show();
    }

    /**
     * - Método que borrar una determinada asignatura de la BD
     */
    public void deleteSubject(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Obtenemos el nombre de la asignatura que queremos borrar
        EditText nombreAsignatura = (EditText) findViewById(R.id.subjectName);
        String nombre_asignatura = nombreAsignatura.getHint().toString();
        String[] values = new String[]{nombre_asignatura};

        //- Borramos la asignatura
        db.delete("Asignatura", "nombre=?", values);
        db.close();

        //- Mensaje de confirmación del borrrado
        Toast.makeText(getApplicationContext(),"ASIGNATURA BORRADA CON ÉXITO",Toast.LENGTH_LONG).show();
    }
}

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

public class SubjectActivity extends AppCompatActivity {

    private final static String NOMBRE = "nombre";
    private final static String CURSO = "curso";
    private final static String DESCRIPCION = "descripcion";
    private final static String ASIGNATURA = "Asignatura";
    private final static String VACIO = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        //- Creación de la asignatura
        Button confirmar = (Button) findViewById(R.id.button);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSubject();
                Intent intent = new Intent(SubjectActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subject, menu);
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
     * - Método que crea una asignatura
     */
    public void createSubject(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Obtención de los datos de la asignatura
        EditText nombre_asignatura = (EditText) findViewById(R.id.subjectName);
        EditText curso_asignatura = (EditText) findViewById(R.id.subjectCurse);
        EditText descripcion_asignatura = (EditText) findViewById(R.id.subjectDescription);
        String nombreAsignatura = nombre_asignatura.getText().toString();
        String cursoAsignatura = curso_asignatura.getText().toString();
        String descripcionAsignatura = descripcion_asignatura.getText().toString();
        ContentValues asignatura = new ContentValues();
        asignatura.put(NOMBRE,nombreAsignatura);
        asignatura.put(CURSO,cursoAsignatura);
        asignatura.put(DESCRIPCION,descripcionAsignatura);

        //- Creación de la asignatura
        db.insert(ASIGNATURA, null, asignatura);
        db.close();

        //- Inserción de datos vacía para futuras creaciones
        nombre_asignatura.setHint(VACIO);
        curso_asignatura.setHint(VACIO);
        descripcion_asignatura.setHint(VACIO);

        //- Mensaje de confirmación de la creación
        Toast.makeText(this.getApplicationContext(),"ASIGNATURA CREADA CON EXITO",Toast.LENGTH_LONG).show();
    }
}

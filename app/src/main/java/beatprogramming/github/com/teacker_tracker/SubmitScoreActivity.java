package beatprogramming.github.com.teacker_tracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitScoreActivity extends AppCompatActivity {

    private final static String EXAMEN = "Examen";
    private final static String VACIO = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_score);

        Button button = (Button) findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExamen();
                Intent intent = new Intent(SubmitScoreActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_score, menu);
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

    public void createExamen(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Obtención de los datos del exámen
        EditText nombreExamen = (EditText) findViewById(R.id.examName);
        EditText fechaExamen = (EditText) findViewById(R.id.examDate);
        EditText nombreAsignatura = (EditText) findViewById(R.id.examNameSubject);
        String nombre_examen = nombreExamen.getText().toString();
        String fecha_examen = fechaExamen.getText().toString();
        String nombre_asignatura = nombreAsignatura.getText().toString();
        ContentValues examen = new ContentValues();
        examen.put("nombre",nombre_examen);
        examen.put("fecha",fecha_examen);
        examen.put("nombreAsignatura",nombre_asignatura);

        //- Creación del exámen
        db.insert(EXAMEN, null, examen);
        db.close();

        //- Inserción de datos vacía para futuras creaciones
        nombreExamen.setHint(VACIO);
        fechaExamen.setHint(VACIO);
        nombreAsignatura.setHint(VACIO);

        //- Mensaje de confirmación de la creación
        Toast.makeText(this.getApplicationContext(),"EXÁMEN CREADO CON EXITO",Toast.LENGTH_LONG).show();
    }
}

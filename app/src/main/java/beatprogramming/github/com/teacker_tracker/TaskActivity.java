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
import android.widget.Toast;

public class TaskActivity extends AppCompatActivity {

    private final static String VACIO = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Button confirmar = (Button) findViewById(R.id.button_add_task);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
                Intent intent = new Intent(TaskActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
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

    public void createTask(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Obtención de los datos de la asignatura
        EditText hora_tarea = (EditText) findViewById(R.id.taskHour);
        EditText descripcion_tarea = (EditText) findViewById(R.id.taskDescription);
        EditText aula_tarea = (EditText) findViewById(R.id.taskRoom);
        EditText asignatura_tarea = (EditText) findViewById(R.id.taskSubject);
        String horaTarea = hora_tarea.getText().toString();
        String descripcionTarea = descripcion_tarea.getText().toString();
        String aulaTarea = aula_tarea.getText().toString();
        String asignaturaTarea = asignatura_tarea.getText().toString();
        ContentValues tarea = new ContentValues();
        tarea.put("hora", horaTarea);
        tarea.put("descripcion", descripcionTarea);
        tarea.put("aula", aulaTarea);
        tarea.put("nombreAsignatura", asignaturaTarea);

        //- Creación de la asignatura
        db.insert("Tarea", null, tarea);
        db.close();

        //- Inserción de datos vacía para futuras creaciones
        hora_tarea.setHint(VACIO);
        descripcion_tarea.setHint(VACIO);
        aula_tarea.setHint(VACIO);
        asignatura_tarea.setHint(VACIO);

        //- Mensaje de confirmación de la creación
        Toast.makeText(this.getApplicationContext(), "TAREA CREADA CON EXITO", Toast.LENGTH_LONG).show();
    }
}

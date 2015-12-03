package beatprogramming.github.com.teacker_tracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        EditText nota = (EditText) findViewById(R.id.subject_note);
        Cursor c = getSubjectNote();
        if(c.moveToFirst()){
            String notas = c.getString(0);
            nota.setText(notas);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
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

    @Override
    public void onBackPressed(){
        saveSubjectNote();
        super.onBackPressed();
    }

    public Cursor getSubjectNote(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        //- Campos que queremos obtener al realizar la query
        String[] campos = new String[] {"nota"};
        String asignatura = getIntent().getExtras().getString("asignatura");
        String[] valores = new String[]{asignatura};

        Cursor c = db.query("Tarea",campos,null,null,null,null,null);

        return c;
    }

    public void saveSubjectNote(){
        //- Conexión con la BD
        BDHelper bd = new BDHelper(this);
        SQLiteDatabase db = bd.getWritableDatabase();

        EditText notas_asignatura = (EditText) findViewById(R.id.subject_note);
        String notasAsignatura = notas_asignatura.getText().toString();
        ContentValues values = new ContentValues();
        values.put("nota",notasAsignatura);
        String asignatura = getIntent().getExtras().getString("asignatura");
        String[] valores = new String[]{asignatura};

        db.update("Tarea", values, "nombreAsignatura=?", valores);
        db.close();
    }
}

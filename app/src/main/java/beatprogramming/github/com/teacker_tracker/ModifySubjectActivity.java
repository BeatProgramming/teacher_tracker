package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        EditText subject = (EditText) findViewById(R.id.subjectName);
        EditText curseSubject = (EditText) findViewById(R.id.subjectCurse);
        String asignatura = getIntent().getExtras().getString(ASIGNATURA);
        String curso = getIntent().getExtras().getString(CURSO);
        subject.setHint(asignatura);
        curseSubject.setHint(curso);

        Button editar = (Button) findViewById(R.id.button);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifySubjectActivity.this,EditSubjectActivity.class);
                Toast.makeText(getApplicationContext(), "NO DISPONIBLE", Toast.LENGTH_LONG).show();
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
}

package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.database.MatrixCursor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        ListView listview = (ListView) findViewById(R.id.subject_listview);

        String[] colSubjects = new String[]{"_id","Asignatura","Curso"};
        MatrixCursor cursor = new MatrixCursor(colSubjects);
        cursor.addRow(new Object[]{"0","Matemáticas","1 ESO"});
        cursor.addRow(new Object[]{"1","Lengua","2 ESO"});
        cursor.addRow(new Object[]{"2","Filosofía","2 ESO"});
        String[] cols = {"Asignatura","Curso"};
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

}

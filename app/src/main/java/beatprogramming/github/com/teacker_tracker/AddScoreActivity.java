package beatprogramming.github.com.teacker_tracker;

import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AddScoreActivity extends AppCompatActivity {

    private final static String ID = "_id";
    private final static String NOMBRE = "nombre";
    private final static String APELLIDO = "apellido";
    private final static String ALUMNO = "alumno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_score);

        createList();
    }

    /**
     * - Método que genera la lista de alumnos por asignatura listos para calificar.
     */
    public void createList(){
        ListView listview = (ListView) findViewById(R.id.subject_add_score_list_view);
        String[] colSubjects = new String[]{ID,NOMBRE,APELLIDO};

        MatrixCursor cursor = new MatrixCursor(colSubjects);
        cursor.addRow(new Object[]{"0","Adrián","Martín Gómez"});
        cursor.addRow(new Object[]{"1","Juan Carlos","González Cabrero"});
        cursor.addRow(new Object[]{"2","Óscar","Fernández Núñez"});
        String[] cols = {NOMBRE,APELLIDO};
        int[] viewSubjects = {R.id.item_name,R.id.item_first_name};
        SimpleCursorAdapter adapter =  new SimpleCursorAdapter(this,R.layout.pupils_listview_entry,cursor,cols,viewSubjects,0);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(itemClickListener);



    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView nombreText = (TextView) view.findViewById(R.id.item_name);
            Intent intent = new Intent(AddScoreActivity.this,SubmitScoreActivity.class);
            intent.putExtra(ALUMNO,nombreText.getText());
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
}

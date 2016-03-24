package beatprogramming.github.com.teacker_tracker;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * - Inicializa las preferencias de la aplicación
 * - Cada vez que se salga de la aplicación, se actualizarán los valores de las preferencias.
 */
public class Settings extends PreferenceActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}

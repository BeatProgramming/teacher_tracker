package beatprogramming.github.com.teacker_tracker;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * - Inicializa las preferencias de la aplicación
 * - Cada vez que se salga de la aplicación, se actualizarán los valores de las preferencias.
 */
public class Settings extends PreferenceActivity {

    /**
     *
     * - Crea las preferencias del xml settings
     *
     */
   @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    /**
     * - Cada vez que se vuelve a la activity anterior lanza un toast
     *   por si se cambian los valores de las preferencias.
     */
    @Override
    public void onBackPressed()
    {
        Toast.makeText(Settings.this, getResources().getString(R.string.onBackSettingsText), Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

}

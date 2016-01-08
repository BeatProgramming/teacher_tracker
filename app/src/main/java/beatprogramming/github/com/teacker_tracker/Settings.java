package beatprogramming.github.com.teacker_tracker;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends PreferenceActivity /*implements SharedPreferences.OnSharedPreferenceChangeListener*/ {

    TextView nombre;

   @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onBackPressed()
    {
        Toast.makeText(Settings.this, "Los cambios se realizarán al reiniciar la aplicación", Toast.LENGTH_SHORT).show();
        super.onBackPressed();  // optional depending on your needs
    }


    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i("","PRUEBA entra aqui");

        if(key.equals("notification")){
            Preference preference = findPreference(key);
            preference.setSummary(sharedPreferences.getString(key,"false"));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registrar escucha
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }*/
}

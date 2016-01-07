package beatprogramming.github.com.teacker_tracker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Settings extends PreferenceActivity /*implements SharedPreferences.OnSharedPreferenceChangeListener*/ {

   @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
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

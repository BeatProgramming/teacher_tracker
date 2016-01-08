package beatprogramming.github.com.teacker_tracker;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Settings extends PreferenceActivity {

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

}

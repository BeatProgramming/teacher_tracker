package beatprogramming.github.com.teacker_tracker.util;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * - Contiene los mensajes de ayuda que se muestran si la app está en modo ayuda.
 */
public class HelpUtil {

    /**
     * - Muestra los mensajes de ayuda de la página de inicio
     */
    public static void showIndexHelp(Context context, String[] msg){
        for(String aHelp_msg: msg){
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * - Muestra los mensajes de ayuda del apartado tareas
     */
    public static void showTaskHelp(Context context, String[] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * - Muestra los mensajes de ayuda del apartado asignaturas
     */
    public static void showSubjectHelp(Context context, String [] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * - Muestra los mensajes de ayuda del apartado calificaciones
     */
    public static void showScoreHelp(Context context, String [] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}

package beatprogramming.github.com.teacker_tracker.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * - Clase que contiene los mensajes de ayuda que se muestran si la app está en modo ayuda.
 */
public class HelpUtil {

    /**
     * Método que muestra los mensajes de ayuda de la página de inicio
     * @param context context en el que mostar el toast
     * @param msg mensaje a mostrar en los toast
     */
    public static void showIndexHelp(Context context, String[] msg){
        for(String aHelp_msg: msg){
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Método que muestra los mensajes de ayuda del apartado tareas
     * @param context context en el que mostar el toast
     * @param msg mensaje a mostrar en los toast
     */
    public static void showTaskHelp(Context context, String[] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Método que muestra los mensajes de ayuda del apartado asignaturas
     * @param context context en el que mostar el toast
     * @param msg mensaje a mostrar en los toast
     */
    public static void showSubjectHelp(Context context, String [] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * Método que muestra los mensajes de ayuda del apartado calificaciones
     * @param context context en el que mostar el toast
     * @param msg mensaje a mostrar en los toast
     */
    public static void showScoreHelp(Context context, String [] msg){
        for (String aHelp_msg : msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}

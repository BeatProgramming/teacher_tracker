package beatprogramming.github.com.teacker_tracker.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * - Clase que contiene los mensajes de ayuda que se muestran si la app está en modo ayuda.
 */
public class HelpUtil {

    private final static String AYUDA_INDEX_1 = "¡Bienvenido a TeacherTracker!";
    private final static String AYUDA_INDEX_2 = "En el menú de la izquierda tendrás las funciones";
    private final static String AYUDA_INDEX_3 = "En el menú de la derecha tendrás la configuración";
    private final static String AYUDA_INDEX_4 = "¿Por qué no lo pruebas?";

    private final static String AYUDA_TAREA_1 = "En esta pantalla podrás crear/editar tareas";
    private final static String AYUDA_TAREA_2 = "Pulse el botón rosa para añadir tareas";
    private final static String AYUDA_TAREA_3 = "Pulse sobre una tarea para modificarla";

    private final static String AYUDA_ASIGNATURA_1 = "En esta pantalla podrás crear/editar asignaturas";
    private final static String AYUDA_ASIGNATURA_2 = "Pulse el botón rosa para añadir asignaturas";
    private final static String AYUDA_ASIGNATURA_3 = "Pulse sobre una asignatura para modificarla";

    private final static String AYUDA_EVALUACION_1 = "En esta pantalla podrás crear/editar exámenes o prácticas";
    private final static String AYUDA_EVALUACION_2 = "Pulse el botón rosa para añadir un exámen o  práctica";
    private final static String AYUDA_EVALUACION_3 = "Pulse sobre una evaluación y a continuación podrás calificar a los alumnos";

    //- Método que muestra los mensajes de ayuda de la página de inicio
    public static void showIndexHelp(Context context){
        String[] help_msg = new String[]{AYUDA_INDEX_1,
                                         AYUDA_INDEX_2,
                                         AYUDA_INDEX_3,
                                         AYUDA_INDEX_4};
        for(String aHelp_msg: help_msg){
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    //- Método que muestra los mensajes de ayuda del apartado tareas
    public static void showTaskHelp(Context context){
        String[] help_msg = new String[]{AYUDA_TAREA_1,
                AYUDA_TAREA_2,
                AYUDA_TAREA_3};
        for (String aHelp_msg : help_msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    //- Método que muestra los mensajes de ayuda del apartado asignaturas
    public static void showSubjectHelp(Context context){
        String[] help_msg = new String[]{AYUDA_ASIGNATURA_1,
                AYUDA_ASIGNATURA_2,
                AYUDA_ASIGNATURA_3};
        for (String aHelp_msg : help_msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    //- Método que muestra los mensajes de ayuda del apartado evaluaciones
    public static void showScoreHelp(Context context){
        String[] help_msg = new String[]{AYUDA_EVALUACION_1,
                AYUDA_EVALUACION_2,
                AYUDA_EVALUACION_3};
        for (String aHelp_msg : help_msg) {
            Toast toast = Toast.makeText(context, aHelp_msg, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}

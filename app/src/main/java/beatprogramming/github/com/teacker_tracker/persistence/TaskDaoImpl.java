package beatprogramming.github.com.teacker_tracker.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.BDHelper;
import beatprogramming.github.com.teacker_tracker.ScriptBD;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.domain.Task;

/**
 * Implementación en SQLite del acceso a base de datos para manejar datos de Tarea.
 */
public class TaskDaoImpl implements TaskDao {

    private static String TAG = TaskDaoImpl.class.getName();

    //Tabla objetivo
    private static final String TASK = "Task";

    //Consultas sql
    private static final String FINDQUERY = "SELECT Task._id AS taskId, Task.subjectId, Task.name AS nameTask, Task.dateTime, Task.note, Task.año," +
            " Task.mes, Task.dia, Task.hora, Subject.name AS nameSubject, Subject.description, Subject.course " +
            " FROM Task LEFT JOIN Subject " +
            "ON Task.subjectId = Subject._id;";

    //Campos de la tabla Task
    private static final String TASKID = "taskId";
    private static final String NAMETASK = "nameTask";
    private static final String NOTE = "note";
    private static final String DATETIME = "dateTime";
    private static final String AÑO = "año";
    private static final String MES = "mes";
    private static final String DIA = "dia";
    private static final String HORA = "hora";
    private static final String SUBJECTID = "subjectId";

    //Campos de la tabla Subject
    private static final String NAMESUBJECT = "nameSubject";
    private static final String DESCRIPTION = "description";
    private static final String COURSE = "course";

    //Variables sql
    private final BDHelper db;
    private static SQLiteDatabase sqldb;
    private static Cursor c;

    //Schedule
    private ScheduleDao scheduleDao;
    private OnLoadFinishListener scheduleListener;
    private static List<Task> listaFinalTask;
    /**
     * Contructor que inicializa el DBHelper
     */
    public TaskDaoImpl() {
        db = BDHelper.getInstance();
        listaFinalTask = new ArrayList<>();
    }

    /**
     * Metodo que devuelte todas las tareas de la base de datos.
     * @param listener instancia del listener
     */
    @Override
    public void findTasks(OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);
        //Lista de tareas
        List tasks = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Subject s =  new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                    c.getString(c.getColumnIndex(DESCRIPTION)),
                    c.getString(c.getColumnIndex(COURSE)));
                s.setId(c.getInt(c.getColumnIndex(SUBJECTID)));
                Task t = new Task(c.getString(c.getColumnIndex(NAMETASK)), s, new DateTime(c.getInt(c.getColumnIndex(DATETIME))));
                t.setId(c.getInt(c.getColumnIndex(TASKID)));
                tasks.add(t);
                Log.d(TAG, "findTasks, " + t.toString());
            }while(c.moveToNext());
        }
        listener.onLoadFinish(tasks);
    }

    /**
     * Metodo que devuelte todas las tareas de la base de datos.
     * @param listener instancia del listener
     */
    @Override
    public void findTasksAndSchedules(OnLoadFinishListener listener) {
        //- Buscar todas las tareas
        sqldb = db.getReadableDatabase();
        c = sqldb.rawQuery(FINDQUERY, null);
        //Lista de tareas
        DateTime fechaTarea;
        DateTime fechaActual= new DateTime();
        String [] stringHour;
        if(c.moveToFirst()){
            do{
                stringHour = c.getString(c.getColumnIndex(HORA)).split(":");
                fechaTarea = new DateTime(c.getInt(c.getColumnIndex(AÑO)), c.getInt(c.getColumnIndex(MES)), c.getInt(c.getColumnIndex(DIA)) - 1,
                        Integer.parseInt(stringHour[0]), Integer.parseInt(stringHour[1]));
                if (fechaTarea.getDayOfWeek() == fechaActual.getDayOfWeek() &&
                        fechaTarea.getYear() == fechaActual.getYear() &&
                        fechaTarea.getMonthOfYear() == fechaActual.getMonthOfYear() ) {
                    Subject s = new Subject(c.getString(c.getColumnIndex(NAMESUBJECT)),
                            c.getString(c.getColumnIndex(DESCRIPTION)),
                            c.getString(c.getColumnIndex(COURSE)));
                    s.setId(c.getInt(c.getColumnIndex(SUBJECTID)));
                    Task t = new Task(c.getString(c.getColumnIndex(NAMETASK)), s, fechaTarea);
                    t.setId(c.getInt(c.getColumnIndex(TASKID)));
                    listaFinalTask.add(t);
                }
            }while(c.moveToNext());
        }
        //- Buscar todos los horarios
        scheduleDao = new ScheduleDaoImpl();
        scheduleListener = new OnLoadFinishListener() {
            @Override
            public void onLoadFinish(List<? extends Serializable> items) {
                Schedule sc;
                DateTime dt = new DateTime();
                Boolean [] dias;
                Task task;
                String [] stringHour;
                for (int i=0;i<items.size();i++){
                    sc = (Schedule) items.get(i);
                    stringHour = sc.getDateTime().split(":");
                    dt = dt.withTime(Integer.parseInt(stringHour[0]), Integer.parseInt(stringHour[1]),0, 0);
                    dias =  sc.getDias();
                    if (dias[dt.getDayOfWeek()]){
                        task = new Task(sc.getAula(), sc.getSubject(), dt);
                        listaFinalTask.add(task);
                    }
                }
            }
        };
        scheduleDao.findSchedule(scheduleListener);
        listener.onLoadFinish(listaFinalTask);
    }

    /**
     * Metodo que actualiza una tarea de la base de datos.
     * Si el id=0, quiere decir que no esta creada en la base de datos y en lugar de actualizar
     * se crea una nueva tarea
     * @param id id de la tarea
     * @param name nombre de la tarea
     * @param subjectId id de asignatura asociada a la tarea
     * @param dateTime dateTime de la tarea
     * @param listener instancia del listener
     */
    @Override
    public void updateTask(int id, String name, int subjectId, DateTime dateTime,
                           OnUpdateFinishListener listener) {

        sqldb = db.getWritableDatabase();
        //Valores para la busqueda en la base de datos
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put(SUBJECTID,subjectId);
        values.put(DATETIME,dateTime.getMillis());
        Log.d(TAG, "updateTask, id: " + id + ", name: " + name + ", subjectId: " + subjectId + ", dateTime: " + dateTime.toString());
        try{
            if(id == 0) {
                //- Insertar tarea
                sqldb.insert(TASK, null, values);

            } else {
                //- Actualizar tarea
                String[] selectionArgs = new String[]{Integer.toString(id)};
                sqldb.update(TASK, values, ScriptBD.ID_TAREA + "=?",selectionArgs);
            }
            listener.onSuccess();

        } catch (Exception e) {
            listener.onError(e.getMessage());
        }
    }

    /**
     * Metodo que borra una tarea de la base de datos
     * @param id id de la tarea a borrar
     * @param listener instancia del listener
     */
    @Override
    public void deleteTask(int id, OnDeleteFinishListener listener) {
        sqldb = db.getWritableDatabase();

        if(id > 0) {
            //- Borrar tarea
            String[] selectionArgs = new String[]{Integer.toString(id)};
            sqldb.delete(TASK, ScriptBD.ID_TAREA + "=?",selectionArgs);
        }
        listener.onDeleteFinish();
    }
}

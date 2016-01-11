package beatprogramming.github.com.teacker_tracker.persistence;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Schedule;

/**
 * - Abstracción del manejo de datos de persistencia de horarios
 */
public interface ScheduleDao {

    void findSchedule(OnLoadFinishListener listener);

    void updateSchedule(int id,int subjectId,String dateTime,String classroom, Boolean[] days,
                       OnUpdateFinishListener listener);

    void deleteShedule(int id, OnDeleteFinishListener listener);

    Schedule findScheduleBySubjectId(int id);
}

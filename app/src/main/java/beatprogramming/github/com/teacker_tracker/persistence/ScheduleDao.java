package beatprogramming.github.com.teacker_tracker.persistence;


import org.joda.time.DateTime;

import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;

public interface ScheduleDao {

    void findSchedule(OnLoadFinishListener listener);

    void updateSchedule(int id, String name,int subjectId,DateTime dateTime,String classroom,
                       OnUpdateFinishListener listener);

    void deleteShedule(int id, OnDeleteFinishListener listener);
}

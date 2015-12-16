package beatprogramming.github.com.teacker_tracker.presenter;

import android.util.Log;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;

import beatprogramming.github.com.teacker_tracker.callback.OnDateTimePickedListener;
import beatprogramming.github.com.teacker_tracker.callback.OnDeleteFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.callback.OnUpdateFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Exam;
import beatprogramming.github.com.teacker_tracker.domain.Project;
import beatprogramming.github.com.teacker_tracker.domain.Review;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.fragments.DatePickerFragment;
import beatprogramming.github.com.teacker_tracker.fragments.TimePickerFragment;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDao;
import beatprogramming.github.com.teacker_tracker.persistence.ReviewDaoImpl;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDao;
import beatprogramming.github.com.teacker_tracker.persistence.SubjectDaoImpl;
import beatprogramming.github.com.teacker_tracker.util.DateTimeFormatter;
import beatprogramming.github.com.teacker_tracker.view.ReviewUpdateView;

/**
 * Responsible object for handling all UI events on behalf of the view.
 */
public class ReviewUpdatePresenter implements OnUpdateFinishListener, OnDeleteFinishListener, OnLoadFinishListener, OnDateTimePickedListener {

    private static String TAG = ReviewUpdatePresenter.class.getName();

    public static final int RADIO_EXAM = 0;
    public static final int RADIO_PROJECT = 1;

    private ReviewUpdateView view;
    private ReviewDao reviewDao;
    private SubjectDao subjectDao;

    public ReviewUpdatePresenter(ReviewUpdateView view) {
        this.view = view;
        reviewDao = new ReviewDaoImpl();
        subjectDao = new SubjectDaoImpl();
    }

    public void onResume() {
        subjectDao.findSubjects(this);
    }

    public void submit(int id, String name, int subjectId, String dateTimeString,  String
            typeValue) {

        if(name.equals("") || subjectId == 0 || typeValue.equals(""))
            view.setError("Completa todos los campos");
        else {
            DateTime dateTime = DateTimeFormatter.stringToDateTime(dateTimeString);
            reviewDao.updateReview(id, name, subjectId, dateTime, typeValue, this);
        }
    }

    public void delete(int id) {
        reviewDao.deleteReview(id, this);
    }

    @Override
    public void onError(String message) {
        view.setError(message);
    }

    @Override
    public void onSuccess() {
        view.loadScoreFragment();
    }

    @Override
    public void onDeleteFinish() {
        view.loadReviewFragment();
    }

    @Override
    public void onLoadFinish(List<? extends Serializable> items) {
        view.setSubjectItems((List<Subject>) items);
    }

    public void onSubjectSelected(Object item) {
        if(item != null) {
            Subject subject = (Subject) item;
            view.setSubjectId(subject.getId());
            Log.d(TAG, "onSubjectSelected, selected: " + subject.toString());
        } else {
            Log.d(TAG, "onSubjectSelected, nothing selected.");
            // ACCION CUANDO NO HAY ASIGNATURA SELECIONADA.
        }

    }

    public void onTypeChanged(int type) {

        String typeValue = null;
        switch (type) {
            case RADIO_EXAM:
                typeValue = ReviewDaoImpl.EXAM;
                break;
            case RADIO_PROJECT:
                typeValue = ReviewDaoImpl.PROJECT;
                break;
            default:
                break;
        }
        view.setTypeValue(typeValue);
    }

    public void showDatePicker() {
        DatePickerFragment fragment = DatePickerFragment.newInstance(this);
        view.showDialog(fragment);
    }

    public void showTimePicker() {
        TimePickerFragment fragment = TimePickerFragment.newInstance(this);
        view.showDialog(fragment);
    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        view.setReviewDate(DateTimeFormatter.dateToString(year, month, day));
    }

    @Override
    public void onTimePicked(int hour, int minute) {
        view.setReviewTime(DateTimeFormatter.timeToString(hour, minute));
    }

    public void fillView(Review review) {

        DateTime dateTime = new DateTime();

        if (review != null) {

            view.setReviewId(review.getId());
            dateTime = review.getDateTime();

            view.setReviewName(review.getName());

            int subjectId = review.getSubject().getId();
            view.setSubject(subjectId);

            if (review instanceof Exam)
                view.selectType(RADIO_EXAM);
            else if(review instanceof Project)
                view.selectType(RADIO_PROJECT);
        }

        onDatePicked(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth());
        onTimePicked(dateTime.getHourOfDay(), dateTime.getMinuteOfHour());

    }
}

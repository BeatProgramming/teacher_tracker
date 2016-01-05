package beatprogramming.github.com.teacker_tracker.view;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Subject;

/**
 * Created by malkomich on 05/01/2016.
 */
public interface StudentTabView {

    void setTabs(List<Subject> items);

    Subject getSubjectFromAdapter(int position);

}

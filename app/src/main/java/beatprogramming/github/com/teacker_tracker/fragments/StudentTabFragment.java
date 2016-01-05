package beatprogramming.github.com.teacker_tracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.R;
import beatprogramming.github.com.teacker_tracker.callback.FragmentCallback;
import beatprogramming.github.com.teacker_tracker.domain.Subject;
import beatprogramming.github.com.teacker_tracker.presenter.StudentPresenter;
import beatprogramming.github.com.teacker_tracker.presenter.StudentTabPresenter;
import beatprogramming.github.com.teacker_tracker.view.StudentTabView;

/**
 * Created by malkomich on 05/01/2016.
 */
public class StudentTabFragment extends Fragment implements StudentTabView, TabHost.OnTabChangeListener {

    private static String TAG = StudentTabFragment.class.getName();

    private FragmentCallback callback;

    private FragmentTabHost tabHost;

    protected StudentTabPresenter presenter;

    public StudentTabFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StudentTabPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // R.layout.fragment_tabs_pager contains the layout as specified in your question
        View rootView = inflater.inflate(R.layout.fragment_students, container, false);

        // Initialise the tab-host
        tabHost = (FragmentTabHost) rootView.findViewById(R.id.tabHost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        presenter.findSubjects();

        return rootView;
    }

    @Override
    public void setTabs(List<Subject> items) {

        for(Subject subject: items){
            String spec = String.valueOf(subject.getId());
            String indicator = subject.getNombre() + " (" + subject.getCurso() + ")";

            Bundle b = new Bundle();
            b.putSerializable(StudentFragment.SUBJECT, subject);

            tabHost.addTab(tabHost.newTabSpec(spec).setIndicator(indicator), StudentFragment.class, b);
        }
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged, " + tabId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabHost = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (FragmentCallback) context;
    }

    @Override
    public Subject getSubjectFromAdapter(int position) {
        // Obtener asignatura a partir del adapter de Tabs.
        return null;
    }

}

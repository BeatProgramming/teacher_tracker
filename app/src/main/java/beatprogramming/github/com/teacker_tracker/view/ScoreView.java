package beatprogramming.github.com.teacker_tracker.view;
import android.support.v4.app.DialogFragment;

import java.util.List;

import beatprogramming.github.com.teacker_tracker.domain.Score;

/**
 * - Define los métodos  sobre los que tiene control el presentador que conecta
 *   la capa de persistencia con la vista.
 */
public interface ScoreView {

    void setItems(List<Score> items);

    void showLoading();

    void hideLoading();

    void showDialog(DialogFragment fragment);

    void setScoreValue(Float scoreValue, int position);

    Float getScoreValue(int position);

}

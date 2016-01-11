package beatprogramming.github.com.teacker_tracker.callback;

/**
 * - Define métodos para el evento de modificación de la hora
 */
public interface OnDateTimePickedListener {

    void onDatePicked(int year, int month, int day);

    void onTimePicked(int hour, int minute);

}

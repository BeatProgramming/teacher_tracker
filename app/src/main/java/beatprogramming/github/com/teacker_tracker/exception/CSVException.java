package beatprogramming.github.com.teacker_tracker.exception;

/**
 * Created by malkomich on 19/11/15.
 */
public class CSVException extends Exception {

    public CSVException()
    {
    }

    public CSVException(String message)
    {
        super(message);
    }

    public CSVException(Throwable cause)
    {
        super(cause);
    }

    public CSVException(String message, Throwable cause)
    {
        super(message, cause);
    }

}

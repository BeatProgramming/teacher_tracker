package beatprogramming.github.com.teacker_tracker.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import beatprogramming.github.com.teacker_tracker.callback.OnLoadFinishListener;
import beatprogramming.github.com.teacker_tracker.domain.Student;
import beatprogramming.github.com.teacker_tracker.exception.CSVException;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDao;
import beatprogramming.github.com.teacker_tracker.persistence.StudentDaoImpl;

/**
 * Clase para tratar un archivo CSV
 */
public class CSVManager {

    private static final String TAG = CSVManager.class.getName();

    private static final String CSV_FILE = "/students.csv";

    private static final Integer NAME_POS_DEFAULT = 0;
    private static final Integer SURNAME_POS_DEFAULT = 1;

    private static final String NAME_HEADER_DEFAULT = "Nombre";
    private static final String SURNAME_HEADER_DEFAULT = "Apellidos";

    private static final int NAME_SURNAME_ORDER = 0;
    private static final int SURNAME_NAME_ORDER = 1;

    private static CSVManager manager;

    private final Context context;

    private StudentDao subjectDao;

    /*
     * Map wich associates each header name with the position in the file.
     */
    private Map<String, Integer> headerPos;

    private String name;
    private String surname;

    private CSVManager(Context context) {

        try {
            name = context.getString(context.getResources().getIdentifier("csv_name", "string", context.getPackageName()));
            surname = context.getString(context.getResources().getIdentifier("csv_surname", "string", context.getPackageName()));
        } catch (Exception e) {
            name = NAME_HEADER_DEFAULT;
            surname = SURNAME_HEADER_DEFAULT;
        }

        this.context = context;
        setHeaderPositions();

        subjectDao = new StudentDaoImpl();
    }

    public static CSVManager getInstance(Context context) {
        if (manager == null)
            manager = new CSVManager(context);
        return manager;
    }

    public Intent exportStudents(File directory) throws CSVException {

        String path = directory.getAbsolutePath() + CSV_FILE;
        Log.d(TAG, path);

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path);
        } catch (IOException e) {
            throw new CSVException("Error accesing storage.");
        }
        final CSVWriter writer = new CSVWriter(fileWriter, ',');
        subjectDao.findStudents(new OnLoadFinishListener() {
            @Override
            public void onLoadFinish(List<? extends Serializable> items) {
                List<String[]> data = toStringArray((List<Student>) items);
                writer.writeAll(data);
            }
        });

        try {
            writer.close();
        } catch (IOException e) {
            throw new CSVException("Error closing file.");
        }

        Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendIntent.setType("application/csv");
        sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Students CSV");
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);

        return sendIntent;
    }

    /*
     * Get the list of items from a specific file located in a given path.
     *
     * @param path
     * @return List of students
     */
    public List<Student> importStudents(Uri uri) {

        InputStream is = null;
        FileOutputStream os = null;
        String fullPath = null;

        String scheme = uri.getScheme();
        String filename = null;

        try {
            if (scheme.equals("file")) {
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments.size() > 0) {
                    filename = pathSegments.get(pathSegments.size() - 1);
                }
            } else if (scheme.equals("content")) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{
                        MediaStore.MediaColumns.DISPLAY_NAME
                }, null, null, null);
                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    filename = cursor.getString(nameIndex);
                }
            }

            if (filename == null) {
                return null;
            }

            int n = filename.lastIndexOf(".");
            String fileExt;

            if (n == -1) {
                return null;
            } else {
                fileExt = filename.substring(n);
                if (!fileExt.equals(".csv")) {
                    return null;
                }
            }

            File dir = context.getExternalFilesDir(null);
            fullPath = dir.getAbsolutePath() + CSV_FILE;

            is = context.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(fullPath);

            byte[] buffer = new byte[4096];
            int count;
            while ((count = is.read(buffer)) > 0) {
                os.write(buffer, 0, count);
            }
            os.close();
            is.close();
        } catch (Exception e) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e1) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e1) {
                }
            }
            if (fullPath != null) {
                File f = new File(fullPath);
                f.delete();
            }
        }

        CSVReader csvReader = null;
        List content = null;
        List<Student> studentList = new ArrayList<>();

        try {
            csvReader = new CSVReader(new FileReader(fullPath), ',');
            content = csvReader.readAll();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        int initialRow = 0;
        if (content != null) {
            if (!content.isEmpty()) {
                List<String> headers = Arrays.asList((String[]) content.get(0));
                if (headers.contains("Nombre") || headers.contains("Name")) {
                    initialRow = 1;
                    setHeaderPositions(headers);
                } else
                    setHeaderPositions();
            }
        }

        if (content != null) {
            for (int i = initialRow; i < content.size(); i++) {
                String[] row = (String[]) content.get(i);

                Student student = new Student();

                student.setName(row[headerPos.get(name)]);
                student.setSurname(row[headerPos.get(surname)]);

                studentList.add(student);
            }
        }

        try {
            if (csvReader != null) {
                csvReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "exported");

        return studentList;
    }

    private void setHeaderPositions(List<String> headers) {

        headerPos = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            String value = headers.get(i);
            switch (value) {
                case "Nombre":
                case "Name":
                case "Firstname":
                    headerPos.put(name, i);
                    break;
                case "Apellidos":
                case "Surname":
                case "Lastname":
                    headerPos.put(surname, i);
                    break;
            }
        }

        if (!headerPos.containsKey(name))
            headerPos.put(name, NAME_POS_DEFAULT);
        if (!headerPos.containsKey(surname))
            headerPos.put(surname, SURNAME_POS_DEFAULT);
    }

    private void setHeaderPositions() {

        setHeaderPositions(new ArrayList<String>());
    }

    /*
     * Convert a list of items to a list of rows. That rows are arrays of strings,
     * defining the values required.
     */
    private List<String[]> toStringArray(List<Student> students) {
        List<String[]> records;
        records = new ArrayList<>();

        // Get csv order preference
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int headerPref = Integer.parseInt(pref.getString("format", "?"));

        switch (headerPref) {
            case NAME_SURNAME_ORDER:
                // Set headers
                records.add(new String[]{name, surname});

                for (Student student : students) {
                    records.add(new String[]{student.getName(), student.getSurname()});
                }
                break;
            case SURNAME_NAME_ORDER:
                // Set headers
                records.add(new String[]{surname, name});

                for (Student student : students) {
                    records.add(new String[]{student.getSurname(), student.getName()});
                }
                break;
            default:
                break;
        }

        return records;
    }
}
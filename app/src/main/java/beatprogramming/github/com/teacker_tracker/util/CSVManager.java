package beatprogramming.github.com.teacker_tracker.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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


public class CSVManager {

    private static final String TAG = CSVManager.class.getName();

    private static final String CSV_FILE = "/students.csv";

    private static final Integer NAME_POS_DEFAULT = 0;
    private static final Integer SURNAME_POS_DEFAULT = 1;

    private static final String NAME_HEADER_DEFAULT = "Nombre";
    private static final String SURNAME_HEADER_DEFAULT = "Apellidos";

    private static CSVManager manager;

    private StudentDao subjectDao;

    /*
     * Map wich associates each header name with the position in the file.
     */
    private Map<String, Integer> headerPos;

    private String name;
    private String surname;

    private CSVManager(Context context) {

        if (context != null) {
            name = context.getString(context.getResources().getIdentifier("csv_name", "string", context.getPackageName()));
            surname = context.getString(context.getResources().getIdentifier("csv_surname", "string", context.getPackageName()));
        } else {
            name = NAME_HEADER_DEFAULT;
            surname = SURNAME_HEADER_DEFAULT;
        }
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
    public List<Student> importStudents(String path) {

        CSVReader csvReader = null;
        List content = null;
        List<Student> studentList = new ArrayList<>();

        try {
            csvReader = new CSVReader(new FileReader(path), ',');
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

        // Set headers
        records.add(new String[]{name, surname});

        for (Student student : students) {
            records.add(new String[]{student.getName(), student.getSurname()});
        }
        return records;
    }
}

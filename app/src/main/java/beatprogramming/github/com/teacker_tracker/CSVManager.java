package beatprogramming.github.com.teacker_tracker;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import beatprogramming.github.com.teacker_tracker.domain.Student;

/**
 * Created by malkomich on 13/11/15.
 */
public class CSVManager {

    private static final String TAG = CSVManager.class.getName();

    private static final Integer NAME_POS_DEFAULT = 0;
    private static final Integer SURNAME_POS_DEFAULT = 1;

    private static final String NAME_HEADER_DEFAULT = "Nombre";
    private static final String SURNAME_HEADER_DEFAULT = "Apellidos";

    private static CSVManager manager;

    /*
     * Map wich associates each header name with the position in the file.
     */
    private Map<String, Integer> headerPos;

    private String name;
    private String surname;

    private Context context;

    private CSVManager(Context context) {

        this.context = context;
        setHeaderPositions();
    }

    public static CSVManager getInstance(Context context) {
        if (manager == null)
            manager = new CSVManager(context);
        return manager;
    }

    public void exportStudents(File directory, List<Student> students) {

        String path = directory.getAbsolutePath() + "/students.csv";
        Log.d(TAG, path);

        FileWriter file = null;
        try {
            file = new FileWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVWriter writer = new CSVWriter(file,',');
        List<String[]> data  = toStringArray(students);
        writer.writeAll(data);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, writer.toString());

    }


    public List<Student> importStudents(String path) {

        CSVReader csvReader = null;
        List content = null;
        List<Student> studentList = new ArrayList<Student>();

        try {
            csvReader = new CSVReader(new FileReader(path), ',');
            content = csvReader.readAll();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        int initialRow = 0;
        if (!content.isEmpty()) {
            List<String> headers = Arrays.asList((String[]) content.get(0));
            if (headers.contains("Nombre") || headers.contains("Name")) {
                initialRow = 1;
                setHeaderPositions(headers);
            } else
                setHeaderPositions();
        }

        for (int i = initialRow; i < content.size(); i++) {
            String[] row = (String[]) content.get(i);

            Student student = new Student();
            student.setName(row[headerPos.get(name)]);
            student.setSurname(row[headerPos.get(surname)]);

            studentList.add(student);
        }

        try {
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    private void setHeaderPositions(List<String> headers) {

        headerPos = new HashMap<String, Integer>();

        if(context != null) {
            name = context.getString(context.getResources().getIdentifier("csv_name", "string", context.getPackageName()));
            surname = context.getString(context.getResources().getIdentifier("csv_surname", "string", context.getPackageName()));
        } else {
            name = NAME_HEADER_DEFAULT;
            surname = SURNAME_HEADER_DEFAULT;
        }

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

    private static List<String[]> toStringArray(List<Student> students) {
        List<String[]> records = new ArrayList<String[]>();
        //add header record
        records.add(new String[]{"Nombre", "Apellidos"});
        Iterator<Student> it = students.iterator();
        while(it.hasNext()){
            Student student = it.next();
            records.add(new String[]{student.getName(), student.getSurname()});
        }
        return records;
    }
}

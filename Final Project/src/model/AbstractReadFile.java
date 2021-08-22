package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReadFile {

    public static final String PREFIX = "data/";
    public static final String SEPARATOR = "-";
    public static final String SEPARATOR_2 = "#";

    /**
     * Read from file
     *
     * @param path File path
     * @return List of strings for each line
     * @throws IOException IOException
     */
    public static List<String> readFromFile(String path) throws IOException {
        //BufferedReader is a file that can be read by line
        List<String> result = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(PREFIX + path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str;
        while ((str = bufferedReader.readLine()) != null) {
            result.add(str);
        }
        //close
        inputStream.close();
        bufferedReader.close();

        return result;
    }

    /**
     * Write to file
     *
     * @param path Write to file
     * @param list List to be written
     * @throws IOException IOException
     */
    public static void saveIntoFile(String path, List<String> list) throws IOException {
        File fout = new File(PREFIX + path);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (String s : list) {
            bw.write(s);
            bw.newLine();
        }
        bw.close();
    }


    public abstract AbstractReadFile readFromString(String s);

}
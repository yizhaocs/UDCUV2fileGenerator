import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by yzhao on 5/27/16.
 */
public class Main {
    public static void main(String[] args) {

        String key = "22571";
        String value = "20160603";
        String time_stamp = "1465233434"; // gets from http://www.epochconverter.com/, then append 000 to the end of current time

        // Location of file to read
        File file = new File("/Users/yzhao/Desktop/table_dump_outout.csv");
        File fout = new File("/Users/yzhao/Desktop/" + getCurrentDate() + "-000001.united-lax1." + time_stamp + "000" + ".csv");
        Scanner scanner = null;
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        int count = 0;
        try {
            scanner = new Scanner(file);
            fos = new FileOutputStream(fout);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            while (scanner.hasNextLine()) {
                String cookieID = scanner.nextLine().trim();
                // ckvraw|20|103467387633636100|2044=;12345|103467387633636100|80|80|80
                bw.write("ckvraw" + "|" + time_stamp + "|" + cookieID + "|" + key + "=" + value + "|" + "null" + "|||");
                bw.newLine();
                count++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Count row:" + count);
    }

    private static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }
}

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by yzhao on 5/27/16.
 */
public class Main {
    public static void main(String[] args) {

        String key = "29426"; // get it from SELECT name, description, key_id, usage_type FROM data_provider_keys WHERE dp_id=1020 and lower(name) like '%xu_ptsfls%';
        String value = "1"; // get it from /tmp/2017-04-07_United_HCA_IN_kly.txt
        String time_stamp = "1510879880"; // gets from http://www.epochconverter.com/, then append 000 to the end of current time

        // Location of file to read
        File file = new File("/Users/yzhao/Desktop/table_dump_outout.csv");
        File fout = new File("/Users/yzhao/Desktop/" + getCurrentDate() + "-000001.united-lax1." + time_stamp + "000" + ".csv"); // 文件名不能有.force，否则不会有clog文件，则不会load到netezza
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

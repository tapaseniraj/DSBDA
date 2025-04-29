import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Reducer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class LogReducer extends Reducer<Text, Text, Text, LongWritable> {

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String loginTime = "";
        String logoutTime = "";

        for (Text val : values) {
            String[] parts = val.toString().split(" ");
            if (parts[0].equalsIgnoreCase("login")) {
                loginTime = parts[1];
            } else if (parts[0].equalsIgnoreCase("logout")) {
                logoutTime = parts[1];
            }
        }

        try {
            Date login = format.parse(loginTime);
            Date logout = format.parse(logoutTime);

            long diffInMinutes = (logout.getTime() - login.getTime()) / (1000 * 60);

            context.write(key, new LongWritable(diffInMinutes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

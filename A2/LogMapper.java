import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class LogMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text user = new Text();
    private Text actionTime = new Text();

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Each line is like: UserA login 09:00
        String[] parts = value.toString().split(" ");
        
        if (parts.length == 3) {
            user.set(parts[0]);              // UserA
            actionTime.set(parts[1] + " " + parts[2]);  // login 09:00
            context.write(user, actionTime);  // (UserA, login 09:00)
        }
    }
}

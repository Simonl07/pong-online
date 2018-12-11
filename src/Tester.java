import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tester {

	public static void main(String args[]) throws IOException {

		for (int i = 0; i < 10000000; i++) {
			URL url = new URL("http://www.bjtime.cn");
			URLConnection uc = url.openConnection();
			uc.connect();
			long time = uc.getDate();
			System.out.println("long time:" + time);
			Date date = new Date(time);
			System.out.println("date:" + date.toString());
			System.out.println(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(date));

		}
	}
}

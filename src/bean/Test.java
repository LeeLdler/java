package bean;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
	

	public static void main(String[] args) { 
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String date = df.format(new Date());
	System.out.println(date);// new Date()为获取当前系统时间
	}

}

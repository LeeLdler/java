package bean;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Test {
	

	public static void main(String[] args) { 
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	String date = df.format(new Date());
	System.out.println(date);// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}

}

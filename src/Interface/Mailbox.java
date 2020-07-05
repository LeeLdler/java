package Interface;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Mailbox {

	        public String authCode = null;
			public static String achieveCode() {  //�������� 1 �� 0 ����ĸ O ��l ��ʱ�ֲ���������ԣ�û������ 1 �� 0
				String[] beforeShuffle= new String[] { "0","1","2", "3", "4", "5", "6", "7", "8", "9"};
				List list = Arrays.asList(beforeShuffle);//������ת��Ϊ����
				Collections.shuffle(list);  //���Ҽ���˳��
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i)); //������ת��Ϊ�ַ���
				}
				return sb.toString().substring(3, 8);  //��ȡ�ַ�����4��8
			}
			public static void sendAuthCodeEmail(String email, String authCode) {
		   		try {
		   	 	SimpleEmail mail = new SimpleEmail();
		   	 	mail.setHostName("smtp.qq.com");//�����ʼ��ķ�����
		   	 	mail.setAuthentication("2462478392@qq.com","tzqofynqlhibeaae");//�ոռ�¼����Ȩ�룬�ǿ���SMTP������
		   	 	mail.setFrom("2462478392@qq.com","������������");  //�����ʼ�������ͷ�����
		   	 	mail.setSSLOnConnect(true); //ʹ�ð�ȫ����
		   	 	mail.addTo(email);//���յ�����
		   	 	//System.out.println("email"+email);
		   	 	mail.setSubject("ע����֤��");//�����ʼ�������
		   		mail.setMsg("�𾴵��û�:���!\n ע����֤��Ϊ:" + authCode+"\n"+"     ");//�����ʼ�������
		   		mail.send();//����
		   		} catch (EmailException e) {
		   			e.printStackTrace();
		   		}  
		   	}
			public Mailbox(String email)
			{
				authCode = achieveCode();
				sendAuthCodeEmail(email,authCode);
			}
			public Mailbox()
			{
				
			}
	
}

package client;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

import bean.ChatFriend;
import tcp.Utils;

/**
 * 
 * ʹ�ö��̷߳�װ�˷��Ͷ�
 * 1.������Ϣ
 * 2.�ӿ���̨��ȡ��Ϣ
 * 3.�ͷ���Դ
 * 4.��дrun
 * 
 *
 */
public class Send implements Runnable{
	private ObjectOutputStream dos;
	private Socket client;
	private boolean isRunning;
	private Object contant;
	
	public Send(Socket client) {
		this.client = client;
		this.isRunning  = true;
		this.contant=null;
		try {
			dos = new ObjectOutputStream(client.getOutputStream());
		} catch (IOException e) {
			System.out.println("=====1=====");
			this.release();
		}
		
	} 
	public void getcontant(Object contant) {
		this.contant = contant;
		this.send(contant);
		System.out.println("�ı�ɹ�");
	}
	@Override
	public void run() {
		while(isRunning) {
			if(this.contant==null) {
				continue;
			}
			System.out.println(this.contant);
			Object msg = contant;
			if(null!=msg&&!msg.equals("")) {
				send(msg);
				this.contant=null;
			}
		}
	}
	//������Ϣ
		 public void send(Object msg) {
			 try {
				//User user =new User();
				 dos.reset();
				dos.writeObject(msg);
				System.out.println("���ͳɹ�");
				dos.flush();
			} catch (IOException e) {
				System.out.println("=====3=====");
				release();
			}
			 
		 }
	//�ͷ���Դ
	private void release() {
		 this.isRunning = false;
		 Utils.close(dos,client);
	 }

}

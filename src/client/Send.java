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
 * 使用多线程封装了发送端
 * 1.发送消息
 * 2.从控制台获取消息
 * 3.释放资源
 * 4.重写run
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
		System.out.println("改变成功");
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
	//发送消息
		 public void send(Object msg) {
			 try {
				//User user =new User();
				 dos.reset();
				dos.writeObject(msg);
				System.out.println("发送成功");
				dos.flush();
			} catch (IOException e) {
				System.out.println("=====3=====");
				release();
			}
			 
		 }
	//释放资源
	private void release() {
		 this.isRunning = false;
		 Utils.close(dos,client);
	 }

}

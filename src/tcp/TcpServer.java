
  package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
     public static void main(String[] args) throws IOException {
    	 System.out.println("-----Server-----");
 		//1.指定端口 使用ServerSocket创建服务器
		ServerSocket server = new ServerSocket(8888);
 		//2.阻塞式等待连接 accept
 		while(true) {
 		Socket client = server.accept();
 		System.out.println("一个客户端建立了连接");
 		new Thread(new Channel(client)).start();
 		}
    }
     //一个客户带边一个Channel
      static class Channel implements Runnable{
    	 private DataInputStream dis ;
		 private DataOutputStream dos ;
		 private Socket client;
		 private boolean isRunning;
		 public Channel(Socket client) {
			 this.client = client;
			 try {
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					isRunning = true;
				} catch (IOException e) {
					System.out.println("-----1-----");
					release();
				}
		 }
    	 //接收消息
    	 private String receive() {
    		 String msg = null; 
    		 try {
				msg = dis.readUTF();
			} catch (IOException e) {
				System.out.println("-----2-----");
				e.printStackTrace();
				release();
			}
    		 return msg;
    	 }
    	 //发送消息
    	 private void send(String msg) {
    		 try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("-----3-----");
				release(); 
			}
    		 
    	 }
    	 //释放资源
    	 private void release() {
    		 this.isRunning = false;
    		 Utils.close(dis,dos,client);
    	 }
		@Override
		public void run() {
             	while(isRunning) {
             		String msg = receive();
             		//System.out.println(msg);
             		if(null!=msg&&!msg.equals("")) {
             			send(msg);
             		}
             	}
		}
     }
}

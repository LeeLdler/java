package server;

import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import bean.ChatFriend;
import bean.FileMessage;
import bean.Friends;
import bean.Group;
import bean.GroupRelationship;
import bean.User;
import bean.UserCustom;
import dao.Jdbc;
import tcp.Utils;


public class Server {

    private static HashMap<Integer,Channel> clientMap;
	public Server() throws IOException{
		System.out.println("-----server-----");
		//1.指定端口 使用ServerSocket创建服务器
		ServerSocket server = new ServerSocket(8888);
	
		clientMap = new HashMap<Integer,Channel>();
		//2.阻塞式等待连接 accept
		while(true) {
	 		Socket client = server.accept();
	 		System.out.println("服务器开启");
	 		System.out.println("一个客户端建立了连接");
	 		new Thread(new Channel(client)).start();
	 		}
	}
	
		static class Channel implements Runnable{
	    	 private ObjectInputStream dis = null;
			 private ObjectOutputStream dos = null;
			 private Socket client = null;
			 private boolean isRunning;
			 public Channel(Socket client) {
				 this.client = client;
				 try {
						dis = new ObjectInputStream(client.getInputStream());
						dos = new ObjectOutputStream(client.getOutputStream());
						isRunning = true;
						
					} catch (IOException e) {
						System.out.println("-----1-----");
						release();
					}
			 }
	    	 //接收消息
	    	 private Object receive() {
	    		 Object msg = null; 
	    		 try {
	    			 System.out.println("流说："+dis.available());
					msg = dis.readObject();
					
				} catch (Exception e) {
					System.out.println("-----2-----");
					release();
				}
	    		 return msg;
	    	 }
	    	 //发送消息
	    	 private void send(Object msg) {
	    		 try {
	    			 dos.reset();
					dos.writeObject(msg);
					//System.out.println(dos);
					dos.flush();
				} catch (IOException e) {
					System.out.println("-----3-----");
					release(); 
				}
	    		 
	    	 }
	    	 //释放资源
	    	 private void release() {
	    		 this.isRunning = false;
	    		 System.out.println("一个客户端走了");
	    		 
	    		 Utils.close(dis,dos,client);
	    	 }
			@Override
			public void run() {
				System.out.println(isRunning);
				while(this.isRunning) {
					Object msgObject;
					
					msgObject = receive();
						if(msgObject instanceof String) {
							String msg = (String) msgObject;
						if(!msg.equals("")&&(null!=msg)) {
					System.out.println("接收成功");
					System.out.println(msg);
					CharSequence type = msg.subSequence(0, 1);
					//登录
		            if(type.equals("1")) {
		            	login(msg);
		            }
		            //注册
		            if(type.equals("2")) {
		            	registration(msg);
		            }
		            if(type.equals("3"))
		            {
		            	query(msg);
		            }
		            if(type.equals("4")) {
		            	String account = msg.substring(msg.lastIndexOf("&")+1);
		            	int id = Integer.parseInt(account)-3384;
		            	List<String> list = Jdbc.getSelect(id);
		            	if(list.size()==0) {
		            		String mss = "4&账号不存在";
		            		send(mss);
		            	}
		            	else
		            	{
		            		String mss = "4&"+list.get(4);
		            		send(mss);
		            	}
		            }
		            if(type.equals("5")) {
		            	String account =msg.substring(msg.indexOf("account=")+8, msg.indexOf("&pwd"));
		            	int id = Integer.parseInt(account)-3384;
		            	String pwd = msg.substring(msg.lastIndexOf("&pwd=")+5);
		            	Jdbc.editePwd(id, pwd);
		            	String ms = "5&修改成功";
		            	send(ms);
		            }
						}
		            
					}
					else if(msgObject instanceof UserCustom)
					{ 
						UserCustom msg = (UserCustom) msgObject;
						if(msg.getType() == 2)
						{
							edite(msg);
						}
						//下线通知
						if(msg.getType() == 3)
						{
							System.out.println(msg.getUserAccount()+"走了");
							Jdbc.updateOnline(msg.getId(), 0);
							msg.setOnline(0);
							clientMap.remove(msg.getId(), this);
							Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
							while(it.hasNext()) {
								Entry<Integer, Channel> next = it.next();
								for(int i=0;i<msg.getFriends().size();i++) {
									if(next.getKey() == msg.getFriends().get(i).getId()) {
										msg.setType(7);
										next.getValue().send(msg);
										break;
									}
								}
							}
						}
					}
					else if(msgObject instanceof Friends){
						Friends msg = (Friends) msgObject;
						if(msg.getType() == 1) {
							System.out.println("进入好友请求");
							Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
							while(it.hasNext()) {
								Entry<Integer, Channel> next = it.next();
								System.out.println("在线User："+next.getKey()+"  "+next.getValue());
								if(next.getKey() == msg.getFriendId()) {
									System.out.println("发送请求好友成功");
									next.getValue().send(msg);
								}
								if(next.getKey() == msg.getMyId()) {
									System.out.println(next.getKey()+"   "+msg.getMyId());
									String message = "发送好友请求成功";
									next.getValue().send(message);
								}
							}
						}
						if(msg.getType() == 2) {
							Jdbc.addFriends(msg.getMyId(), msg.getFriendId());
							
							Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
							while(it.hasNext()) {
								Entry<Integer, Channel> next = it.next();
								if(next.getKey() == msg.getFriendId()) {
									List<String> list = Jdbc.getSelect(msg.getMyId());
									System.out.println("同意了");
									UserCustom msg1 = new UserCustom();
									msg1.setBir(list.get(3));
									msg1.setHead(list.get(6));
									msg1.setId(Integer.parseInt(list.get(0)));
									msg1.setUserAccount(Integer.parseInt(list.get(0))+3384);
									msg1.setNickName(list.get(5));
									msg1.setSex(list.get(2));
									msg1.setSignature(list.get(7));
									msg1.setType(5);
									msg1.setOnline(Integer.parseInt(list.get(8)));
									next.getValue().send(msg1);
								}
								if(next.getKey() == msg.getMyId()) {
									List<String> list = Jdbc.getSelect(msg.getFriendId());
									System.out.println("同意了");
									UserCustom msg1 = new UserCustom();
									msg1.setBir(list.get(3));
									msg1.setHead(list.get(6));
									msg1.setId(Integer.parseInt(list.get(0)));
									msg1.setUserAccount(Integer.parseInt(list.get(0))+3384);
									msg1.setNickName(list.get(5));
									msg1.setSex(list.get(2));
									msg1.setSignature(list.get(7));
									msg1.setOnline(Integer.parseInt(list.get(8)));
									msg1.setType(6);
									next.getValue().send(msg1);
								}
							}
						}
						if(msg.getType() == 3) {
							Jdbc.removeFriends(msg.getMyId(), msg.getFriendId());
							Jdbc.removeFriends(msg.getFriendId(), msg.getMyId());
							Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
							while(it.hasNext()) {
								Entry<Integer, Channel> next = it.next();
								if(next.getKey() == msg.getFriendId()) { 
									next.getValue().send(msg);
								}
								if(next.getKey() == msg.getMyId()) {
									msg.setMyId(msg.getFriendId());
									next.getValue().send(msg);
								}
							}
						}
					}
					else if(msgObject instanceof ChatFriend) {
						ChatFriend msg = (ChatFriend) msgObject;
						if(msg.getType() == 1) {
							if(msg.getCurrentMessage().getType() == 1) {
								if(!msg.isGroup()) {
								    sendWords(msg);
								}
								else {
									sendAllWord(msg);
								}
							}
						}
						if(msg.getType() == 2) {
							if(!msg.isGroup()) {
						    msg.setOffOnline(Jdbc.exportOffline(msg.getFriendId(), msg.getMyId()));
						    Jdbc.removechat(msg.getFriendId(), msg.getMyId());
						    send(msg);
							}
							else
							{
								msg.setOffOnline(Jdbc.exportGroupOffline( msg.getMyId(),msg.getFriendId()));
								Jdbc.removeGroupchat(msg.getMyId(),msg.getFriendId());
								msg.setFile(Jdbc.exportfile(msg.getFriendId()));
								send(msg);
							}
						}
					}
					else if(msgObject instanceof Group) {
						Group msg = (Group) msgObject;
						msg.setId(Jdbc.getSelectGroup());
						msg.setAccount(msg.getId()+5050);
						List<User> us = new  ArrayList<User>();
						User uu = new User();
						uu.setHead(msg.getCreateHead());
						uu.setId(msg.getCreateId());
						uu.setNickName(msg.getCreateName());
						uu.setUserAccount(msg.getCreateAccount());
						us.add(uu);
						msg.setGroupUser(us);
						Jdbc.addGroup(msg.getAccount(), msg.getName());
						Jdbc.addGroupg(msg.getCreateId(), msg.getId());
						send(msg);
					}
					else if(msgObject instanceof GroupRelationship) {
						GroupRelationship msg = (GroupRelationship) msgObject;
						if(msg.getType() == 1) {
						List<String> group = Jdbc.getGroup(msg.getGroupId());
						if(group.size() == 0) {
							String message = "改群不存在";
							System.out.println(message);
							send(message);
						}
						else {
						
						List<Integer> users = Jdbc.exportGroupUser(msg.getGroupId());
						Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
						while(it.hasNext()) {
							Entry<Integer, Channel> next = it.next();
							for(int i=0;i<users.size();i++) {
								if(next.getKey() == users.get(i)) {
									msg.setType(2);
									next.getValue().send(msg);
									break;
								}
							}
						}
						users.add(msg.getUserId());
						Jdbc.addGroupg(msg.getUserId(), msg.getGroupId());
						msg.setName(group.get(2));
						List<User> us = new ArrayList<User>();
						for(int i=0;i<users.size();i++) {
							List<String>uu = Jdbc.getSelect(users.get(i));
							User u = new User();
							u.setHead(uu.get(6));
							u.setId(Integer.parseInt(uu.get(0)));
							u.setUserAccount(Integer.parseInt(uu.get(0))+3384);
							u.setNickName(uu.get(5));
							us.add(u);
						}
						msg.setGroupUser(us);
						msg.setAccount(Integer.parseInt(group.get(1)));
						msg.setType(1);
						send(msg);
						}
						}
						if(msg.getType() == 3) {
							List<Integer> users = Jdbc.exportGroupUser(msg.getGroupId());
							List<String> group = Jdbc.getGroup(msg.getGroupId());
							Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
							while(it.hasNext()) {
								Entry<Integer, Channel> next = it.next();
								for(int i=0;i<users.size();i++) {
									if(next.getKey() == users.get(i)) {
										msg.setType(2);
										next.getValue().send(msg);
										break;
									}
								}
								if(next.getKey() == msg.getUserId()) {
									users.add(msg.getUserId());
									Jdbc.addGroupg(msg.getUserId(), msg.getGroupId());
									msg.setName(group.get(2));
									List<User> us = new ArrayList<User>();
									for(int i=0;i<users.size();i++) {
										List<String>uu = Jdbc.getSelect(users.get(i));
										User u = new User();
										u.setHead(uu.get(6));
										u.setId(Integer.parseInt(uu.get(0)));
										u.setUserAccount(Integer.parseInt(uu.get(0))+3384);
										u.setNickName(uu.get(5));
										us.add(u);
									}
									msg.setGroupUser(us);
									msg.setAccount(Integer.parseInt(group.get(1)));
									msg.setType(4);
									next.getValue().send(msg);
								}
							}
							
						}
					}
					else if(msgObject instanceof FileMessage) {
						FileMessage msg = (FileMessage) msgObject;
						if(msg.getType() == 2 || msg.getType() == 1) {
						Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
						while(it.hasNext()) {
							Entry<Integer, Channel> next = it.next();
							if(next.getKey() == msg.getFriendId()) {
								next.getValue().send(msg);
								break;
							}
						}
					}
							if(msg.getType() == 3) {
								Jdbc.addFile(msg);
								System.out.println("已经加上");
								Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
								while(it.hasNext()) {
									Entry<Integer, Channel> next = it.next();
									for(int i=0;i<msg.getGroupUser().size();i++) {
										if(next.getKey() == msg.getGroupUser().get(i).getId()) {
											next.getValue().send(msg);
											System.out.println("发出来了");
											break;
										}
										
									}
								}
							}
							if(msg.getType() == 5) {
								Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
								while(it.hasNext()) {
									Entry<Integer, Channel> next = it.next();
									if(next.getKey() == msg.getFriendId()) {
										next.getValue().send(msg);
										break;
									}
								}
							}
					}
				}
				
			}
			private void sendAllWord(ChatFriend msg) {
				for(int i=0;i<msg.getGroupUser().size();i++) {
					if(msg.getGroupUser().get(i).getId() == msg.getMyId()) {
						continue;
					}
					int flag=0;
				Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
				while(it.hasNext()) {
					Entry<Integer, Channel> next = it.next();
					if(next.getKey() == msg.getGroupUser().get(i).getId()) {
						flag=1;
						msg.getCurrentMessage().setId(msg.getGroupUser().get(i).getId());
						next.getValue().send(msg);
						break;
					}
				}
				if(flag==0) {
					Jdbc.addGroupMessage(msg.getMyId(), msg.getGroupUser().get(i).getId(), msg.getCurrentMessage().getMessage(), msg.getCurrentMessage().getType(), msg.getCurrentMessage().getDate(), msg.getFriendId());
				}
				}
			}
			private void sendWords(ChatFriend msg) {
				int flag=0;
				Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
				while(it.hasNext()) {
					Entry<Integer, Channel> next = it.next();
					if(next.getKey() == msg.getFriendId()) {
						System.out.println("服务器"+msg.getMyNickName()+"说"+msg.getCurrentMessage().getMessage());
						flag=1;
						next.getValue().send(msg);
						break;
					}
				}
				if(flag==0) {
					Jdbc.addMessage(msg.getMyId(), msg.getFriendId(), msg.getCurrentMessage().getMessage(), msg.getCurrentMessage().getType(),msg.getCurrentMessage().getDate());
				}
				
			}
			private void query(String datas) {
				String account = datas.substring(datas.indexOf("3查询")+3);
				int id = (Integer.parseInt(account)-3384);
				//数据库操作
				List<String> list = Jdbc.getSelect(id);
				
				if(list.size()==0)  {
					String msg = "3查询无此用户信息查询失败";
					send(msg);
				}
				else {
					User msg = new User();
					msg.setBir(list.get(3));
					msg.setHead(list.get(6));
					msg.setId(Integer.parseInt(list.get(0)));
					msg.setUserAccount(Integer.parseInt(list.get(0))+3384);
					msg.setNickName(list.get(5));
					msg.setSex(list.get(2));
					msg.setSignature(list.get(7));
					msg.setOnline(Integer.parseInt(list.get(8)));
					System.out.println("在线吗"+Integer.parseInt(list.get(8)));
					msg.setType(4);
					send(msg);
				}
			}
			private void edite(UserCustom msg) {
				Jdbc.editeInformation(msg);
					Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
					while(it.hasNext()) {
						Entry<Integer, Channel> next = it.next();
						for(int i=0;i<msg.getFriends().size();i++) {
							if(msg.getFriends().get(i).getId() == next.getKey()) {
								msg.setType(8);
								next.getValue().send(msg);
							}
						}
						}
					msg.setType(2);
				send(msg);
			}
			private void registration(String datas) {
				String msg = "";
				//解析报文
				String name =datas.substring(datas.indexOf("name=")+5, datas.indexOf("&pwd"));
				String pwd = datas.substring(datas.indexOf("&pwd=")+5, datas.indexOf("&bir"));
				String bir = datas.substring(datas.indexOf("&bir=")+5, datas.indexOf("&sex"));;
				String sex = datas.substring(datas.indexOf("&sex=")+5, datas.indexOf("&email"));;
				String email = datas.substring(datas.indexOf("&email=")+7, datas.indexOf("&head"));;
				String head = datas.substring(datas.lastIndexOf("=")+1);
				System.out.println(name);
				System.out.println(pwd);
				System.out.println(bir);
				System.out.println(sex);
				System.out.println(email);
				System.out.println(head);
				//数据库增加
				Jdbc.add(name, pwd, bir, sex, email, head);
			    //给账号
				List<String> list = Jdbc.getSelectId(); 
    			int size = list.size();
    			int id = Integer.parseInt(list.get(size-1));
    			int number = id + 3384;
    			msg = "2注册"+number;
    			send(msg);
				
			}
			private void login(String datas) {
				// TODO Auto-generated method stub
				
				//解析报文
				String uname = datas.substring(datas.indexOf("uname=")+6, datas.indexOf("&"));
				String upwd = datas.substring(datas.lastIndexOf("=")+1);
				System.out.println(uname);
				System.out.println(upwd);
				int id = (Integer.parseInt(uname)-3384);
				//数据库操作
				List<String> list = Jdbc.getSelect(id);
				
				if(list.size()==0 ||!list.get(1).equals(upwd)||Integer.parseInt(list.get(8))==1 ) {
					String msg = "1登录账号或密码输入错误";
					send(msg);
				}
				else {
					//添加个人信息
					UserCustom msg = new UserCustom();
					msg.setBir(list.get(3));
					msg.setHead(list.get(6));
					msg.setId(Integer.parseInt(list.get(0)));
					msg.setUserAccount(Integer.parseInt(list.get(0))+3384);
					msg.setNickName(list.get(5));
					msg.setSex(list.get(2));
					msg.setSignature(list.get(7));
					msg.setType(1);
					msg.setPwd(list.get(1));
					//修改online
					Jdbc.updateOnline(Integer.parseInt(list.get(0)), 1);
					msg.setOnline(1);
					//导入好友
					List<User> allFriends = new ArrayList<User>();
					List<Integer> friendList = Jdbc.exportFriends(msg.getId());
					System.out.println("好友数量："+friendList.size());
					for(int i = 0 ; i < friendList.size() ; i++) {
						List<String> friend = Jdbc.getSelect(friendList.get(i));
						User aFriend = new User();
						aFriend.setBir(friend.get(3));
						aFriend.setHead(friend.get(6));
						aFriend.setId(Integer.parseInt(friend.get(0)));
						aFriend.setUserAccount(Integer.parseInt(friend.get(0))+3384);
						aFriend.setNickName(friend.get(5));
						aFriend.setSex(friend.get(2));
						aFriend.setSignature(friend.get(7));
						aFriend.setOnline(Integer.parseInt(friend.get(8)));
						allFriends.add(aFriend);
						}
					for(int i=0;i<allFriends.size();i++) {
						System.out.println("好友有："+allFriends.get(i).getNickName());
					}
					msg.setFriends(allFriends);
					//导入群
					List<Group> allGroup = new ArrayList<Group>();
					List<Integer> groupList = Jdbc.exportGroup(msg.getId());
					for(int i=0;i<groupList.size();i++) {
						List<String> group = Jdbc.getGroup(groupList.get(i));
						List<Integer> groupUser = Jdbc.exportGroupUser(groupList.get(i));
						List<User> us = new ArrayList<User>();
						for(int j=0;j<groupUser.size();j++) {
							List<String>uu = Jdbc.getSelect(groupUser.get(j));
							User u = new User();
							u.setHead(uu.get(6));
							u.setId(Integer.parseInt(uu.get(0)));
							u.setUserAccount(Integer.parseInt(uu.get(0))+3384);
							u.setNickName(uu.get(5));
							us.add(u);
						}
						Group aGroup = new Group();
						aGroup.setAccount(Integer.parseInt(group.get(1)));
						aGroup.setId(Integer.parseInt(group.get(0)));
						aGroup.setName(group.get(2));
						aGroup.setGroupUser(us);
						allGroup.add(aGroup);
					}
					msg.setGroup(allGroup);
					//添加Map
					clientMap.put(Integer.parseInt(list.get(0)), this);
					send(msg);
					//通知其他人
					Iterator<Entry<Integer, Channel>> it = clientMap.entrySet().iterator();
					while(it.hasNext()) {
						Entry<Integer, Channel> next = it.next();
						for(int i=0;i<msg.getFriends().size();i++) {
							if(next.getKey() == msg.getFriends().get(i).getId()) {
								msg.setType(7);
								next.getValue().send(msg);
								break;
							}
						}
					}
					
				}
				//System.out.println(msg);
				
			}
	     }
}

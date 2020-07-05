package client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import Interface.AddGroup;
import Interface.ChatWithFriend;
import Interface.EditeInformation;
import Interface.FriendRequest;
import Interface.FriendsQuery;
import Interface.GetFile;
import Interface.Mailbox;
import Interface.MainInterface;
import Interface.MyRecord;
import Interface.PersonalPanel;
import Interface.RegistrationInterface;
import Interface.RetrievePassword;
import Interface.SignInterface;
import Interface.tip;
import bean.ChatFriend;
import bean.FileMessage;
import bean.Friends;
import bean.Group;
import bean.GroupRelationship;
import bean.Message;
import bean.Phrase;
import bean.User;
import bean.UserCustom;
import tcp.Utils;

public class Client  {
	private UserCustom user;
	private Socket client; 
	private Send send;
	private Receive receive;
	private SignInterface sign;
	private RegistrationInterface regist;
	public MainInterface main;
	private PersonalPanel person;
	private EditeInformation edite;
	private tip atip;
	private FriendsQuery friendsQuery;
	private FriendRequest friendRequest;
	public ChatWithFriend chatFriend;
	private AddGroup addGroup;
	private Phrase ph=null;
	private GetFile getFile;
	private RetrievePassword pas;
	public MyRecord video;
	public Phrase getPh() {
		return ph;
	}
	public void setPh(Phrase ph) {
		this.ph = ph;
	}
	public Client() throws UnknownHostException, IOException{
		System.out.println("------Client-----");
    	//1.建立连接：使用Socket创建客户端+服务的地址和端口
 		client = new Socket("localhost",8888);
 		//2.客户端发送消息
 		send = new Send(client);
 		receive = new Receive(client);
 		new Thread(send).start();
 		new Thread(receive).start();
	}
	public UserCustom getUser() {
		return user;
	}
	public void getMessage(Object msg)
	{
		send.getcontant(msg);
	}
	public void setPersonal(User user,int type) {
		this.person = new PersonalPanel(this,user,type);
		person.frame.setVisible(true);
	}
	public void closePersonal() {
		person.frame.setVisible(false);
	}
	public void setMyRecord() {
		this.video = new MyRecord(this);
	}
	public void closeMyRecord() {
		video.setVisible(false);
	}
	public void setEdite(User user) {
		this.edite = new EditeInformation(this,user);
		edite.frame.setVisible(true);
	}
	public void closeEdite() {
		edite.frame.setVisible(false);
	}
	public void setChatWithFriend(ChatFriend friend,Boolean isGroup) {
		this.ph = readPhrase();
		this.chatFriend = new ChatWithFriend(this,friend,isGroup,ph);
		chatFriend.setVisible(true);
	}
	public void closeChatWithFriend() {
		chatFriend.setVisible(false);
	}
	public void setFriendsQuery() {
		this.friendsQuery = new FriendsQuery(this);
		friendsQuery.frame.setVisible(true);
	}
	public void closeFriendsQuery() {
		friendsQuery.frame.setVisible(false);
	}
	public void setAddGroup() {
		this.addGroup = new AddGroup(this);
		addGroup.frame.setVisible(true);
	}
	public void closeAddGroup() {
		addGroup.frame.setVisible(false);
	}
	public void setSign() {
		this.sign = new SignInterface(this);
		sign.frmQq.setVisible(true);
	}
	public void closeSign() {
		sign.frmQq.setVisible(false);
	}
	public void setGetFile(FileMessage msg) {
		this.getFile = new GetFile(this,msg);
		getFile.frame.setVisible(true);
	}
	public void closeGetFile() {
		getFile.frame.setVisible(false);
	}
	public void setFriendRequest(Friends msg) {
		this.friendRequest = new FriendRequest(this,msg);
		friendRequest.frame.setVisible(true);
	}
	public void closeFriendRequest() {
		friendRequest.frame.setVisible(false);
	}
	public void setMain(UserCustom user) {
		this.main = new MainInterface(this,user);
		this.user = user;
		main.frame.setVisible(true);
	}
	public void closeMain() {
		main.frame.setVisible(false);
	}
	public void setRegist() {
		System.out.println("注册窗口开启");
		this.regist = new RegistrationInterface(this);
		regist.frmQq.setVisible(true);
	}
	public void closeRegist() {
		regist.frmQq.setVisible(false);
	}
	public void setRetrievePassword() {
		this.pas = new RetrievePassword(this);
		pas.frame.setVisible(true);
	}
	public void closeRetrievePassword() {
		pas.frame.setVisible(false);
	}
	public void settip(String tips1,String tips2) {
		this.atip = new tip(tips1,tips2,this);
		atip.frame.setVisible(true);
	}
	private Phrase readPhrase() {
		String path = "E:\\Chat room\\record\\"+this.getUser().getId()+"phrase.txt";
		File f = new File(path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		        Phrase msg = new Phrase();
		        Vector<String> m = new Vector<String>(); 
		        //打开带读取的文件
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("E:\\Chat room\\record\\"+this.getUser().getId()+"phrase.txt"));
			String line=null;
		
		while((line=br.readLine())!=null) {
			m.add(line);
			}
		
		br.close();//关闭文件
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg.setPhrase(m);
		
		return msg;

	}
	
	public class Receive implements Runnable{
	    private ObjectInputStream dis;
		private Socket client;
		private boolean isRunning;
	    public Receive(Socket client) {
	    	this.client = client;
	    	this.isRunning = true;
	    	try {
				dis = new ObjectInputStream(client.getInputStream());
			} catch (IOException e) {
				System.out.println("=====2=====");
				release();
			}
	    }
	     //接收消息 
		 private Object receive() {
			 Object msg = null; 
			 try {
				msg = dis.readObject();
			} catch (Exception e) {
				System.out.println("=====4=====");
				e.printStackTrace();
				release();
			}
			 return msg;
		 }
		@Override
		public void run() {
			while(isRunning) {
				Object msgObject = receive();
				System.out.println("接收成功");
				if(msgObject instanceof String) {
					String msg = (String) msgObject;
				if(!msg.equals("")&&(null!=msg)) {
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
		            //查询好友
		            if(type.equals("3")) {
		            	JOptionPane.showMessageDialog(friendsQuery.frame, "此用户不存在，查询失败");
		            }
		            if(type.equals("4")) {
		            	String mail = msg.substring(msg.lastIndexOf("&")+1);
		            	if(mail.equals("账号不存在")) {
		            		JOptionPane.showMessageDialog(pas.frame, "此用户不存在，获取失败");
		            	}
		            	else {
		            		Mailbox sendmail = new Mailbox(mail);
		            		pas.setAut(sendmail.authCode);
		            		JOptionPane.showMessageDialog(pas.frame, "获取验证码成功");
		            	}
		            }
		            if(type.equals("5")) {
		            	
		            }
		            if(msg.equals("发送好友请求成功")) {
		            	closePersonal();
		            }
		            if(msg.equals("改群不存在")) {
		            	
		            	JOptionPane.showMessageDialog(addGroup.frame, "此群不存在，查询失败");
		            }
						}
		            
					}
				else if(msgObject instanceof UserCustom) {
					UserCustom msg =(UserCustom) msgObject;
					if(msg.getType() == 1) {
					System.out.println("登陆成功");
					msg.setOnline(1);
					if(sign.radioButton.isSelected() == true) {
						try {
							writePwd(msg.getUserAccount(),msg.getPwd());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						 File file = new File("E:\\Chat room\\record\\"+"pwd"+".txt");
						 file.delete();
					}
					setMain(msg);
					closeSign();
					}
					if(msg.getType() == 2) {
						
						msg.setFriends(getUser().getFriends());
						closeMain();
						setMain(msg);
						closeEdite();
					}
					//对方同意
					if(msg.getType() == 5) {
						User m = msg;
						getUser().getFriends().add(m);
						main.setUser(user);
						main.setFriendTree();
						closeFriendRequest();
					}
					//自己知道
					if(msg.getType() == 6) {
						System.out.println("我知道了");
						User m = msg;
						getUser().getFriends().add(m);
						main.setUser(user);
						main.setFriendTree();
					}
					if(msg.getType() == 7) {
						for(int i=0;i<user.getFriends().size();i++) {
							if(user.getFriends().get(i).getId() == msg.getId()) {
								user.getFriends().get(i).setOnline(msg.getOnline());
								System.out.println(msg.getNickName()+"上线了没"+user.getFriends().get(i).getOnline());
								break;
							}
						}
						
						main.setUser(user);
						main.setFriendTree();
					}
					if(msg.getType() == 8) {
						for(int i=0;i<user.getFriends().size();i++) {
							if(user.getFriends().get(i).getId() == msg.getId()) {
								user.getFriends().get(i).setBir(msg.getBir());
								user.getFriends().get(i).setHead(msg.getHead());
								user.getFriends().get(i).setNickName(msg.getNickName());
								user.getFriends().get(i).setSex(msg.getSex());
								user.getFriends().get(i).setSignature(msg.getSignature());
								break;
							}
						}
						main.setUser(user);
						main.setFriendTree();
					}
				}
				else if(msgObject instanceof User) {
					User msg = (User) msgObject; 
					if(msg.getType() == 4) {
						closeFriendsQuery();
						setPersonal(msg,2);
					}
				}
				else if(msgObject instanceof Friends) {
					Friends msg = (Friends) msgObject;
					if(msg.getType() == 1) {
					setFriendRequest(msg);
					}
					if(msg.getType() == 2) {
						
					}
					if(msg.getType() == 3) {
						for(int i = 0; i < user.getFriends().size(); i++) {
							if(user.getFriends().get(i).getId() == msg.getMyId()) {
								user.getFriends().remove(i);
								break;
							}
						}
						//closeMain();
						//setMain(user);
						main.setUser(user);
						main.setFriendTree();
					}
				}
				else if(msgObject instanceof ChatFriend) {
					ChatFriend msg = (ChatFriend) msgObject;
					if(msg.getType() == 1) {
						if(msg.getCurrentMessage().getType() == 1) {
							System.out.println(msg.getCurrentMessage().getMessage());
							chatFriend.addMessage(msg.getMyNickName(), msg.getCurrentMessage().getDate(), msg.getCurrentMessage().getMessage(), false,false);
							if(!msg.isGroup()) {
							try {
								writeRecord(msg.getMyId(),msg.getCurrentMessage().getMessage(),msg.getCurrentMessage().getDate(),msg.getCurrentMessage().getType(),msg.getFriendId(),msg.getMyId(),msg.isGroup());
							} catch (IOException e) {
								e.printStackTrace();
							}
							}
							else
							{
								try {
									writeRecord(msg.getMyId(),msg.getCurrentMessage().getMessage(),msg.getCurrentMessage().getDate(),msg.getCurrentMessage().getType(),msg.getCurrentMessage().getId(),msg.getFriendId(),msg.isGroup());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					if(msg.getType() == 2) {
						setChatWithFriend(msg, msg.isGroup());
					}
				}
				else if(msgObject instanceof Group) {
					Group msg = (Group) msgObject;
					String tips2 = "创建成功";
					String tips1 = "账号为"+msg.getAccount(); 
					settip(tips1,tips2);
					System.out.println("刚刚创的群里面有"+msg.getGroupUser().size());
					user.getGroup().add(msg);
				}
				else if(msgObject instanceof GroupRelationship) {
					GroupRelationship msg = (GroupRelationship) msgObject;
					if(msg.getType() == 1) {
						Group group = new Group();
						group.setAccount(msg.getAccount());
						group.setGroupUser(msg.getGroupUser());
						group.setId(msg.getGroupId());
						group.setName(msg.getName());
						user.getGroup().add(group);
						main.setGroupTree();
						closeAddGroup();
						
					}
					if(msg.getType() == 2) {
						for(int i=0;i<user.getGroup().size();i++) {
						if(user.getGroup().get(i).getId() == msg.getGroupId()) {
							User use = new User();
							use.setHead(msg.getUserHead());
							use.setId(msg.getUserId());
							use.setNickName(msg.getUserName());
							use.setUserAccount(msg.getUserAccount());
							user.getGroup().get(i).getGroupUser().add(use);
							break;
						}
						}
						main.setGroupTree();
					}
					if(msg.getType() == 4) {
						Group group = new Group();
						group.setAccount(msg.getAccount());
						group.setGroupUser(msg.getGroupUser());
						group.setId(msg.getGroupId());
						group.setName(msg.getName());
						user.getGroup().add(group);
						JOptionPane.showMessageDialog(main.frame, msg.getFriend()+"邀请你进"+msg.getName());
						main.setGroupTree();
					}
				}
				else if(msgObject instanceof FileMessage) {
					FileMessage msg = (FileMessage) msgObject;
					if(msg.getType()==2) {
						setGetFile(msg);
					}
					if(msg.getType() == 1) {
						byteFile(msg.getB(),"E:\\Chat room\\getfile","\\"+msg.getName());
						chatFriend.addPic(msg.getFriendName(),msg.getDate(), "E:\\Chat room\\getfile"+"\\"+msg.getName(), false);
						try {
							writeRecord(msg.getMyId(),"E:\\Chat room\\getfile"+"\\"+msg.getName(),msg.getDate(),2,msg.getFriendId(),msg.getMyId(),false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(msg.getType() == 3) {
						chatFriend.addFiles(msg);
					}
					if(msg.getType()==5) {
						byteFile(msg.getB(),"E:\\Chat room\\receiveVoice","\\"+msg.getName());
						chatFriend.addVideo(msg.getMyName(), msg.getDate(),"E:\\Chat room\\receiveVoice"+"\\"+msg.getName(), false);
						try {
							writeRecord(msg.getMyId(),"E:\\Chat room\\receiveVoice"+"\\"+msg.getName(),msg.getDate(),5,msg.getFriendId(),msg.getMyId(),false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				}
		}
		private void writePwd(int userAccount, String pwd) throws FileNotFoundException {
			OutputStream os = new FileOutputStream("E:\\Chat room\\record\\"+"pwd"+".txt");
			PrintWriter pw=new PrintWriter(os);
			pw.println(userAccount);
			pw.println(pwd);//每输入一个数据，自动换行，便于我们每一行每一行地进行读
			pw.close();
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		private void byteFile(byte[] b,String filePath,String fileName) {
			BufferedOutputStream bos=null;
		    FileOutputStream fos=null;
		    File file=null;
		    try{
		        File dir=new File(filePath);
		        if(!dir.exists() && !dir.isDirectory()){//判断文件目录是否存在
		            dir.mkdirs();
		        }
		        file=new File(filePath+fileName);
		        fos=new FileOutputStream(file);
		        bos=new BufferedOutputStream(fos);
		        bos.write(b);
		    }
		    catch(Exception e){
		        System.out.println(e.getMessage());
		        e.printStackTrace();
		    }
		    finally{
		        try{
		            if(bos != null){
		                bos.close();
		            }
		            if(fos != null){
		                fos.close();
		            }
		        }
		        catch(Exception e){
		            System.out.println(e.getMessage());
		            e.printStackTrace();
		        }
		    }
	}
			private void registration(String datas) {
				String tips2 = "注册成功";
				String tips1 = "账号为"+datas.substring(datas.indexOf("2注册")+3); 
				System.out.println(tips1);
				settip(tips1,tips2);
			
		}
			private void login(String datas) {
				 if(datas.equals("1登录ojbk")) {
					 
				 }
				 else {
					 String tip1 = "  登录账号或密码输入错误";
	     			String tip2 = "登录失败";
	     			JOptionPane.showMessageDialog(sign.frmQq, "登录账号或密码错误");
				 }
			
		}
			private void writeRecord(int id,String msg,String date,int type,int myId,int friendId,boolean isGroup) throws IOException {
				OutputStream os = new FileOutputStream("E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt",true);
				PrintWriter pw=new PrintWriter(os);
				pw.println(id);
				pw.println(msg);//每输入一个数据，自动换行，便于我们每一行每一行地进行读取
				pw.println(date);
				pw.println(type);
				pw.println(0);
				pw.close();
				os.close();
		 }
			
			
		
		
		//释放资源
		private void release() {
			 this.isRunning = false;
			 Utils.close(dis,client);
		 }

	}

}

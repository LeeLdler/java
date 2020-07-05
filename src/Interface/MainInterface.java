package Interface;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import Interface.JPopMenuDemo.ChangeColorAction;
import bean.ChatFriend;
import bean.Friends;
import bean.GroupRelationship;
import bean.Message;
import bean.User;
import bean.UserCustom;
import client.Client;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;
public class MainInterface {

	public JFrame frame;
	JMenuItem[] friendItems;//���Ѳ˵���
	JMenuItem[] groupItems;//Ⱥ�Ĳ˵���
	private Client client;
	private UserCustom user;
	private JTree friendTree;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTree groupTree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//MainInterface window = new MainInterface();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainInterface(Client client,UserCustom user) {
		this.client = client;
		this.user = user;
		initialize();
	}
	
	public void setUser(UserCustom user) {
		this.user = user;
	}
    public void setFriendTree() {
    	scrollPane.repaint();
		JPopupMenu popupMenu; //�����˵�
		FriendAction action = new FriendAction(); //�˵����¼�����
		String[] friendStr = {"������Ϣ","�鿴����","ɾ������","�����ļ�"}; //�˵�������
		popupMenu=new JPopupMenu(); //ʵ���������˵�
		friendItems=new JMenuItem[4]; //��ʼ������
		for (int i=0;i<4;i++) {
			friendItems[i]=new JMenuItem(friendStr[i]); //ʵ�����˵���
		popupMenu.add(friendItems[i]); //���Ӳ˵���˵���
		
		friendItems[i].addActionListener(action); //�˵����¼�����
		}
		FriendNode friendRoot=new FriendNode("�ҵĺ���",3);
		
		for(int i=0;i<user.getFriends().size();i++) {
			FriendNode node=new FriendNode(new ImageIcon("src//interface//"+user.getFriends().get(i).getHead()),user.getFriends().get(i).getNickName(),user.getFriends().get(i).getOnline(),user.getFriends().get(i).getUserAccount());
			friendRoot.add(node);
			System.out.println(user.getFriends().get(i).getNickName()+"������"+user.getFriends().get(i).getOnline());
		}
		
		 
		
		scrollPane.setBounds(0, 199, 367, 229);
		//��scrollPane��ӵ�JPanel��������ʾ
       frame.getContentPane().add(scrollPane);
       //�������˫�������¼�
       //tree.addMouseListener(new MouseHandle());
        
      
       //���ղŵĸ��ڵ���ӵ�JTree��
       friendTree=new JTree(friendRoot);
     
       friendTree.addMouseListener(new MouseAdapter() { 
    	
    	   
       	@Override
       	public void mouseClicked(MouseEvent e) {
       	    

       		if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
       			
                System.out.println("����");
                System.out.println(getIdex(friendTree));

            }
       	}
       	public void mousePressed(MouseEvent event) { //������
			
			triggerEvent(event); //����triggerEvent���������¼�
			
			}

			public void mouseReleased(MouseEvent event) { //�ͷ����
			
			triggerEvent(event);
			
			}

			private void triggerEvent(MouseEvent event) { //�����¼�
			
			if (event.isPopupTrigger()&& getIdex(friendTree)>=0) //����ǵ����˵��¼�(����ƽ̨��ͬ���ܲ�ͬ)
			
			popupMenu.show(event.getComponent(),event.getX(),event.getY()); //��ʾ�˵�
			
			}
       });
       //��tree��ӵ�JScrollPane ��
       scrollPane.setViewportView(friendTree);
       
		//������ǰ������ȥ��
		friendTree.putClientProperty("JTree.lineStyle", "Horizontal");
		//�������������С����ʽ
		friendTree.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//�������ڵ�ĸ߶�
		friendTree.setRowHeight(80);
		//���õ�Ԫ����
		friendTree.setCellRenderer(new FriendNodeRenderer());
    	
    }
    public void setGroupTree() {
    	scrollPane_1.repaint();
    	JPopupMenu popupMenu_1; //�����˵�
		GroupAction gaction = new GroupAction(); //�˵����¼�����
		String[] groupStr =  {"������Ϣ","�鿴����","�˳�Ⱥ��","�������"};
		popupMenu_1=new JPopupMenu(); //ʵ���������˵�
		groupItems=new JMenuItem[4]; //��ʼ������
		for (int i=0;i<4;i++) {
			groupItems[i]=new JMenuItem(groupStr[i]); //ʵ�����˵���
		popupMenu_1.add(groupItems[i]); //���Ӳ˵���˵���
		
		groupItems[i].addActionListener(gaction); //�˵����¼�����
		}
		GroupNode groupRoot=new GroupNode("�ҵ�Ⱥ��",1);
		for(int i=0;i<user.getGroup().size();i++) {
			GroupNode node = new GroupNode(user.getGroup().get(i).getName(),user.getGroup().get(i).getAccount(),user.getGroup().get(i).getGroupUser().size(),2);
			System.out.println("Ⱥ������ˣ�"+user.getGroup().get(i).getGroupUser().size());
			groupRoot.add(node);
			//System.out.println(user.getFriends().get(i).getNickName()+"������"+user.getFriends().get(i).getOnline());
		}
		scrollPane_1.setBounds(0, 428, 367, 231);
		frame.getContentPane().add(scrollPane_1);
		//���ղŵĸ��ڵ���ӵ�JTree��
	     groupTree=new JTree(groupRoot);
		//��tree��ӵ�JScrollPane ��
		scrollPane_1.setViewportView(groupTree);
	       
			//������ǰ������ȥ��
		groupTree.putClientProperty("JTree.lineStyle", "Horizontal");
			//�������������С����ʽ
		groupTree.setFont(new Font("΢���ź�", Font.PLAIN, 15));
			//�������ڵ�ĸ߶�
		groupTree.setRowHeight(80);
			//���õ�Ԫ����
		groupTree.setCellRenderer(new GroupNodeRenderer());
		groupTree.addMouseListener(new MouseAdapter() { 
	    	   
	       	public void mousePressed(MouseEvent event) { //������
				
				triggerEvent(event); //����triggerEvent���������¼�
				
				}

				public void mouseReleased(MouseEvent event) { //�ͷ����
				
				triggerEvent(event);
				
				}

				private void triggerEvent(MouseEvent event) { //�����¼�
				
				if (event.isPopupTrigger()) //����ǵ����˵��¼�(����ƽ̨��ͬ���ܲ�ͬ)
				
				popupMenu_1.show(event.getComponent(),event.getX(),event.getY()); //��ʾ�˵�
				
				}
	       });
    }
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				System.out.println("������");
				user.setType(3);
				client.getMessage(user);
				System.exit(0);
			}
		});
		frame.setTitle("\u5B9D\u53EF\u68A6\u804A\u5929\u5BA4");
		frame.setBounds(100, 100, 385, 706);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // ���ñ�������ͼ�� 
        frame.setIconImage(imageIcon.getImage());
        //ͷ��
         ImageIcon img = new ImageIcon("src//interface//"+user.getHead());
 		img.setImage(img.getImage().getScaledInstance(99,100,Image.SCALE_DEFAULT ));
 		JLabel imagLabel = new JLabel(img); 
 		imagLabel.setBounds(37, 27, 91, 88);
 		imagLabel.addMouseListener(new MouseAdapter() {
 			@Override
 			public void mouseClicked(MouseEvent e) {
 				if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2)
 				client.setPersonal(user,1);
 			}
 		});
 		frame.getContentPane().setLayout(null);
 		frame.getContentPane().add(imagLabel);
		
		
		JLabel label = new JLabel(user.getNickName());
		label.setBounds(167, 55, 91, 27);
		label.setFont(new Font("����",0,17));
		label.setForeground(new Color(255,255,255));
		frame.getContentPane().add(label);
		
		/*
		 * ����JTree
		 */
		JPopupMenu popupMenu; //�����˵�
		FriendAction action = new FriendAction(); //�˵����¼�����
		String[] friendStr = {"������Ϣ","�鿴����","ɾ������","�����ļ�"}; //�˵�������
		String[] groupStr =  {"������Ϣ","�鿴����","�˳�Ⱥ��","�������"};
		popupMenu=new JPopupMenu(); //ʵ���������˵�
		friendItems=new JMenuItem[4]; //��ʼ������
		for (int i=0;i<4;i++) {
			friendItems[i]=new JMenuItem(friendStr[i]); //ʵ�����˵���
		popupMenu.add(friendItems[i]); //���Ӳ˵���˵���
		
		friendItems[i].addActionListener(action); //�˵����¼�����
		}
		
		FriendNode friendRoot=new FriendNode("�ҵĺ���",3);
		
		for(int i=0;i<user.getFriends().size();i++) {
			FriendNode node=new FriendNode(new ImageIcon("src//interface//"+user.getFriends().get(i).getHead()),user.getFriends().get(i).getNickName(),user.getFriends().get(i).getOnline(),user.getFriends().get(i).getUserAccount());
			friendRoot.add(node);
			System.out.println(user.getFriends().get(i).getNickName()+"������"+user.getFriends().get(i).getOnline());
		}
		
		 
		//��tree��ӵ�JScrollPane ��
		scrollPane=new JScrollPane();
		scrollPane.setBounds(0, 199, 367, 229);
		//��scrollPane��ӵ�JPanel��������ʾ
       frame.getContentPane().add(scrollPane);
       //�������˫�������¼�
       //tree.addMouseListener(new MouseHandle());
        
      
       //���ղŵĸ��ڵ���ӵ�JTree��
       friendTree=new JTree(friendRoot);
     
       friendTree.addMouseListener(new MouseAdapter() { 
    	
    	   
       	@Override
       	public void mouseClicked(MouseEvent e) {
       	    

       		if(e.getModifiers()==InputEvent.BUTTON1_MASK && e.getClickCount()==2) {
       			
                System.out.println("����");
                System.out.println(getIdex(friendTree));

            }
       	}
       	public void mousePressed(MouseEvent event) { //������
			
			triggerEvent(event); //����triggerEvent���������¼�
			
			}

			public void mouseReleased(MouseEvent event) { //�ͷ����
			
			triggerEvent(event);
			
			}

			private void triggerEvent(MouseEvent event) { //�����¼�
			
			if (event.isPopupTrigger()&& getIdex(friendTree)>=0) //����ǵ����˵��¼�(����ƽ̨��ͬ���ܲ�ͬ)
			
			popupMenu.show(event.getComponent(),event.getX(),event.getY()); //��ʾ�˵�
			
			}
       });
       //��tree��ӵ�JScrollPane ��
       scrollPane.setViewportView(friendTree);
       
		//������ǰ������ȥ��
		friendTree.putClientProperty("JTree.lineStyle", "Horizontal");
		//�������������С����ʽ
		friendTree.setFont(new Font("΢���ź�", Font.PLAIN, 15));
		//�������ڵ�ĸ߶�
		friendTree.setRowHeight(80);
		//���õ�Ԫ����
		friendTree.setCellRenderer(new FriendNodeRenderer());
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 428, 367, 231);
		frame.getContentPane().add(scrollPane_1);
		
		
		
		GroupNode groupRoot=new GroupNode("�ҵ�Ⱥ��",1);
		for(int i=0;i<user.getGroup().size();i++) {
			GroupNode node = new GroupNode(user.getGroup().get(i).getName(),user.getGroup().get(i).getAccount(),user.getGroup().get(i).getGroupUser().size(),2);
			groupRoot.add(node);
			//System.out.println(user.getFriends().get(i).getNickName()+"������"+user.getFriends().get(i).getOnline());
		}
		//���ղŵĸ��ڵ���ӵ�JTree��
	    groupTree=new JTree(groupRoot);
		//��tree��ӵ�JScrollPane ��
		scrollPane_1.setViewportView(groupTree);
	       
			//������ǰ������ȥ��
		groupTree.putClientProperty("JTree.lineStyle", "Horizontal");
			//�������������С����ʽ
		groupTree.setFont(new Font("΢���ź�", Font.PLAIN, 15));
			//�������ڵ�ĸ߶�
		groupTree.setRowHeight(80);
			//���õ�Ԫ����
		groupTree.setCellRenderer(new GroupNodeRenderer());
		
		//��Ӻ���
		JButton button = new JButton("\u6DFB\u52A0\u597D\u53CB");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.setFriendsQuery();
			}
		});
		button.setBounds(0, 173, 113, 27);
		button.setFont(new Font("����",0,14));
		frame.getContentPane().add(button);
		
		//����Ⱥ��
		JButton button_1 = new JButton("\u521B\u5EFA\u7FA4\u804A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateGroup m = new CreateGroup(client);
				m.frame.setVisible(true);
			}
		});
		button_1.setBounds(126, 173, 113, 27);
		button_1.setFont(new Font("����",0,14));
		frame.getContentPane().add(button_1);
		
		//����Ⱥ��
		JButton button_2 = new JButton("\u52A0\u5165\u7FA4\u804A");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.setAddGroup();
			}
		});
		button_2.setBounds(253, 173, 113, 27);
		button_2.setFont(new Font("����",0,14));
		frame.getContentPane().add(button_2);
		System.out.println(user.getSignature());
		String signature = user.getSignature();
		if(null == signature||signature.equals(""))
		{
			signature = "\u5FEB\u6765\u7F16\u5199\u4F60\u7684\u4E2A\u6027\u7B7E\u540D\u5427";
		}
		JLabel label_1 = new JLabel(signature);
		label_1.setBounds(37, 133, 316, 27);
		label_1.setFont(new Font("����",0,17));
		label_1.setForeground(new Color(255,255,255));
		frame.getContentPane().add(label_1);
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 367, 200);
		ImageIcon img1 = new ImageIcon("img/luofei.jpeg");
		img1.setImage(img1.getImage().getScaledInstance(367,200,Image.SCALE_DEFAULT ));
		
		JLabel imagLabel1 = new JLabel(img1); 
		imagLabel1.setBounds(0,0,img1.getIconWidth(),img1.getIconHeight());
		panel1.add(imagLabel1);
		frame.getContentPane().add(panel1);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		
		JPopupMenu popupMenu_1; //�����˵�
		GroupAction gaction = new GroupAction(); //�˵����¼�����
		popupMenu_1=new JPopupMenu(); //ʵ���������˵�
		groupItems=new JMenuItem[4]; //��ʼ������
		for (int i=0;i<4;i++) {
			groupItems[i]=new JMenuItem(groupStr[i]); //ʵ�����˵���
		popupMenu_1.add(groupItems[i]); //���Ӳ˵���˵���
		
		groupItems[i].addActionListener(gaction); //�˵����¼�����
		}
		groupTree.addMouseListener(new MouseAdapter() { 
	    	   
	       	public void mousePressed(MouseEvent event) { //������
				
				triggerEvent(event); //����triggerEvent���������¼�
				
				}

				public void mouseReleased(MouseEvent event) { //�ͷ����
				
				triggerEvent(event);
				
				}

				private void triggerEvent(MouseEvent event) { //�����¼�
				
				if (event.isPopupTrigger()) //����ǵ����˵��¼�(����ƽ̨��ͬ���ܲ�ͬ)
				
				popupMenu_1.show(event.getComponent(),event.getX(),event.getY()); //��ʾ�˵�
				
				}
	       });
	       
		
	}
	private Vector<Message> readRecord(int myId,int friendId,boolean isGroup) {
		String path = "E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt";
		File f = new File(path);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		        Vector<Message> msg = new Vector<Message>();
		        //�򿪴���ȡ���ļ�
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("E:\\Chat room\\record\\"+myId+"to"+friendId+"isGroup"+isGroup+".txt"));
			String line=null;
		
		while((line=br.readLine())!=null) {
			Message s = new Message();
			s.setId(Integer.parseInt(line));
			line = br.readLine();
			
			s.setMessage(line);
			line = br.readLine();
			
			s.setDate(line);
			line = br.readLine();
			
			s.setType(Integer.parseInt(line));
			line = br.readLine();
			s.setIsMy(Integer.parseInt(line));
			msg.add(s);
			}
//		for(int i=0;i<msg.size();i++) {
//			System.out.println(msg.get(i).getMessage());
//			System.out.println(msg.get(i).getDate());
//			System.out.println(msg.get(i).getIsMy());
//			System.out.println(msg.get(i).getType());
//		}
		br.close();//�ر��ļ�
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return msg;

	}
	public int getIdex(JTree tree) {
		tree.getSelectionModel().setSelectionMode(
 	    	   TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
 		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree
 	    	   .getLastSelectedPathComponent();
 	    	   if (treeNode == null) {
 	    	   return -1;
 	    	   }
 	    	   
 	    	   // ��ȡѡ�нڵ�ĸ��ڵ�
 	    	   DefaultMutableTreeNode parent = (DefaultMutableTreeNode) treeNode
 	    	   .getParent();
 	    	   if (parent == null) {
 	    	   return -1;
 	    	   };

 	    	   // ��ȡѡ�нڵ�Ľڵ�����
 	    	   int selectedIndex = parent.getIndex(treeNode);
 	    	   return selectedIndex;
	}
	class FriendAction implements ActionListener { //�˵����¼�����

	 	public void actionPerformed(ActionEvent event) {

	 		for (int i=0;i<4;i++) {
				if (event.getSource()==friendItems[i]) { //�ж��¼��������ĸ��˵���
					
					int select = getIdex(friendTree);
	 				if(i == 1) {
	 					client.setPersonal(user.getFriends().get(select), 3);
	 				}
	 				if(i == 2) {
	 					Friends msg = new Friends(user.getId(),user.getFriends().get(select).getId(),3);
	 					client.getMessage(msg);
	 				}
	 				if(i == 0) {
	 					ChatFriend chat = new ChatFriend();
	 					chat.setFriendAccount(user.getFriends().get(select).getUserAccount());
	 					chat.setFriendHead(user.getFriends().get(select).getHead());
	 					chat.setFriendId(user.getFriends().get(select).getId());
	 					chat.setFriendNickName(user.getFriends().get(select).getNickName());
	 					chat.setFriendSignature(user.getFriends().get(select).getSignature());
	 					chat.setMyAccount(user.getUserAccount());
	 					chat.setMyId(user.getId());
	 					chat.setMyNickName(user.getNickName());
	 					chat.setGroup(false);
	 					try {
							chat.setMessage(readRecord(chat.getMyId(),chat.getFriendId(),false));
						} catch (Exception e) {
							e.printStackTrace();
						}
	 					chat.setType(2);
	 					client.getMessage(chat);
	 				}
	 				return;
		}
}

}
 }
	class GroupAction implements ActionListener { //�˵����¼�����

	 	public void actionPerformed(ActionEvent event) {

	 		for (int i=0;i<4;i++) {
				if (event.getSource()==groupItems[i]) { //�ж��¼��������ĸ��˵���
					int select = getIdex(groupTree);
	 				if(i == 0) {
	 					ChatFriend chat = new ChatFriend();
	 					chat.setFriendAccount(user.getGroup().get(select).getAccount());
	 					chat.setFriendNickName(user.getGroup().get(select).getName());
	 					chat.setFriendHead("group.png");
	 					chat.setFriendSignature(user.getGroup().get(select).getGroupUser().size()+("��"));
	 					chat.setFriendId(user.getGroup().get(select).getId());
	 					chat.setMyAccount(user.getUserAccount());
	 					chat.setMyId(user.getId());
	 					chat.setMyNickName(user.getNickName());
	 					chat.setGroup(true);
	 					chat.setGroupUser(user.getGroup().get(select).getGroupUser());
	 					chat.setMessage(readRecord(chat.getMyId(),chat.getFriendId(),true));
	 					chat.setType(2);
	 					client.getMessage(chat);
	 				}
	 				int select2 = getIdex(friendTree);
	 				if(i == 3) {
	 					if(select2 == -1) {
	 						JOptionPane.showMessageDialog(frame, "δ��ȡ����");
	 					}
	 					else {
	 						int flag=0;
	 						for(int j=0;j<client.getUser().getGroup().get(select).getGroupUser().size();j++) {
	 							if(client.getUser().getGroup().get(select).getGroupUser().get(j).getId()==client.getUser().getFriends().get(select2).getId()) {
	 								JOptionPane.showMessageDialog(frame, "�ú�������Ⱥ��");
	 								flag=1;
	 								break;
	 							}
	 						}
	 						if(flag==0) {
	 							GroupRelationship msg = new GroupRelationship();
	 							msg.setGroupId(user.getGroup().get(select).getId());
	 							msg.setUserId(user.getFriends().get(select2).getId());
	 							msg.setUserAccount(user.getFriends().get(select2).getUserAccount());
	 							msg.setUserHead(user.getFriends().get(select2).getHead());
	 							msg.setUserName(user.getFriends().get(select2).getNickName());
	 							msg.setFriend(user.getNickName());
	 							msg.setType(3);
	 							client.getMessage(msg);
	 						}
	 					}
	 				}
	 				return;
		}
}

}
 }
	
}


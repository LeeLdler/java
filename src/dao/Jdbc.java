package dao;

import java.beans.Statement;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import bean.FileMessage;
import bean.Message;
import bean.User;
import bean.UserCustom;

public class Jdbc {
	
	static final String URL = "jdbc:mysql://localhost:3306/qq?serverTimezone=UTC";
	static final String USER = "root";
	static final String PASSWORD = "123456";
	private static Connection connection()
	{	
		Connection conn = null;
		//ע������
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//��������
		try {
			conn = (Connection) DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;	
	}
	public static void add(String name,String pwd,String bir,String sex,String email,String head)
	{
        String sql = "insert into user (pwd,sex,bir,email,name,head,online) values(?,?,?,?,?,?,?)";
        Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        // Ϊsql����е�һ���ʺŸ�ֵ
        try {
			ps.setString(1, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ϊsql����еڶ����ʺŸ�ֵ
        try {
			ps.setString(2, sex);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ϊsql����е������ʺŸ�ֵ
        try {
			ps.setString(3, bir);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ϊsql����е��ĸ��ʺŸ�ֵ
        try {
			ps.setString(4, email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ϊsql����е�����ʺŸ�ֵ
        try {
			ps.setString(5, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ϊsql����е������ʺŸ�ֵ
        try {
			ps.setString(6, head);
			ps.setInt(7, 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // ִ��sql���
        try {
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // �ر����ݿ����Ӷ���
        try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static List<String> getSelectId() { 
		    // sql���
	        String sql = "select * from user";
             // ��ȡ������
	        Connection conn = connection();
	        PreparedStatement pst = null;
	        // ����һ��list���ڽ������ݿ��ѯ��������
	        List<String> list = new ArrayList<String>();
	        try {
	            pst = (PreparedStatement) conn.prepareStatement(sql);
	            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	                // ����ѯ����������ӵ�list��
	                list.add(rs.getString("id"));
	            }
	        } catch (Exception e) {
	        }finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	        return list;
	    }
	public static List<String> getSelect(int id) {
		
			// sql���
	        String sql = "select * from user";
             // ��ȡ������
	        Connection conn = connection();
	        PreparedStatement pst = null;
	        // ����һ��list���ڽ������ݿ��ѯ��������
	        List<String> list = new ArrayList<String>();
	        try {
	            pst = (PreparedStatement) conn.prepareStatement(sql);
	            ResultSet rs = pst.executeQuery();
	            while (rs.next()) {
	                if(rs.getString("id").equals(""+id)) {
	                list.add(rs.getString("id"));
	                list.add(rs.getString("pwd"));
	                list.add(rs.getString("sex"));
	                list.add(rs.getString("bir"));
	                list.add(rs.getString("email"));
	                list.add(rs.getString("name"));
	                list.add(rs.getString("head"));
	                list.add(rs.getString("signature"));
	                list.add(rs.getString("online"));
	                }
	            }
	        } catch (Exception e) {
	        }finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	        return list;
		
		
	}
	public static void editeInformation(UserCustom user)
	{
		// sql���
		String sql = "UPDATE user SET name=?, bir=?, sex=?, head=?, signature=? WHERE id=?";
		 // ��ȡ������
        Connection conn = connection();
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setString(1, user.getNickName());
			ps.setString(2, user.getBir());
		    ps.setString(3, user.getSex());
		    ps.setString(4, user.getHead());
		    ps.setString(5, user.getSignature());
		    ps.setInt(6, user.getId());
		    ps.executeUpdate();
		    System.out.println("�޸ĳɹ�(*�����)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	public static void editePwd(int id,String pwd)
	{
		// sql���
		String sql = "UPDATE user SET pwd=? WHERE id=?";
		 // ��ȡ������
        Connection conn = connection();
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setString(1, pwd);
			ps.setInt(2, id);
		    ps.executeUpdate();
		    System.out.println("�޸ĳɹ�(*�����)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	public static void updateOnline(int id,int type) {
		// sql���
		String sql = "UPDATE user SET online=? WHERE id=?";
		 // ��ȡ������
        Connection conn = connection();
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, type);
			ps.setInt(2, id);
		    ps.executeUpdate();
		    System.out.println("�޸�isOnline�ɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addFriends(int myId,int friendsId) {
		String sql = "insert into friend (friend1,friend2) values(?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, myId);
			ps.setInt(2, friendsId);
		    ps.executeUpdate();
		    System.out.println("��Ӻ��ѳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static List<Integer> exportFriends(int myId){
		// sql���
        String sql = "select * from friend";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<Integer> list = new ArrayList<Integer>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("friend1") == myId) {
                list.add(rs.getInt("friend2"));
                }
                if(rs.getInt("friend2") == myId) {
                	list.add(rs.getInt("friend1"));
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static void removeFriends(int myId,int friendId) {
		String sql = "DELETE FROM friend WHERE friend1=? and friend2=?";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, myId);
			ps.setInt(2, friendId);
		    ps.executeUpdate();
		    System.out.println("ɾ�����ѳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addMessage(int myId,int friendId,String chat,int types,String date) {
		String sql = "insert into chatrecord (friend1,friend2,chat,types,chatdate) values(?,?,?,?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, myId);
			ps.setInt(2, friendId);
			ps.setString(3, chat);
			ps.setInt(4, types);
			ps.setString(5, date);
		    ps.executeUpdate();
		    //System.out.println("��Ӻ��ѳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addGroupMessage(int myId,int friendId,String chat,int types,String date,int groupId) {
		String sql = "insert into chatrecord (friend1,friend2,chat,types,chatdate,groupId) values(?,?,?,?,?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, myId);
			ps.setInt(2, friendId);
			ps.setString(3, chat);
			ps.setInt(4, types);
			ps.setString(5, date);
			ps.setInt(6, groupId);
		    ps.executeUpdate();
		    //System.out.println("��Ӻ��ѳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Vector<Message> exportOffline(int myId,int friendId){
		// sql���
        String sql = "select * from chatrecord";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        Vector<Message> list = new Vector<Message>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("friend1") == myId && rs.getInt("friend2") == friendId) {
                	Message msg = new Message();
                	msg.setDate(rs.getString("chatdate"));
                	msg.setType(rs.getInt("types"));
                	msg.setMessage(rs.getString("chat"));
                	list.add(msg);
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static Vector<Message> exportGroupOffline(int myId,int friendId){
		// sql���
        String sql = "select * from chatrecord";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        Vector<Message> list = new Vector<Message>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("groupId") == friendId&&rs.getInt("friend2") == myId) {
                	Message msg = new Message();
                	msg.setDate(rs.getString("chatdate"));
                	msg.setId(rs.getInt("friend1"));
                	msg.setType(rs.getInt("types"));
                	msg.setMessage(rs.getString("chat"));
                	list.add(msg);
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static void removechat(int myId,int friendId) {
		String sql = "DELETE FROM chatrecord WHERE friend1=? and friend2=?";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, myId);
			ps.setInt(2, friendId);
		    ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void removeGroupchat(int myId,int friendId) {
		String sql = "DELETE FROM chatrecord WHERE friend2=? and groupId=?";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1,myId);
			ps.setInt(2, friendId);
		    ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void addGroup(int account,String name) {
		String sql = "insert into groupu (account,name) values(?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, account);
			ps.setString(2, name);
		    ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static int getSelectGroup() { 
	    // sql���
        String sql = "select * from groupu";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<String> list = new ArrayList<String>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                // ����ѯ����������ӵ�list��
                list.add(rs.getString("id"));
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list.size()+1;
    }
	public static void addGroupg(int userId,int groupId) {
		String sql = "insert into groupg (userId,groupId) values(?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, groupId);
		    ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static List<Integer> exportGroup(int myId){
		// sql���
        String sql = "select * from groupg";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<Integer> list = new ArrayList<Integer>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("userId") == myId) {
                list.add(rs.getInt("groupId"));
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static List<String> getGroup(int id) {
		
		// sql���
        String sql = "select * from groupu";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<String> list = new ArrayList<String>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getString("id").equals(""+id)) {
                list.add(rs.getString("id"));
                list.add(rs.getString("account"));
                list.add(rs.getString("name"));
                
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static List<Integer> exportGroupUser(int groupId){
		// sql���
        String sql = "select * from groupg";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<Integer> list = new ArrayList<Integer>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("groupId") == groupId) {
                list.add(rs.getInt("userId"));
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static void addFile(FileMessage file) {
		String sql = "insert into fileliu (fileb,groupid,filena,sendman) values(?,?,?,?)";
		Connection conn = connection();
        // ����һ��Statment����
        PreparedStatement ps = null;
        try {
        	ps = conn.prepareStatement(sql);
			ps.setBytes(1, file.getB());
			ps.setInt(2, file.getFriendId());
			ps.setString(3, file.getName());
			ps.setInt(4, file.getMyId());
		    ps.executeUpdate();
		    //System.out.println("��Ӻ��ѳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static List<FileMessage> exportfile(int groupId){
		// sql���
        String sql = "select * from fileliu";
         // ��ȡ������
        Connection conn = connection();
        PreparedStatement pst = null;
        // ����һ��list���ڽ������ݿ��ѯ��������
        List<FileMessage> list = new ArrayList<FileMessage>();
        try {
            pst = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if(rs.getInt("groupId") == groupId) {
                	FileMessage file = new FileMessage();
                	file.setB(blobToBytes(rs.getBlob("fileb")));
                	file.setMyId(rs.getInt("sendman"));
                	file.setName(rs.getString("filena"));
                list.add(file);
                }
            }
        } catch (Exception e) {
        }finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return list;
	}
	public static byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len
					&& (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
				return null;
			}
		}
	}


	
    
}

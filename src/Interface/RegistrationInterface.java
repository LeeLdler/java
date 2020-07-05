package Interface;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.eltima.components.ui.DatePicker;

import Interface.CustomComboBoxDemo.ComboBoxRenderer;
import client.Client;
import dao.Jdbc;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JButton;
public class RegistrationInterface {

	ImageIcon[] images;
    String[] petStrings = {"1", "2", "3","4","5"};
	public JFrame frmQq;
	private JTextField nametext;
	private JPasswordField passwordField;
	private JPasswordField passwordField_2;
	private JTextField textField_1;
	private JTextField textField_2;
	private Client client;
	Mailbox sendmail = new Mailbox();
	int flag=0;

	

	/**
	 * Create the application.
	 */
	public RegistrationInterface(Client client) {
		initialize();
		this.client = client;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "rawtypes", "rawtypes" })
	private void initialize() {
		frmQq = new JFrame();
		frmQq.setTitle("宝可梦聊天室\u6CE8\u518C");
		frmQq.setBounds(100, 100, 358, 651);
		frmQq.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQq.setResizable(false);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        frmQq.setIconImage(imageIcon.getImage());
		frmQq.getContentPane().setLayout(null);
		nametext = new JTextField();
		nametext.setBounds(97, 136, 182, 24);
		frmQq.getContentPane().add(nametext);
		nametext.setColumns(10);
		
		JLabel label = new JLabel("\u6635\u79F0");
		label.setBounds(52, 139, 31, 18);
		label.setFont(new Font("宋体",0,15));
		frmQq.getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("\u5BC6\u7801");
		lblNewLabel.setBounds(52, 186, 30, 18);
		lblNewLabel.setFont(new Font("宋体",0,15));
		frmQq.getContentPane().add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_1.setBounds(23, 229, 60, 18);
		label_1.setFont(new Font("宋体",0,15));
		frmQq.getContentPane().add(label_1);
		
		//性别设置
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setBounds(52, 280, 31, 18);
		label_2.setFont(new Font("宋体",0,15));
		frmQq.getContentPane().add(label_2);
        ButtonGroup buttonGroup1 = new ButtonGroup();
        JRadioButton RadioButton1 = new JRadioButton("男",true);
        RadioButton1.setBounds(97, 276, 43, 27);
		RadioButton1.setText("\u7537");
		frmQq.getContentPane().add(RadioButton1);
		buttonGroup1.add(RadioButton1);
		RadioButton1.setFont(new Font("宋体",0,14));
		JRadioButton RadioButton2 = new JRadioButton();
		RadioButton2.setBounds(165, 276, 51, 27);
		RadioButton2.setText("\u5973");
		frmQq.getContentPane().add(RadioButton2);
		buttonGroup1.add(RadioButton2);
		RadioButton2.setFont(new Font("宋体",0,14));
		
		RadioButton1.setFocusPainted(false);
		RadioButton1.setContentAreaFilled(false);
		RadioButton2.setFocusPainted(false);
		RadioButton2.setContentAreaFilled(false);
		
		//生日设置
		final DatePicker datepick;
        datepick = getDatePicker();
        frmQq.getContentPane().add(datepick);
        
        JLabel label_4 = new JLabel("\u751F\u65E5");
        label_4.setBounds(52, 325, 31, 18);
        frmQq.getContentPane().add(label_4);
        label_4.setFont(new Font("宋体",0,15));
        
        //密码和确认密码
        passwordField = new JPasswordField();
        passwordField.setBounds(97, 183, 182, 24);
        frmQq.getContentPane().add(passwordField);
        
        passwordField_2 = new JPasswordField();
        passwordField_2.setBounds(97, 226, 182, 24);
        frmQq.getContentPane().add(passwordField_2);
        
        textField_1 = new JTextField();
        textField_1.setBounds(97, 371, 182, 24);
        frmQq.getContentPane().add(textField_1);
        textField_1.setColumns(10);
        
        JLabel label_5 = new JLabel("\u90AE\u7BB1");
        label_5.setFont(new Font("宋体",0,15));
        label_5.setBounds(52, 374, 31, 18);
        frmQq.getContentPane().add(label_5);
        
        textField_2 = new JTextField();
        textField_2.setBounds(97, 413, 86, 24);
        frmQq.getContentPane().add(textField_2);
        textField_2.setColumns(10);
        
        JLabel lblNewLabel_1 = new JLabel("\u9A8C\u8BC1\u7801");
        lblNewLabel_1.setBounds(37, 416, 46, 18);
        lblNewLabel_1.setFont(new Font("宋体",0,15));
        frmQq.getContentPane().add(lblNewLabel_1);
        
        //头像
        images = new ImageIcon[petStrings.length];
        Integer[] intArray = new Integer[petStrings.length];
        for (int i = 0; i < petStrings.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = createImageIcon( petStrings[i] + ".jpg");
            if (images[i] != null) {
                images[i].setDescription(petStrings[i]);
            }
        }

        //Create the combo box.
        JComboBox petList = new JComboBox(intArray);
        @SuppressWarnings({ })
		ComboBoxRenderer renderer= new ComboBoxRenderer();
        renderer.setPreferredSize(new Dimension(100, 100));
        for(int i=0;i<petStrings.length;i++)
        	images[i].setImage(images[i].getImage().getScaledInstance(129,110,Image.SCALE_DEFAULT ));
        petList.setRenderer(renderer);
        petList.setMaximumRowCount(3);
        petList.setBounds(112, 13, 129, 110);
        frmQq.getContentPane().add(petList);
        
        //获取验证码
        JButton button = new JButton("\u83B7\u53D6\u9A8C\u8BC1\u7801");
        button.addMouseListener(new MouseAdapter() {
        	@SuppressWarnings("unused")
			@Override
        	public void mouseClicked(MouseEvent e) {
        		String password1 = String.valueOf(passwordField.getPassword());
        		String password2 = String.valueOf(passwordField_2.getPassword());
        		String name = nametext.getText();
        		Boolean sexidel =   RadioButton1.isSelected();
        		String sex;
        		if(sexidel == true)
        		  sex = RadioButton1.getActionCommand();
        		else
        		  sex = RadioButton2.getActionCommand();
        	    String bir = datepick.getText();
        	    String email = textField_1.getText();
        	    String reg = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
        		if(nametext.getText().equals(""))
        		{
        			String tip1 = "   昵称不能为空！";
        			String tip2 = "获取失败";
        			tip t = new tip(tip1,tip2,client);
        		}
        		else if(password1.length()<6)
        		{
        			String tip1 = "  密码至少六位！";
        			String tip2 = "获取失败";
        			tip t = new tip(tip1,tip2,client);
        		}
        		else if(!password1.equals(password2))
        		{
        			System.out.println(password1 + '\n' + password2);
        			String tip1 = "两次输入密码不一致！";
        			String tip2 = "获取失败";
        			tip t = new tip(tip1,tip2,client);
        		}
        		else if(email.matches(reg) == false)
        		{
        			//System.out.println(email);
        			String tip1 = "  邮箱输入不合法！";
        			String tip2 = "获取失败";
        			tip t = new tip(tip1,tip2,client);
        		}
        		else
        		{
        			sendmail = new Mailbox(email);
        			flag=1;
        		}
        	}
        });
        button.setBounds(197, 412, 117, 27);
        button.setFont(new Font("宋体",0,15));
        frmQq.getContentPane().add(button);
        
        JButton button_1 = new JButton("\u6CE8    \u518C");
        button_1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		String received = textField_2.getText();
        		String send = sendmail.authCode;
        		System.out.println(received);
        		System.out.println(send);
        		if(!send.equals(null)) {
        		if(!received.equals(send))
        		{
        			String tip1 = "  验证码输入错误！";
        			String tip2 = "注册失败";
        			tip t = new tip(tip1,tip2,client);
        		}
        		else
        		{
        			String password1 = String.valueOf(passwordField.getPassword());
            		String password2 = String.valueOf(passwordField_2.getPassword());
            		String name = nametext.getText();
            		Boolean sexidel =   RadioButton1.isSelected();
            		String sex;
            		if(sexidel == true)
            		  sex = RadioButton1.getActionCommand();
            		else
            		  sex = RadioButton2.getActionCommand();
            	    String bir = datepick.getText();
            	    String email = textField_1.getText();
            	    int headIndex =petList.getSelectedIndex();
            	    String head = petStrings[headIndex]+".jpg";
            	    String msg = "2"+"name="+name+"&pwd="+password1+"&bir="+bir+"&sex="+sex+"&email="+email+"&head="+head;
        			client.getMessage(msg);
        		}
        		}
        	}
        });
        button_1.setBounds(97, 470, 113, 27);
        button_1.setFont(new Font("宋体",0,15));
        frmQq.getContentPane().add(button_1);
               
        
		
		//设置背景板
		JPanel panel = new JPanel();
		ImageIcon img = new ImageIcon("img/zcp.jpg");
		img.setImage(img.getImage().getScaledInstance(352,648,Image.SCALE_DEFAULT ));
		JLabel imagLabel = new JLabel(img);
		panel.add(imagLabel);
		imagLabel.setBounds(176,5,-1,-1);
		panel.setBounds(0, -13, 352, 629);
		frmQq.getContentPane().add(panel);
	}
	 private static DatePicker getDatePicker() {
	        final DatePicker datepick;
	        // 格式
	        String DefaultFormat = "yyyy-MM-dd";
	        // 当前时间
	        Date date = new Date();
	        // 字体
	        Font font = new Font("宋体", Font.BOLD, 14);

	        Dimension dimension = new Dimension(177, 24);

	        int[] hilightDays = { 1, 3, 5, 7 };

	        int[] disabledDays = { 4, 6, 5, 9 };
	        //构造方法（初始时间，时间显示格式，字体，控件大小）
	        datepick = new DatePicker(date, DefaultFormat, font, dimension);

	        // datepick.setLocation(500, 100);//设置起始位置
	        
	        //也可用setBounds()直接设置大小与位置
	        datepick.setBounds(97, 322, 182, 24);
	        
	        // 设置一个月份中需要高亮显示的日子
	        datepick.setHightlightdays(hilightDays, Color.red);
	        // 设置一个月份中不需要的日子，呈灰色显示
	        datepick.setDisableddays(disabledDays);
	        // 设置国家
	        datepick.setLocale(Locale.CANADA);
	        // 设置时钟面板可见
	        datepick.setTimePanleVisible(true);
	        return datepick;
	        //监听器获取时间为datepick.getValue();
	    }
	 
	 protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = CustomComboBoxDemo.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	                return null;
	        }
	    }
	 class ComboBoxRenderer extends JLabel
     implements ListCellRenderer {
		 private Font uhOhFont;

		 	public ComboBoxRenderer() {
		 		setOpaque(true);
		 		setHorizontalAlignment(CENTER);
		 		setVerticalAlignment(CENTER);
		 	}

		 	/*
		 	 * This method finds the image and text corresponding
		 	 * to the selected value and returns the label, set up
		 	 * to display the text and image.
		 	 */
		 	public Component getListCellRendererComponent(
                     JList list,
                     Object value,
                     int index,
                     boolean isSelected,
                     boolean cellHasFocus) {
		 		//Get the selected index. (The index param isn't
		 		//always valid, so just use the value.)
		 		int selectedIndex = ((Integer)value).intValue();

		 		if (isSelected) {
		 			setBackground(list.getSelectionBackground());
		 			setForeground(list.getSelectionForeground());
		 		} else {
		 			setBackground(list.getBackground());
		 			setForeground(list.getForeground());
		 		}

		 		//Set the icon and text.  If icon was null, say so.
		 		ImageIcon icon = images[selectedIndex];
		 		String pet = petStrings[selectedIndex];
		 		setIcon(icon);
		 		if (icon != null) {
		 			setText(pet);
		 			setFont(list.getFont());
		 		} else {
		 			setUhOhText(pet + " (no image available)",
      list.getFont());
		 		}

		 		return this;
		 	}

		 	//Set the font and text when no image was found.
		 	protected void setUhOhText(String uhOhText, Font normalFont) {
		 		if (uhOhFont == null) { //lazily create this font
		 			uhOhFont = normalFont.deriveFont(Font.ITALIC);
		 		}
		 		setFont(uhOhFont);
		 		setText(uhOhText);
		 	}
	 }
}
  
   

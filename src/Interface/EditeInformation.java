package Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.eltima.components.ui.DatePicker;

import Interface.RegistrationInterface.ComboBoxRenderer;
import bean.User;
import bean.UserCustom;
import client.Client;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditeInformation {

	public JFrame frame;
	ImageIcon[] images;
    String[] petStrings = {"1", "2", "3","4","5"};
    private JTextField textField;
    private Client client;
    private User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	EditeInformation window = new EditeInformation();
				//	window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public EditeInformation(Client client,User user) {
		this.client = client;
		this.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("编辑信息");
		frame.setBounds(100, 100, 358, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setResizable(false);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(  
                "1.png"));  
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1000,1000,Image.SCALE_DEFAULT ));
        // 设置标题栏的图标 
        frame.setIconImage(imageIcon.getImage());
		frame.getContentPane().setLayout(null);
		JTextField nametext = new JTextField(user.getNickName());
		nametext.setBounds(97, 243, 182, 24);
		frame.getContentPane().add(nametext);
		nametext.setColumns(10);
		
		JLabel label = new JLabel("\u6635\u79F0");
		label.setBounds(52, 246, 31, 18);
		label.setFont(new Font("宋体",0,15));
		frame.getContentPane().add(label);
		
		//性别设置
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setBounds(52, 280, 31, 18);
		label_2.setFont(new Font("宋体",0,15));
		frame.getContentPane().add(label_2);
        ButtonGroup buttonGroup1 = new ButtonGroup();
        JRadioButton RadioButton1 = new JRadioButton("男",true);
        RadioButton1.setBounds(97, 276, 43, 27);
		RadioButton1.setText("\u7537");
		frame.getContentPane().add(RadioButton1);
		buttonGroup1.add(RadioButton1);
		RadioButton1.setFont(new Font("宋体",0,14));
		JRadioButton RadioButton2 = new JRadioButton();
		RadioButton2.setBounds(159, 276, 51, 27);
		RadioButton2.setText("\u5973");
		frame.getContentPane().add(RadioButton2);
		buttonGroup1.add(RadioButton2);
		RadioButton2.setFont(new Font("宋体",0,14));
		
		RadioButton1.setFocusPainted(false);
		RadioButton1.setContentAreaFilled(false);
		RadioButton2.setFocusPainted(false);
		RadioButton2.setContentAreaFilled(false);
		
		//生日设置
		final DatePicker datepick;
        datepick = getDatePicker();
        frame.getContentPane().add(datepick);
        
        JLabel label_4 = new JLabel("\u751F\u65E5");
        label_4.setBounds(52, 325, 31, 18);
        frame.getContentPane().add(label_4);
        label_4.setFont(new Font("宋体",0,15));
        
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
        petList.setBounds(97, 101, 129, 110);
        frame.getContentPane().add(petList);
        
        
        JButton button_1 = new JButton("\u5B8C  \u6210");
        button_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Boolean sexidel =  RadioButton1.isSelected();
        		String sex;
        		if(sexidel == true)
        		  sex = RadioButton1.getActionCommand();
        		else
        		  sex = RadioButton2.getActionCommand();
        		String bir = datepick.getText();
        		String name = nametext.getText();
        		String signature = textField.getText();
        		int headIndex =petList.getSelectedIndex();
        	    String head = petStrings[headIndex]+".jpg";
        		if(name.equals("")) {
        		JOptionPane.showMessageDialog(frame, "昵称不能为空");
        		}
        		else
        		{
        			UserCustom msg = new UserCustom();
        			msg=client.getUser();
        			msg.setHead(head);
        			msg.setBir(bir);
        			msg.setNickName(name);
        			msg.setSignature(signature);
        			msg.setType(2);
        			client.getMessage(msg);
        		}
        	}
        });
        
        button_1.setBounds(97, 435, 113, 27);
        button_1.setFont(new Font("宋体",0,15));
        frame.getContentPane().add(button_1);
        
        JLabel label_1 = new JLabel("\u4E2A\u6027\u7B7E\u540D");
        label_1.setFont(new Font("宋体",0,15));
        label_1.setBounds(23, 371, 60, 18);
        frame.getContentPane().add(label_1);
        
        textField = new JTextField(user.getSignature());
        textField.setBounds(97, 368, 182, 24);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
               
        
		
		//设置背景板
		JPanel panel = new JPanel();
		ImageIcon img = new ImageIcon("img/zcp.jpg");
		img.setImage(img.getImage().getScaledInstance(352,648,Image.SCALE_DEFAULT ));
		JLabel imagLabel = new JLabel(img);
		panel.add(imagLabel);
		imagLabel.setBounds(176,5,-1,-1);
		panel.setBounds(0, -13, 352, 629);
		frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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


package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Style extends JDialog implements ActionListener, ListSelectionListener {
	String fontstyle, fontsize, colorsize;
	JTextArea textArea;
	String fontName = "宋体";
	int fontStyle = Font.PLAIN;// 普通样式常量
	int fontSize = 16;
	//将文本字体先设置为 宋体 普通样式常量 字号为16
	Font font = new Font(fontName, fontStyle, fontSize);// 字体，风格，字号
													// 获取本地图形环境
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	// 返回一个String数组 向用户展示特定字体系列名称的选择
	String name[] = ge.getAvailableFontFamilyNames();
	JList nameList = new JList(name);
	String style[] = { "常规", "粗体", "倾斜", "粗偏斜体" };
	JList styleList = new JList(style );
	
	String size[] = { "12", "14", "16", "18", "20", "22","24","26","28","36","48","72" };
	JList sizeList = new JList(size);
	
	String color[]= {"黑色","白色","黄色","蓝色","红色","绿色"};
	JList colorList = new JList(color);

	JButton confirm = new JButton("确定");
	JButton cancel = new JButton("取消");

	JPanel mainPanel = new JPanel();
	JPanel Panel1 = new JPanel();
	JPanel Panel2 = new JPanel();
	JPanel Panel3 = new JPanel();
	JPanel Panel4 = new JPanel();
	//设置标签
	JLabel Label1 = new JLabel("字体(F):");
	JLabel Label2 = new JLabel("字形(Y):");
	JLabel Label3 = new JLabel("大小(S):");
	JLabel Label4 = new JLabel("颜色(C):");

	JTextField Field1 = new JTextField("宋体", 10);
	JTextField Field2 = new JTextField("常规", 10);
	JTextField Field3 = new JTextField("12", 10);
	JTextField Field4 = new JTextField("黑色", 10);

	Style() {
		Item();
		setBounds(560, 300, 690, 400);
		//用户无法改变窗口大小
		setResizable(false);
	}

	public void setTa(JTextArea textArea1) {
		textArea = textArea1;
	}

	void Item() {
		//清空布局管理器
		mainPanel.setLayout(null);
		//设置边界布局
		Panel1.setLayout(new BorderLayout());
		Panel2.setLayout(new BorderLayout());
		Panel3.setLayout(new BorderLayout());
		Panel4.setLayout(new BorderLayout());

		Label1.setBounds(50, 30, 80, 30);
		Label2.setBounds(240, 30, 80, 30);
		Label3.setBounds(400, 30, 80, 30);
		Label4.setBounds(560, 30, 80, 30);
		Field1.setBounds(50, 60, 110, 25);
		Field2.setBounds(240, 60, 80, 25);
		Field3.setBounds(400, 60, 80, 25);
		Field4.setBounds(560, 60, 80, 25);
		Panel1.setBounds(50, 90, 110, 200);
		Panel2.setBounds(240, 90, 80, 200);
		Panel3.setBounds(400, 90, 80, 200);
		Panel4.setBounds(560, 90, 80, 200);
		confirm.setBounds(460, 300, 70, 40);
		cancel.setBounds(560, 300, 70, 40);

		mainPanel.add(Label1);
		mainPanel.add(Label2);
		mainPanel.add(Label3);
		mainPanel.add(Label4);
		mainPanel.add(Field1);
		mainPanel.add(Field2);
		mainPanel.add(Field3);
		mainPanel.add(Field4);

		Panel1.add(new JScrollPane(nameList));
		Panel2.add(new JScrollPane(styleList));
		Panel3.add(new JScrollPane(sizeList));
		Panel4.add(new JScrollPane(colorList));
		//将三个面板居中
		mainPanel.add(Panel1, BorderLayout.CENTER);
		mainPanel.add(Panel2, BorderLayout.CENTER);
		mainPanel.add(Panel3, BorderLayout.CENTER);
		mainPanel.add(Panel4, BorderLayout.CENTER);
		mainPanel.add(confirm);
		mainPanel.add(cancel);

		add(mainPanel);

		nameList.addListSelectionListener(this);
		styleList.addListSelectionListener(this);
		sizeList.addListSelectionListener(this);
		colorList.addListSelectionListener(this);
		confirm.addActionListener(this);
		cancel.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			font = new Font(fontName, fontStyle, fontSize);
			textArea.setFont(font);
		}

		if (e.getSource() == cancel) {
			//释放所有本机屏幕资源
			this.dispose();
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == nameList) {
			//得到链表中所选项目的索引，没有一个项目被选中就返回-1
			int m = nameList.getSelectedIndex();
			fontName = name[m];
			Field1.setText(fontName);
		}

		if (e.getSource() == styleList) {
			int m = styleList.getSelectedIndex();
			fontstyle = style[m];
			if (fontstyle.equals("粗体"))
				fontStyle = Font.BOLD;
			if (fontstyle.equals("倾斜"))
				fontStyle = Font.ITALIC;
			if (fontstyle.equals("常规"))
				fontStyle = Font.PLAIN;
			if (fontstyle.equals("粗偏斜体"))
				fontStyle = Font.BOLD + Font.ITALIC;
			Field2.setText(fontstyle);
		}

		if (e.getSource() == sizeList) {
			int m = sizeList.getSelectedIndex();
			fontsize = size[m];
			//返回用十进制参数表示的整数值
			fontSize = Integer.parseInt(fontsize);
			Field3.setText(fontsize);
		}
		if (e.getSource() == colorList) {
			int m = colorList.getSelectedIndex();
			colorsize = color[m];
			if (colorsize.equals("黑色")) 
				textArea.setForeground(Color.black);
			if (colorsize.equals("白色"))
				textArea.setForeground(Color.white);
			if (colorsize.equals("黄色"))
				textArea.setForeground(Color.yellow);
			if (colorsize.equals("蓝色"))
				textArea.setForeground(Color.blue);
			if (colorsize.equals("红色"))
				textArea.setForeground(Color.red);
			if (colorsize.equals("绿色"))
				textArea.setForeground(Color.green);
		}
		Field4.setText(colorsize);

	}

}
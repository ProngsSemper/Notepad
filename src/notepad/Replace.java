package notepad;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Replace extends JDialog implements ActionListener {

	JLabel findContent = new JLabel("查找内容(N):");
	JLabel replaceAs = new JLabel("替换为(p):");
	JButton replace = new JButton("替换");
	JButton find = new JButton("查找下一个");
	JButton replaceAll = new JButton("全部替换");
	JTextField field1 = new JTextField(5);
	JTextField field2 = new JTextField(5);
	JTextArea textArea;
	JPanel pane1 = new JPanel();
	JPanel pane2 = new JPanel();
	JPanel pane3 = new JPanel();
	JPanel pane4 = new JPanel();
	JPanel pane5 = new JPanel();
	JPanel pane6 = new JPanel();
	JPanel pane7 = new JPanel();
	JPanel pane8 = new JPanel();
	JPanel pane9 = new JPanel();
	JPanel pane10 = new JPanel();
	int a = 0, b = 0;
	String str1, str2, str3, str4, strA, strB;
	JCheckBox matchcase = new JCheckBox("区分大小写");
	JCheckBox up = new JCheckBox("向上");
	JCheckBox down = new JCheckBox("向下");

	Replace() {
		Item();
		setBounds(100, 100, 300, 200);
		setResizable(false);
		setVisible(false);
	}

	void Item() {
		field1.setEditable(true);
		// 创建具有指定行数和列数的网格布局。 这里为两行三列
		pane1.setLayout(new GridLayout(3, 3));
		pane2.setLayout(new FlowLayout());
		pane3.setLayout(new FlowLayout());
		pane4.setLayout(new FlowLayout());
		pane5.setLayout(new FlowLayout());
		pane6.setLayout(new FlowLayout());
		pane7.setLayout(new FlowLayout());
		pane8.setLayout(new FlowLayout());
		pane9.setLayout(new FlowLayout());
		pane10.setLayout(new FlowLayout());
		// 一个界面只可以有一个JFrame窗体组件，但是可以有多个JPanel面板组件 这里把标签文本域和按钮添加到各个面板组件
		pane2.add(findContent);
		pane3.add(field1);
		pane4.add(replace);
		pane5.add(up);
		pane5.add(down);
		pane6.add(matchcase);
		pane7.add(find);
		pane8.add(replaceAs);
		pane9.add(field2);
		pane10.add(replaceAll);
		// 将所有子面板组件添加到主面板组件
		pane1.add(pane2);
		pane1.add(pane3);
		pane1.add(pane4);
		pane1.add(pane5);
		pane1.add(pane6);
		pane1.add(pane7);
		pane1.add(pane8);
		pane1.add(pane9);
		pane1.add(pane10);
		add(pane1);
		replace.addActionListener(this);
		replaceAll.addActionListener(this);
		find.addActionListener(this);

	}

	void setTa(JTextArea textArea1) {
		textArea = textArea1;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == replace) {
			// 获取文本区内的内容
			String source = textArea.getText();
			// 获取需要被替换的内容和要用来替换的内容
			String find = field1.getText();
			String dest = field2.getText();
			// 返回指定字符在字符串中第一次出现处的索引（位置），如果此字符串中没有这样的字符，则返回 -1
			int m = source.indexOf(find);
			if (m == -1)
				JOptionPane.showMessageDialog(null, "没有找到内容 ", "错误 ", JOptionPane.ERROR_MESSAGE);
			// 获取将被替换的内容前面的内容
			String s1 = source.substring(0, m);
			// 获取将被替换的内容后面的内容
			String s2 = source.substring(m + find.length(), source.length());
			// 将替换了的内容和前后内容拼接起来
			source = s1 + dest + s2;
			textArea.setText(source);
		}

		if (e.getSource() == replaceAll) {
			String source = textArea.getText();
			String find = field1.getText();
			String dest = field2.getText();
			int m = source.indexOf(find);
			if (m == -1)
				JOptionPane.showMessageDialog(null, "没有找到内容 ", "错误 ", JOptionPane.ERROR_MESSAGE);
			/*
			 * m会随着要被替换的内容的位置变化而变化 当内容有被找到时m的值不为-1 例如当要被替换的内容在文本框的第一位时m的值为0 
			 * 直至所有要被替换的内容都被替换后
			 * m的值变成-1跳出循环
			 */
			while (m >= 0) {
				if (m == -1)
					break;
				else {
					m = source.indexOf(find);
					String s1 = source.substring(0, m);
					String s2 = source.substring(m + find.length(), source.length());
					source = s1 + dest + s2;
				}

				textArea.setText(source);

			}

		}
		if (e.getSource() == find) {
			str1 = textArea.getText();
			str2 = str1.toLowerCase();
			str3 = field1.getText();
			str4 = str3.toLowerCase();
			//光标的位置
			int findPoint = textArea.getCaretPosition();
			if (matchcase.isSelected()) {
				strA = str1;
				strB = str3;
			} else {
				// 全员小写
				strA = str2;
				strB = str4;
			}

			if (up.isSelected()) {
				//getSelectedText()获取光标选中内容
				//textArea.getSelectedText() == null鼠标光标没有选中内容时
				if (textArea.getSelectedText() == null) {
					//lastIndexOf返回指定字符在此字符串中最后一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
					/*
					  * 因为没有用光标选中所以findPoint表示末尾位置(\r) 所以需要-1来达到内容末尾
					  * 从内容末尾开始搜索strB（在查找内容框中所输入的字符串）
					  * 最后把这个字符串的位置赋值给a
					 * */
					a = strA.lastIndexOf(strB, findPoint - 1);
				} else {
					a = strA.lastIndexOf(strB, findPoint - field1.getText().length() - 1);
				}
			} else if (down.isSelected()) {
				if (textArea.getSelectedText() == null) {
					a = strA.indexOf(strB, findPoint);
				} else {
					a = strA.indexOf(strB, findPoint - field1.getText().length() + 1);
				}

			}
			if (a > -1) {
				if (up.isSelected()) {
					//将光标移动到找到的字符串的位置
					textArea.setCaretPosition(a);
					b = field1.getText().length();
					textArea.select(a, a + b);
				} else if (down.isSelected()) {
					textArea.setCaretPosition(a);
					b = field1.getText().length();
					textArea.select(a, a + b);
				}
			} else {
				JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

}

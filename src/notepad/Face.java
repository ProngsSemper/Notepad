package notepad;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Face extends JFrame implements ActionListener, DocumentListener, WindowListener {
	// 设置菜单栏
	JMenuBar menuBar = new JMenuBar();
	//设置右键菜单
	JPopupMenu popupMenu = new JPopupMenu();
	// 设置菜单
	JMenu file = new JMenu("文件");
	JMenu edit = new JMenu("编辑");
	JMenu format = new JMenu("格式");
	JMenu backgroundColor = new JMenu("背景颜色");
	// 设置菜单选项
	JMenuItem New = new JMenuItem("新建(N)");
	JMenuItem open = new JMenuItem("打开(O)");
	JMenuItem save = new JMenuItem("保存(S)");
	JMenuItem saveAs = new JMenuItem("另存为");
	JMenuItem exit = new JMenuItem("退出(X)");
	JMenuItem copy = new JMenuItem("复制(C)");
	JMenuItem paste = new JMenuItem("黏贴(P)");
	JMenuItem delete = new JMenuItem("删除(L)");
	JMenuItem cutting = new JMenuItem("剪切(T)");
	JMenuItem searchReplace = new JMenuItem("搜索与替换(F)");
	JMenuItem time = new JMenuItem("时间/日期");
	JMenuItem cancel = new JMenuItem("撤销");
	JMenuItem redo = new JMenuItem("恢复");
	JMenuItem fontsetting = new JMenuItem("字体设置");
	JMenuItem backgroundBlack = new JMenuItem("黑色");
	JMenuItem backgroundWhite = new JMenuItem("白色");
	JMenuItem backgroundEyes = new JMenuItem("护眼色");
	//设置右键菜单子菜单
	JMenuItem popupcopy = new JMenuItem("复制(C)");
	JMenuItem popuppaste = new JMenuItem("黏贴(P)");
	JMenuItem popupcutting = new JMenuItem("剪切(T)");
	JMenuItem popupcancel = new JMenuItem("撤销");
	JMenuItem popupredo = new JMenuItem("恢复");
	// 设置护眼色
	Color Eye = new Color(199, 237, 204);
	// JMenuItem linebreak = new JMenuItem("自动换行");
	// 为了让用户知道是否选择了自动换行功能所以这里选用复选框
	JCheckBox linebreak = new JCheckBox("自动换行");
	Style style = new Style();
	// 设置系统剪切板
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();
	Replace rp = new Replace();
	// 设置一个显示纯文本的多行区域
	JTextArea area = new JTextArea();
	// 设置文件选择器
	JFileChooser fileDialog = new JFileChooser();
	// 设置字符缓冲输入流
	BufferedReader in;
	// 设置读取文件
	FileReader fileReader;
	// 设置字符缓冲输出流
	BufferedWriter out;
	// 设置写入数据
	FileWriter fileWriter;
	int flag = 1;
	int flag1 = 0;
	File dir;
	String name;
	// 创建撤消操作管理器
	public UndoManager undo = new UndoManager();

	public Face() {
		setTitle("记事本");
		// 前两个数字是左上角在容器中的位置 后两个数字是组件宽度和高度
		setBounds(300, 200, 550, 300);
		setJMenuBar(menuBar);
		Item();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	
	void Item() {
		// 设置快捷键
		New.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		open.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		save.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		exit.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
		copy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
		paste.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		delete.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
		cutting.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
		searchReplace.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
		cancel.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		// 添加菜单及子菜单
		style.setTa(area);
		rp.setTa(area);
		menuBar.add(file);
		file.add(New);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.add(exit);
		menuBar.add(edit);
		edit.add(copy);
		edit.add(paste);
		edit.add(delete);
		edit.add(cutting);
		edit.add(searchReplace);
		edit.add(time);
		edit.add(cancel);
		edit.add(redo);
		menuBar.add(format);
		format.add(fontsetting);
		format.add(backgroundColor);
		backgroundColor.add(backgroundBlack);
		backgroundColor.add(backgroundWhite);
		backgroundColor.add(backgroundEyes);
		format.add(linebreak);
		//添加右键菜单子菜单
		popupMenu.add(popupcopy);
		popupMenu.addSeparator();
		popupMenu.add(popuppaste);
		popupMenu.addSeparator();
		popupMenu.add(popupcutting);
		popupMenu.addSeparator();
		popupMenu.add(popupcancel);
		popupMenu.addSeparator();
		popupMenu.add(popupredo);
		(area.getDocument()).addDocumentListener(this);
		area.getDocument().addUndoableEditListener(undo);
		// 添加滚动条
		add(new JScrollPane(area));
		// 为各子菜单添加事件监听器
		New.addActionListener(this);
		open.addActionListener(this);
		save.addActionListener(this);
		saveAs.addActionListener(this);
		exit.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		delete.addActionListener(this);
		cutting.addActionListener(this);
		time.addActionListener(this);
		linebreak.addActionListener(this);
		searchReplace.addActionListener(this);
		cancel.addActionListener(this);
		redo.addActionListener(this);
		fontsetting.addActionListener(this);
		backgroundBlack.addActionListener(this);
		backgroundWhite.addActionListener(this);
		backgroundEyes.addActionListener(this);
		popupcopy.addActionListener(this);
		popuppaste.addActionListener(this);
		popupcutting.addActionListener(this);
		popupcancel.addActionListener(this);
		popupredo.addActionListener(this);
		this.addWindowListener(this);
		area.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					// 弹出菜单
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	// 设置保存方法
	public void save() {
		int state = fileDialog.showSaveDialog(this);
		if (state == JFileChooser.APPROVE_OPTION) {
			try {
				// 返回路径
				dir = fileDialog.getCurrentDirectory();
				// 返回文件名
				name = fileDialog.getSelectedFile().getName();
				File file = new File(dir, name);
				// 写入文件
				fileWriter = new FileWriter(file);
				out = new BufferedWriter(fileWriter);
				out.write(area.getText());
				// 释放资源
				out.close();
				fileWriter.close();
			} catch (IOException exception) {
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == New) {
			// 点击新建按钮是要是文本框内没有输入内容则不作出反应
			if ("".equals(area.getText())) {
			}
			// 若在文本框内已经输入内容时点击新建按钮弹出确认保存对话框
			if (!("".equals(area.getText()))) {
				int n = JOptionPane.showConfirmDialog(this, "文件尚未保存，确认是否保存", "确认保存对话框", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					save();
				else if (n == JOptionPane.NO_OPTION)
					area.setText(null);
			}
		}
		if (e.getSource() == open) {
			// 若文本框内有文本时点击打开按钮弹出是否保存对话框若选择否则清空文本框内内容并弹出文件选择器选择要读取的文件
			if (!(area.getText().equals(""))) {
				int n = JOptionPane.showConfirmDialog(this, "文件尚未保存，确认是否保存", "确认对话框", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION)
					save();
				else if (n == JOptionPane.NO_OPTION)
					area.setText(null);
			}
			// 设置文件选择器弹出参数 null为当前电脑显示器中央 this为当前编写程序的中央
			int state = fileDialog.showOpenDialog(this);
			if (state == JFileChooser.APPROVE_OPTION) {
				try {
					// 返回选择的目录
					File dir = fileDialog.getCurrentDirectory();
					// 返回选择的文件名
					String name = fileDialog.getSelectedFile().getName();
					File file = new File(dir, name);
					// 读取文件
					fileReader = new FileReader(file);
					in = new BufferedReader(fileReader);
					String s = null;
					while ((s = in.readLine()) != null) {
						area.append(s + "\n");
					}
					// 释放资源
					in.close();
					fileReader.close();
				} catch (IOException exception) {
				}
			}
		}
		if (e.getSource() == save) {
			if (flag == 1 && flag1 == 0) {
				save();
				// 当文件保存后把flag的值修改为0这样点击窗口关闭按钮时就不会调用save()方法
				flag = 0;
				// 当文件的内容发生了变化时flag的值会被重新修改为1（见insertUpdate等被覆写的方法）使得可以重新写入
			} else if (flag == 1 && flag1 == 1) {
				try {
					File file = new File(dir, name);
					fileWriter = new FileWriter(file);
					out = new BufferedWriter(fileWriter);
					out.write(area.getText());
				} catch (IOException exception) {
				}
			}
			flag1 = 1;
		}
		if (e.getSource() == saveAs) {
			save();
		}
		if (e.getSource() == exit) {
			// 若文件已保存则直接退出
			if (flag == 0)
				System.exit(0);
			// 若文件未保存或被更改则弹出是否保存对话框并根据用户选择判断是否调用save()方法
			if (flag == 1) {
				int n = JOptionPane.showConfirmDialog(this, "文件尚未保存，确认是否保存", "确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
				if (n == JOptionPane.YES_OPTION)
					save();
				else if (n == JOptionPane.NO_OPTION)
					System.exit(0);
				else if (n == JOptionPane.CANCEL_OPTION)
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		if (e.getSource() == copy || e.getSource() == popupcopy) {
			area.copy();
		}
		if (e.getSource() == paste || e.getSource() == popuppaste) {
			// 获取用户焦点
			area.requestFocus();
			// 返回剪切板中的内容
			Transferable contents = clipBoard.getContents(this);
			if (contents == null)
				return;
			String text = "";
			try {
				// 将剪切板中的内容赋值给text
				// 返回表示要传输的数据的对象
				// ↓
				text = (String) contents.getTransferData(DataFlavor.stringFlavor);
				// ↑
				// 在触发“粘贴”操作时，实例化一个DataFlavor用来读取剪贴板上的内容
			} catch (Exception exception) {
			}
			// 将选中区域中的内容修改为text中的内容
			area.replaceRange(text, area.getSelectionStart(), area.getSelectionEnd());
		}
		if (e.getSource() == delete) {
			// 获取用户焦点
			area.requestFocus();
			// 从选中开始到选中结束将其中文本替换为空文本达到删除效果
			area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
		}
		if (e.getSource() == cutting || e.getSource() == popupcutting) {
			area.requestFocus();
			// 获取选中文本
			String text = area.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			// 将选中且被剪切的地方替换为空文本
			area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
		}
		if (e.getSource() == time) {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			area.append(simpleDateFormat.format(date));
		}
		if (e.getSource() == linebreak) {
			if (linebreak.isSelected())
				area.setLineWrap(true);
			else
				area.setLineWrap(false);

		}
		if (e.getSource() == searchReplace) {
			rp.setVisible(true);
		}
		if (e.getSource() == cancel ||e.getSource() == popupcancel) {
			area.requestFocus();
			if (undo.canUndo()) {
				try {
					undo.undo();
				} catch (CannotUndoException exception) {
					System.out.println("Unable to undo: " + exception);
					exception.printStackTrace();
				}
			}
			if (!undo.canUndo()) {
				cancel.setEnabled(false);
			}
		}
		if (e.getSource() == redo || e.getSource() == popupredo) {
			area.requestFocus();
			if (undo.canRedo()) {
				try {
					undo.redo();
				} catch (CannotRedoException exception) {
					System.out.println("Unable to redo: " + exception);
					exception.printStackTrace();
				}
			}
			if (!undo.canRedo()) {
				redo.setEnabled(false);
			}
		}
		if (e.getSource() == fontsetting) {
			style.setVisible(true);
		}
		if (e.getSource() == backgroundBlack) {
			area.setBackground(Color.black);
		}
		if (e.getSource() == backgroundWhite) {
			area.setBackground(Color.white);
		}
		if (e.getSource() == backgroundEyes) {
			area.setBackground(Eye);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// 当文本框有内容且未保存时弹出是否保存对话框
		if (flag == 1) {
			int n = JOptionPane.showConfirmDialog(this, "文件尚未保存，确认是否保存", "确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
			if (n == JOptionPane.YES_OPTION)
				save();
			else if (n == JOptionPane.NO_OPTION)
				System.exit(0);
			else if (n == JOptionPane.CANCEL_OPTION)
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		} else {
			System.exit(0);
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertUpdate(DocumentEvent e) { // 对文档执行了插入操作时将flag的值修改为1以便重新写入
		flag = 1;

	}

	@Override
	public void removeUpdate(DocumentEvent e) { // 移除了一部分文档时将flag的值修改为1以便重新写入
		flag = 1;

	}

	@Override
	public void changedUpdate(DocumentEvent e) { // 属性或属性集发生了更改时将flag的值修改为1以便重新写入
		flag = 1;

	}

}

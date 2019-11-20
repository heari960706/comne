package Frame;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLDocument;

import client.Sender;
import client.FileChooserUtil;
import client.FileSaveLoadUtil;
import client.HTMLMaker;
//import client.HTMLMaker;
//import client.UserList;
import model.Message;

/*
 * 실제로 채팅이 이뤄지는 패널
 */
public class ChatPanel extends JPanel {

	JScrollPane chatScrollPane;
	JList userList;
	JTextPane chatTextPane;
	JTextArea txtrMessage;
	HTMLDocument doc;
	DefaultListModel<String> userListModel = new DefaultListModel<String>();
	private StringBuffer messageList = new StringBuffer();
	private boolean isOpenList = false;
	private StringBuffer chatLog = new StringBuffer();
	private HTMLMaker htmlMaker = new HTMLMaker();
	
	public ChatPanel() {		
		setLayout(null);
		
		JPanel chatBoardPane = new JPanel();
		chatBoardPane.setBackground(Color.LIGHT_GRAY);
		chatBoardPane.setBounds(0, 0, 300, 440);
		add(chatBoardPane);
		chatBoardPane.setLayout(null);
		
		chatScrollPane = new JScrollPane();
		chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatScrollPane.setBounds(0, 45, 300, 395);
		chatBoardPane.add(chatScrollPane);
		
		chatTextPane = new JTextPane();
		chatTextPane.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		chatTextPane.setBackground(Color.LIGHT_GRAY);
		chatScrollPane.setViewportView(chatTextPane);
		chatTextPane.setText("");
				

		chatTextPane.setContentType("text/html");
		doc = (HTMLDocument) chatTextPane.getStyledDocument();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 450, 189, 70);
		add(scrollPane);
		
		txtrMessage = new JTextArea();
		txtrMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (isEnter(e)) {
					pressEnter(txtrMessage.getText().replaceAll("\n", ""));
				}
			}
		});
		txtrMessage.setLineWrap(true);
		txtrMessage.setWrapStyleWord(true);
		scrollPane.setViewportView(txtrMessage);
		
		JButton btnNewButton = new JButton("전송");
		btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btnNewButton.setBackground(Color.ORANGE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pressEnter(txtrMessage.getText());
			}
		});
		btnNewButton.setBounds(211, 450, 65, 35);
		add(btnNewButton);
		
		JLabel lblImage = new JLabel(new ImageIcon("images/image.png"));
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sendImage();
			}
		});
		lblImage.setBounds(211, 490, 30, 30);
		add(lblImage);
	
	}
	


	private boolean isDoubleClicked(MouseEvent e) {
		return e.getClickCount() == 2;
	}
	
	private boolean isEnter(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_ENTER;
	}

	private void pressEnter(String userMessage) {
		if (isNullString(userMessage)) {
			return;
		} else {
			sendMessage(userMessage);
		}
		txtrMessage.setText("");
		txtrMessage.setCaretPosition(0);
	}
	

	private void sendMessage(String userMessage) {
		Sender.getSender().sendMessage(userMessage);
	}

	private boolean isNullString(String userMessage) {
		return userMessage == null || userMessage.equals("");
	}

	
	private void sendImage() {
		String imagePath = FileChooserUtil.getFilePath();
		if (imagePath == null) {
			return;
		} else if (imagePath.endsWith("png") || imagePath.endsWith("jpg")) {
			Sender.getSender().sendImage(imagePath);
		} else {
			JOptionPane.showMessageDialog(null, ".png, .jpg 확장자 파일만 전송 가능합니다.");
		}
	}
	

	
	public void addMessage(String adminMessage) {
		messageList.append(htmlMaker.getHTML(adminMessage));
		rewriteChatPane();
		addChatLog(adminMessage);
	}
	
	public void addMessage(boolean isMine, Message message) {
		messageList.append(htmlMaker.getHTML(isMine, message));
		rewriteChatPane();
		addChatLog(message.getName(), message.getMessage());
	}
	
	private void rewriteChatPane() {
		chatTextPane.setText(messageList.toString());
		chatTextPane.setCaretPosition(doc.getLength());
	}
	
	private void addChatLog(String adminMessage) {
		chatLog.append(adminMessage + "\r\n");
	}
	
	private void addChatLog(String userName, String userMsg) {
		chatLog.append(userName + " : " + userMsg + "\r\n");
	}
}
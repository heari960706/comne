package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Frame.AppFrame;
import Frame.ChatPanel;
import client.FileSaveLoadUtil;
import client.UserList;
import model.FilePath;
import model.Message;

/*
 * ���ʿ� ������ ���� �� ���� �������� ���۵Ǵ� �޼����� �����մϴ�
 * login() - ���� ����
 * islogined() - ���� �ƴ��� Ȯ��
 * receive() - �������� ���۵Ǵ� �޼��� ����
 */
public class Listener implements Runnable {

	private static final String HOST = "169.254.252.116";
	//private static final String HOST = "127.0.0.1";

	private static final int PORT = 6464;

	Socket socket;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	AppFrame frame;
	ChatPanel chatPanel;
	String name;
	Message message;

	public Listener(AppFrame frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(HOST, PORT);
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			name = frame.getLoginPane().getTxtName();
			chatPanel = frame.getChatPane();
			login();
			if (isLogined()) {
				receive();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			networkDisconnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	private void login() throws IOException {
		message = new Message();
		message.setName(name);
		message.setMessage(""+frame.getLoginPane().getProfileNum());
		objectOutputStream.writeObject(message);
		objectOutputStream.reset();
	}
	
	private boolean isLogined() throws ClassNotFoundException, IOException {
		message = (Message) objectInputStream.readObject();
		switch (message.getType()) {
		case WELCOME:
			UserList.setList(message.getUserList());
		default:
			frame.changeToChat();
			new Sender(objectOutputStream,name);
			printMessage(message.getMessage());
			return true;
		}
	}

	private void receive() throws IOException, ClassNotFoundException {
		while (socket.isConnected()) {
			message = (Message) objectInputStream.readObject();
			switch (message.getType()) {

			case IMAGE:
				message.setMessage((saveImage()));
				printMessage();
				break;
			case WELCOME:
			case EXIT:
				UserList.setList(message.getUserList());
				printMessage(message.getMessage());
				break;
			default:
				printMessage();
				break;
			}
		}
	}
	
	private void duplicateName() {
		frame.changeToError("�̹� �����ϴ� �̸��Դϴ�. �ٸ� �̸��� ������ �ּ���.");
	}
	
	private void networkDisconnection() {
		frame.changeToError("��Ʈ��ũ�� ������ �߻��߽��ϴ�.");
	}
	
	private void printMessage(String adminMsg) {
		chatPanel.addMessage(adminMsg);
	}

	private void printMessage() {
		chatPanel.addMessage(isMine(),message);
	}
	
	private boolean isMine() {
		return name.equals(message.getName());
	}
	
	private String saveImage() {
		return FileSaveLoadUtil.fileSave(message.getImageExtention(), FilePath.DOWNLOADFILEPATH.toString(), message.getImage());
	}

	private void closeConnection() {
		if (objectInputStream != null) {
			try {
				objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (objectOutputStream != null) {
			try {
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
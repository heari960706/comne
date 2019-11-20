package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Message;
import model.TypeOfMessage;
import model.User;
//import server.DuplicateUsernameException;
import server.UserList;

/*
 * 클라이언트에서 넘어온 message를 
 * 현재 접속되어 있는 클라이언트 들에게 송신
 */
public class Handler implements Runnable {

	Socket socket;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	String name;
	Message message;
	User user;
	
	public Handler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			addUser();
			receiveMessage();
			removeUser();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	private void addUser() throws IOException, ClassNotFoundException {
		objectInputStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		Message userName = (Message) objectInputStream.readObject();
		user = new User(userName.getName(), Integer.parseInt(userName.getMessage()), objectOutputStream);
		if (UserList.addList(user)) {
			name = userName.getName();
			sendWelcomeMessage();
		} 
	}
	
	private void sendWelcomeMessage() throws IOException {		
		String adminMessage = name + " 님이 입장하셨습니다.";
		setAdminMessage(TypeOfMessage.WELCOME, adminMessage);
		sendMessage();
	}
	
	private void setAdminMessage(TypeOfMessage type, String adminMessage) {
		String adminName = "운영자";
		message = new Message();
		message.setName(adminName);
		message.setType(type);
		message.setMessage(adminMessage);
		message.setUserList(UserList.getList());
	}
	
	private void setSearchMessage(String userMessage) {
		message.setMessage(userMessage);
	}
	
	private void receiveMessage() throws ClassNotFoundException {
		try {
			while (socket.isConnected()) {
				message = (Message) objectInputStream.readObject();
				switch (message.getType()) {
				case IMAGE:
					sendMessage();
					break;


				default:
					sendMessage();					
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage() throws IOException {
		for (User user : UserList.getList()) {
			sendMessageToOne(user.getObjectOutputStream());
		}
	}
	
	private void sendMessageToOne(ObjectOutputStream objectOutputStreamToOne) throws IOException {
		objectOutputStreamToOne.writeObject(message);
		objectOutputStreamToOne.reset();
	}
	
	private void removeUser() throws IOException {
		if (name != null) {
			UserList.getList().remove(user);
			String adminMessage = name + " 님이 퇴장하셨습니다.";
			setAdminMessage(TypeOfMessage.EXIT, adminMessage);
			sendMessage();
		}		
	}
	

	private void closeConnection() {
		if (name != null) {
			UserList.getList().remove(user);
		}
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
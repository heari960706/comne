package client;
import java.util.ArrayList;

import model.User;

public class UserList {
	
	transient private static ArrayList<User> list = new ArrayList<User>();
	
	public synchronized static boolean addList(User newUser) {
			list.add(newUser);
			return true;

	}
	

	
	public static boolean contain(String targetName) {
		for (User user : list) {
			if (user.getName().equals(targetName)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<User> getList() {
		return list;
	}

	public static void setList(ArrayList<User> list) {
		UserList.list = list;
	}
	
	public static String[] getUsernameList() {
		String[] usernameList = new String[list.size()];
		for (int i = 0; i < usernameList.length; i++) {
			usernameList[i] = list.get(i).getName();
		}
		return usernameList;
	}
	
	public static User getUser(String userName) {
		for (User user : list) {
			if (user.getName().equals(userName)) {
				return user;
			}
		}
		return null;
	}
}

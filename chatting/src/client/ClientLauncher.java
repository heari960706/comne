package client;

import Frame.AppFrame;

/*
 * Ŭ���̾�Ʈ ����
 */
public class ClientLauncher {

	public static void main(String[] args) {
		try {
			AppFrame frame = new AppFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.Listener;

public class LoginPanel extends JPanel {
	
	private JTextField txtName;
	private int profileNum = 0;

	public LoginPanel(final AppFrame frame) {
		
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JLabel lblTitle = new JLabel("Ã¤ÆÃ ÇÁ·Î±×·¥");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 34));
		lblTitle.setBounds(12, 100, 276, 46);
		add(lblTitle);
		
		txtName = new JTextField();
		txtName.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		txtName.setHorizontalAlignment(SwingConstants.CENTER);
		txtName.setBounds(82, 366, 136, 27);
		add(txtName);
		txtName.setColumns(10);
		
		JButton btnLogin = new JButton("login");
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText() != null && !txtName.getText().equals("")) {
					new Thread(new Listener(frame)).start();
				}
			}
		});
		btnLogin.setBounds(82, 403, 136, 27);
		add(btnLogin);
		

	}
	


	public String getTxtName() {
		return txtName.getText();
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}
	
	public int getProfileNum() {
		return profileNum;
	}
}

package Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ErrorPanel extends JPanel {

	public ErrorPanel(final AppFrame frame, String errorMessage) {
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 18));
		textPane.setBackground(Color.ORANGE);
		textPane.setBounds(12, 161, 276, 94);
		add(textPane);
		textPane.setText(errorMessage);
		
		JButton btnNewButton = new JButton("µ¹¾Æ°¡±â");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.changeToLogin();
			}
		});
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(100, 296, 97, 23);
		add(btnNewButton);
	}
}
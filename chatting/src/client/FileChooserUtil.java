package client;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooserUtil {

	public static String getFilePath() {
		String folderPath = "";

		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		chooser.setCurrentDirectory(new File("/"));
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setDialogTitle("���� Ž����");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int returnVal = chooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			folderPath = chooser.getSelectedFile().toString();
		} else {
			folderPath = null;
		}

		return folderPath;
	}
}
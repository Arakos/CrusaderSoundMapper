package crusader.mapper.controler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import crusader.mapper.data.DAO;
import crusader.mapper.util.UI_Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DAOController implements Initializable {

	private static final Preferences daoPrefs = Preferences.userNodeForPackage(DAO.class);

	public static void ensureDAOInitializable(Stage mainStage) throws Exception {
		if (daoPrefs.get(DAO.CRUSADER_PATH_PREF_DIR, "").isEmpty()
				|| daoPrefs.get(DAO.WORKING_DIR_PREF_KEY, "").isEmpty()) {
			Region baseDirDialog = FXMLLoader.load(DAO.class.getResource("/fxml/BaseDirectoryDialog.fxml"));
			Stage subStage = new Stage();
			subStage.initModality(Modality.APPLICATION_MODAL);
			subStage.initOwner(mainStage);
			UI_Util.setOnStage(subStage, baseDirDialog);
		}
	}

	@FXML
	TextField baseDirValue;

	@FXML
	Button baseDirBtn;

	@FXML
	TextField workDirValue;

	@FXML
	Button workDirBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("init works?");
		baseDirValue.setText(daoPrefs.get(DAO.CRUSADER_PATH_PREF_DIR, "Not set"));
		workDirValue.setText(daoPrefs.get(DAO.WORKING_DIR_PREF_KEY, "Not set"));

		baseDirBtn.setOnAction(event -> {
			System.out.println("doing stuff: " + baseDirValue.getText());
			File file = checkDirSet(baseDirValue.getText());
			baseDirValue.setText(file.getAbsolutePath());
		});
		workDirBtn.setOnAction(event -> {
			File file = checkDirSet(workDirValue.getText());
			workDirValue.setText(file.getAbsolutePath());
		});
	}

	private File checkDirSet(String fileStr) {
		File dir = new File(fileStr);
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setInitialDirectory(dir.exists() && dir.isDirectory() ? dir : null);
		return dirChooser.showDialog(null);
	}

}

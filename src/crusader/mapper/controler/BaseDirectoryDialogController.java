package crusader.mapper.controler;

import static crusader.mapper.data.DAO.CRUSADER_PATH_PREF_KEY;
import static crusader.mapper.data.DAO.WORKING_DIR_PREF_KEY;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.DAO;
import crusader.mapper.util.UI_Util;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BaseDirectoryDialogController implements Initializable {

	@FXML
	private TextField baseDirValue, workDirValue;

	@FXML
	private Label invcrusaderdirtext, invworkdirtext;

	@FXML
	private Button baseDirBtn, workDirBtn, okbtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		baseDirValue.setText(DAO.getGlobalPref(CRUSADER_PATH_PREF_KEY));
		workDirValue.setText(DAO.getGlobalPref(WORKING_DIR_PREF_KEY));

		baseDirBtn.setOnAction(e -> selectBaseDir(CRUSADER_PATH_PREF_KEY, baseDirValue, invcrusaderdirtext,
				"Choose Stronghold Crusader Directory"));
		workDirBtn.setOnAction(
				e -> selectBaseDir(WORKING_DIR_PREF_KEY, workDirValue, invworkdirtext, "Choose Working Directory"));

		okbtn.setOnAction(e -> checkAndClose());
	}

	private void selectBaseDir(String DAO_key, TextField field, Label errrLabel, String title) {
		File file = UI_Util.openFileDialog(field.getText(), title);
		// file is null if user canceled dir chooser action
		if (file != null) {
			field.setText(file.getAbsolutePath());
			errrLabel.visibleProperty().set(!DAO.checkPrefValid(DAO_key, file.getAbsolutePath()));
		}
	}

	private void checkAndClose() {
		String crusaderDir = baseDirValue.getText();
		String workDir = workDirValue.getText();
		if (DAO.checkPrefValid(CRUSADER_PATH_PREF_KEY, crusaderDir)
				&& DAO.checkPrefValid(WORKING_DIR_PREF_KEY, workDir)) {
			DAO.setGlobalPref(CRUSADER_PATH_PREF_KEY, crusaderDir);
			DAO.setGlobalPref(WORKING_DIR_PREF_KEY, workDir);
			((Stage) okbtn.getScene().getWindow()).close();
		}
	}

}

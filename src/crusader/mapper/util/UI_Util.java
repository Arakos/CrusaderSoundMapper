package crusader.mapper.util;

import java.io.File;

import crusader.mapper.ui.fragments.Fragment;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UI_Util {

	public static void showAlert(String msg, Exception e) {
		showAlert(msg, AlertType.ERROR, e);
	}

	public static void showAlert(String msg, AlertType type, Exception e) {
		Alert alert = new Alert(type);
		alert.setDialogPane(new DialogPane());
		alert.setTitle(type.name());
		alert.setHeaderText(msg == null ? "No message available" : msg);
		alert.setContentText(
				e == null ? type.equals(AlertType.ERROR) ? "No exception information available" : "" : e.toString());
		alert.showAndWait();
	}

	public static void openInNewWindow(Fragment<? extends Region> root) {
		openInNewWindow(root.createFragment());
	}

	public static void openInNewWindow(Region root) {
		Stage newStage = new Stage();
		newStage.initModality(Modality.APPLICATION_MODAL);
		setOnStage(newStage, root, true);
	}

	public static void setOnStage(Stage stage, Region root, boolean showAndWait) {

		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());

		Scene scene = new Scene(root);
		scene.getStylesheets().add(UI_Util.class.getResource("/css/application.css").toExternalForm());

		stage.setScene(scene);
		stage.hide();
		if (showAndWait) {
			stage.showAndWait();
		} else {
			stage.show();
		}
	}

	public static File openFileDialog(String baseFileStr, String title) {
		File baseDir = null;
		if (baseFileStr != null) {
			baseDir = new File(baseFileStr);
		}
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setInitialDirectory(baseDir != null && baseDir.exists() && baseDir.isDirectory() ? baseDir : null);
		dirChooser.setTitle(title);
		return dirChooser.showDialog(null);
	}

}

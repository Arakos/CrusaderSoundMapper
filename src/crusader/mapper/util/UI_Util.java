package crusader.mapper.util;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
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

	public static void setOnStage(Stage stage, Region root) {

		root.prefWidthProperty().bind(stage.widthProperty());
		root.prefHeightProperty().bind(stage.heightProperty());

		Scene scene = new Scene(root);
		scene.getStylesheets().add(UI_Util.class.getResource("/css/application.css").toExternalForm());

		stage.setScene(scene);
		stage.hide();
		stage.show();
	}

}

package crusader.mapper.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UI_Util {

	
	 public static void showAlert(String msg, Exception e) {
		 showAlert(msg, AlertType.ERROR, e);
	 }
	 
	 public static void showAlert(String msg, AlertType type, Exception e) {
		 Alert alert = new Alert(type);
		 alert.setTitle(type.name());
		 alert.setHeaderText(msg == null ? "No message available" : msg);
		 alert.setContentText(e == null ? type.equals(AlertType.ERROR) ? "No exception information available" : "" : e.toString());
		 alert.showAndWait();
	 }
	
}

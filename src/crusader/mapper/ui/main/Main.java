package crusader.mapper.ui.main;

import crusader.mapper.controler.DAOController;
import crusader.mapper.util.UI_Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {

			TabPane root = (TabPane) FXMLLoader.load(getClass().getResource("/fxml/MainUI.fxml"));
			UI_Util.setOnStage(stage, root);

			DAOController.ensureDAOInitializable(stage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void bindProps(Region r1, Region r2, boolean width, boolean height) {
		if (width)
			r1.prefWidthProperty().bind(r2.widthProperty());
		if (height)
			r1.prefHeightProperty().bind(r2.heightProperty());
	}
}

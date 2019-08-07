package crusader.mapper.ui.main;

import crusader.mapper.data.DAO;
import crusader.mapper.ui.fragments.BaseDirectoryChooserFragment;
import crusader.mapper.util.UI_Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage mainStage) {
		try {
			TabPane root = (TabPane) FXMLLoader.load(getClass().getResource("/fxml/MainUI.fxml"));
			UI_Util.setOnStage(mainStage, root, false);

			while (!DAO.isInitializeable()) {
				UI_Util.openInNewWindow(new BaseDirectoryChooserFragment());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < args.length - 1; i += 2) {
			boolean ok = DAO.setGlobalPref(args[i], args[i + 1]);
			System.out.println("Setpref: " + args[i] + "=" + args[i + 1] + " -> " + ok);
		}
		launch(args);
	}

	private void bindProps(Region r1, Region r2, boolean width, boolean height) {
		if (width)
			r1.prefWidthProperty().bind(r2.widthProperty());
		if (height)
			r1.prefHeightProperty().bind(r2.heightProperty());
	}
}

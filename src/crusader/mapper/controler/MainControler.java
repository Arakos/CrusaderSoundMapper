package crusader.mapper.controler;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.DAO;
import crusader.mapper.data.model.CrusaderSound;
import crusader.mapper.ui.fragments.SoundEntryFragment;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class MainControler implements Initializable {

	@FXML
	private ListView<CrusaderSound> topleftlist;

	@FXML
	private ListView<Object> bottomleftlist;

	@FXML
	private Button testbtn;

	@FXML
	private VBox contentVbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topleftlist.setItems(FXCollections.observableArrayList(DAO.getInstance().getCrusaderSoundFiles()));

		topleftlist.setOnMouseClicked(e -> {
			CrusaderSound selectedSound = topleftlist.getSelectionModel().getSelectedItem();
			if (e.getClickCount() == 1) {
				VBox fragmentRoot = new SoundEntryFragment(contentVbox, new SoundEntryController(selectedSound))
						.createFragment();
			}
		});
		testbtn.setOnAction(event -> {
			BaseDirectoryDialogController.openBaseDirChooserDialg();
		});

	}

}

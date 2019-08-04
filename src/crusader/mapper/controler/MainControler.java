package crusader.mapper.controler;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
	private ListView<Object> topleftlist;

	@FXML
	private ListView<Object> bottomleftlist;

	@FXML
	private Button testbtn;

	@FXML
	private VBox contentVbox;

	public MainControler() {
		System.out.println("main controller");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topleftlist.setItems(FXCollections.observableArrayList(DAO.getInstance().getCrusaderSoundFiles().stream()
				.map(file -> new CrusaderSound(file)).collect(Collectors.toList())));

		topleftlist.setOnMouseClicked(e -> {
			VBox fragmentRoot = new SoundEntryFragment(contentVbox,
					new SoundEntryController(topleftlist.getSelectionModel().getSelectedItem().toString()))
							.createFragment();
		});
		testbtn.setOnAction(event -> {
			BaseDirectoryDialogController.openBaseDirChooserDialg();
		});

	}

}

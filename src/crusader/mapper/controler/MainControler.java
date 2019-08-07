package crusader.mapper.controler;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.DAO;
import crusader.mapper.data.model.CrusaderSound;
import crusader.mapper.ui.fragments.BaseDirectoryChooserFragment;
import crusader.mapper.ui.fragments.SoundEntryFragment;
import crusader.mapper.util.SoundUtil;
import crusader.mapper.util.UI_Util;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class MainControler implements Initializable {

	@FXML
	private ListView<CrusaderSound> topleftlist, bottomleftlist;

	@FXML
	private Button testbtn;

	@FXML
	private VBox contentVbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topleftlist.setItems(FXCollections.observableArrayList(DAO.getInstance().getTaggedCrusaderSoundFiles()));

		topleftlist.setOnMouseClicked(e -> {
			CrusaderSound selectedSound = topleftlist.getSelectionModel().getSelectedItem();
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() == 1) {
					VBox fragmentRoot = new SoundEntryFragment(contentVbox, new SoundEntryController(selectedSound))
							.createFragment();
				} else if (e.getClickCount() == 2) {
					SoundUtil.playSound(selectedSound.getFile());
				}
			}
		});
		testbtn.setOnAction(event -> {
			UI_Util.openInNewWindow(new BaseDirectoryChooserFragment());
		});

	}

}

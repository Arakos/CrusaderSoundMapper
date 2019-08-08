package crusader.mapper.controler;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.model.CrusaderSound;
import crusader.mapper.data.model.FileWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SoundEntryController implements Initializable {

	private final FileWrapper soundEntry;

	@FXML
	private Label origSound, replacedSound;

	public SoundEntryController(CrusaderSound soundEntry) {
		this.soundEntry = soundEntry;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("controlling sound entry: " + soundEntry);
		origSound.setText("Orig: " + soundEntry);
		replacedSound.setText("Replaced: " + soundEntry);
	}

}

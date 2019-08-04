package crusader.mapper.controler;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class SoundEntryController implements Initializable {

	String entry = "NONE";

	@FXML
	private Label origSound, replacedSound;

	public SoundEntryController() {
	}

	public SoundEntryController(String test) {
		entry = test;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("controlling sound entry: " + entry);
		origSound.setText("Orig: " + entry);
		replacedSound.setText("Replaced: " + entry);
	}

}

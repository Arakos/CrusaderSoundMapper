package crusader.mapper.ui.fragments;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.controler.SoundEntryController;
import crusader.mapper.data.model.FileWrapper;
import crusader.mapper.util.SoundUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PlaySoundFragment extends BaseFragment<Object> implements Initializable {

    FileWrapper soundFile;

    @FXML
    Button playBtn;

    @FXML
    Label soundLabel;

	public PlaySoundFragment(Parent root, FileWrapper soundFile) {
        super(root, null);
        loader.setController(this);
        this.soundFile = soundFile;
	}

	@Override
    protected String getResourceFXML() {
        return "/fxml/PlaySoundFragment.fxml";
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        soundLabel.setText(soundFile.toString());
        playBtn.setOnAction(e -> SoundUtil.playSound(soundFile.getFile()));
	}

}

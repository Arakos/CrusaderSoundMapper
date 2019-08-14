package crusader.mapper.ui.fragments;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.model.FileWrapper;
import crusader.mapper.util.SoundUtil;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PlaySoundFragment extends BaseFragment<Object> {

    FileWrapper soundFile;

    @FXML
    Button playBtn;

    @FXML
    Label soundLabel;

	public PlaySoundFragment(FileWrapper soundFile) {
		this(null, soundFile);
	}

	public PlaySoundFragment(Parent root, FileWrapper soundFile) {
		super(root);
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

package crusader.mapper.ui.fragments;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.model.FileWrapper;
import crusader.mapper.data.model.SoundMapping;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class SoundMappingFragment extends BaseFragment<Object> implements Initializable {

    SoundMapping mapping;

    @FXML
    VBox origSoundContainer, mappedSoundContainer;

	public SoundMappingFragment(Parent root, SoundMapping mapping) {
		super(root);
        this.mapping = mapping;
	}

	@Override
    protected String getResourceFXML() {
        return "/fxml/SoundMappingFragment.fxml";
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        for(FileWrapper replacment : mapping.getReplacements()) {
            new PlaySoundFragment(mappedSoundContainer, replacment).createFragment();
        }
		new PlaySoundFragment(origSoundContainer, mapping.getOrigSound()).createFragment();
	}

}

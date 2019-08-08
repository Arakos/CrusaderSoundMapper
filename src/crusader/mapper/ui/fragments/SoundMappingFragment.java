package crusader.mapper.ui.fragments;

import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.controler.SoundEntryController;
import crusader.mapper.data.model.FileWrapper;
import crusader.mapper.data.model.SoundMapping;
import crusader.mapper.util.SoundUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SoundMappingFragment extends BaseFragment<Object> implements Initializable {

    SoundMapping mapping;

    @FXML
    VBox origSoundContainer, mappedSoundContainer;

	public SoundMappingFragment(Parent root, SoundMapping mapping) {
        super(root, null);
        loader.setController(this);
        this.mapping = mapping;
	}

	@Override
    protected String getResourceFXML() {
        return "/fxml/SoundMappingFragment.fxml";
    }

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        new PlaySoundFragment(origSoundContainer, mapping.getOrigSound()).createFragment();
        for(FileWrapper replacment : mapping.getReplacements()) {
            new PlaySoundFragment(mappedSoundContainer, replacment).createFragment();
        }
	}

}

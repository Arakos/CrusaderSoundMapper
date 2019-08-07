package crusader.mapper.ui.fragments;

import crusader.mapper.controler.SoundEntryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public abstract class BaseFragment<T> implements Fragment<T> {

	protected final FXMLLoader loader;

	protected BaseFragment(Parent root) {
		this(root, null);
	}

	protected BaseFragment(Parent root, Object controller) {
		this.loader = new FXMLLoader(SoundEntryController.class.getResource(getResourceFXML()));
		if (controller != null) {
			loader.setController(controller);
		}
		if (root != null) {
			loader.setRoot(root);
		}
	}

	protected abstract String getResourceFXML();

	@Override
	public T createFragment() {
		try {
			return loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

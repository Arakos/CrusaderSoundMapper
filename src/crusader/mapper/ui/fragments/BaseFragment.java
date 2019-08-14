package crusader.mapper.ui.fragments;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public abstract class BaseFragment<T> implements Fragment<T>, Initializable {

	protected final FXMLLoader loader;

	protected BaseFragment() {
		this(null);
	}

	protected BaseFragment(Parent root) {
		loader = new FXMLLoader(getClass().getResource(getResourceFXML()));
		loader.setController(this);
		setParent(root);
	}

	public void setParent(Parent root) {
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

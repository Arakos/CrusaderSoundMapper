package crusader.mapper.data;

import java.io.File;
import java.util.prefs.Preferences;

public class DAO {

	public static final String CRUSADER_PATH_PREF_DIR = "cursader_base_path";
	public static final String WORKING_DIR_PREF_KEY = "working_dir_path";

	private static final String C_SPEECH_PATH = "fx" + File.separatorChar + "speech";

	private static final String EXTENSION = "wav";

	// singlton
	private static DAO instace;

	private File crusaderBaseDir;

	private File workingBaseDir;

	private DAO() {
		crusaderBaseDir = getFileFromPref(CRUSADER_PATH_PREF_DIR);
		workingBaseDir = getFileFromPref(WORKING_DIR_PREF_KEY);
	}

	private File getFileFromPref(String pref) {
		String prefValue = Preferences.userNodeForPackage(DAO.class).get(pref, null);
		if (prefValue != null) {
			File tmp = new File(prefValue);
			if (tmp.exists() && tmp.isDirectory()) {
				return tmp;
			}
		}
		return null;
	}

	private void isInitialized() {
		if (crusaderBaseDir == null) {
			throw new IllegalStateException();
		}
		if (workingBaseDir == null) {
			throw new IllegalStateException();
		}
	}

	public static final DAO getInstance() {
		if (instace == null) {
			instace = new DAO();
		}
		return instace;
	}

	public File getBaseDir() {
		return crusaderBaseDir;
	}

	public void setBaseDir(File crusaderBaseDir) {
		this.crusaderBaseDir = crusaderBaseDir;
	}

	public File getWorkingDir() {
		return workingBaseDir;
	}

	public void setWorkingDir(File workingBaseDir) {
		this.workingBaseDir = workingBaseDir;
	}

}

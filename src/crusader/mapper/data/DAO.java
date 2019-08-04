package crusader.mapper.data;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

public class DAO {

	public static final String CRUSADER_PATH_PREF_KEY = "cursader_base_path";
	public static final String WORKING_DIR_PREF_KEY = "working_dir_path";

	private static final String C_SPEECH_PATH = "fx" + File.separatorChar + "speech";

	private static final String EXTENSION = "wav";

	// singleton
	private static DAO instace;

	private static final Preferences DAO_PREFS = Preferences.userNodeForPackage(DAO.class);

	private static final Map<String, Predicate<String>> predicateMap = new HashMap<String, Predicate<String>>() {
		{
			put(CRUSADER_PATH_PREF_KEY, crusaderBaseDirStr -> {
				if (crusaderBaseDirStr == null || crusaderBaseDirStr.isEmpty()) {
					return false;
				}
				File crusaderBaseDir = new File(crusaderBaseDirStr);
				if (crusaderBaseDir.exists() && crusaderBaseDir.isDirectory()) {
					File speech = new File(crusaderBaseDir.getAbsolutePath() + File.separatorChar + C_SPEECH_PATH);
					return speech.exists() && speech.isDirectory();
				}
				return false;
			});
			put(WORKING_DIR_PREF_KEY, workingBaseDirStr -> {
				if (workingBaseDirStr == null || workingBaseDirStr.isEmpty()) {
					return false;
				}
				File workingBaseDir = new File(workingBaseDirStr);
				return workingBaseDir.exists() && workingBaseDir.isDirectory();
			});
		}
	};

	private File crusaderBaseDir;

	private File workingBaseDir;

	private DAO() {
		if (!isInitializeable()) {
			throw new IllegalStateException(
					"DAO not initializeable! CrusaderDir: " + crusaderBaseDir + " | WorkDir: " + workingBaseDir);
		}
		crusaderBaseDir = new File(DAO_PREFS.get(WORKING_DIR_PREF_KEY, null));
		workingBaseDir = new File(DAO_PREFS.get(CRUSADER_PATH_PREF_KEY, null));
	}

	public static boolean isInitializeable() {
		return initializeable(null);
	}

	private static boolean initializeable(String key) {
		if (key == null) {
			return initializeable(CRUSADER_PATH_PREF_KEY) && initializeable(WORKING_DIR_PREF_KEY);
		} else {
			return checkPrefValid(key, DAO_PREFS.get(key, null));
		}
	}

	public static final boolean checkPrefValid(String key, String value) {
		return predicateMap.containsKey(key) && predicateMap.get(key).test(value);
	}

	public static final void setGlobalPref(String key, String value) {
		if (checkPrefValid(key, value)) {
			DAO_PREFS.put(key, value);
		}
	}

	public static final String getGlobalPref(String key) {
		return DAO_PREFS.get(key, "");
	}

	/**
	 * For debug purpose
	 */
	public static void clearPrefs() {
		DAO_PREFS.remove(CRUSADER_PATH_PREF_KEY);
		DAO_PREFS.remove(WORKING_DIR_PREF_KEY);
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
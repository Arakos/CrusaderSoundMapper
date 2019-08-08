package crusader.mapper.data.model;

import java.io.File;

import crusader.mapper.data.DAO;

public class FileWrapper {

	protected final File source;

	public FileWrapper(String pathname) {
		this(new File(pathname));
	}

	public FileWrapper(File file) {
		this.source = assertValid(file);
	}

	private File assertValid(File file) {
		// if (file == null || !file.exists() || !file.getName().endsWith(DAO.EXTENSION)) {
		// 	throw new IllegalArgumentException("Soundfile not valid: " + file == null ? "<null>" : file.getName());
		// }
		return file;
	}

	@Override
	public String toString() {
		return source.getName();//.replace(DAO.EXTENSION, "");
	}

	public File getFile() {
		return source;
	}

}

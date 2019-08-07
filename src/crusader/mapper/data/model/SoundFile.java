package crusader.mapper.data.model;

import java.io.File;

import crusader.mapper.data.DAO;

public class SoundFile extends File {

	public SoundFile(String pathname) {
		super(pathname);
		if (!pathname.endsWith(DAO.EXTENSION)) {
			throw new IllegalArgumentException("Soundfile without '.wav'-Extension specified: " + getAbsolutePath());
		}
	}

	@Override
	public String toString() {
		return this.getName().replace(DAO.EXTENSION, "");
	}

}

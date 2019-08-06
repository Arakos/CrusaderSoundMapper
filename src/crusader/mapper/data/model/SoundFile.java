package crusader.mapper.data.model;

import java.io.File;

import crusader.mapper.data.DAO;

public class SoundFile extends File {

	public SoundFile(String pathname) {
		super(pathname);
	}

	@Override
	public String toString() {
		return this.getName().replace(DAO.EXTENSION, "");
	}

}

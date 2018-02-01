package crusader.mapper;

import java.io.File;

public class FileWrapper {
	
	private File src;

	public FileWrapper(File f) {
		this.src = f;
	}
	
	@Override
	public String toString() {
		int idx = src.getName().lastIndexOf('.');
		if(src.isDirectory()) {
			return "/" + src.getName();
		}
		if(idx == -1) {
			return src.getName();
		}
		return src.getName().substring(0, idx);
	}
	
	public File getFile() {
		return src;
	}
	
}

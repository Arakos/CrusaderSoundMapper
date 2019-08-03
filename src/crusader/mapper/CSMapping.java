package crusader.mapper;

public class CSMapping {

	private FileWrapper cFile, sFile;

	public CSMapping(FileWrapper cFile, FileWrapper sFile) {
		super();
		this.cFile = cFile;
		this.sFile = sFile;
	}

	public FileWrapper getcFile() {
		return cFile;
	}

	public void setcFile(FileWrapper cFile) {
		this.cFile = cFile;
	}

	public FileWrapper getsFile() {
		return sFile;
	}

	public void setsFile(FileWrapper sFile) {
		this.sFile = sFile;
	}

	@Override
	public String toString() {
		return sFile.toString() + " -> " + cFile.toString();
	}

	public String toCVS() {
		return cFile.getFile().getAbsolutePath() + "," + sFile.getFile().getAbsolutePath();
	}

}

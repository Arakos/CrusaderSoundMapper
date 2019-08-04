package crusader.mapper.data.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CrusaderSound {

	File source;

	private static final Map<Integer, String> ORDER = new HashMap<>();

	{
		ORDER.put(0, "Unit");
		ORDER.put(1, "Type");
		ORDER.put(2, "Unknown2");
		ORDER.put(3, "Unknown3");
		ORDER.put(4, "Unknown4");
	}

	private Map<String, String> valueMap = new HashMap<>();

	public CrusaderSound(File source) {
		this.source = source;
		String[] split = source.getName().split("_|\\.");
		for (int i = 0; i < split.length - 1; i++) {
			valueMap.put(ORDER.get(i), split[i]);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> entry : valueMap.entrySet()) {
			sb.append(entry.getKey()).append(": '").append(entry.getValue()).append("'\t");
		}
		return sb.toString();
	}

}

package crusader.mapper.data.model;

import java.io.File;
import java.util.Set;

import crusader.mapper.data.model.SoundTagManager.META_TAG;
import crusader.mapper.data.model.SoundTagManager.SOUND_TAG;

public class CrusaderSound extends FileWrapper {

	final Set<SOUND_TAG> tags;

	public CrusaderSound(File sound) {
		super(sound);
		this.tags = SoundTagManager.getSoundTags(sound.getName());
	}

	public Set<SOUND_TAG> getTags() {
		return tags;
	}

	public Set<META_TAG> getMetaTags() {
		return SoundTagManager.getMetaTags(getTags());
	}

	public String getDisplayName() {
		return source.getName().replace(".wav", "") + " " + getTags() + " " + getMetaTags();
	}

	@Override
	public String toString() {
		return getDisplayName();
	}

}

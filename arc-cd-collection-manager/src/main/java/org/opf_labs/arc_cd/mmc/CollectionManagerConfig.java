/**
 * 
 */
package org.opf_labs.arc_cd.mmc;

import java.io.File;

import org.opf_labs.utils.Environments;

import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc for CollectionManagerConfig.</p>
 * TODO Tests for CollectionManagerConfig.</p>
 * TODO Implementation for CollectionManagerConfig.</p>
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 29 Aug 2013:17:13:22
 */

public final class CollectionManagerConfig {
	// Constants for defaults
	static final boolean HELP_DEFAULT = false;
	static final boolean CUES_DEFAULT = false;
	static final boolean MANIFESTS_DEFAULT = false;
	static final String ROOT_DEFAULT = Environments.getUserHome()
			+ File.separator + "Music";
	static final String NAME_DEFAULT = "arccd";
	static final CollectionManagerConfig DEFAULT = new CollectionManagerConfig();

	private final boolean helpRequested;
	private final boolean generateCues;
	private final boolean checkManifests;
	private final String name;
	private final String root;
	
	private CollectionManagerConfig() {
		this(HELP_DEFAULT, CUES_DEFAULT, MANIFESTS_DEFAULT, ROOT_DEFAULT, NAME_DEFAULT);
	}
	
	private CollectionManagerConfig(final boolean helpRequested, final boolean generateCues, final boolean checkManifests, final String root, final String name) {
		this.helpRequested = helpRequested;
		this.generateCues = generateCues;
		this.checkManifests = checkManifests;
		this.root = root;
		this.name = name;
	}

	/**
	 * @return true if the user requested help, otherwise false
	 */
	public boolean helpRequested() {
		return this.helpRequested;
	}
	
	/**
	 * @return true if user requested cue generation
	 */
	public boolean generateCues() {
		return this.generateCues;
	}
	
	/**
	 * @return true if user requested manifest check
	 */
	public boolean checkManifests() {
		return this.checkManifests;
	}
	/**
	 * @return the archive root directory
	 */
	public String getCollectionRoot() {
		return this.root + File.separator + this.name;
	}

	/**
	 * @return the path root of the archive
	 */
	public String getArchiveRoot() {
		return this.root;
	}

	/**
	 * @return the name of the archive
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "ArcCdConfig:[root=" + this.root + ", name=" +  this.name + "]";
	}

	/**
	 * @return the path of the default root directory
	 */
	public static String getDefaultRoot() {
		return ROOT_DEFAULT;
	}

	/**
	 * @return the default collection name
	 */
	public static String getDefaultName() {
		return NAME_DEFAULT;
	}

	/**
	 * @return the default configuration object
	 */
	public static CollectionManagerConfig getDefault() {
		return DEFAULT;
	}
	
	/**
	 * @param helpRequested true if the user requested help
	 * @param generateCues true if user requested cue generation
	 * @param checkManifests true if user requested manifest check
	 * @param root the path of the root directory of the archive
	 * @param name the name of the archive
	 * @return the config instance
	 */
	public static CollectionManagerConfig fromValues(final boolean helpRequested, final boolean generateCues, final boolean checkManifests, final String root, final String name) {
		Preconditions.checkNotNull(root, "root == null");
		Preconditions.checkNotNull(name, "name == null");
		Preconditions.checkArgument(!root.isEmpty(), "root.isEmpty() == true");
		Preconditions.checkArgument(!name.isEmpty(), "name.isEmpty() == true");
		// Replace \ with / and remove any trailing separator
		File rootDir = new File(root);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		return new CollectionManagerConfig(helpRequested, generateCues, checkManifests, rootDir.getAbsolutePath(), name);
	}
	
	/**
	 * Builder class for config instances.
	 * 
	 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
	 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
	 * @version 0.1
	 * 
	 * Created 30 Aug 2013:00:52:30
	 */
	@SuppressWarnings("hiding") 
	public static class Builder {
		private boolean helpRequested = CollectionManagerConfig.HELP_DEFAULT;
		private boolean generateCues = CollectionManagerConfig.CUES_DEFAULT;
		private boolean checkManifests = CollectionManagerConfig.MANIFESTS_DEFAULT;
		private String root = CollectionManagerConfig.ROOT_DEFAULT;
		private String name = CollectionManagerConfig.NAME_DEFAULT;
		
		/**
		 * Default constructor
		 */
		public Builder() {
			/** Nothing to initialise */
		}
		/**
		 * @param root
		 */
		public Builder(String root) {
			Preconditions.checkNotNull(root, "root == null");
			Preconditions.checkArgument(!root.isEmpty(), "root.isEmpty() == true");
			this.root = root;
		}

		/**
		 * @param root
		 * @param name
		 */
		public Builder(String root, String name) {
			Preconditions.checkNotNull(root, "root == null");
			Preconditions.checkArgument(!root.isEmpty(), "root.isEmpty() == true");
			Preconditions.checkNotNull(name, "name == null");
			Preconditions.checkArgument(!name.isEmpty(), "name.isEmpty() == true");
			this.root = root;
			this.name = name;
		}
		
		/**
		 * @param helpRequested boolean set true if user requested help
		 * @return the Builder instance
		 */
		public Builder helpRequested(boolean helpRequested) {
			this.helpRequested = helpRequested;
			return this;
		}
		
		/**
		 * @param generateCues boolean set true if user requested cue generation
		 * @return the Builder instance
		 */
		public Builder generateCues(boolean generateCues) {
			this.generateCues = generateCues;
			return this;
		}
		
		/**
		 * @param checkManifests boolean set true if user requested manifest generation
		 * @return the Builder instance
		 */
		public Builder checkManifests(boolean checkManifests) {
			this.checkManifests = checkManifests;
			return this;
		}
		/**
		 * @param root the root directory for archive collections
		 * @return the Builder instance
		 */
		public Builder root(String root) {
			Preconditions.checkNotNull(root, "root == null");
			Preconditions.checkArgument(!root.isEmpty(), "root.isEmpty() == true");
			this.root = root;
			return this;
		}
		
		/**
		 * @param name the name of the archive collection
		 * @return the Builder instance
		 */
		public Builder name(String name) {
			Preconditions.checkNotNull(name, "name == null");
			Preconditions.checkArgument(!name.isEmpty(), "name.isEmpty() == true");
			this.name = name;
			return this;
		}
	
		/**
		 * @return a new Config object from the builder fields
		 */
		public CollectionManagerConfig build() {
			return CollectionManagerConfig.fromValues(this.helpRequested, this.generateCues, this.checkManifests, this.root, this.name);
		}
	}
}

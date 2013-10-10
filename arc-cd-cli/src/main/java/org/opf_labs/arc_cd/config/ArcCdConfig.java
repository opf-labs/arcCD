/**
 * 
 */
package org.opf_labs.arc_cd.config;

import java.io.File;

import org.opf_labs.utils.Environments;

import com.google.common.base.Preconditions;

/**
 * TODO JavaDoc for ArcCdConfig.</p>
 * TODO Tests for ArcCdConfig.</p>
 * TODO Implementation for ArcCdConfig.</p>
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 29 Aug 2013:17:13:22
 */

public final class ArcCdConfig {
	// Constants for defaults
	static final boolean HELP_DEFAULT = false;
	static final String ROOT_DEFAULT = Environments.getUserHome()
			+ File.separator + "Music";
	static final String NAME_DEFAULT = "arccd";
	static final String CDRDAO_DEFAULT = Environments.isWindows() ? ".\\bin\\cdrdao" : "cdrdao";
	static final ArcCdConfig DEFAULT = new ArcCdConfig();

	private final boolean helpRequested;
	private final String name;
	private final String root;
	private final String cdrdaoPath;
	
	private ArcCdConfig() {
		this(HELP_DEFAULT, ROOT_DEFAULT, NAME_DEFAULT, CDRDAO_DEFAULT);
	}
	
	private ArcCdConfig(final boolean helpRequested, final String root, final String name, final String cdrdaoPath) {
		this.helpRequested = helpRequested;
		this.root = root;
		this.name = name;
		this.cdrdaoPath = cdrdaoPath;
	}

	/**
	 * @return true if the user requested help, otherwise false
	 */
	public boolean helpRequested() {
		return this.helpRequested;
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
	
	/**
	 * @return the path to cdrdao exe
	 */
	public String getCdrdaoPath() {
		return this.cdrdaoPath;
	}

	@Override
	public String toString() {
		return "ArcCdConfig:[root=" + this.root + ", name=" +  this.name + ", cdrdao=" + this.cdrdaoPath + "]";
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
	public static ArcCdConfig getDefault() {
		return DEFAULT;
	}
	
	/**
	 * @param helpRequested true if the user requested help
	 * @param root the path of the root directory of the archive
	 * @param name the name of the archive
	 * @param cdrdao  the path to a cdrdao executable
	 * @return the config instance
	 */
	public static ArcCdConfig fromValues(final boolean helpRequested, final String root, final String name, final String cdrdao) {
		Preconditions.checkNotNull(root, "root == null");
		Preconditions.checkNotNull(name, "name == null");
		Preconditions.checkNotNull(cdrdao, "cdrdao == null");
		Preconditions.checkArgument(!root.isEmpty(), "root.isEmpty() == true");
		Preconditions.checkArgument(!name.isEmpty(), "name.isEmpty() == true");
		Preconditions.checkArgument(!cdrdao.isEmpty(), "cdrdao.isEmpty() == true");
		// Replace \ with / and remove any trailing separator
		File rootDir = new File(root);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		String cleanCdrdao = cdrdao;
		if (cdrdao.endsWith("/") || cdrdao.endsWith("\\")) {
			cleanCdrdao = cdrdao.substring(0, cdrdao.length() - 1);
		}
		return new ArcCdConfig(helpRequested, rootDir.getAbsolutePath(), name, cleanCdrdao);
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
		private boolean helpRequested = ArcCdConfig.HELP_DEFAULT;
		private String root = ArcCdConfig.ROOT_DEFAULT;
		private String name = ArcCdConfig.NAME_DEFAULT;
		private String cdrdao = ArcCdConfig.CDRDAO_DEFAULT;
		
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
		 * @param cdrdao the path to a cdrdao executable
		 * @return the Builder instance
		 */
		public Builder cdrdao(String cdrdao) {
			Preconditions.checkNotNull(cdrdao, "cdrdao == null");
			Preconditions.checkArgument(!cdrdao.isEmpty(), "cdrdao.isEmpty() == true");
			this.cdrdao = cdrdao;
			return this;
		}
		
		/**
		 * @return a new Config object from the builder fields
		 */
		public ArcCdConfig build() {
			return ArcCdConfig.fromValues(this.helpRequested, this.root, this.name, this.cdrdao);
		}
	}
}

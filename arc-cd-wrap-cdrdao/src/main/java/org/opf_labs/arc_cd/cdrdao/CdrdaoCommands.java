/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

/**
 * Class to hold the set of cdrdao commands and options. For documentation see
 * the cdrdao site http://cdrdao.sourceforge.net/ or the Linux Man page for
 * cdrdao http://linuxreviews.org/man/cdrdao/ alternatively
 * http://www.linuxcommand.org/man_pages/cdrdao1.html
 * 
 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 *          Created 6 Oct 2013:22:09:00
 */

public final class CdrdaoCommands {
	/**
	 * Enumeration for cdrdao command set
	 */
	public enum Command {
		/** cdrdao scanbus command */
		SCANBUS("scanbus", new Option[] {}),
		/** cdrdao drive-info command */
		DRIVE_INFO("drive-info", new Option[] { Option.DEVICE }),
		/** cdrdao disk-info command */
		DISC_INFO("disk-info", new Option[] { Option.DEVICE }),
		/** cdrdao read-toc command */
		READ_TOC("read-toc", new Option[] { Option.READ_RAW_OPTION,
				Option.FAST_TOC_OPTION, Option.DEVICE }),
		/** cdrdao read-cd command */
		READ_CD("read-cd", new Option[] { Option.DEVICE,
				Option.READ_RAW_OPTION, Option.DATA_FILE_OPTION });

		private final String command;
		private final List<Option> defaultOptions;

		Command(final String command, final Option[] defaultOptions) {
			this.command = command;
			this.defaultOptions = Collections.unmodifiableList(Arrays
					.asList(defaultOptions));
		}

		/**
		 * @return the cdrdao command string
		 */
		public String value() {
			return this.command;
		}

		/**
		 * @return the default options for the command
		 */
		public List<Option> defaultOptions() {
			return this.defaultOptions;
		}
	}

	/**
	 * Enumeration for cdrdao option set
	 */
	public enum Option {
		/** cdrdao device option */
		DEVICE("--device"),
		/** cdrdao read-raw option */
		READ_RAW_OPTION("--read-raw"),
		/** cdrdao fast-tco option */
		FAST_TOC_OPTION("--fast-toc"),
		/** cdrdao datafile option */
		DATA_FILE_OPTION("--datafile");

		private final String option;

		Option(final String option) {
			this.option = option;
		}

		/**
		 * @return the string option value
		 */
		public String getOption() {
			return this.option;
		}
	}

	/**
	 * Encapsulates the concept of CD Paranoia mode option for cdrdao
	 * 
	 * @author <a href="mailto:carl@openplanetsfoundation.org">Carl
	 *         Wilson</a>.</p> <a
	 *         href="https://github.com/carlwilson">carlwilson AT
	 *         github</a>.</p>
	 * @version 0.1
	 * 
	 *          Created 7 Oct 2013:11:29:47
	 */
	public enum ParanoiaModeOption {
		/** Fastest mode with no error checking */
		NO_CHECKING(0),
		/** Overlapped Reading to avoid jitter */
		OVERLAPPED_READING(1),
		/** OVERLAPPED_READING with additional checks of read audio data */
		AUDIO_DATA_CHECKS(2),
		/** AUDION_DATA_CHECKS with additional scratch detection and repair */
		SCRATCH_REPAIR(3);
		static final String PARANOIA_MODE_OPTION = "--paranoia-mode";

		private final int modeValue;
		private final List<String> optionList;

		private ParanoiaModeOption(final int modeValue) {
			this.modeValue = modeValue;
			this.optionList = Collections.unmodifiableList(new ArrayList<>(
					Arrays.asList(new String[] { PARANOIA_MODE_OPTION,
							this.getModeValueAsString() })));
		}

		/**
		 * @return the cd paranoia mode value as a string for use in commands
		 */
		public String getModeValueAsString() {
			return Integer.toString(this.modeValue);
		}

		/**
		 * @return the cd paranoia mode value
		 */
		public int getModeValue() {
			return this.modeValue;
		}

		/**
		 * @return an unmodifiable list containing the cdrdao options for
		 *         paranoia mode
		 */
		public List<String> getOptionList() {
			return this.optionList;
		}
	}

	private final String command;
	final ParanoiaModeOption paranoiaMode;
	private final Map<Option, String> optionValues = new HashMap<>();

	private CdrdaoCommands() {
		throw new AssertionError("Should never enter CdrdaoCommands()");
	}

	private CdrdaoCommands(final String command,
			final ParanoiaModeOption paranoiaMode) {
		this.command = command;
		this.paranoiaMode = paranoiaMode;
	}

	static CdrdaoCommands getParanoidInstance(final String command) {
		return CdrdaoCommands.getNewInstance(command,
				ParanoiaModeOption.SCRATCH_REPAIR);
	}

	static CdrdaoCommands getNewInstance(final String command,
			final ParanoiaModeOption paranoiaMode) {
		Preconditions.checkNotNull(command, "command == null");
		Preconditions.checkNotNull(paranoiaMode, "paranoiaMode == null");
		Preconditions.checkArgument(!command.isEmpty(), "command.isEmpty()");
		return new CdrdaoCommands(command, paranoiaMode);
	}

	/**
	 * @return the List<String> argument array for cdrdao scanbus
	 */
	public List<String> scanbus() {
		return scanbusCommand(this.command);
	}

	/**
	 * @param device
	 *            the device name for CD device to use
	 * @return the List<String> argument array for cdrdao drive-info
	 */
	public List<String> driveInfo(final String device) {
		Preconditions.checkNotNull(device, "device == null");
		Preconditions.checkArgument(!device.isEmpty(), "device.isEmpty()");
		this.optionValues.clear();
		this.optionValues.put(Option.DEVICE, device);
		return Collections
				.unmodifiableList(createCommandList(Command.DRIVE_INFO));
	}

	/**
	 * @param device
	 *            the device name for CD device to use
	 * @return the List<String> argument array for cdrdao disk-info
	 */
	public List<String> diskInfo(final String device) {
		Preconditions.checkNotNull(device, "device == null");
		Preconditions.checkArgument(!device.isEmpty(), "device.isEmpty()");
		this.optionValues.clear();
		this.optionValues.put(Option.DEVICE, device);
		return Collections
				.unmodifiableList(createCommandList(Command.DISC_INFO));
	}

	/**
	 * @param device
	 *            the device name for CD device to use
	 * @param tocPath
	 *            the path for the created toc file
	 * @return the List<String> argument array for cdrdao disk-info
	 */
	public List<String> readToc(final String device, final String tocPath) {
		Preconditions.checkNotNull(device, "device == null");
		Preconditions.checkNotNull(tocPath, "tocPath == null");
		Preconditions.checkArgument(!device.isEmpty(), "device.isEmpty()");
		Preconditions.checkArgument(!tocPath.isEmpty(), "tocPath.isEmpty()");
		this.optionValues.clear();
		this.optionValues.put(Option.DEVICE, device);
		List<String> commands = createCommandList(Command.READ_TOC);
		commands.add(tocPath);
		return Collections.unmodifiableList(commands);
	}

	/**
	 * @param device
	 *            the device name for CD device to use
	 * @param tocPath
	 *            the path for the created toc file
	 * @param binPath
	 *            the path for the created bin file, will hold the ripped cd
	 *            data
	 * @return the List<String> argument array for cdrdao disk-info
	 */
	public List<String> readCd(final String device, final String tocPath,
			final String binPath) {
		Preconditions.checkNotNull(device, "device == null");
		Preconditions.checkNotNull(tocPath, "tocPath == null");
		Preconditions.checkNotNull(binPath, "binPath == null");
		Preconditions.checkArgument(!device.isEmpty(), "device.isEmpty()");
		Preconditions.checkArgument(!tocPath.isEmpty(), "tocPath.isEmpty()");
		Preconditions.checkArgument(!binPath.isEmpty(), "binPath.isEmpty()");
		this.optionValues.clear();
		this.optionValues.put(Option.DEVICE, device);
		this.optionValues.put(Option.DATA_FILE_OPTION, binPath);
		List<String> commands = createCommandList(Command.READ_CD);
		commands.add(ParanoiaModeOption.PARANOIA_MODE_OPTION);
		commands.add(this.paranoiaMode.getModeValueAsString());
		commands.add(tocPath);
		return Collections.unmodifiableList(commands);
	}

	private List<String> createCommandList(Command subcommand) {
		List<String> commandList = initialCommandList(subcommand);
		for (Option option : subcommand.defaultOptions()) {
			commandList.add(option.getOption());
			if (this.optionValues.containsKey(option)) {
				commandList.add(this.optionValues.get(option));
			}
		}
		return commandList;
	}

	/**
	 * @return the List<String> argument array for cdrdao scanbus
	 */
	static List<String> scanbusCommand(final String cdrdaoCommand) {
		String[] commands = new String[] { cdrdaoCommand,
				Command.SCANBUS.value() };
		return Collections.unmodifiableList(Arrays.asList(commands));
	}

	private List<String> initialCommandList(Command subcommand) {
		List<String> commandList = new ArrayList<>();
		commandList.add(this.command);
		commandList.add(subcommand.value());
		return commandList;
	}
}

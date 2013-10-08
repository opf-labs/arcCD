/**
 * 
 */
package org.opf_labs.arc_cd.cdrdao.test;

import java.util.Arrays;
import java.util.List;

import org.opf_labs.utils.ProcessRunner;
import org.opf_labs.utils.ProcessRunnerFactory;

/**
 * TODO JavaDoc for MockCdrProcessFactory.</p>
 * TODO Tests for MockCdrProcessFactory.</p>
 * TODO Implementation for MockCdrProcessFactory.</p>
 * 
 * @author  <a href="mailto:carl@openplanetsfoundation.org">Carl Wilson</a>.</p>
 *          <a href="https://github.com/carlwilson">carlwilson AT github</a>.</p>
 * @version 0.1
 * 
 * Created 6 Oct 2013:00:07:03
 */

public final class MockCdrProcessFactory implements ProcessRunnerFactory {
	private static MockCdrProcessFactory INSTANCE = new MockCdrProcessFactory();
	
	private MockCdrProcessFactory() {
		
	}
	/**
	 * @see org.opf_labs.utils.ProcessRunnerFactory#createProcessRunner()
	 */
	@Override
	public ProcessRunner createProcessRunner() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunnerFactory#createProcessRunner(java.lang.String)
	 */
	@Override
	public ProcessRunner createProcessRunner(String command) {
		return createProcessRunner(new String[] { command });
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunnerFactory#createProcessRunner(java.lang.String[])
	 */
	@Override
	public ProcessRunner createProcessRunner(String[] commands) {
		return createProcessRunner(Arrays.asList(commands));
	}

	/**
	 * @see org.opf_labs.utils.ProcessRunnerFactory#createProcessRunner(java.util.List)
	 */
	@Override
	public ProcessRunner createProcessRunner(List<String> commands) {
		return new MockCdrdaoProcessRunner(commands);
	}
	
	/**
	 * @return the mock factory instance
	 */
	public static MockCdrProcessFactory getInstance() {
		return INSTANCE;
	}
}

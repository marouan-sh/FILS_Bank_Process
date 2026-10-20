package bank.filter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class BankFilter {

	// Name of the filter class to be executed in the pipeline
	private static final String FILTER_CLASS = bank.filter.StatementFilter.class.getName();

	// Commands of the pipeline (first filter and second filter)
	private static final String[] COMMAND_1 = { "java", "-Duser.dir=bin", FILTER_CLASS, "amount", "-gt", "100" };
	private static final String[] COMMAND_2 = { "java", "-Duser.dir=bin", FILTER_CLASS, "amount", "-lt", "200" };
	// private static final String[] COMMAND_2 = { "java", "-Duser.dir=bin",
	// FILTER_CLASS, "operation", "-eq", "withdraw" };

	private static final String[][] PIPE_LINE = { COMMAND_1, COMMAND_2 };

	// Input and output files
	private static final String STATEMENT_PREFIX = "statement-";
	private static final String STATEMENT_SUFFIX = ".txt";
	private static final String OUTPUT_FILE = "filter.txt";

	private static final int MONTHS = 12;
	private static final String CURRENT_DIRECTORY = new File("").getAbsolutePath();

	/*
	 * IMPORTANT: All pipelines must run in parallel. Do not wait for one to finish
	 * before starting the next.
	 */
	public static void main(String[] args) throws IOException {

		// Para borrar el fichero con los resultados antiguos
		new File(OUTPUT_FILE).delete();

		// Here we store all the last processes of the pipeline in order to get their
		// outputs later
		List<Process> lastPipelineProcesses = new ArrayList<>();

		// TODO: For each month (1 to 12), build the pipeline
		for (int month = 1; month <= MONTHS; month++) {
			String fileName = String.format("%s%02d%s", STATEMENT_PREFIX, month, STATEMENT_SUFFIX);

			// TODO: Create a list of ProcessBuilders representing the pipeline
			List<ProcessBuilder> pipelineProcessBuilders = new ArrayList<>();
			for (String[] command : PIPE_LINE) {
				ProcessBuilder pb = new ProcessBuilder(command);
				pb.redirectError(ProcessBuilder.Redirect.INHERIT);
				pipelineProcessBuilders.add(pb);
			}

			// TODO: Configure first process builder to get input from his corresponding
			// fileName
			pipelineProcessBuilders.get(0).redirectInput(new File("bank", fileName));

			// TODO: Start the pipeline and collect the all processes (replace null
			// initialization)
			List<Process> pipelineProcesses = ProcessBuilder.startPipeline(pipelineProcessBuilders);

			// TODO: Store the last process of the pipeline (needed to collect the final
			// output)
			Process lastPipelineProcess = pipelineProcesses.get(pipelineProcesses.size() - 1);
			lastPipelineProcesses.add(lastPipelineProcess);
		}

		// TODO: Collect the output of all pipelines in order, appending to OUTPUT_FILE
		// (hint: use FileOutputStream with append = true)

		try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE, true)) {
			for (Process p : lastPipelineProcesses) {
				p.getInputStream().transferTo(fos);
			}
		}

		System.exit(0);
	}
}

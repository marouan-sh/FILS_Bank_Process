package bank;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import bank.BankProcess.Operation;

public class AccountMonitor {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private String fileName;

	public AccountMonitor(String fileName) {
		this.fileName = fileName;
	}

	// Block of fileLocke
	public void writeOperation(Operation operation, String concept, double amount) {
		String fecha = LocalDateTime.now().format(FORMATTER);
		String operacion = operation.toString();
		String signo = operation.sign();
		String importe = String.format(Locale.ROOT, "%.2f", amount);

		String linea = fecha + " | " + operacion + " | " + concept + " | " + signo + importe + " €\n";

		try (FileOutputStream fos = new FileOutputStream(fileName, true);
				FileLock lock = fos.getChannel().lock()) {
			fos.write(linea.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
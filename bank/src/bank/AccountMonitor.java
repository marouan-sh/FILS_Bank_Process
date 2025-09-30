package bank;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bank.BankProcess.Operation;

public class AccountMonitor {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final String CURRENCY = "EUR";

	private String fileName;

	public AccountMonitor(String fileName) {
		this.fileName = fileName;
	}

	// Write operation to the account statement
	public void writeOperation(Operation operation, String concept, double amount) {
		//TODO Implement file locking to prevent concurrent write issues
		//TODO Format the entry as: date time | operation sign amount currency | concept
		//TODO Append the entry to the file
	}
}

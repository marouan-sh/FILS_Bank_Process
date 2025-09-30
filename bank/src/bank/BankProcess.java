package bank;

public class BankProcess {
	public enum Operation {
		DEPOSIT, WITHDRAW;

		String sign() {
			return DEPOSIT.equals(this) ? "+" : "-";
		}
	}

	private static final String STATEMENT_FILE = "statement.txt";

	public static void main(String[] args) {

		//TODO Implement the main process loop
		//TODO Use ProcessInputUtils to read user input
		//TODO Use AccountMonitor to write operations to the statement file
		//TODO Handle exit condition gracefully
		
		System.out.println("Bank process finished.");
	}
}

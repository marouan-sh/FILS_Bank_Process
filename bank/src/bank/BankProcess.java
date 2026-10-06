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
		AccountMonitor monitor = new AccountMonitor(STATEMENT_FILE);

		System.out.println("Bienvenido a la oficina : " + ProcessHandle.current().pid());

		boolean seguir = true;
		while (seguir) {
			Operation operation = ProcessInputUtils.readOperationMenu();
			if (operation == null) {
				break;
			}

			String concept = ProcessInputUtils.readConcept();
			double amount = ProcessInputUtils.readAmount();

			monitor.writeOperation(operation, concept, amount);

			seguir = ProcessInputUtils.confirmAnotherOperation();
		}

		System.out.println("Bank process finished.");
	}
}
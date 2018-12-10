package project.wip.androidclient;

import java.util.List;

/**
 * Account is the class which contains all information for creating an account object
 * according to the output of the banking server. The class helps to create objects out of the gson-
 * data given from the API.
 * @author Sebastian Rieck
 */
public class Account {

    private int id;
	private String owner;
	private String number;
	private List<Transaction> transactions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}

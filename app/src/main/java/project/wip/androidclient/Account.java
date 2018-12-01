package project.wip.androidclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Account {

	private int id;
	private String owner;
	private String number;
	private List<Transaction> transactions;

	public Account(int id, String owner, String number, List<Transaction> transactions) {
		this.id = id;
		this.owner = owner;
		this.number = number;
		this.transactions = transactions;
	}

	public Account(){ }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}

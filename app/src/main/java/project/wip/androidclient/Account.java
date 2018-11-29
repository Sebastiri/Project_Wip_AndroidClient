package project.wip.androidclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Account //implements Parcelable
        {
	private int id;
	private String owner;
	private String number;
	private List<Transaction> transactions;

/*	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(owner);
		out.writeString(number);
		out.writeList(transactions);
	}

	public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
		public Account createFromParcel(Parcel in) {
			return new Account(in);
		}

		public Account[] newArray(int size) {
			return new Account[size];
		}
	};

	private Account(Parcel in) {
		id = in.readInt();
		owner = in.readString();
		number = in.readString();
		transactions = in.readList(transactions, null);
	}*/


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

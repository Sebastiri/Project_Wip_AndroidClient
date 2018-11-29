package project.wip.androidclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


public class Transaction //implements Parcelable
         {
	private int id;
	private Account sender;
	private Account receiver;
	private BigDecimal amount;
	private String reference;
	private Date transactionDate;

	/*@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(id);
		out.writeString(amount.toString());
		out.writeString(reference);
		out.writeString(transactionDate.toString());
	}

	public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
		public Transaction createFromParcel(Parcel in) {
			return new Transaction(in);
		}

		public Transaction[] newArray(int size) {
			return new Transaction[size];
		}
	};

	private Transaction(Parcel in) {
		id = in.readInt();
		amount = in.readString();
		reference = in.readString();
		transactionDate = (Date) in.readString();
	}*/

	public String getDayFromDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(transactionDate);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        return sb.toString();
    }

    public String getMonthFromDate(){
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(transactionDate);
	    int month = cal.get(Calendar.MONTH);
	    StringBuilder sb = new StringBuilder();
	    sb.append(month);
        return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getSender() {
		return sender;
	}

	public void setSender(Account sender) {
		this.sender = sender;
	}

	public Account getReceiver() {
		return receiver;
	}

	public void setReceiver(Account receiver) {
		this.receiver = receiver;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}

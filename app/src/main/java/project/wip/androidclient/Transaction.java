package project.wip.androidclient;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Transaction //implements Parcelable
         {
	private int id;
	private Account sender;
	private Account receiver;
	private BigDecimal amount;
	private String reference;
	private Date transactionDate;

	public String getDayFromDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(transactionDate);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String result = String.valueOf(day);
        if(result.length() == 1){
            result = String.format("0%s",result);
        }
        return result;
    }

    public String getMonthFromDate(){
		SimpleDateFormat formatter = new SimpleDateFormat("MMM",Locale.GERMAN);
		return formatter.format(transactionDate).substring(0,3);
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

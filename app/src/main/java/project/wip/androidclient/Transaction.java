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
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(transactionDate);
	    int month = cal.get(Calendar.MONTH);
	    String result;
		switch(month) {
			case 1:
				result = "Jan";
				break;
			case 2:
				result = "Feb";
				break;
			case 3:
				result = "Mär";
				break;
			case 4:
				result = "Apr";
				break;
			case 5:
				result = "Mai";
				break;
			case 6:
				result = "Jun";
				break;
			case 7:
				result = "Jul";
				break;
			case 8:
				result = "Aug";
				break;
			case 9:
				result = "Sep";
				break;
			case 10:
				result = "Okt";
				break;
			case 11:
				result = "Nov";
				break;
			case 12:
				result = "Dez";
				break;
				default: result = null;
		}
        return result;
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

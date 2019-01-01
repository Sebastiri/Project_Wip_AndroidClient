package project.wip.androidclient;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The class Transaction contains the information received by the server for one transaction.
 * @author Sebastian Rieck
 */
public class Transaction {
	private int id;
	private Account sender;
	private Account receiver;
	private BigDecimal amount;
	private String reference;
	private Date transactionDate;

    /**
     * Getter method for the day of month from the transaction.
     * @return the day of month
     * @author Sebastian Rieck
     */
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

    /**
     * Getter method for the name of month as three initials.
     * @return the month in a pattern of three initials.
     */
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

    public Account getReceiver() {
		return receiver;
	}

    public BigDecimal getAmount() {
		return amount;
	}

    public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}

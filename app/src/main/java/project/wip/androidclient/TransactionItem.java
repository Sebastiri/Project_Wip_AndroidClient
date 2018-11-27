package project.wip.androidclient;

public class TransactionItem {
    private String day;
    private  String month;
    private String name;
    private String reference;
    private String amount;

    public TransactionItem(String day, String month, String name, String reference, String amount){
        this.day = day;
        this.month = month;
        this.name = name;
        this.reference = reference;
        this.amount = amount;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getName() {
        return name;
    }

    public String getReference() {
        return reference;
    }

    public String getAmount() {
        return amount;
    }
}
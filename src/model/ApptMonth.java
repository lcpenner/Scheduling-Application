package model;

/**This class is the model for the appointment month report. */
public class ApptMonth {

    private String apptMonth;
    private int apptMonthTotal;
    private int monthNumber;

    /**This method is the constructor for the appointment month. */
    public ApptMonth(String apptMonth, int apptMonthTotal, int monthNumber) {
        this.apptMonth = apptMonth;
        this.apptMonthTotal = apptMonthTotal;
        this.monthNumber = monthNumber;
    }

    public String getApptMonth() {
        return apptMonth;
    }

    public void setApptMonth(String apptMonth) {
        this.apptMonth = apptMonth;
    }

    public int getApptMonthTotal() {
        return apptMonthTotal;
    }

    public void setApptMonthTotal(int apptMonthTotal) {
        this.apptMonthTotal = apptMonthTotal;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }
}

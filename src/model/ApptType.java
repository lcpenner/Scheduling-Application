package model;

/**This class is the model for the appointment type report. */
public class ApptType {

    private String apptType;
    private int apptTotal;

    /**This method is the constructor for the appointment type. */
    public ApptType(String apptType, int apptTotal) {
        this.apptType = apptType;
        this.apptTotal = apptTotal;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public int getApptTotal() {
        return apptTotal;
    }

    public void setApptTotal(int apptTotal) {
        this.apptTotal = apptTotal;
    }
}

package lbt.com.manager.Models.Firebase;

public class objmaytinhs {
    boolean consudung;
    String mamay;
    String tenmay;

    public objmaytinhs(boolean consudung, String mamay, String tenmay) {
        this.consudung = consudung;
        this.mamay = mamay;
        this.tenmay = tenmay;
    }

    public objmaytinhs() {
    }

    public boolean isConsudung() {
        return consudung;
    }

    public void setConsudung(boolean consudung) {
        this.consudung = consudung;
    }

    public String getMamay() {
        return mamay;
    }

    public void setMamay(String mamay) {
        this.mamay = mamay;
    }

    public String getTenmay() {
        return tenmay;
    }

    public void setTenmay(String tenmay) {
        this.tenmay = tenmay;
    }
}

package lbt.com.manager.Models.Firebase;

public class objlichsucapnhatthietbikhac {
    objthietbikhacs thietbinew;
    objthietbikhacs thietbiold;
    String emailnguoicapnhat;
    String loai;
    long ngaycapnhat;

    public objlichsucapnhatthietbikhac(objthietbikhacs thietbinew, objthietbikhacs thietbiold, String emailnguoicapnhat, String loai, long ngaycapnhat) {
        this.thietbinew = thietbinew;
        this.thietbiold = thietbiold;
        this.emailnguoicapnhat = emailnguoicapnhat;
        this.loai = loai;
        this.ngaycapnhat = ngaycapnhat;
    }

    public objlichsucapnhatthietbikhac() {
    }

    public objthietbikhacs getThietbinew() {
        return thietbinew;
    }

    public void setThietbinew(objthietbikhacs thietbinew) {
        this.thietbinew = thietbinew;
    }

    public objthietbikhacs getThietbiold() {
        return thietbiold;
    }

    public void setThietbiold(objthietbikhacs thietbiold) {
        this.thietbiold = thietbiold;
    }

    public String getEmailnguoicapnhat() {
        return emailnguoicapnhat;
    }

    public void setEmailnguoicapnhat(String emailnguoicapnhat) {
        this.emailnguoicapnhat = emailnguoicapnhat;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public long getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(long ngaycapnhat) {
        this.ngaycapnhat = ngaycapnhat;
    }
}

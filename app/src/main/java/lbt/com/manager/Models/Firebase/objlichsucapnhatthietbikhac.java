package lbt.com.manager.Models.Firebase;

public class objlichsucapnhatthietbikhac {
    objthietbikhacs chitiet;
    String emailnguoicapnhat;
    String loai;
    long ngaycapnhat;

    public objlichsucapnhatthietbikhac(objthietbikhacs chitiet, String emailnguoicapnhat, String loai, long ngaycapnhat) {
        this.chitiet = chitiet;
        this.emailnguoicapnhat = emailnguoicapnhat;
        this.loai = loai;
        this.ngaycapnhat = ngaycapnhat;
    }

    public objlichsucapnhatthietbikhac() {
    }

    public objthietbikhacs getChitiet() {
        return chitiet;
    }

    public void setChitiet(objthietbikhacs chitiet) {
        this.chitiet = chitiet;
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

package lbt.com.manager.Models.Firebase;

import java.util.List;

public class objlichsucapnhatmaytinh {
    List<String> chitiet;
    String emailnguoicapnhat;
    long ngaycapnhat;
    String loai;

    public objlichsucapnhatmaytinh(List<String> chitiet, String emailnguoicapnhat, long ngaycapnhat, String loai) {
        this.chitiet = chitiet;
        this.emailnguoicapnhat = emailnguoicapnhat;
        this.ngaycapnhat = ngaycapnhat;
        this.loai = loai;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public objlichsucapnhatmaytinh() {
    }

    public List<String> getChitiet() {
        return chitiet;
    }

    public void setChitiet(List<String> chitiet) {
        this.chitiet = chitiet;
    }

    public String getEmailnguoicapnhat() {
        return emailnguoicapnhat;
    }

    public void setEmailnguoicapnhat(String emailnguoicapnhat) {
        this.emailnguoicapnhat = emailnguoicapnhat;
    }

    public long getNgaycapnhat() {
        return ngaycapnhat;
    }

    public void setNgaycapnhat(long ngaycapnhat) {
        this.ngaycapnhat = ngaycapnhat;
    }
}

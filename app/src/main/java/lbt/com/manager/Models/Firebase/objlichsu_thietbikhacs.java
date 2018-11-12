package lbt.com.manager.Models.Firebase;

public class objlichsu_thietbikhacs {
    objchitietsuachua chitietsuachua;
    boolean dasuachua;
    String emailnguoibaocao;
    long ngaybaocao;

    public objlichsu_thietbikhacs(objchitietsuachua chitietsuachua, boolean dasuachua, String emailnguoibaocao, long ngaybaocao) {
        this.chitietsuachua = chitietsuachua;
        this.dasuachua = dasuachua;
        this.emailnguoibaocao = emailnguoibaocao;
        this.ngaybaocao = ngaybaocao;
    }

    public objlichsu_thietbikhacs() {
    }

    public objchitietsuachua getChitietsuachua() {
        return chitietsuachua;
    }

    public void setChitietsuachua(objchitietsuachua chitietsuachua) {
        this.chitietsuachua = chitietsuachua;
    }

    public boolean isDasuachua() {
        return dasuachua;
    }

    public void setDasuachua(boolean dasuachua) {
        this.dasuachua = dasuachua;
    }

    public String getEmailnguoibaocao() {
        return emailnguoibaocao;
    }

    public void setEmailnguoibaocao(String emailnguoibaocao) {
        this.emailnguoibaocao = emailnguoibaocao;
    }

    public long getNgaybaocao() {
        return ngaybaocao;
    }

    public void setNgaybaocao(long ngaybaocao) {
        this.ngaybaocao = ngaybaocao;
    }


}

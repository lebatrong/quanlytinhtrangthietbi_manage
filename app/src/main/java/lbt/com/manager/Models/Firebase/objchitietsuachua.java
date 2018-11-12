package lbt.com.manager.Models.Firebase;

public class objchitietsuachua {
    objthietbikhacs default_thietbi, thietbihu;

    public objchitietsuachua(objthietbikhacs default_thietbi, objthietbikhacs thietbihu) {
        this.default_thietbi = default_thietbi;
        this.thietbihu = thietbihu;
    }

    public objchitietsuachua() {
    }

    public objthietbikhacs getDefault_thietbi() {
        return default_thietbi;
    }

    public void setDefault_thietbi(objthietbikhacs default_thietbi) {
        this.default_thietbi = default_thietbi;
    }

    public objthietbikhacs getThietbihu() {
        return thietbihu;
    }

    public void setThietbihu(objthietbikhacs thietbihu) {
        this.thietbihu = thietbihu;
    }
}
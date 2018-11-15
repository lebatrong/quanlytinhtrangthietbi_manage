package lbt.com.manager.Models.App;

import java.util.List;

import lbt.com.manager.Models.Firebase.objmaytinhs;
import lbt.com.manager.Models.Firebase.objthietbikhacs;

public class objthietbiphongmay_app {

    String maphong;
    List<objmaytinhs> maytinh;
    objthietbikhacs thietbikhacs;

    public objthietbiphongmay_app(String maphong, List<objmaytinhs> maytinh, objthietbikhacs thietbikhacs) {
        this.maphong = maphong;
        this.maytinh = maytinh;
        this.thietbikhacs = thietbikhacs;
    }

    public objthietbiphongmay_app() {
    }

    public String getMaphong() {
        return maphong;
    }

    public void setMaphong(String maphong) {
        this.maphong = maphong;
    }

    public List<objmaytinhs> getMaytinh() {
        return maytinh;
    }

    public void setMaytinh(List<objmaytinhs> maytinh) {
        this.maytinh = maytinh;
    }

    public objthietbikhacs getThietbikhacs() {
        return thietbikhacs;
    }

    public void setThietbikhacs(objthietbikhacs thietbikhacs) {
        this.thietbikhacs = thietbikhacs;
    }

}

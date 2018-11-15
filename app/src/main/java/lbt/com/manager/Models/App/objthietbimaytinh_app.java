package lbt.com.manager.Models.App;


import android.content.ClipData;

import lbt.com.manager.Models.Firebase.objlichsu_maytinhs;
import lbt.com.manager.Models.Firebase.objmaytinhs;

public class objthietbimaytinh_app {
    objmaytinhs thietbi;
    objlichsu_maytinhs lichsusuachua;



    public objthietbimaytinh_app() {
    }

    public objthietbimaytinh_app(objmaytinhs thietbi, objlichsu_maytinhs lichsusuachua) {
        this.thietbi = thietbi;
        this.lichsusuachua = lichsusuachua;
    }

    public objmaytinhs getThietbi() {
        return thietbi;
    }

    public void setThietbi(objmaytinhs thietbi) {
        this.thietbi = thietbi;
    }

    public objlichsu_maytinhs getLichsusuachua() {
        return lichsusuachua;
    }

    public void setLichsusuachua(objlichsu_maytinhs lichsusuachua) {
        this.lichsusuachua = lichsusuachua;
    }


}

package lbt.com.manager.Presenter;

import java.util.List;

import lbt.com.manager.Models.App.objThietBi;
import lbt.com.manager.Models.Firebase.objlichsu_thietbikhacs;

public interface iThongBao {
    void loiduongtruyen();
    void pushthongbao(List<objThietBi> thietBi, objlichsu_thietbikhacs mThietBiKhac );
    void phongmayhoatdongtot();
}

package lbt.com.manager.Presenter;

import java.util.List;

import lbt.com.manager.Models.App.objLichSu;
import lbt.com.manager.Models.Firebase.objlichsucapnhatmaytinh;
import lbt.com.manager.Models.Firebase.objlichsucapnhatthietbikhac;

public interface iLichSu {
    void loidulieu();
    void lichsutrong();
    void getlistlichsu(List<objLichSu> list);
    void getListLichSuCapNhatMayTinh(List<objlichsucapnhatmaytinh> list);
    void getListLichSuCapNhatThietBiKhac(List<objlichsucapnhatthietbikhac> list);
}

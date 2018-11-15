package lbt.com.manager.Presenter;

import java.util.List;

import lbt.com.manager.Models.Firebase.objmaytinhs;

public interface icapnhatmaytinh {
    void result_addmaytinh(boolean isSuccess);
    void result_lichsucapnhatmaytinh(boolean isSuccess);
    void danhsachmaytinh(List<objmaytinhs> mamay);
    void loitaidanhsachmay();
    void ketquadelete(boolean isSuccess);
    void ketquacapnhat(boolean isSuccess);
}

package lbt.com.manager.Models.App;

import lbt.com.manager.Models.Firebase.objmaytinhs;

public class objmaytinh_app {
    objmaytinhs maytinh;
    boolean Checked;

    public objmaytinh_app(objmaytinhs maytinh, boolean checked) {
        this.maytinh = maytinh;
        Checked = checked;
    }

    public objmaytinh_app(){}

    public objmaytinhs getMaytinh() {
        return maytinh;
    }

    public void setMaytinh(objmaytinhs maytinh) {
        this.maytinh = maytinh;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }
}

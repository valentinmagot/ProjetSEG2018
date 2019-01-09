package collectifyamoukoudji.projetseg;

import java.util.ArrayList;

public class Horraire {

    private ArrayList<ArrayList<Boolean>> _array;
    private String _id;
    private boolean _flag;

    public Horraire(){
        _id = "";
        _array = new ArrayList<>(12);
        for (int i = 0; i < 12 ; i++) {
            ArrayList<Boolean> tmp = new ArrayList<>(7);
            for (int j = 0; j < 7; j++) {
                tmp.add(false);
            }
            _array.add(tmp);
        }
        _flag = false;
    }

    public Horraire(String id, boolean flag, ArrayList<ArrayList<Boolean>> _array) {
        this._array = _array;
        this._id = id;
        this._flag = flag;
    }

    public ArrayList<ArrayList<Boolean>> get_array() {
        return _array;
    }

    public void set_array(ArrayList<ArrayList<Boolean>> _array) {
        this._array = _array;
    }

    public boolean is_flag() {
        return _flag;
    }

    public void set_flag(boolean _flag) {
        this._flag = _flag;
    }
}


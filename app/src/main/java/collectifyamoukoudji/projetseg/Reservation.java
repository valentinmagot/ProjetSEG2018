package collectifyamoukoudji.projetseg;

import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private String _id;
    private String _organisationName;
    private String _fournisseurName;
    private String _clientname;
    private long rDate;
    private String type_service;
    private int timeslt;
    public void setTimeslt(int timeslt) {
        this.timeslt = timeslt;
    }


    public int getTimeslt() {
        return timeslt;
    }




    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_organisationName() {
        return _organisationName;
    }

    public void set_organisationName(String _organisationName) {
        this._organisationName = _organisationName;
    }

    public String get_clientname() {
        return _clientname;
    }

    public void set_clientname(String _clientname) {
        this._clientname = _clientname;
    }

    public long getrDate() {
        return rDate;
    }

    public void setrDate(long rDate) {
        this.rDate = rDate;
    }
    public Reservation(){
        this._id = "";
        this._organisationName = "";
        this._clientname = "";
        this.rDate = 0;
        this.type_service = "";
        this._fournisseurName = "";
        this.timeslt= 0;
    }
    public Reservation(String _id, String _organisationName, String _fournisseurName, String _clientname,
                       long d, String type_service,int timeslt){
        this._id = _id;
        this._organisationName = _organisationName;
        this._clientname = _clientname;
        this.rDate = d;
        this.type_service = type_service;
        this._fournisseurName = _fournisseurName;
        this.timeslt = timeslt;
    }

    public String getType_service() {
        return type_service;
    }

    public void setType_service(String type_service) {
        this.type_service = type_service;
    }

    public String get_fournisseurName() {
        return _fournisseurName;
    }

    public void set_fournisseurName(String _fournisseurName) {
        this._fournisseurName = _fournisseurName;
    }

}
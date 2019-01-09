package collectifyamoukoudji.projetseg;

public class Service {
    /**
     * Contains a String type of value
     * that is assigned for the id of the service.
     */
    private String _id;
    /**
     * Contains a String type of value
     * that is assigned for the servicename of the service.
     */
    private String _servicename;
    /**
     * Contains a String type of value
     * that is assigned for the rate of the service.
     */
    private double _rate;
    /**
     * Constructor
     */
    public Service() {}
    /**
     * Constructor
     */
    public Service(String id, String servicename, double rate) {
        _id = id;
        _servicename = servicename;
        _rate = rate;
    }
    /**
     * Constructor
     */
    public Service(String servicename, double rate) {
        _servicename = servicename;
        _rate = rate;
    }
    //Instance methods **************************************************
    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setServiceName(String servicename) {
        _servicename = servicename;
    }
    public String getServiceName() {
        return _servicename;
    }
    public void setRate(double rate) {
        _rate = rate;
    }
    public double getRate() {
        return _rate;
    }
}
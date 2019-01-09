package collectifyamoukoudji.projetseg;

import java.util.ArrayList;

public class Organisation {

    private String _id;
    private String _organisationName;
    private String _organisationDescription;
    private Boolean _isLiscenced;
    private Address _organisationAddress;

    private Horraire _organisationHorraire;
    private ArrayList<Service> _services;
    private ArrayList<Double> _rating;



    /////////////////////////////////////////////////////
    private ArrayList<Reservation> reservations;
    /////////////////////////////////////////////////////




    public Organisation() {
        this._id = "";
        this._organisationName = "";
        this._organisationDescription = "";
        this._rating = new ArrayList<>();
        this._isLiscenced = false;
        this._organisationAddress = new Address();
        this._services = new ArrayList<Service>();
        this._organisationHorraire = new Horraire();

        this.reservations = new ArrayList<Reservation>();

    }



    public Organisation(String id, String name, String description, boolean isLiscenced, Address address, ArrayList<Service> list, Horraire h, ArrayList<Double> rating, ArrayList<Reservation> resa) {
        this._id = id;
        this._organisationName = name;
        this._organisationDescription = description;
        this._isLiscenced = isLiscenced;
        this._organisationAddress = address;
        this._services = list;
        this._organisationHorraire = h;
        this._rating = rating;
        this.reservations = resa;

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

    public String get_organisationDescription() {
        return _organisationDescription;
    }

    public void set_organisationDescription(String _organisationDescription) {
        this._organisationDescription = _organisationDescription;
    }

    public Boolean get_isLiscenced() {
        return _isLiscenced;
    }

    public void set_isLiscenced(Boolean _isLiscenced) {
        this._isLiscenced = _isLiscenced;
    }

    public Address get_organisationAddress() {
        return _organisationAddress;
    }

    public void set_organisationAddress(Address _organisationAddress) {
        this._organisationAddress = _organisationAddress;
    }

    public ArrayList<Service> get_services() {
        return _services;
    }

    public void set_services(ArrayList<Service> _services) {
        this._services = _services;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "_id='" + _id + '\'' +
                ", _organisationName='" + _organisationName + '\'' +
                ", _organisationDescription='" + _organisationDescription + '\'' +
                ", _isLiscenced=" + _isLiscenced +
                ", _organisationAddress=" + _organisationAddress +
                ", _organisationHorraire=" + _organisationHorraire +
                ", _services=" + _services +
                ", _rating=" + _rating +


                ///////////////////////////////////
                ", reservations=" + reservations +
                ///////////////////////////////////


                '}';
    }

    public ArrayList<Double> get_rating() {
        return _rating;
    }

    public void set_rating(ArrayList<Double> _rating) {
        this._rating = _rating;
    }

    public Horraire get_organisationHorraire() {
        return _organisationHorraire;
    }

    public void set_organisationHorraire(Horraire _organisationHorraire) {
        this._organisationHorraire = _organisationHorraire;
    }



    ////////////////////////////////////////////////////////

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    ///////////////////////////////////////////////////////
}
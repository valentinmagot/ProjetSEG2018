package collectifyamoukoudji.projetseg;

import static org.junit.Assert.*;
import org.junit.Test;

public class AddressTest {

    Address address = new Address( "id","209", "Yamakoudji street", "K2N 9I8", "Ottawa",
            "Canada", "654374833","shop@shop.com", "shop.com");

    @Test
    public void check_ID(){
        assertEquals("check the address id", "id", address.get_id());
        System.out.println("ID --> OK");
    }

    @Test
    public void check_numero(){
        assertEquals("check the address number", "209", address.get_num());
        System.out.println("Number --> OK");
    }

    @Test
    public void check_streetName(){
        assertEquals("check the address streetname", "Yamakoudji street", address.get_sname());
        System.out.println("Street name --> OK");
    }

    @Test
    public void check_postalCode(){
        assertEquals("check the address postal code", "K2N 9I8", address.get_pcode());
        System.out.println("Postal code --> OK");
    }

    @Test
    public void check_city(){
        assertEquals("check the address City", "Ottawa", address.get_city());
        System.out.println("City --> OK");
    }

    @Test
    public void check_country(){
        assertEquals("check the address country", "Canada", address.get_country());
        System.out.println("Country --> OK");
    }

    @Test
    public void check_phoneNumb(){
        assertEquals("check the phone number", "654374833", address.get_phonenum());
        System.out.println("Phone number --> OK");
    }

    @Test
    public void check_shopMail(){
        assertEquals("check the shop mail", "shop@shop.com", address.get_shopemail());
        System.out.println("shop mail --> OK");
    }

    @Test
    public void check_web(){
        assertEquals("check the web site", "shop.com", address.get_website());
        System.out.println("Web site --> OK");
    }

}

package collectifyamoukoudji.projetseg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
    @Test
    public void checkServiceName() {
        Service service = new Service("1", "Reparation", 180);
        assertEquals("Check the name of the product", "Reparation", service.getServiceName());
    }


}

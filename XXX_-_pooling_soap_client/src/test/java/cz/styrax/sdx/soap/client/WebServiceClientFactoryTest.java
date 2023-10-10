package cz.styrax.sdx.soap.client;

import com.thomas_bayer.blz.BLZServicePortType;
import cz.styrax.sdx.soap.client.factory.WebServiceClientFactoryImpl;
import javax.xml.ws.Service;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author tomas.cejka
 */
public class WebServiceClientFactoryTest {

    private WebServiceClientFactoryImpl tested;
    private static final String WSDL_URL = "http://www.thomas-bayer.com/axis2/services/BLZService?wsdl";

    @Before
    public void setUp() {
        tested = new WebServiceClientFactoryImpl();
    }

    @Test
    public void testInfo() {
        tested.showWsdlApiInfo(WSDL_URL);
    }

    @Test
    public void testCreateService() throws Exception {
        Service service = tested.createService(WSDL_URL);
        Assert.assertNotNull(service);
    }

    @Test
    public void testCreatePort() throws Exception {
        BLZServicePortType port = tested.createPort(WSDL_URL, BLZServicePortType.class);
        Assert.assertNotNull(port);
    }
}

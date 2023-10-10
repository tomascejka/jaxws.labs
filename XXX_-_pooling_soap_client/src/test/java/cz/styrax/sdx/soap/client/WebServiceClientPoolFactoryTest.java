package cz.styrax.sdx.soap.client;

import com.thomas_bayer.blz.BLZServicePortType;
import cz.styrax.sdx.soap.client.factory.WebServiceClientFactoryImpl;
import cz.styrax.sdx.soap.client.pool.WebServiceClientPoolFactory;
import java.util.Date;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author tomas.cejka
 */
public class WebServiceClientPoolFactoryTest {

    private WebServiceClientFactoryImpl clientFactory;
    //private static final String WSDL_URL = "http://www.thomas-bayer.com/axis2/services/BLZService?wsdl";
    private final String WSDL_URL = "http://localhost:8088/mockBLZServiceSOAP12Binding?WSDL";//local soapui
    private final String MS = " ms";
    private final String BANK_CODE = "37050198";
    private final int ATTEMPTS = 10;

    private GenericObjectPool<BLZServicePortType> tested;

    @Before
    public void setUp() {
        clientFactory = new WebServiceClientFactoryImpl();
        tested = new GenericObjectPool<>(new WebServiceClientPoolFactory<>(clientFactory, WSDL_URL, BLZServicePortType.class));
    }

    @After
    public void tearDown() {
        tested.clear();
        tested.close();
    }

    @Test
    public void testNonPooled() throws Exception {
        BLZServicePortType port;
        for (int i = 0; i < ATTEMPTS; i++) {
            port = clientFactory.createPort(WSDL_URL, BLZServicePortType.class);
            long start = new Date().getTime();
            port.getBank(BANK_CODE);//response me nezajima
            System.out.println(i + ".(np): " + (new Date().getTime() - start) + MS);
        }
    }

    @Test
    public void testPooled() throws Exception {
        BLZServicePortType port;
        for (int i = 0; i < ATTEMPTS; i++) {
            port = tested.borrowObject();
            long start = new Date().getTime();
            port.getBank(BANK_CODE);//response me nezajima
            System.out.println(i + ".(po): " + (new Date().getTime() - start) + MS);
        }
    }
}

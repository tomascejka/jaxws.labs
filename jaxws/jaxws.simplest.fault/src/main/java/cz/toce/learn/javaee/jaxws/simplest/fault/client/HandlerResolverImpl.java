package cz.toce.learn.javaee.jaxws.simplest.fault.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * @author tomas.cejka
 */
public class HandlerResolverImpl implements HandlerResolver {

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
          List<Handler> handlerChain = new ArrayList<>();
          handlerChain.add(new BasicSoapHandler());
          return handlerChain;
    }
    
}

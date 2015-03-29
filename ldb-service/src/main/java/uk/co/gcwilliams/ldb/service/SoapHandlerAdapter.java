package uk.co.gcwilliams.ldb.service;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import java.util.Set;

/**
 * The SOAP handler adapter
 *
 * @author Gareth Williams
 */
class SoapHandlerAdapter implements SOAPHandler {

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(MessageContext context) {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) { }
}

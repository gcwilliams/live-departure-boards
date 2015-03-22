package uk.co.gcwilliams.ldb.service;

import uk.co.gcwilliams.ldb.stubs.AccessToken;
import uk.co.gcwilliams.ldb.stubs.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * The authentication handler, this sets the authentication token
 * on the soap request
 *
 * @author Gareth Williams (466567)
 */
class AuthenticationHandler extends SoapHandlerAdapter {

    private static final JAXBContext JAXB_CONTEXT;

    private final AccessToken accessToken;

    static {
        try {
            JAXB_CONTEXT = JAXBContext.newInstance(AccessToken.class);
        } catch (JAXBException ex) {
            throw new AuthenticationHandlerRuntimeException(ex);
        }
    }

    /**
     * Default constructor
     *
     * @param accessToken The access token
     */
    AuthenticationHandler(String accessToken) {
        this.accessToken = new AccessToken();
        this.accessToken.setTokenValue(accessToken);
    }

    @Override
    public boolean handleMessage(MessageContext context) {

        boolean successful = false;

        Boolean outbound = (Boolean)context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (context instanceof SOAPMessageContext && Boolean.TRUE.equals(outbound)) {

            SOAPMessageContext soap = (SOAPMessageContext) context;

            try {

                SOAPHeader header = soap.getMessage().getSOAPPart().getEnvelope().getHeader();
                final Marshaller marshaller = JAXB_CONTEXT.createMarshaller();
                marshaller.marshal(new ObjectFactory().createAccessToken(accessToken), header);
                soap.getMessage().saveChanges();
                successful = true;

            } catch (SOAPException | JAXBException ex) {
                throw new AuthenticationHandlerRuntimeException(ex);
            }
        }

        return successful;
    }
}

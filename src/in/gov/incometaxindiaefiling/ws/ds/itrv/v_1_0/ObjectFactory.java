
package in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ItrvTokenNoRequestEle_QNAME = new QName("http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", "itrvTokenNoRequestEle");
    private final static QName _ItrvAckNoRequestEle_QNAME = new QName("http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", "itrvAckNoRequestEle");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ITRVByAckNoRequest }
     * 
     */
    public ITRVByAckNoRequest createITRVByAckNoRequest() {
        return new ITRVByAckNoRequest();
    }

    /**
     * Create an instance of {@link ITRVByTokenNoRequest }
     * 
     */
    public ITRVByTokenNoRequest createITRVByTokenNoRequest() {
        return new ITRVByTokenNoRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRVByTokenNoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", name = "itrvTokenNoRequestEle")
    public JAXBElement<ITRVByTokenNoRequest> createItrvTokenNoRequestEle(ITRVByTokenNoRequest value) {
        return new JAXBElement<ITRVByTokenNoRequest>(_ItrvTokenNoRequestEle_QNAME, ITRVByTokenNoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRVByAckNoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", name = "itrvAckNoRequestEle")
    public JAXBElement<ITRVByAckNoRequest> createItrvAckNoRequestEle(ITRVByAckNoRequest value) {
        return new JAXBElement<ITRVByAckNoRequest>(_ItrvAckNoRequestEle_QNAME, ITRVByAckNoRequest.class, null, value);
    }

}


package in.gov.incometaxindiaefiling.itrservice.v_1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRBusinessServiceFaultException;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRFaultException;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRInvalidCertificateFaultException;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRInvalidDocFaultException;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRServiceFaultException;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the in.gov.incometaxindiaefiling.itrservice.v_1_0 package. 
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

    private final static QName _ITRInvalidCertificateFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRInvalidCertificateFaultException");
    private final static QName _ITRCredentialFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRCredentialFaultException");
    private final static QName _ITRServiceFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRServiceFaultException");
    private final static QName _ITRBusinessServiceFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRBusinessServiceFaultException");
    private final static QName _ITRFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRFaultException");
    private final static QName _ITRInvalidDocFaultException_QNAME = new QName("http://incometaxindiaefiling.gov.in/itrservice/v_1_0", "ITRInvalidDocFaultException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: in.gov.incometaxindiaefiling.itrservice.v_1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRInvalidCertificateFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRInvalidCertificateFaultException")
    public JAXBElement<ITRInvalidCertificateFaultException> createITRInvalidCertificateFaultException(ITRInvalidCertificateFaultException value) {
        return new JAXBElement<ITRInvalidCertificateFaultException>(_ITRInvalidCertificateFaultException_QNAME, ITRInvalidCertificateFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRCredentialFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRCredentialFaultException")
    public JAXBElement<ITRCredentialFaultException> createITRCredentialFaultException(ITRCredentialFaultException value) {
        return new JAXBElement<ITRCredentialFaultException>(_ITRCredentialFaultException_QNAME, ITRCredentialFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRServiceFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRServiceFaultException")
    public JAXBElement<ITRServiceFaultException> createITRServiceFaultException(ITRServiceFaultException value) {
        return new JAXBElement<ITRServiceFaultException>(_ITRServiceFaultException_QNAME, ITRServiceFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRBusinessServiceFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRBusinessServiceFaultException")
    public JAXBElement<ITRBusinessServiceFaultException> createITRBusinessServiceFaultException(ITRBusinessServiceFaultException value) {
        return new JAXBElement<ITRBusinessServiceFaultException>(_ITRBusinessServiceFaultException_QNAME, ITRBusinessServiceFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRFaultException")
    public JAXBElement<ITRFaultException> createITRFaultException(ITRFaultException value) {
        return new JAXBElement<ITRFaultException>(_ITRFaultException_QNAME, ITRFaultException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ITRInvalidDocFaultException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0", name = "ITRInvalidDocFaultException")
    public JAXBElement<ITRInvalidDocFaultException> createITRInvalidDocFaultException(ITRInvalidDocFaultException value) {
        return new JAXBElement<ITRInvalidDocFaultException>(_ITRInvalidDocFaultException_QNAME, ITRInvalidDocFaultException.class, null, value);
    }

}

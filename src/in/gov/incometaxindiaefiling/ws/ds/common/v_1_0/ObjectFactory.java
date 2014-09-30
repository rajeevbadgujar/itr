
package in.gov.incometaxindiaefiling.ws.ds.common.v_1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the in.gov.incometaxindiaefiling.ws.ds.common.v_1_0 package. 
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

    private final static QName _DITWSAuthInfoEle_QNAME = new QName("http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", "DITWSAuthInfoEle");
    private final static QName _DITWSResponseEle_QNAME = new QName("http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", "DITWSResponseEle");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: in.gov.incometaxindiaefiling.ws.ds.common.v_1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DITWSResponse }
     * 
     */
    public DITWSResponse createDITWSResponse() {
        return new DITWSResponse();
    }

    /**
     * Create an instance of {@link DITWSAuthInfo }
     * 
     */
    public DITWSAuthInfo createDITWSAuthInfo() {
        return new DITWSAuthInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DITWSAuthInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", name = "DITWSAuthInfoEle")
    public JAXBElement<DITWSAuthInfo> createDITWSAuthInfoEle(DITWSAuthInfo value) {
        return new JAXBElement<DITWSAuthInfo>(_DITWSAuthInfoEle_QNAME, DITWSAuthInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DITWSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", name = "DITWSResponseEle")
    public JAXBElement<DITWSResponse> createDITWSResponseEle(DITWSResponse value) {
        return new JAXBElement<DITWSResponse>(_DITWSResponseEle_QNAME, DITWSResponse.class, null, value);
    }

}

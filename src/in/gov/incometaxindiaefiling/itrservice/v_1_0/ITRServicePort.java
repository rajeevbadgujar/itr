
package in.gov.incometaxindiaefiling.itrservice.v_1_0;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ITRServicePort", targetNamespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    in.gov.incometaxindiaefiling.itrservice.v_1_0.ObjectFactory.class,
    in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.ObjectFactory.class,
    in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ObjectFactory.class
})
public interface ITRServicePort {


    /**
     * 
     * @param authInfo
     * @param itrXMLFile
     * @return
     *     returns in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse
     * @throws ITRBusinessServiceFaultException
     * @throws ITRCredentialFaultException
     * @throws ITRFaultException
     * @throws ITRInvalidCertificateFaultException
     * @throws ITRInvalidDocFaultException
     * @throws ITRServiceFaultException
     */
    @WebMethod(action = "http://incometaxindiaefiling.gov.in/submitITReturn")
    @WebResult(name = "DITWSResponseEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", partName = "itrStatus")
    public DITWSResponse submitITR(
        @WebParam(name = "DITWSAuthInfoEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", partName = "authInfo")
        DITWSAuthInfo authInfo,
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @WebParam(name = "itrXMLFile", targetNamespace = "", partName = "itrXMLFile")
        byte[] itrXMLFile)
        throws ITRBusinessServiceFaultException, ITRCredentialFaultException, ITRFaultException, ITRInvalidCertificateFaultException, ITRInvalidDocFaultException, ITRServiceFaultException
    ;

}

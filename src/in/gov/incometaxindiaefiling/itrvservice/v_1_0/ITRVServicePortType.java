
package in.gov.incometaxindiaefiling.itrvservice.v_1_0;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse;
import in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0.ITRVByAckNoRequest;
import in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0.ITRVByTokenNoRequest;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ITRVServicePortType", targetNamespace = "http://incometaxindiaefiling.gov.in/itrvservice/v_1_0")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.ObjectFactory.class,
    in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ObjectFactory.class,
    in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0.ObjectFactory.class
})
public interface ITRVServicePortType {


    /**
     * 
     * @param itrvByAckNoRequest
     * @return
     *     returns in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse
     * @throws GetITRVInvalidCertificateFaultException
     * @throws GetITRVCredentialFaultException
     * @throws GetITRVServiceFaultException
     */
    @WebMethod(action = "http://incometaxindiaefiling.gov.in/getITRV")
    @WebResult(name = "DITWSResponseEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", partName = "itrvResponse")
    public DITWSResponse getITRVByAckNo(
        @WebParam(name = "itrvAckNoRequestEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", partName = "itrvByAckNoRequest")
        ITRVByAckNoRequest itrvByAckNoRequest)
        throws GetITRVCredentialFaultException, GetITRVInvalidCertificateFaultException, GetITRVServiceFaultException
    ;

    /**
     * 
     * @param itrvByTokenNoRequest
     * @return
     *     returns in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSResponse
     * @throws GetITRVInvalidCertificateFaultException
     * @throws GetITRVCredentialFaultException
     * @throws GetITRVServiceFaultException
     */
    @WebMethod(action = "http://incometaxindiaefiling.gov.in/getITRV")
    @WebResult(name = "DITWSResponseEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0", partName = "itrvResponse")
    public DITWSResponse getITRVByTokenNo(
        @WebParam(name = "itrvTokenNoRequestEle", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/itrv/v_1_0", partName = "itrvByTokenNoRequest")
        ITRVByTokenNoRequest itrvByTokenNoRequest)
        throws GetITRVCredentialFaultException, GetITRVInvalidCertificateFaultException, GetITRVServiceFaultException
    ;

}

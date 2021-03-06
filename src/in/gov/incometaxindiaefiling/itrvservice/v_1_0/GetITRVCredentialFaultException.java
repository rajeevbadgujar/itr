
package in.gov.incometaxindiaefiling.itrvservice.v_1_0;

import javax.xml.ws.WebFault;
import in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRVCredentialFaultException;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "itrvCredentialFaultException", targetNamespace = "http://incometaxindiaefiling.gov.in/ws/ds/ex/v_1_0")
public class GetITRVCredentialFaultException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ITRVCredentialFaultException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public GetITRVCredentialFaultException(String message, ITRVCredentialFaultException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public GetITRVCredentialFaultException(String message, ITRVCredentialFaultException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRVCredentialFaultException
     */
    public ITRVCredentialFaultException getFaultInfo() {
        return faultInfo;
    }

}

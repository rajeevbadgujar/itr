
package in.gov.incometaxindiaefiling.itrservice.v_1_0;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ITRCredentialFaultException", targetNamespace = "http://incometaxindiaefiling.gov.in/itrservice/v_1_0")
public class ITRCredentialFaultException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public ITRCredentialFaultException(String message, in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public ITRCredentialFaultException(String message, in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException
     */
    public in.gov.incometaxindiaefiling.ws.ds.ex.v_1_0.ITRCredentialFaultException getFaultInfo() {
        return faultInfo;
    }

}

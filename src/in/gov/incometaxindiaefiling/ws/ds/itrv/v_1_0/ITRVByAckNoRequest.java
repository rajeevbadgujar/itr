
package in.gov.incometaxindiaefiling.ws.ds.itrv.v_1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import in.gov.incometaxindiaefiling.ws.ds.common.v_1_0.DITWSAuthInfo;


/**
 * <p>Java class for ITRVByAckNoRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ITRVByAckNoRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="authInfo" type="{http://incometaxindiaefiling.gov.in/ws/ds/common/v_1_0}DITWSAuthInfo"/>
 *         &lt;element name="acknowledgementNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ITRVByAckNoRequest", propOrder = {
    "authInfo",
    "acknowledgementNo"
})
public class ITRVByAckNoRequest {

    @XmlElement(required = true)
    protected DITWSAuthInfo authInfo;
    @XmlElement(required = true)
    protected String acknowledgementNo;

    /**
     * Gets the value of the authInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DITWSAuthInfo }
     *     
     */
    public DITWSAuthInfo getAuthInfo() {
        return authInfo;
    }

    /**
     * Sets the value of the authInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DITWSAuthInfo }
     *     
     */
    public void setAuthInfo(DITWSAuthInfo value) {
        this.authInfo = value;
    }

    /**
     * Gets the value of the acknowledgementNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcknowledgementNo() {
        return acknowledgementNo;
    }

    /**
     * Sets the value of the acknowledgementNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcknowledgementNo(String value) {
        this.acknowledgementNo = value;
    }

}

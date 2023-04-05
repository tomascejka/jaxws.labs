
package cz.toce.learn.javaee.jaxws.simplest.api.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HelloRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HelloRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="greetings" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HelloRequestType", propOrder = {
    "greetings"
})
@XmlSeeAlso({
    HelloSoapFaultExceptionRequest.class,
    HelloWebServiceExceptionRequest.class,
    HelloCheckedExceptionRequest.class,
    HelloRuntimeExceptionRequest.class
})
public class HelloRequestType {

    @XmlElement(required = true)
    protected String greetings;

    /**
     * Gets the value of the greetings property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGreetings() {
        return greetings;
    }

    /**
     * Sets the value of the greetings property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGreetings(String value) {
        this.greetings = value;
    }

}

package com.thomas_bayer.blz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for getBankType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="getBankType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="blz" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getBankType", propOrder = {
    "blz"
})
public class GetBankType {

    @XmlElement(required = true)
    protected String blz;

    /**
     * Gets the value of the blz property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getBlz() {
        return blz;
    }

    /**
     * Sets the value of the blz property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setBlz(String value) {
        this.blz = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.15 at 07:34:01 PM EST 
//


package com.example.response;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The root tag AnalyzerReport has Metric as child element, which contains a property
 * named "id". There can be multiple occurrence of Metric element
 * , one per each instance of metric from the SwA tools. The "id" attribute in the
 * Metric tag is a unique integer id generated by the Assessment Parser tool.
 *
 *
 * <p>Java class for MetricType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="MetricType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="Class" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MetricType", propOrder = {
        "location",
        "clazz",
        "method",
        "type",
        "value"
})
public class MetricType {

    @XmlElement(name = "Location", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String location;
    @XmlElement(name = "Class")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String clazz;
    @XmlElement(name = "Method")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String method;
    @XmlElement(name = "Type", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String type;
    @XmlElement(name = "Value", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String value;
    @XmlAttribute(name = "id", required = true)
    protected int id;

    /**
     * Gets the value of the location property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the clazz property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * Sets the value of the clazz property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setClazz(String value) {
        this.clazz = value;
    }

    /**
     * Gets the value of the method property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMethod(String value) {
        this.method = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }

}

/*******************************************************************************
 * Participact
 * Copyright 2013-2018 Alma Mater Studiorum - Università di Bologna
 * 
 * This file is part of ParticipAct.
 * 
 * ParticipAct is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * ParticipAct is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with ParticipAct. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2014.12.07 alle 03:58:23 PM CET 
//


package it.unibo.tper.ws.domain.extensions;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Table">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codice_linea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="codice_fermata" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ubicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="coordinata_x" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="coordinata_y" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                   &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *                   &lt;element name="codice_zona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "table"
})
@XmlRootElement(name = "NewDataSet")
public class FermateResponse {

    @XmlElement(name = "Table")
    protected List<FermateResponse.Table> table;

    /**
     * Gets the value of the table property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the table property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NewDataSet.Table }
     * 
     * 
     */
    public List<FermateResponse.Table> getTable() {
        if (table == null) {
            table = new ArrayList<FermateResponse.Table>();
        }
        return this.table;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codice_linea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="codice_fermata" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ubicazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="coordinata_x" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="coordinata_y" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *         &lt;element name="latitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="longitudine" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
     *         &lt;element name="codice_zona" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codiceLinea",
        "codiceFermata",
        "denominazione",
        "ubicazione",
        "comune",
        "coordinataX",
        "coordinataY",
        "latitudine",
        "longitudine",
        "codiceZona"
    })
    public static class Table {

        @XmlElement(name = "codice_linea")
        protected String codiceLinea;
        @XmlElement(name = "codice_fermata")
        protected Integer codiceFermata;
        protected String denominazione;
        protected String ubicazione;
        protected String comune;
        @XmlElement(name = "coordinata_x")
        protected Integer coordinataX;
        @XmlElement(name = "coordinata_y")
        protected Integer coordinataY;
        protected Double latitudine;
        protected Double longitudine;
        @XmlElement(name = "codice_zona")
        protected Integer codiceZona;

        /**
         * Recupera il valore della proprietà codiceLinea.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceLinea() {
            return codiceLinea;
        }

        /**
         * Imposta il valore della proprietà codiceLinea.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceLinea(String value) {
            this.codiceLinea = value;
        }

        /**
         * Recupera il valore della proprietà codiceFermata.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCodiceFermata() {
            return codiceFermata;
        }

        /**
         * Imposta il valore della proprietà codiceFermata.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCodiceFermata(Integer value) {
            this.codiceFermata = value;
        }

        /**
         * Recupera il valore della proprietà denominazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDenominazione() {
            return denominazione;
        }

        /**
         * Imposta il valore della proprietà denominazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDenominazione(String value) {
            this.denominazione = value;
        }

        /**
         * Recupera il valore della proprietà ubicazione.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUbicazione() {
            return ubicazione;
        }

        /**
         * Imposta il valore della proprietà ubicazione.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUbicazione(String value) {
            this.ubicazione = value;
        }

        /**
         * Recupera il valore della proprietà comune.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComune() {
            return comune;
        }

        /**
         * Imposta il valore della proprietà comune.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComune(String value) {
            this.comune = value;
        }

        /**
         * Recupera il valore della proprietà coordinataX.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCoordinataX() {
            return coordinataX;
        }

        /**
         * Imposta il valore della proprietà coordinataX.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCoordinataX(Integer value) {
            this.coordinataX = value;
        }

        /**
         * Recupera il valore della proprietà coordinataY.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCoordinataY() {
            return coordinataY;
        }

        /**
         * Imposta il valore della proprietà coordinataY.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCoordinataY(Integer value) {
            this.coordinataY = value;
        }

        /**
         * Recupera il valore della proprietà latitudine.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getLatitudine() {
            return latitudine;
        }

        /**
         * Imposta il valore della proprietà latitudine.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setLatitudine(Double value) {
            this.latitudine = value;
        }

        /**
         * Recupera il valore della proprietà longitudine.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getLongitudine() {
            return longitudine;
        }

        /**
         * Imposta il valore della proprietà longitudine.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setLongitudine(Double value) {
            this.longitudine = value;
        }

        /**
         * Recupera il valore della proprietà codiceZona.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCodiceZona() {
            return codiceZona;
        }

        /**
         * Imposta il valore della proprietà codiceZona.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCodiceZona(Integer value) {
            this.codiceZona = value;
        }

    }

}

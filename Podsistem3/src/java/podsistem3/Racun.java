/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jana
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findByIDRac", query = "SELECT r FROM Racun r WHERE r.iDRac = :iDRac"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDozvoljeniMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racun.findByDatumVremeOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumVremeOtvaranja = :datumVremeOtvaranja"),
    @NamedQuery(name = "Racun.findByBrojTransakcija", query = "SELECT r FROM Racun r WHERE r.brojTransakcija = :brojTransakcija"),
    @NamedQuery(name = "Racun.findByIDKom", query = "SELECT r FROM Racun r WHERE r.iDKom = :iDKom"),
    @NamedQuery(name = "Racun.findByIDMes", query = "SELECT r FROM Racun r WHERE r.iDMes = :iDMes")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRac")
    private Integer iDRac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private double stanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvoljeniMinus")
    private double dozvoljeniMinus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremeOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrojTransakcija")
    private int brojTransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKom")
    private int iDKom;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMes")
    private int iDMes;

    public Racun() {
    }

    public Racun(Integer iDRac) {
        this.iDRac = iDRac;
    }

    public Racun(Integer iDRac, double stanje, String status, double dozvoljeniMinus, Date datumVremeOtvaranja, int brojTransakcija, int iDKom, int iDMes) {
        this.iDRac = iDRac;
        this.stanje = stanje;
        this.status = status;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.datumVremeOtvaranja = datumVremeOtvaranja;
        this.brojTransakcija = brojTransakcija;
        this.iDKom = iDKom;
        this.iDMes = iDMes;
    }

    public Integer getIDRac() {
        return iDRac;
    }

    public void setIDRac(Integer iDRac) {
        this.iDRac = iDRac;
    }

    public double getStanje() {
        return stanje;
    }

    public void setStanje(double stanje) {
        this.stanje = stanje;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(double dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public Date getDatumVremeOtvaranja() {
        return datumVremeOtvaranja;
    }

    public void setDatumVremeOtvaranja(Date datumVremeOtvaranja) {
        this.datumVremeOtvaranja = datumVremeOtvaranja;
    }

    public int getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(int brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public int getIDKom() {
        return iDKom;
    }

    public void setIDKom(int iDKom) {
        this.iDKom = iDKom;
    }

    public int getIDMes() {
        return iDMes;
    }

    public void setIDMes(int iDMes) {
        this.iDMes = iDMes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDRac != null ? iDRac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.iDRac == null && other.iDRac != null) || (this.iDRac != null && !this.iDRac.equals(other.iDRac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Racun[ iDRac=" + iDRac + " ]";
    }
    
}

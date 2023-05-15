/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jana
 */
@Entity
@Table(name = "filijala")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filijala.findAll", query = "SELECT f FROM Filijala f"),
    @NamedQuery(name = "Filijala.findByIDFil", query = "SELECT f FROM Filijala f WHERE f.iDFil = :iDFil"),
    @NamedQuery(name = "Filijala.findByNaziv", query = "SELECT f FROM Filijala f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "Filijala.findByAdresa", query = "SELECT f FROM Filijala f WHERE f.adresa = :adresa"),
    @NamedQuery(name = "Filijala.findByIDMes", query = "SELECT f FROM Filijala f WHERE f.iDMes = :iDMes")})
public class Filijala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDFil")
    private Integer iDFil;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMes")
    private int iDMes;

    public Filijala() {
    }

    public Filijala(Integer iDFil) {
        this.iDFil = iDFil;
    }

    public Filijala(Integer iDFil, String naziv, String adresa, int iDMes) {
        this.iDFil = iDFil;
        this.naziv = naziv;
        this.adresa = adresa;
        this.iDMes = iDMes;
    }

    public Integer getIDFil() {
        return iDFil;
    }

    public void setIDFil(Integer iDFil) {
        this.iDFil = iDFil;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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
        hash += (iDFil != null ? iDFil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filijala)) {
            return false;
        }
        Filijala other = (Filijala) object;
        if ((this.iDFil == null && other.iDFil != null) || (this.iDFil != null && !this.iDFil.equals(other.iDFil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Filijala[ iDFil=" + iDFil + " ]";
    }
    
}

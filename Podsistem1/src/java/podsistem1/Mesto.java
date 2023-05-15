/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jana
 */
@Entity
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIDMes", query = "SELECT m FROM Mesto m WHERE m.iDMes = :iDMes"),
    @NamedQuery(name = "Mesto.findByPostanskiBroj", query = "SELECT m FROM Mesto m WHERE m.postanskiBroj = :postanskiBroj"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDMes")
    private Integer iDMes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PostanskiBroj")
    private int postanskiBroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMes")
    private List<Filijala> filijalaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMes")
    private List<Komitent> komitentList;

    public Mesto() {
    }

    public Mesto(Integer iDMes) {
        this.iDMes = iDMes;
    }

    public Mesto(Integer iDMes, int postanskiBroj, String naziv) {
        this.iDMes = iDMes;
        this.postanskiBroj = postanskiBroj;
        this.naziv = naziv;
    }

    public Integer getIDMes() {
        return iDMes;
    }

    public void setIDMes(Integer iDMes) {
        this.iDMes = iDMes;
    }

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Filijala> getFilijalaList() {
        return filijalaList;
    }

    public void setFilijalaList(List<Filijala> filijalaList) {
        this.filijalaList = filijalaList;
    }

    @XmlTransient
    public List<Komitent> getKomitentList() {
        return komitentList;
    }

    public void setKomitentList(List<Komitent> komitentList) {
        this.komitentList = komitentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMes != null ? iDMes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.iDMes == null && other.iDMes != null) || (this.iDMes != null && !this.iDMes.equals(other.iDMes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "podsistem1.Mesto[ iDMes=" + iDMes + " ]";
    }
    
}

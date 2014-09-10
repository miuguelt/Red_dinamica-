/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Palabras_clave")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Palabrasclave.findAll", query = "SELECT p FROM Palabrasclave p"),
    @NamedQuery(name = "Palabrasclave.findByPalabraId", query = "SELECT p FROM Palabrasclave p WHERE p.palabraId = :palabraId"),
    @NamedQuery(name = "Palabrasclave.findByPalabraPalabra", query = "SELECT p FROM Palabrasclave p WHERE p.palabraPalabra = :palabraPalabra")})
public class Palabrasclave implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "palabra_id")
    private Integer palabraId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "palabra_palabra")
    private String palabraPalabra;
    @ManyToMany(mappedBy = "palabrasclaveCollection")
    private Collection<Archivos> archivosCollection;

    public Palabrasclave() {
    }

    public Palabrasclave(Integer palabraId) {
        this.palabraId = palabraId;
    }

    public Palabrasclave(Integer palabraId, String palabraPalabra) {
        this.palabraId = palabraId;
        this.palabraPalabra = palabraPalabra;
    }

    public Integer getPalabraId() {
        return palabraId;
    }

    public void setPalabraId(Integer palabraId) {
        this.palabraId = palabraId;
    }

    public String getPalabraPalabra() {
        return palabraPalabra;
    }

    public void setPalabraPalabra(String palabraPalabra) {
        this.palabraPalabra = palabraPalabra;
    }

    @XmlTransient
    public Collection<Archivos> getArchivosCollection() {
        return archivosCollection;
    }

    public void setArchivosCollection(Collection<Archivos> archivosCollection) {
        this.archivosCollection = archivosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (palabraId != null ? palabraId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Palabrasclave)) {
            return false;
        }
        Palabrasclave other = (Palabrasclave) object;
        if ((this.palabraId == null && other.palabraId != null) || (this.palabraId != null && !this.palabraId.equals(other.palabraId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Palabrasclave[ palabraId=" + palabraId + " ]";
    }
    
}

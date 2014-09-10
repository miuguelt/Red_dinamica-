/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Version")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Version.findAll", query = "SELECT v FROM Version v"),
    @NamedQuery(name = "Version.findByVersionId", query = "SELECT v FROM Version v WHERE v.versionId = :versionId"),
    @NamedQuery(name = "Version.findByVersionFecha", query = "SELECT v FROM Version v WHERE v.versionFecha = :versionFecha"),
    @NamedQuery(name = "Version.findByVersionNumero", query = "SELECT v FROM Version v WHERE v.versionNumero = :versionNumero")})
public class Version implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "version_id")
    private Integer versionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version_fecha")
    @Temporal(TemporalType.DATE)
    private Date versionFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version_numero")
    private int versionNumero;
    @JoinColumn(name = "version_archivo_id", referencedColumnName = "archivo_id")
    @ManyToOne(optional = false)
    private Archivos versionArchivoId;

    public Version() {
    }

    public Version(Integer versionId) {
        this.versionId = versionId;
    }

    public Version(Integer versionId, Date versionFecha, int versionNumero) {
        this.versionId = versionId;
        this.versionFecha = versionFecha;
        this.versionNumero = versionNumero;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Date getVersionFecha() {
        return versionFecha;
    }

    public void setVersionFecha(Date versionFecha) {
        this.versionFecha = versionFecha;
    }

    public int getVersionNumero() {
        return versionNumero;
    }

    public void setVersionNumero(int versionNumero) {
        this.versionNumero = versionNumero;
    }

    public Archivos getVersionArchivoId() {
        return versionArchivoId;
    }

    public void setVersionArchivoId(Archivos versionArchivoId) {
        this.versionArchivoId = versionArchivoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (versionId != null ? versionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Version)) {
            return false;
        }
        Version other = (Version) object;
        if ((this.versionId == null && other.versionId != null) || (this.versionId != null && !this.versionId.equals(other.versionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Version[ versionId=" + versionId + " ]";
    }
    
}

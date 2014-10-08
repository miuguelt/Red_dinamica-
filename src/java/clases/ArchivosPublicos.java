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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "ArchivosPublicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivosPublicos.findAll", query = "SELECT a FROM ArchivosPublicos a"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivoId", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivoId = :archivoId"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivoTitulo", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivoTitulo = :archivoTitulo"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivoAutor", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivoAutor = :archivoAutor"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivoVisitas", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivoVisitas = :archivoVisitas"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivopalabrasClave", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivopalabrasClave = :archivopalabrasClave"),
    @NamedQuery(name = "ArchivosPublicos.findByArchivoTipo", query = "SELECT a FROM ArchivosPublicos a WHERE a.archivoTipo = :archivoTipo")})
public class ArchivosPublicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "archivo_id")
    private Integer archivoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "archivo_titulo")
    private String archivoTitulo;
    @Size(max = 100)
    @Column(name = "archivo_autor")
    private String archivoAutor;
    @Column(name = "archivo_visitas")
    private Integer archivoVisitas;
    @Size(max = 200)
    @Column(name = "archivo_palabrasClave")
    private String archivopalabrasClave;
    @Column(name = "archivo_tipo")
    private Integer archivoTipo;
    @JoinTable(name = "BibliografiaColectivo", joinColumns = {
        @JoinColumn(name = "biblio_archivo_id", referencedColumnName = "archivo_id")}, inverseJoinColumns = {
        @JoinColumn(name = "biblio_colect_id", referencedColumnName = "colect_id")})
    @ManyToMany
    private Collection<Colectivos> colectivosCollection;
    @JoinColumn(name = "archivo_usr_id", referencedColumnName = "usr_id")
    @ManyToOne(optional = false)
    private Usuarios archivoUsrId;

    public ArchivosPublicos() {
    }

    public ArchivosPublicos(Integer archivoId) {
        this.archivoId = archivoId;
    }

    public ArchivosPublicos(Integer archivoId, String archivoTitulo) {
        this.archivoId = archivoId;
        this.archivoTitulo = archivoTitulo;
    }

    public Integer getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(Integer archivoId) {
        this.archivoId = archivoId;
    }

    public String getArchivoTitulo() {
        return archivoTitulo;
    }

    public void setArchivoTitulo(String archivoTitulo) {
        this.archivoTitulo = archivoTitulo;
    }

    public String getArchivoAutor() {
        return archivoAutor;
    }

    public void setArchivoAutor(String archivoAutor) {
        this.archivoAutor = archivoAutor;
    }

    public Integer getArchivoVisitas() {
        return archivoVisitas;
    }

    public void setArchivoVisitas(Integer archivoVisitas) {
        this.archivoVisitas = archivoVisitas;
    }

    public String getArchivopalabrasClave() {
        return archivopalabrasClave;
    }

    public void setArchivopalabrasClave(String archivopalabrasClave) {
        this.archivopalabrasClave = archivopalabrasClave;
    }

    public Integer getArchivoTipo() {
        return archivoTipo;
    }

    public void setArchivoTipo(Integer archivoTipo) {
        this.archivoTipo = archivoTipo;
    }

    @XmlTransient
    public Collection<Colectivos> getColectivosCollection() {
        return colectivosCollection;
    }

    public void setColectivosCollection(Collection<Colectivos> colectivosCollection) {
        this.colectivosCollection = colectivosCollection;
    }

    public Usuarios getArchivoUsrId() {
        return archivoUsrId;
    }

    public void setArchivoUsrId(Usuarios archivoUsrId) {
        this.archivoUsrId = archivoUsrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (archivoId != null ? archivoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivosPublicos)) {
            return false;
        }
        ArchivosPublicos other = (ArchivosPublicos) object;
        if ((this.archivoId == null && other.archivoId != null) || (this.archivoId != null && !this.archivoId.equals(other.archivoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.ArchivosPublicos[ archivoId=" + archivoId + " ]";
    }
    
}

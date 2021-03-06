/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Universidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Universidades.findAll", query = "SELECT u FROM Universidades u"),
    @NamedQuery(name = "Universidades.findByUniversidadId", query = "SELECT u FROM Universidades u WHERE u.universidadId = :universidadId"),
    @NamedQuery(name = "Universidades.findByUniversidadNombre", query = "SELECT u FROM Universidades u WHERE u.universidadNombre = :universidadNombre")})
public class Universidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "universidad_id")
    private Integer universidadId;
    @Size(max = 70)
    @Column(name = "universidad_nombre")
    private String universidadNombre;
    @OneToMany(mappedBy = "usrUniversidad")
    private Collection<Usuarios> usuariosCollection;
    @OneToMany(mappedBy = "eventoUniversidadId")
    private Collection<Eventos> eventosCollection;
    @JoinColumn(name = "universidad_ciudad", referencedColumnName = "ciudad_id")
    @ManyToOne(optional = false)
    private Ciudad universidadCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidades")
    private Collection<Gruposinvestiga> gruposinvestigaCollection;

    public Universidades() {
    }

    public Universidades(Integer universidadId) {
        this.universidadId = universidadId;
    }

    public Integer getUniversidadId() {
        return universidadId;
    }

    public void setUniversidadId(Integer universidadId) {
        this.universidadId = universidadId;
    }

    public String getUniversidadNombre() {
        return universidadNombre;
    }

    public void setUniversidadNombre(String universidadNombre) {
        this.universidadNombre = universidadNombre;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Eventos> getEventosCollection() {
        return eventosCollection;
    }

    public void setEventosCollection(Collection<Eventos> eventosCollection) {
        this.eventosCollection = eventosCollection;
    }

    public Ciudad getUniversidadCiudad() {
        return universidadCiudad;
    }

    public void setUniversidadCiudad(Ciudad universidadCiudad) {
        this.universidadCiudad = universidadCiudad;
    }

    @XmlTransient
    public Collection<Gruposinvestiga> getGruposinvestigaCollection() {
        return gruposinvestigaCollection;
    }

    public void setGruposinvestigaCollection(Collection<Gruposinvestiga> gruposinvestigaCollection) {
        this.gruposinvestigaCollection = gruposinvestigaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (universidadId != null ? universidadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universidades)) {
            return false;
        }
        Universidades other = (Universidades) object;
        if ((this.universidadId == null && other.universidadId != null) || (this.universidadId != null && !this.universidadId.equals(other.universidadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Universidades[ universidadId=" + universidadId + " ]";
    }
    
}

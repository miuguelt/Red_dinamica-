/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Grupos_investiga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gruposinvestiga.findAll", query = "SELECT g FROM Gruposinvestiga g"),
    @NamedQuery(name = "Gruposinvestiga.findByGruposInvestigaId", query = "SELECT g FROM Gruposinvestiga g WHERE g.gruposinvestigaPK.gruposInvestigaId = :gruposInvestigaId"),
    @NamedQuery(name = "Gruposinvestiga.findByGruposInvestigaNombre", query = "SELECT g FROM Gruposinvestiga g WHERE g.gruposInvestigaNombre = :gruposInvestigaNombre"),
    @NamedQuery(name = "Gruposinvestiga.findByGruposInvestigaUniversidad", query = "SELECT g FROM Gruposinvestiga g WHERE g.gruposinvestigaPK.gruposInvestigaUniversidad = :gruposInvestigaUniversidad"),
    @NamedQuery(name = "Gruposinvestiga.findByGruposInvestigaDescripcion", query = "SELECT g FROM Gruposinvestiga g WHERE g.gruposInvestigaDescripcion = :gruposInvestigaDescripcion")})
public class Gruposinvestiga implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GruposinvestigaPK gruposinvestigaPK;
    @Size(max = 45)
    @Column(name = "grupos_investiga_nombre")
    private String gruposInvestigaNombre;
    @Size(max = 100)
    @Column(name = "grupos_investiga_descripcion")
    private String gruposInvestigaDescripcion;
    @JoinColumn(name = "grupos_investiga_universidad", referencedColumnName = "universidad_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Universidades universidades;

    public Gruposinvestiga() {
    }

    public Gruposinvestiga(GruposinvestigaPK gruposinvestigaPK) {
        this.gruposinvestigaPK = gruposinvestigaPK;
    }

    public Gruposinvestiga(int gruposInvestigaId, int gruposInvestigaUniversidad) {
        this.gruposinvestigaPK = new GruposinvestigaPK(gruposInvestigaId, gruposInvestigaUniversidad);
    }

    public GruposinvestigaPK getGruposinvestigaPK() {
        return gruposinvestigaPK;
    }

    public void setGruposinvestigaPK(GruposinvestigaPK gruposinvestigaPK) {
        this.gruposinvestigaPK = gruposinvestigaPK;
    }

    public String getGruposInvestigaNombre() {
        return gruposInvestigaNombre;
    }

    public void setGruposInvestigaNombre(String gruposInvestigaNombre) {
        this.gruposInvestigaNombre = gruposInvestigaNombre;
    }

    public String getGruposInvestigaDescripcion() {
        return gruposInvestigaDescripcion;
    }

    public void setGruposInvestigaDescripcion(String gruposInvestigaDescripcion) {
        this.gruposInvestigaDescripcion = gruposInvestigaDescripcion;
    }

    public Universidades getUniversidades() {
        return universidades;
    }

    public void setUniversidades(Universidades universidades) {
        this.universidades = universidades;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruposinvestigaPK != null ? gruposinvestigaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gruposinvestiga)) {
            return false;
        }
        Gruposinvestiga other = (Gruposinvestiga) object;
        if ((this.gruposinvestigaPK == null && other.gruposinvestigaPK != null) || (this.gruposinvestigaPK != null && !this.gruposinvestigaPK.equals(other.gruposinvestigaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Gruposinvestiga[ gruposinvestigaPK=" + gruposinvestigaPK + " ]";
    }
    
}

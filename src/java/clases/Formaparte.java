/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Forma_parte")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formaparte.findAll", query = "SELECT f FROM Formaparte f"),
    @NamedQuery(name = "Formaparte.findByFormaUsrId", query = "SELECT f FROM Formaparte f WHERE f.formapartePK.formaUsrId = :formaUsrId"),
    @NamedQuery(name = "Formaparte.findByFormaColectId", query = "SELECT f FROM Formaparte f WHERE f.formapartePK.formaColectId = :formaColectId"),
    @NamedQuery(name = "Formaparte.findByFormaEstado", query = "SELECT f FROM Formaparte f WHERE f.formaEstado = :formaEstado")})
public class Formaparte implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FormapartePK formapartePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "forma_estado")
    private boolean formaEstado;
    @JoinColumn(name = "forma_colect_id", referencedColumnName = "colect_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Colectivos colectivos;
    @JoinColumn(name = "forma_usr_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Formaparte() {
    }

    public Formaparte(FormapartePK formapartePK) {
        this.formapartePK = formapartePK;
    }

    public Formaparte(FormapartePK formapartePK, boolean formaEstado) {
        this.formapartePK = formapartePK;
        this.formaEstado = formaEstado;
    }

    public Formaparte(int formaUsrId, int formaColectId) {
        this.formapartePK = new FormapartePK(formaUsrId, formaColectId);
    }

    public FormapartePK getFormapartePK() {
        return formapartePK;
    }

    public void setFormapartePK(FormapartePK formapartePK) {
        this.formapartePK = formapartePK;
    }

    public boolean getFormaEstado() {
        return formaEstado;
    }

    public void setFormaEstado(boolean formaEstado) {
        this.formaEstado = formaEstado;
    }

    public Colectivos getColectivos() {
        return colectivos;
    }

    public void setColectivos(Colectivos colectivos) {
        this.colectivos = colectivos;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (formapartePK != null ? formapartePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formaparte)) {
            return false;
        }
        Formaparte other = (Formaparte) object;
        if ((this.formapartePK == null && other.formapartePK != null) || (this.formapartePK != null && !this.formapartePK.equals(other.formapartePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Formaparte[ formapartePK=" + formapartePK + " ]";
    }
    
}

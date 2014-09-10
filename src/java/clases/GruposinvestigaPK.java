/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author entorno
 */
@Embeddable
public class GruposinvestigaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "grupos_investiga_id")
    private int gruposInvestigaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "grupos_investiga_universidad")
    private int gruposInvestigaUniversidad;

    public GruposinvestigaPK() {
    }

    public GruposinvestigaPK(int gruposInvestigaId, int gruposInvestigaUniversidad) {
        this.gruposInvestigaId = gruposInvestigaId;
        this.gruposInvestigaUniversidad = gruposInvestigaUniversidad;
    }

    public int getGruposInvestigaId() {
        return gruposInvestigaId;
    }

    public void setGruposInvestigaId(int gruposInvestigaId) {
        this.gruposInvestigaId = gruposInvestigaId;
    }

    public int getGruposInvestigaUniversidad() {
        return gruposInvestigaUniversidad;
    }

    public void setGruposInvestigaUniversidad(int gruposInvestigaUniversidad) {
        this.gruposInvestigaUniversidad = gruposInvestigaUniversidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) gruposInvestigaId;
        hash += (int) gruposInvestigaUniversidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposinvestigaPK)) {
            return false;
        }
        GruposinvestigaPK other = (GruposinvestigaPK) object;
        if (this.gruposInvestigaId != other.gruposInvestigaId) {
            return false;
        }
        if (this.gruposInvestigaUniversidad != other.gruposInvestigaUniversidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.GruposinvestigaPK[ gruposInvestigaId=" + gruposInvestigaId + ", gruposInvestigaUniversidad=" + gruposInvestigaUniversidad + " ]";
    }
    
}

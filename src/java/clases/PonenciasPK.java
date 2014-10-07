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
public class PonenciasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "ponencias_ponente_id")
    private int ponenciasPonenteId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ponencias_evento_id")
    private int ponenciasEventoId;

    public PonenciasPK() {
    }

    public PonenciasPK(int ponenciasPonenteId, int ponenciasEventoId) {
        this.ponenciasPonenteId = ponenciasPonenteId;
        this.ponenciasEventoId = ponenciasEventoId;
    }

    public int getPonenciasPonenteId() {
        return ponenciasPonenteId;
    }

    public void setPonenciasPonenteId(int ponenciasPonenteId) {
        this.ponenciasPonenteId = ponenciasPonenteId;
    }

    public int getPonenciasEventoId() {
        return ponenciasEventoId;
    }

    public void setPonenciasEventoId(int ponenciasEventoId) {
        this.ponenciasEventoId = ponenciasEventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) ponenciasPonenteId;
        hash += (int) ponenciasEventoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PonenciasPK)) {
            return false;
        }
        PonenciasPK other = (PonenciasPK) object;
        if (this.ponenciasPonenteId != other.ponenciasPonenteId) {
            return false;
        }
        if (this.ponenciasEventoId != other.ponenciasEventoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.PonenciasPK[ ponenciasPonenteId=" + ponenciasPonenteId + ", ponenciasEventoId=" + ponenciasEventoId + " ]";
    }
    
}

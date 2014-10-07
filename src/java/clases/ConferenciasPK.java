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
public class ConferenciasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "conferencia_usr_id")
    private int conferenciaUsrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "conferencia_evento_id")
    private int conferenciaEventoId;

    public ConferenciasPK() {
    }

    public ConferenciasPK(int conferenciaUsrId, int conferenciaEventoId) {
        this.conferenciaUsrId = conferenciaUsrId;
        this.conferenciaEventoId = conferenciaEventoId;
    }

    public int getConferenciaUsrId() {
        return conferenciaUsrId;
    }

    public void setConferenciaUsrId(int conferenciaUsrId) {
        this.conferenciaUsrId = conferenciaUsrId;
    }

    public int getConferenciaEventoId() {
        return conferenciaEventoId;
    }

    public void setConferenciaEventoId(int conferenciaEventoId) {
        this.conferenciaEventoId = conferenciaEventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) conferenciaUsrId;
        hash += (int) conferenciaEventoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConferenciasPK)) {
            return false;
        }
        ConferenciasPK other = (ConferenciasPK) object;
        if (this.conferenciaUsrId != other.conferenciaUsrId) {
            return false;
        }
        if (this.conferenciaEventoId != other.conferenciaEventoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.ConferenciasPK[ conferenciaUsrId=" + conferenciaUsrId + ", conferenciaEventoId=" + conferenciaEventoId + " ]";
    }
    
}

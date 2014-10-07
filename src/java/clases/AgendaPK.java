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
public class AgendaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "agenda_usr_id")
    private int agendaUsrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "agenda_evento_id")
    private int agendaEventoId;

    public AgendaPK() {
    }

    public AgendaPK(int agendaUsrId, int agendaEventoId) {
        this.agendaUsrId = agendaUsrId;
        this.agendaEventoId = agendaEventoId;
    }

    public int getAgendaUsrId() {
        return agendaUsrId;
    }

    public void setAgendaUsrId(int agendaUsrId) {
        this.agendaUsrId = agendaUsrId;
    }

    public int getAgendaEventoId() {
        return agendaEventoId;
    }

    public void setAgendaEventoId(int agendaEventoId) {
        this.agendaEventoId = agendaEventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) agendaUsrId;
        hash += (int) agendaEventoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgendaPK)) {
            return false;
        }
        AgendaPK other = (AgendaPK) object;
        if (this.agendaUsrId != other.agendaUsrId) {
            return false;
        }
        if (this.agendaEventoId != other.agendaEventoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.AgendaPK[ agendaUsrId=" + agendaUsrId + ", agendaEventoId=" + agendaEventoId + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Agenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agenda.findAll", query = "SELECT a FROM Agenda a"),
    @NamedQuery(name = "Agenda.findByAgendaUsrId", query = "SELECT a FROM Agenda a WHERE a.agendaPK.agendaUsrId = :agendaUsrId"),
    @NamedQuery(name = "Agenda.findByAgendaEventoId", query = "SELECT a FROM Agenda a WHERE a.agendaPK.agendaEventoId = :agendaEventoId"),
    @NamedQuery(name = "Agenda.findByAgendaD\u00eda", query = "SELECT a FROM Agenda a WHERE a.agendaD\u00eda = :agendaD\u00eda"),
    @NamedQuery(name = "Agenda.findByAgendaHoraInicio", query = "SELECT a FROM Agenda a WHERE a.agendaHoraInicio = :agendaHoraInicio"),
    @NamedQuery(name = "Agenda.findByAgendaHoraFin", query = "SELECT a FROM Agenda a WHERE a.agendaHoraFin = :agendaHoraFin"),
    @NamedQuery(name = "Agenda.findByAgendaActividad", query = "SELECT a FROM Agenda a WHERE a.agendaActividad = :agendaActividad"),
    @NamedQuery(name = "Agenda.findByAgendaLugar", query = "SELECT a FROM Agenda a WHERE a.agendaLugar = :agendaLugar")})
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AgendaPK agendaPK;
    @Column(name = "agenda_d\u00eda")
    @Temporal(TemporalType.DATE)
    private Date agendaDía;
    @Column(name = "agenda_hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date agendaHoraInicio;
    @Column(name = "agenda_hora_fin")
    @Temporal(TemporalType.TIME)
    private Date agendaHoraFin;
    @Size(max = 200)
    @Column(name = "agenda_actividad")
    private String agendaActividad;
    @Size(max = 100)
    @Column(name = "agenda_lugar")
    private String agendaLugar;
    @JoinColumn(name = "agenda_evento_id", referencedColumnName = "evento_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "agenda_usr_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Agenda() {
    }

    public Agenda(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public Agenda(int agendaUsrId, int agendaEventoId) {
        this.agendaPK = new AgendaPK(agendaUsrId, agendaEventoId);
    }

    public AgendaPK getAgendaPK() {
        return agendaPK;
    }

    public void setAgendaPK(AgendaPK agendaPK) {
        this.agendaPK = agendaPK;
    }

    public Date getAgendaDía() {
        return agendaDía;
    }

    public void setAgendaDía(Date agendaDía) {
        this.agendaDía = agendaDía;
    }

    public Date getAgendaHoraInicio() {
        return agendaHoraInicio;
    }

    public void setAgendaHoraInicio(Date agendaHoraInicio) {
        this.agendaHoraInicio = agendaHoraInicio;
    }

    public Date getAgendaHoraFin() {
        return agendaHoraFin;
    }

    public void setAgendaHoraFin(Date agendaHoraFin) {
        this.agendaHoraFin = agendaHoraFin;
    }

    public String getAgendaActividad() {
        return agendaActividad;
    }

    public void setAgendaActividad(String agendaActividad) {
        this.agendaActividad = agendaActividad;
    }

    public String getAgendaLugar() {
        return agendaLugar;
    }

    public void setAgendaLugar(String agendaLugar) {
        this.agendaLugar = agendaLugar;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
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
        hash += (agendaPK != null ? agendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.agendaPK == null && other.agendaPK != null) || (this.agendaPK != null && !this.agendaPK.equals(other.agendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Agenda[ agendaPK=" + agendaPK + " ]";
    }
    
}

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
@Table(name = "Conferencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conferencias.findAll", query = "SELECT c FROM Conferencias c"),
    @NamedQuery(name = "Conferencias.findByConferenciaUsrId", query = "SELECT c FROM Conferencias c WHERE c.conferenciasPK.conferenciaUsrId = :conferenciaUsrId"),
    @NamedQuery(name = "Conferencias.findByConferenciaEventoId", query = "SELECT c FROM Conferencias c WHERE c.conferenciasPK.conferenciaEventoId = :conferenciaEventoId"),
    @NamedQuery(name = "Conferencias.findByConferenciaTitulo", query = "SELECT c FROM Conferencias c WHERE c.conferenciaTitulo = :conferenciaTitulo"),
    @NamedQuery(name = "Conferencias.findByConferenciaAceptada", query = "SELECT c FROM Conferencias c WHERE c.conferenciaAceptada = :conferenciaAceptada")})
public class Conferencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConferenciasPK conferenciasPK;
    @Size(max = 200)
    @Column(name = "conferencia_titulo")
    private String conferenciaTitulo;
    @Column(name = "conferencia_aceptada")
    private Boolean conferenciaAceptada;
    @JoinColumn(name = "conferencia_usr_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @JoinColumn(name = "conferencia_evento_id", referencedColumnName = "evento_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eventos eventos;

    public Conferencias() {
    }

    public Conferencias(ConferenciasPK conferenciasPK) {
        this.conferenciasPK = conferenciasPK;
    }

    public Conferencias(int conferenciaUsrId, int conferenciaEventoId) {
        this.conferenciasPK = new ConferenciasPK(conferenciaUsrId, conferenciaEventoId);
    }

    public ConferenciasPK getConferenciasPK() {
        return conferenciasPK;
    }

    public void setConferenciasPK(ConferenciasPK conferenciasPK) {
        this.conferenciasPK = conferenciasPK;
    }

    public String getConferenciaTitulo() {
        return conferenciaTitulo;
    }

    public void setConferenciaTitulo(String conferenciaTitulo) {
        this.conferenciaTitulo = conferenciaTitulo;
    }

    public Boolean getConferenciaAceptada() {
        return conferenciaAceptada;
    }

    public void setConferenciaAceptada(Boolean conferenciaAceptada) {
        this.conferenciaAceptada = conferenciaAceptada;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Eventos getEventos() {
        return eventos;
    }

    public void setEventos(Eventos eventos) {
        this.eventos = eventos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conferenciasPK != null ? conferenciasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conferencias)) {
            return false;
        }
        Conferencias other = (Conferencias) object;
        if ((this.conferenciasPK == null && other.conferenciasPK != null) || (this.conferenciasPK != null && !this.conferenciasPK.equals(other.conferenciasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Conferencias[ conferenciasPK=" + conferenciasPK + " ]";
    }
    
}

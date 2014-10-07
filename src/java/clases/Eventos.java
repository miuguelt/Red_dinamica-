/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e"),
    @NamedQuery(name = "Eventos.findByEventoId", query = "SELECT e FROM Eventos e WHERE e.eventoId = :eventoId"),
    @NamedQuery(name = "Eventos.findByEventoTitulo", query = "SELECT e FROM Eventos e WHERE e.eventoTitulo = :eventoTitulo"),
    @NamedQuery(name = "Eventos.findByEventoDescripcion", query = "SELECT e FROM Eventos e WHERE e.eventoDescripcion = :eventoDescripcion"),
    @NamedQuery(name = "Eventos.findByEventoArchivo", query = "SELECT e FROM Eventos e WHERE e.eventoArchivo = :eventoArchivo"),
    @NamedQuery(name = "Eventos.findByEventoInicio", query = "SELECT e FROM Eventos e WHERE e.eventoInicio = :eventoInicio"),
    @NamedQuery(name = "Eventos.findByEventoFin", query = "SELECT e FROM Eventos e WHERE e.eventoFin = :eventoFin")})
public class Eventos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "evento_id")
    private Integer eventoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "evento_titulo")
    private String eventoTitulo;
    @Size(max = 1000)
    @Column(name = "evento_descripcion")
    private String eventoDescripcion;
    @Size(max = 300)
    @Column(name = "evento_archivo")
    private String eventoArchivo;
    @Column(name = "evento_inicio")
    @Temporal(TemporalType.DATE)
    private Date eventoInicio;
    @Column(name = "evento_fin")
    @Temporal(TemporalType.DATE)
    private Date eventoFin;
    @ManyToMany(mappedBy = "eventosCollection")
    private Collection<Usuarios> usuariosCollection;
    @ManyToMany(mappedBy = "eventosCollection1")
    private Collection<Usuarios> usuariosCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Conferencias> conferenciasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "novedadEventoId")
    private Collection<Novedades> novedadesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Ponencias> ponenciasCollection;
    @JoinColumn(name = "evento_universidad_id", referencedColumnName = "universidad_id")
    @ManyToOne
    private Universidades eventoUniversidadId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventos")
    private Collection<Agenda> agendaCollection;

    public Eventos() {
    }

    public Eventos(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public Eventos(Integer eventoId, String eventoTitulo) {
        this.eventoId = eventoId;
        this.eventoTitulo = eventoTitulo;
    }

    public Integer getEventoId() {
        return eventoId;
    }

    public void setEventoId(Integer eventoId) {
        this.eventoId = eventoId;
    }

    public String getEventoTitulo() {
        return eventoTitulo;
    }

    public void setEventoTitulo(String eventoTitulo) {
        this.eventoTitulo = eventoTitulo;
    }

    public String getEventoDescripcion() {
        return eventoDescripcion;
    }

    public void setEventoDescripcion(String eventoDescripcion) {
        this.eventoDescripcion = eventoDescripcion;
    }

    public String getEventoArchivo() {
        return eventoArchivo;
    }

    public void setEventoArchivo(String eventoArchivo) {
        this.eventoArchivo = eventoArchivo;
    }

    public Date getEventoInicio() {
        return eventoInicio;
    }

    public void setEventoInicio(Date eventoInicio) {
        this.eventoInicio = eventoInicio;
    }

    public Date getEventoFin() {
        return eventoFin;
    }

    public void setEventoFin(Date eventoFin) {
        this.eventoFin = eventoFin;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection() {
        return usuariosCollection;
    }

    public void setUsuariosCollection(Collection<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    @XmlTransient
    public Collection<Usuarios> getUsuariosCollection1() {
        return usuariosCollection1;
    }

    public void setUsuariosCollection1(Collection<Usuarios> usuariosCollection1) {
        this.usuariosCollection1 = usuariosCollection1;
    }

    @XmlTransient
    public Collection<Conferencias> getConferenciasCollection() {
        return conferenciasCollection;
    }

    public void setConferenciasCollection(Collection<Conferencias> conferenciasCollection) {
        this.conferenciasCollection = conferenciasCollection;
    }

    @XmlTransient
    public Collection<Novedades> getNovedadesCollection() {
        return novedadesCollection;
    }

    public void setNovedadesCollection(Collection<Novedades> novedadesCollection) {
        this.novedadesCollection = novedadesCollection;
    }

    @XmlTransient
    public Collection<Ponencias> getPonenciasCollection() {
        return ponenciasCollection;
    }

    public void setPonenciasCollection(Collection<Ponencias> ponenciasCollection) {
        this.ponenciasCollection = ponenciasCollection;
    }

    public Universidades getEventoUniversidadId() {
        return eventoUniversidadId;
    }

    public void setEventoUniversidadId(Universidades eventoUniversidadId) {
        this.eventoUniversidadId = eventoUniversidadId;
    }

    @XmlTransient
    public Collection<Agenda> getAgendaCollection() {
        return agendaCollection;
    }

    public void setAgendaCollection(Collection<Agenda> agendaCollection) {
        this.agendaCollection = agendaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventoId != null ? eventoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.eventoId == null && other.eventoId != null) || (this.eventoId != null && !this.eventoId.equals(other.eventoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Eventos[ eventoId=" + eventoId + " ]";
    }
    
}

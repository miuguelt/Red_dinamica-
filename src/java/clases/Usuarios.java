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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsrId", query = "SELECT u FROM Usuarios u WHERE u.usrId = :usrId"),
    @NamedQuery(name = "Usuarios.findByUsrNombres", query = "SELECT u FROM Usuarios u WHERE u.usrNombres = :usrNombres"),
    @NamedQuery(name = "Usuarios.findByUsrApellidos", query = "SELECT u FROM Usuarios u WHERE u.usrApellidos = :usrApellidos"),
    @NamedQuery(name = "Usuarios.findByUsrEmail", query = "SELECT u FROM Usuarios u WHERE u.usrEmail = :usrEmail"),
    @NamedQuery(name = "Usuarios.findByUsrPass", query = "SELECT u FROM Usuarios u WHERE u.usrPass = :usrPass"),
    @NamedQuery(name = "Usuarios.findByUsrSexo", query = "SELECT u FROM Usuarios u WHERE u.usrSexo = :usrSexo"),
    @NamedQuery(name = "Usuarios.findByUsrTipo", query = "SELECT u FROM Usuarios u WHERE u.usrTipo = :usrTipo"),
    @NamedQuery(name = "Usuarios.findByUsrEstado", query = "SELECT u FROM Usuarios u WHERE u.usrEstado = :usrEstado"),
    @NamedQuery(name = "Usuarios.findByUsrFoto", query = "SELECT u FROM Usuarios u WHERE u.usrFoto = :usrFoto")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usr_id")
    private Integer usrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "usr_nombres")
    private String usrNombres;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "usr_apellidos")
    private String usrApellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "usr_email")
    private String usrEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "usr_pass")
    private String usrPass;
    @Column(name = "usr_sexo")
    private Boolean usrSexo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_tipo")
    private boolean usrTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_estado")
    private boolean usrEstado;
    @Size(max = 45)
    @Column(name = "usr_foto")
    private String usrFoto;
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Eventos> eventosCollection;
    @ManyToMany(mappedBy = "usuariosCollection1")
    private Collection<Eventos> eventosCollection1;
    @JoinTable(name = "Contactos", joinColumns = {
        @JoinColumn(name = "cont_usr_id", referencedColumnName = "usr_id")}, inverseJoinColumns = {
        @JoinColumn(name = "cont_contacto_id", referencedColumnName = "usr_id")})
    @ManyToMany
    private Collection<Usuarios> usuariosCollection;
    @ManyToMany(mappedBy = "usuariosCollection")
    private Collection<Usuarios> usuariosCollection1;
    @JoinTable(name = "Evaluadores", joinColumns = {
        @JoinColumn(name = "evaluador_usr_id", referencedColumnName = "usr_id")}, inverseJoinColumns = {
        @JoinColumn(name = "evaluador_evento_id", referencedColumnName = "evento_id")})
    @ManyToMany
    private Collection<Eventos> eventosCollection2;
    @JoinColumn(name = "usr_universidad", referencedColumnName = "universidad_id")
    @ManyToOne
    private Universidades usrUniversidad;
    @JoinColumn(name = "usr_departamento", referencedColumnName = "departamento_id")
    @ManyToOne
    private Departamentos usrDepartamento;
    @JoinColumn(name = "usr_ciudad", referencedColumnName = "ciudad_id")
    @ManyToOne
    private Ciudad usrCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Formaparte> formaparteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Conferencias> conferenciasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialUsrId")
    private Collection<Historialarchivos> historialarchivosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "noticiaUsrId")
    private Collection<Noticias> noticiasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Ponencias> ponenciasCollection;
    @OneToMany(mappedBy = "ponenciasEvaluadorId")
    private Collection<Ponencias> ponenciasCollection1;
    @OneToMany(mappedBy = "ponenciasEvaluador1Id")
    private Collection<Ponencias> ponenciasCollection2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "msjDestinatario")
    private Collection<Mensaje> mensajeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "msjRemitente")
    private Collection<Mensaje> mensajeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "foroUsrId")
    private Collection<Foros> forosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoUsrId")
    private Collection<ArchivosPublicos> archivosPublicosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comentUsrId")
    private Collection<Comentarios> comentariosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    private Collection<Agenda> agendaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convUsr1Id")
    private Collection<Conversacion> conversacionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "convUsr2Id")
    private Collection<Conversacion> conversacionCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "archivoUsrId")
    private Collection<Archivos> archivosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "versionUsrId")
    private Collection<Version> versionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "solicitudUsrId")
    private Collection<Solicitudes> solicitudesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colectUsrId")
    private Collection<Colectivos> colectivosCollection;

    public Usuarios() {
    }

    public Usuarios(Integer usrId) {
        this.usrId = usrId;
    }

    public Usuarios(Integer usrId, String usrNombres, String usrApellidos, String usrEmail, String usrPass, boolean usrTipo, boolean usrEstado) {
        this.usrId = usrId;
        this.usrNombres = usrNombres;
        this.usrApellidos = usrApellidos;
        this.usrEmail = usrEmail;
        this.usrPass = usrPass;
        this.usrTipo = usrTipo;
        this.usrEstado = usrEstado;
    }

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public String getUsrNombres() {
        return usrNombres;
    }

    public void setUsrNombres(String usrNombres) {
        this.usrNombres = usrNombres;
    }

    public String getUsrApellidos() {
        return usrApellidos;
    }

    public void setUsrApellidos(String usrApellidos) {
        this.usrApellidos = usrApellidos;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrPass() {
        return usrPass;
    }

    public void setUsrPass(String usrPass) {
        this.usrPass = usrPass;
    }

    public Boolean getUsrSexo() {
        return usrSexo;
    }

    public void setUsrSexo(Boolean usrSexo) {
        this.usrSexo = usrSexo;
    }

    public boolean getUsrTipo() {
        return usrTipo;
    }

    public void setUsrTipo(boolean usrTipo) {
        this.usrTipo = usrTipo;
    }

    public boolean getUsrEstado() {
        return usrEstado;
    }

    public void setUsrEstado(boolean usrEstado) {
        this.usrEstado = usrEstado;
    }

    public String getUsrFoto() {
        return usrFoto;
    }

    public void setUsrFoto(String usrFoto) {
        this.usrFoto = usrFoto;
    }

    @XmlTransient
    public Collection<Eventos> getEventosCollection() {
        return eventosCollection;
    }

    public void setEventosCollection(Collection<Eventos> eventosCollection) {
        this.eventosCollection = eventosCollection;
    }

    @XmlTransient
    public Collection<Eventos> getEventosCollection1() {
        return eventosCollection1;
    }

    public void setEventosCollection1(Collection<Eventos> eventosCollection1) {
        this.eventosCollection1 = eventosCollection1;
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
    public Collection<Eventos> getEventosCollection2() {
        return eventosCollection2;
    }

    public void setEventosCollection2(Collection<Eventos> eventosCollection2) {
        this.eventosCollection2 = eventosCollection2;
    }

    public Universidades getUsrUniversidad() {
        return usrUniversidad;
    }

    public void setUsrUniversidad(Universidades usrUniversidad) {
        this.usrUniversidad = usrUniversidad;
    }

    public Departamentos getUsrDepartamento() {
        return usrDepartamento;
    }

    public void setUsrDepartamento(Departamentos usrDepartamento) {
        this.usrDepartamento = usrDepartamento;
    }

    public Ciudad getUsrCiudad() {
        return usrCiudad;
    }

    public void setUsrCiudad(Ciudad usrCiudad) {
        this.usrCiudad = usrCiudad;
    }

    @XmlTransient
    public Collection<Formaparte> getFormaparteCollection() {
        return formaparteCollection;
    }

    public void setFormaparteCollection(Collection<Formaparte> formaparteCollection) {
        this.formaparteCollection = formaparteCollection;
    }

    @XmlTransient
    public Collection<Conferencias> getConferenciasCollection() {
        return conferenciasCollection;
    }

    public void setConferenciasCollection(Collection<Conferencias> conferenciasCollection) {
        this.conferenciasCollection = conferenciasCollection;
    }

    @XmlTransient
    public Collection<Historialarchivos> getHistorialarchivosCollection() {
        return historialarchivosCollection;
    }

    public void setHistorialarchivosCollection(Collection<Historialarchivos> historialarchivosCollection) {
        this.historialarchivosCollection = historialarchivosCollection;
    }

    @XmlTransient
    public Collection<Noticias> getNoticiasCollection() {
        return noticiasCollection;
    }

    public void setNoticiasCollection(Collection<Noticias> noticiasCollection) {
        this.noticiasCollection = noticiasCollection;
    }

    @XmlTransient
    public Collection<Ponencias> getPonenciasCollection() {
        return ponenciasCollection;
    }

    public void setPonenciasCollection(Collection<Ponencias> ponenciasCollection) {
        this.ponenciasCollection = ponenciasCollection;
    }

    @XmlTransient
    public Collection<Ponencias> getPonenciasCollection1() {
        return ponenciasCollection1;
    }

    public void setPonenciasCollection1(Collection<Ponencias> ponenciasCollection1) {
        this.ponenciasCollection1 = ponenciasCollection1;
    }

    @XmlTransient
    public Collection<Ponencias> getPonenciasCollection2() {
        return ponenciasCollection2;
    }

    public void setPonenciasCollection2(Collection<Ponencias> ponenciasCollection2) {
        this.ponenciasCollection2 = ponenciasCollection2;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection() {
        return mensajeCollection;
    }

    public void setMensajeCollection(Collection<Mensaje> mensajeCollection) {
        this.mensajeCollection = mensajeCollection;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection1() {
        return mensajeCollection1;
    }

    public void setMensajeCollection1(Collection<Mensaje> mensajeCollection1) {
        this.mensajeCollection1 = mensajeCollection1;
    }

    @XmlTransient
    public Collection<Foros> getForosCollection() {
        return forosCollection;
    }

    public void setForosCollection(Collection<Foros> forosCollection) {
        this.forosCollection = forosCollection;
    }

    @XmlTransient
    public Collection<ArchivosPublicos> getArchivosPublicosCollection() {
        return archivosPublicosCollection;
    }

    public void setArchivosPublicosCollection(Collection<ArchivosPublicos> archivosPublicosCollection) {
        this.archivosPublicosCollection = archivosPublicosCollection;
    }

    @XmlTransient
    public Collection<Comentarios> getComentariosCollection() {
        return comentariosCollection;
    }

    public void setComentariosCollection(Collection<Comentarios> comentariosCollection) {
        this.comentariosCollection = comentariosCollection;
    }

    @XmlTransient
    public Collection<Agenda> getAgendaCollection() {
        return agendaCollection;
    }

    public void setAgendaCollection(Collection<Agenda> agendaCollection) {
        this.agendaCollection = agendaCollection;
    }

    @XmlTransient
    public Collection<Conversacion> getConversacionCollection() {
        return conversacionCollection;
    }

    public void setConversacionCollection(Collection<Conversacion> conversacionCollection) {
        this.conversacionCollection = conversacionCollection;
    }

    @XmlTransient
    public Collection<Conversacion> getConversacionCollection1() {
        return conversacionCollection1;
    }

    public void setConversacionCollection1(Collection<Conversacion> conversacionCollection1) {
        this.conversacionCollection1 = conversacionCollection1;
    }

    @XmlTransient
    public Collection<Archivos> getArchivosCollection() {
        return archivosCollection;
    }

    public void setArchivosCollection(Collection<Archivos> archivosCollection) {
        this.archivosCollection = archivosCollection;
    }

    @XmlTransient
    public Collection<Version> getVersionCollection() {
        return versionCollection;
    }

    public void setVersionCollection(Collection<Version> versionCollection) {
        this.versionCollection = versionCollection;
    }

    @XmlTransient
    public Collection<Solicitudes> getSolicitudesCollection() {
        return solicitudesCollection;
    }

    public void setSolicitudesCollection(Collection<Solicitudes> solicitudesCollection) {
        this.solicitudesCollection = solicitudesCollection;
    }

    @XmlTransient
    public Collection<Colectivos> getColectivosCollection() {
        return colectivosCollection;
    }

    public void setColectivosCollection(Collection<Colectivos> colectivosCollection) {
        this.colectivosCollection = colectivosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrId != null ? usrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usrId == null && other.usrId != null) || (this.usrId != null && !this.usrId.equals(other.usrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Usuarios[ usrId=" + usrId + " ]";
    }
    
}

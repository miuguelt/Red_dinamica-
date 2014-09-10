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
@Table(name = "Respuestas_coment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuestascoment.findAll", query = "SELECT r FROM Respuestascoment r"),
    @NamedQuery(name = "Respuestascoment.findByRespRespuestaId", query = "SELECT r FROM Respuestascoment r WHERE r.respuestascomentPK.respRespuestaId = :respRespuestaId"),
    @NamedQuery(name = "Respuestascoment.findByRespComentId", query = "SELECT r FROM Respuestascoment r WHERE r.respuestascomentPK.respComentId = :respComentId"),
    @NamedQuery(name = "Respuestascoment.findByRespDescripcion", query = "SELECT r FROM Respuestascoment r WHERE r.respDescripcion = :respDescripcion"),
    @NamedQuery(name = "Respuestascoment.findByRespFecha", query = "SELECT r FROM Respuestascoment r WHERE r.respFecha = :respFecha")})
public class Respuestascoment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RespuestascomentPK respuestascomentPK;
    @Size(max = 145)
    @Column(name = "resp_descripcion")
    private String respDescripcion;
    @Column(name = "resp_fecha")
    @Temporal(TemporalType.DATE)
    private Date respFecha;
    @JoinColumn(name = "resp_usr_id", referencedColumnName = "usr_id")
    @ManyToOne(optional = false)
    private Usuarios respUsrId;
    @JoinColumn(name = "resp_coment_id", referencedColumnName = "coment_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comentarios comentarios;
    @JoinColumn(name = "resp_respuesta_id", referencedColumnName = "coment_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comentarios comentarios1;

    public Respuestascoment() {
    }

    public Respuestascoment(RespuestascomentPK respuestascomentPK) {
        this.respuestascomentPK = respuestascomentPK;
    }

    public Respuestascoment(int respRespuestaId, int respComentId) {
        this.respuestascomentPK = new RespuestascomentPK(respRespuestaId, respComentId);
    }

    public RespuestascomentPK getRespuestascomentPK() {
        return respuestascomentPK;
    }

    public void setRespuestascomentPK(RespuestascomentPK respuestascomentPK) {
        this.respuestascomentPK = respuestascomentPK;
    }

    public String getRespDescripcion() {
        return respDescripcion;
    }

    public void setRespDescripcion(String respDescripcion) {
        this.respDescripcion = respDescripcion;
    }

    public Date getRespFecha() {
        return respFecha;
    }

    public void setRespFecha(Date respFecha) {
        this.respFecha = respFecha;
    }

    public Usuarios getRespUsrId() {
        return respUsrId;
    }

    public void setRespUsrId(Usuarios respUsrId) {
        this.respUsrId = respUsrId;
    }

    public Comentarios getComentarios() {
        return comentarios;
    }

    public void setComentarios(Comentarios comentarios) {
        this.comentarios = comentarios;
    }

    public Comentarios getComentarios1() {
        return comentarios1;
    }

    public void setComentarios1(Comentarios comentarios1) {
        this.comentarios1 = comentarios1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestascomentPK != null ? respuestascomentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuestascoment)) {
            return false;
        }
        Respuestascoment other = (Respuestascoment) object;
        if ((this.respuestascomentPK == null && other.respuestascomentPK != null) || (this.respuestascomentPK != null && !this.respuestascomentPK.equals(other.respuestascomentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Respuestascoment[ respuestascomentPK=" + respuestascomentPK + " ]";
    }
    
}

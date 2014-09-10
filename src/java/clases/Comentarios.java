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
import javax.persistence.Lob;
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
@Table(name = "Comentarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentarios.findAll", query = "SELECT c FROM Comentarios c"),
    @NamedQuery(name = "Comentarios.findByComentId", query = "SELECT c FROM Comentarios c WHERE c.comentId = :comentId"),
    @NamedQuery(name = "Comentarios.findByComentFecha", query = "SELECT c FROM Comentarios c WHERE c.comentFecha = :comentFecha")})
public class Comentarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "coment_id")
    private Integer comentId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 2147483647)
    @Column(name = "coment_descripcion")
    private String comentDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "coment_fecha")
    @Temporal(TemporalType.DATE)
    private Date comentFecha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comentarios")
    private Collection<Respuestascoment> respuestascomentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comentarios1")
    private Collection<Respuestascoment> respuestascomentCollection1;
    @JoinColumn(name = "coment_usr_id", referencedColumnName = "usr_id")
    @ManyToOne(optional = false)
    private Usuarios comentUsrId;
    @JoinColumn(name = "coment_foro_id", referencedColumnName = "foro_id")
    @ManyToOne(optional = false)
    private Foros comentForoId;

    public Comentarios() {
    }

    public Comentarios(Integer comentId) {
        this.comentId = comentId;
    }

    public Comentarios(Integer comentId, String comentDescripcion, Date comentFecha) {
        this.comentId = comentId;
        this.comentDescripcion = comentDescripcion;
        this.comentFecha = comentFecha;
    }

    public Integer getComentId() {
        return comentId;
    }

    public void setComentId(Integer comentId) {
        this.comentId = comentId;
    }

    public String getComentDescripcion() {
        return comentDescripcion;
    }

    public void setComentDescripcion(String comentDescripcion) {
        this.comentDescripcion = comentDescripcion;
    }

    public Date getComentFecha() {
        return comentFecha;
    }

    public void setComentFecha(Date comentFecha) {
        this.comentFecha = comentFecha;
    }

    @XmlTransient
    public Collection<Respuestascoment> getRespuestascomentCollection() {
        return respuestascomentCollection;
    }

    public void setRespuestascomentCollection(Collection<Respuestascoment> respuestascomentCollection) {
        this.respuestascomentCollection = respuestascomentCollection;
    }

    @XmlTransient
    public Collection<Respuestascoment> getRespuestascomentCollection1() {
        return respuestascomentCollection1;
    }

    public void setRespuestascomentCollection1(Collection<Respuestascoment> respuestascomentCollection1) {
        this.respuestascomentCollection1 = respuestascomentCollection1;
    }

    public Usuarios getComentUsrId() {
        return comentUsrId;
    }

    public void setComentUsrId(Usuarios comentUsrId) {
        this.comentUsrId = comentUsrId;
    }

    public Foros getComentForoId() {
        return comentForoId;
    }

    public void setComentForoId(Foros comentForoId) {
        this.comentForoId = comentForoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comentId != null ? comentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentarios)) {
            return false;
        }
        Comentarios other = (Comentarios) object;
        if ((this.comentId == null && other.comentId != null) || (this.comentId != null && !this.comentId.equals(other.comentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Comentarios[ comentId=" + comentId + " ]";
    }
    
}

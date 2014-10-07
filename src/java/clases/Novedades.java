/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author entorno
 */
@Entity
@Table(name = "Novedades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Novedades.findAll", query = "SELECT n FROM Novedades n"),
    @NamedQuery(name = "Novedades.findByNovedadId", query = "SELECT n FROM Novedades n WHERE n.novedadId = :novedadId"),
    @NamedQuery(name = "Novedades.findByNovedadTitulo", query = "SELECT n FROM Novedades n WHERE n.novedadTitulo = :novedadTitulo"),
    @NamedQuery(name = "Novedades.findByNovedadContenido", query = "SELECT n FROM Novedades n WHERE n.novedadContenido = :novedadContenido")})
public class Novedades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "novedad_id")
    private Integer novedadId;
    @Size(max = 120)
    @Column(name = "novedad_titulo")
    private String novedadTitulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "novedad_contenido")
    private String novedadContenido;
    @JoinColumn(name = "novedad_evento_id", referencedColumnName = "evento_id")
    @ManyToOne(optional = false)
    private Eventos novedadEventoId;

    public Novedades() {
    }

    public Novedades(Integer novedadId) {
        this.novedadId = novedadId;
    }

    public Novedades(Integer novedadId, String novedadContenido) {
        this.novedadId = novedadId;
        this.novedadContenido = novedadContenido;
    }

    public Integer getNovedadId() {
        return novedadId;
    }

    public void setNovedadId(Integer novedadId) {
        this.novedadId = novedadId;
    }

    public String getNovedadTitulo() {
        return novedadTitulo;
    }

    public void setNovedadTitulo(String novedadTitulo) {
        this.novedadTitulo = novedadTitulo;
    }

    public String getNovedadContenido() {
        return novedadContenido;
    }

    public void setNovedadContenido(String novedadContenido) {
        this.novedadContenido = novedadContenido;
    }

    public Eventos getNovedadEventoId() {
        return novedadEventoId;
    }

    public void setNovedadEventoId(Eventos novedadEventoId) {
        this.novedadEventoId = novedadEventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (novedadId != null ? novedadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Novedades)) {
            return false;
        }
        Novedades other = (Novedades) object;
        if ((this.novedadId == null && other.novedadId != null) || (this.novedadId != null && !this.novedadId.equals(other.novedadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Novedades[ novedadId=" + novedadId + " ]";
    }
    
}

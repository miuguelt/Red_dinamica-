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
import javax.persistence.Lob;
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
@Table(name = "Ponencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ponencias.findAll", query = "SELECT p FROM Ponencias p"),
    @NamedQuery(name = "Ponencias.findByPonenciasPonenteId", query = "SELECT p FROM Ponencias p WHERE p.ponenciasPK.ponenciasPonenteId = :ponenciasPonenteId"),
    @NamedQuery(name = "Ponencias.findByPonenciasEventoId", query = "SELECT p FROM Ponencias p WHERE p.ponenciasPK.ponenciasEventoId = :ponenciasEventoId"),
    @NamedQuery(name = "Ponencias.findByPonenciaTitulo", query = "SELECT p FROM Ponencias p WHERE p.ponenciaTitulo = :ponenciaTitulo"),
    @NamedQuery(name = "Ponencias.findByPonenciasConcepto", query = "SELECT p FROM Ponencias p WHERE p.ponenciasConcepto = :ponenciasConcepto"),
    @NamedQuery(name = "Ponencias.findByPonenciasConcepto1", query = "SELECT p FROM Ponencias p WHERE p.ponenciasConcepto1 = :ponenciasConcepto1")})
public class Ponencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PonenciasPK ponenciasPK;
    @Size(max = 200)
    @Column(name = "ponencia_titulo")
    private String ponenciaTitulo;
    @Column(name = "ponencias_concepto")
    private Integer ponenciasConcepto;
    @Column(name = "ponencias_concepto1")
    private Integer ponenciasConcepto1;
    @Lob
    @Size(max = 16777215)
    @Column(name = "ponencia_correpciones")
    private String ponenciaCorrepciones;
    @Lob
    @Size(max = 16777215)
    @Column(name = "ponencia_correpciones1")
    private String ponenciaCorrepciones1;
    @JoinColumn(name = "ponencias_ponente_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @JoinColumn(name = "ponencias_evento_id", referencedColumnName = "evento_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eventos eventos;
    @JoinColumn(name = "ponencias_evaluador_id", referencedColumnName = "usr_id")
    @ManyToOne
    private Usuarios ponenciasEvaluadorId;
    @JoinColumn(name = "ponencias_evaluador1_id", referencedColumnName = "usr_id")
    @ManyToOne
    private Usuarios ponenciasEvaluador1Id;

    public Ponencias() {
    }

    public Ponencias(PonenciasPK ponenciasPK) {
        this.ponenciasPK = ponenciasPK;
    }

    public Ponencias(int ponenciasPonenteId, int ponenciasEventoId) {
        this.ponenciasPK = new PonenciasPK(ponenciasPonenteId, ponenciasEventoId);
    }

    public PonenciasPK getPonenciasPK() {
        return ponenciasPK;
    }

    public void setPonenciasPK(PonenciasPK ponenciasPK) {
        this.ponenciasPK = ponenciasPK;
    }

    public String getPonenciaTitulo() {
        return ponenciaTitulo;
    }

    public void setPonenciaTitulo(String ponenciaTitulo) {
        this.ponenciaTitulo = ponenciaTitulo;
    }

    public Integer getPonenciasConcepto() {
        return ponenciasConcepto;
    }

    public void setPonenciasConcepto(Integer ponenciasConcepto) {
        this.ponenciasConcepto = ponenciasConcepto;
    }

    public Integer getPonenciasConcepto1() {
        return ponenciasConcepto1;
    }

    public void setPonenciasConcepto1(Integer ponenciasConcepto1) {
        this.ponenciasConcepto1 = ponenciasConcepto1;
    }

    public String getPonenciaCorrepciones() {
        return ponenciaCorrepciones;
    }

    public void setPonenciaCorrepciones(String ponenciaCorrepciones) {
        this.ponenciaCorrepciones = ponenciaCorrepciones;
    }

    public String getPonenciaCorrepciones1() {
        return ponenciaCorrepciones1;
    }

    public void setPonenciaCorrepciones1(String ponenciaCorrepciones1) {
        this.ponenciaCorrepciones1 = ponenciaCorrepciones1;
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

    public Usuarios getPonenciasEvaluadorId() {
        return ponenciasEvaluadorId;
    }

    public void setPonenciasEvaluadorId(Usuarios ponenciasEvaluadorId) {
        this.ponenciasEvaluadorId = ponenciasEvaluadorId;
    }

    public Usuarios getPonenciasEvaluador1Id() {
        return ponenciasEvaluador1Id;
    }

    public void setPonenciasEvaluador1Id(Usuarios ponenciasEvaluador1Id) {
        this.ponenciasEvaluador1Id = ponenciasEvaluador1Id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ponenciasPK != null ? ponenciasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ponencias)) {
            return false;
        }
        Ponencias other = (Ponencias) object;
        if ((this.ponenciasPK == null && other.ponenciasPK != null) || (this.ponenciasPK != null && !this.ponenciasPK.equals(other.ponenciasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.Ponencias[ ponenciasPK=" + ponenciasPK + " ]";
    }
    
}

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
public class FormapartePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "forma_usr_id")
    private int formaUsrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "forma_colect_id")
    private int formaColectId;

    public FormapartePK() {
    }

    public FormapartePK(int formaUsrId, int formaColectId) {
        this.formaUsrId = formaUsrId;
        this.formaColectId = formaColectId;
    }

    public int getFormaUsrId() {
        return formaUsrId;
    }

    public void setFormaUsrId(int formaUsrId) {
        this.formaUsrId = formaUsrId;
    }

    public int getFormaColectId() {
        return formaColectId;
    }

    public void setFormaColectId(int formaColectId) {
        this.formaColectId = formaColectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) formaUsrId;
        hash += (int) formaColectId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormapartePK)) {
            return false;
        }
        FormapartePK other = (FormapartePK) object;
        if (this.formaUsrId != other.formaUsrId) {
            return false;
        }
        if (this.formaColectId != other.formaColectId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clases.FormapartePK[ formaUsrId=" + formaUsrId + ", formaColectId=" + formaColectId + " ]";
    }
    
}

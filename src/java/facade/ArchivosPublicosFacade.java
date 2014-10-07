/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import clases.ArchivosPublicos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author entorno
 */
@Stateless
public class ArchivosPublicosFacade extends AbstractFacade<ArchivosPublicos> {
    @PersistenceContext(unitName = "red_dinamicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivosPublicosFacade() {
        super(ArchivosPublicos.class);
    }
    
}

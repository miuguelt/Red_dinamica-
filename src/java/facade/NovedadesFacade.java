/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import clases.Novedades;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author entorno
 */
@Stateless
public class NovedadesFacade extends AbstractFacade<Novedades> {
    @PersistenceContext(unitName = "red_dinamicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadesFacade() {
        super(Novedades.class);
    }
    
}

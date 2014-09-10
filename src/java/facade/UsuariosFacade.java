/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import clases.Usuarios;
import controllers.util.JsfUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author entorno
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> {

    @PersistenceContext(unitName = "red_dinamicaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    public void actulizarEm(Usuarios usuario) {

        em.merge(usuario);
        JsfUtil.addSuccessMessage("Merge");
        try {
            em.flush();
            em.clear();
        } catch (Exception e) {
            JsfUtil.addSuccessMessage("Error actuliar em");
            em.refresh(usuario);
            em.clear();
            em.getEntityManagerFactory().getCache().evictAll();
        }
    }


    public boolean validarLogueo(String cedula, String contrasena) {
        int ced = Integer.parseInt(cedula);
        String sentencia = "SELECT * FROM Usuarios u WHERE u.usrId = '" + ced + "' AND u.usrPassword = '" + contrasena + "'";
        System.out.println(sentencia);
        Query q = em.createNativeQuery(sentencia, Usuarios.class);
        return q.getResultList().size() == 1;
    }

    public List<Usuarios> buscarUsuariosActivos() {
        try {
            String sentencia = "SELECT * FROM Usuarios u WHERE  u.usr_estado = 1";
            Query q = em.createNativeQuery(sentencia, Usuarios.class);
            return q.getResultList();

        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al realizar la consulta en la BD: " + e + "\nLocalize:" + e.getLocalizedMessage(), "   " + e.getLocalizedMessage()));
        }
        return null;
    }

}

package controllers;

import clases.Colectivos;
import clases.Formaparte;
import clases.Foros;
import clases.Usuarios;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.ColectivosFacade;
import facade.FormaparteFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("colectivosController")
@SessionScoped
public class ColectivosController implements Serializable {

    @EJB
    private facade.ColectivosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ColectivosController() {
    }

    public Colectivos getSelected() {
        if (current == null) {
            current = new Colectivos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ColectivosFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Colectivos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Colectivos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColectivosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Colectivos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColectivosDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Colectivos getColectivos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Colectivos.class)
    public static class ColectivosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ColectivosController controller = (ColectivosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "colectivosController");
            return controller.getColectivos(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Colectivos) {
                Colectivos o = (Colectivos) object;
                return getStringKey(o.getColectId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Colectivos.class.getName());
            }
        }
    }
    //CODIGO PERSONAL
    private Colectivos current;
    private static Usuarios usuario = UsuariosController.getUsurioActual(); //Puede ser el current o el usuario seleccionado en perfiles
    private static Colectivos colectivoActual;
    private DataModel items = null;
    private DataModel itemsAdministrador = null;
    private DataModel itemsColaborador = null;
    private List<Colectivos> colectivosAllList;
    @EJB
    private facade.FormaparteFacade ejbFormaParteFacade;

    public FormaparteFacade getFormaParteFacade() {
        return ejbFormaParteFacade;
    }

    @PostConstruct
    public void init() {
        setColectivosAllList(getFacade().findAll());
        asignarBoton();
    }
    
    public void asignarUsuarioActual(){
        setUsuario(UsuariosController.getUsurioActual());
    }

    public static Usuarios getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuarios usuario) {
        ColectivosController.usuario = usuario;
    }

    public void asignarBoton() {
        List<Formaparte> auxList = new ArrayList<>(getUsuario().getFormaparteCollection());
        for (int i = 0; i < getUsuario().getFormaparteCollection().size(); i++) {
            colectivosAllList.get(colectivosAllList.indexOf(auxList.get(i).getColectivos())).setColectEstado(Boolean.TRUE);
        }
        auxList.clear();
        List<Colectivos> auxListC = new ArrayList<>(getUsuario().getColectivosCollection());
        for (int i = 0; i < getUsuario().getColectivosCollection().size(); i++) {
            colectivosAllList.get(colectivosAllList.indexOf(auxListC.get(i))).setColectEstado(Boolean.TRUE);
        }
        auxListC.clear();
    }

    public static Colectivos getColectivoActual() {
        return colectivoActual;
    }

    public static void setColectivoActual(Colectivos colectivoActual) {
        ColectivosController.colectivoActual = colectivoActual;
    }

    public void setColectivosAllList(List<Colectivos> colectivos) {
        this.colectivosAllList = colectivos;
    }

    public DataModel getItemsAdministrador() {
        return itemsAdministrador = new ListDataModel(new ArrayList<>(getUsuario().getColectivosCollection()));
    }

    public DataModel getItemsColaborador() {
        return itemsColaborador = new ListDataModel(new ArrayList<>(getUsuario().getFormaparteCollection()));
    }

    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
        return items = new ListDataModel(colectivosAllList);
    }

    public void setColectivo(Colectivos colectivo) throws IOException {
        setColectivoActual(colectivo);
        ForosController.setHayForo(false);
        JsfUtil.addSuccessMessage("Segundo mensaje");
        ComentariosController.setForoActual(new Foros());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/red_dinamica/faces/web/foros/index.xhtml");
    }

    public void prepareCreate() {
        current = new Colectivos();
        selectedItemIndex = -1;
    }

    public void create() {
        try {
            asignarTodo();
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Nuevo colectivo Creado");
            UsuariosController.getUsurioActual().getColectivosCollection().add(current);
            colectivosAllList.add(current);
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/red_dinamica/faces/web/foros/List.xhtml");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void asignarTodo() {
        current.setColectFecha(new Date());
        current.setColectEstado(false);
        current.setColectUsrId(getUsuario());
    }

    public void AsignarEstado(Colectivos colectivo) { //Agrega el colectivo a la lista personal
        Formaparte formaparte = new Formaparte(new clases.FormapartePK());
        formaparte.setColectivos(colectivo);
        formaparte.setFormaEstado(true);
        formaparte.setUsuarios(getUsuario());
        formaparte.getFormapartePK().setFormaUsrId(formaparte.getUsuarios().getUsrId());
        formaparte.getFormapartePK().setFormaColectId(formaparte.getColectivos().getColectId());
        getFormaParteFacade().create(formaparte);
        UsuariosController.getUsurioActual().getFormaparteCollection().add(formaparte);
        colectivosAllList.get(colectivosAllList.indexOf(colectivo)).setColectEstado(true);
        JsfUtil.addSuccessMessage("Colectivo Agregado a la Lista Personal");
    }
}
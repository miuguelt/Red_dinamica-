package controllers;

import clases.Colectivos;
import clases.Foros;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.ForosFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;

@Named("forosController")
@ViewScoped
public class ForosController implements Serializable {

    private DataModel items = null;
    @EJB
    private facade.ForosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ForosController() {
    }

    public Foros getSelected() {
        if (current == null) {
            current = new Foros();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ForosFacade getFacade() {
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
        current = (Foros) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Foros) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ForosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Foros) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ForosDeleted"));
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

    public Foros getForos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Foros.class)
    public static class ForosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ForosController controller = (ForosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "forosController");
            return controller.getForos(getKey(value));
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
            if (object instanceof Foros) {
                Foros o = (Foros) object;
                return getStringKey(o.getForoId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Foros.class.getName());
            }
        }

    }

    //CODIGO PERSONAL
    private Foros current;
    private Colectivos colectivoActual = ColectivosController.getColectivoActual();
    private Foros foroSelect = new Foros();
    private String titulo=colectivoActual.getColectTitulo();

    public String getTitulo() {
        return titulo;
    }
    
    public Colectivos getColectivoActual() {
        return colectivoActual;
    }
    
    public Foros getForoSelect() {
        return foroSelect;
    }

    public void setForoSelect(Foros foroSelect){
        this.foroSelect = foroSelect;
    }

    public boolean isForoSelected(){
        if (getForoSelect().getForoId() == null) return false;
        else return true;
    }
            
    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
        return items = new ListDataModel((List) getColectivoActual().getForosCollection());
    }

    public void prepareCreate() {
        current = new Foros();
        selectedItemIndex = -1;
    }

    public void create() {
        try {
            asignarTodo();
            getFacade().create(current);
            if (!getColectivoActual().getForosCollection().isEmpty()) {
                recreateModel();
                getColectivoActual().getForosCollection().add(current);
            } else {
                List<Foros> lista = new ArrayList<>();
                lista.add(current);
                getColectivoActual().setForosCollection(lista);
            }
            setForoSelect(current);
            prepareCreate();
            JsfUtil.addSuccessMessage("Nuevo foro creado");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void asignarTodo() {
        current.setForoFecha(new Date());
        current.setForoUsrId(UsuariosController.getUsuarioActual());
        current.setForoColectId(getColectivoActual());
    }

    public void RowSelect() {
        JsfUtil.addSuccessMessage("Row select");
        ComentariosController.setForoActual(foroSelect);
    }
}
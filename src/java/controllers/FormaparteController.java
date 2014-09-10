package controllers;

import clases.Formaparte;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.FormaparteFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
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

@Named("formaparteController")
@SessionScoped
public class FormaparteController implements Serializable {

    private Formaparte current;
    private DataModel items = null;
    @EJB
    private facade.FormaparteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public FormaparteController() {
    }

    public Formaparte getSelected() {
        if (current == null) {
            current = new Formaparte();
            current.setFormapartePK(new clases.FormapartePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private FormaparteFacade getFacade() {
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
        current = (Formaparte) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Formaparte();
        current.setFormapartePK(new clases.FormapartePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getFormapartePK().setFormaUsrId(current.getUsuarios().getUsrId());
            current.getFormapartePK().setFormaColectId(current.getColectivos().getColectId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaparteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Formaparte) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getFormapartePK().setFormaUsrId(current.getUsuarios().getUsrId());
            current.getFormapartePK().setFormaColectId(current.getColectivos().getColectId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaparteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Formaparte) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FormaparteDeleted"));
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
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

    public Formaparte getFormaparte(clases.FormapartePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Formaparte.class)
    public static class FormaparteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FormaparteController controller = (FormaparteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "formaparteController");
            return controller.getFormaparte(getKey(value));
        }

        clases.FormapartePK getKey(String value) {
            clases.FormapartePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new clases.FormapartePK();
            key.setFormaUsrId(Integer.parseInt(values[0]));
            key.setFormaColectId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(clases.FormapartePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFormaUsrId());
            sb.append(SEPARATOR);
            sb.append(value.getFormaColectId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Formaparte) {
                Formaparte o = (Formaparte) object;
                return getStringKey(o.getFormapartePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Formaparte.class.getName());
            }
        }
    }
}

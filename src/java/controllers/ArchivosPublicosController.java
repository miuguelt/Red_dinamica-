package controllers;

import clases.ArchivosPublicos;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.ArchivosPublicosFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("archivosPublicosController")
@ViewScoped
public class ArchivosPublicosController implements Serializable {

    private ArchivosPublicos current;
    private DataModel items = null;
    @EJB
    private facade.ArchivosPublicosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ArchivosPublicosController() {
    }

    private ArchivosPublicosFacade getFacade() {
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
        current = (ArchivosPublicos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (ArchivosPublicos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivosPublicosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ArchivosPublicos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivosPublicosDeleted"));
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
        return items = new ListDataModel(getFacade().findAll());
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

    public ArchivosPublicos getArchivosPublicos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ArchivosPublicos.class)
    public static class ArchivosPublicosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArchivosPublicosController controller = (ArchivosPublicosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "archivosPublicosController");
            return controller.getArchivosPublicos(getKey(value));
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
            if (object instanceof ArchivosPublicos) {
                ArchivosPublicos o = (ArchivosPublicos) object;
                return getStringKey(o.getArchivoId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ArchivosPublicos.class.getName());
            }
        }
    }
    
    //MI CODIGO
    private ArchivosPublicos archivoPublico;

    public ArchivosPublicos getArchivoPublico() {
        return archivoPublico;
    }

    public void setArchivoPublico(ArchivosPublicos archivoPublico) {
        this.archivoPublico = archivoPublico;
    }
    
    public void prepareCreate() {
        this.archivoPublico = new ArchivosPublicos();
        getArchivoPublico().setArchivoUsrId(UsuariosController.getUsuarioActual());
    }
    
    public ArchivosPublicos getSelected() {
        return archivoPublico;
    }

    public void create() {
        try {
            getFacade().create(getArchivoPublico());
            JsfUtil.addSuccessMessage("Archivo subido con exito");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured")+"   "+e);
        }
    }
    
    //Subir archivos
    private UploadedFile file;
    private InputStream in;

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }
    
    public void handleFileUploadAfiche(FileUploadEvent event) throws IOException, URISyntaxException { //Subir afiche de Evento
        setIn(event.getFile().getInputstream());
        setFile(event.getFile());
//        TransferFile("afiche", "" + getEventoSelect().getEventoId());
        JsfUtil.addSuccessMessage("Afiche subido");
    }

    public String getExtencion(String FileName) {
        String extValidate;
        String ext = FileName;
        if (ext != null) {
            extValidate = ext.substring(ext.indexOf(".") + 1);
        } else {
            extValidate = null;
        }
        return extValidate;
    }

    public String ruta() throws UnsupportedEncodingException { //Retorna la direccion de un archivo para ubicar una ruta
        String rutaca = ArchivosController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        rutaca = URLDecoder.decode(rutaca, "utf-8");
        String[] base = rutaca.split("/");
        String direccion = "";
        for (int i = 0; i < base.length - 6; i++) {
            direccion = direccion + base[i] + "/";
        }
        return direccion + "web/Usuarios/eventos/";
    }

    public String TransferFile(String nombreArchivo, String directorioEvento) {
        try {
            String direccion = ruta() + directorioEvento;
            //Crear carpeta de usuarios
            File folder = new File(direccion);
            if (!folder.exists()) {
                folder.mkdirs(); // esto crea la carpeta java, independientemente que exista el path completo, si no existe crea toda la ruta necesaria                 
            }
            OutputStream out = new FileOutputStream(new File(direccion + "/" + nombreArchivo));
            int read;
            byte[] bytes = new byte[(int) getFile().getSize()];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            return direccion;
        } catch (IOException e) {
            JsfUtil.addErrorMessage("Error al subir el archivo");
            return null;
        }
    }
    
}

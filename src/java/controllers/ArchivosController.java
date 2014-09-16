package controllers;

import clases.Archivos;
import clases.Colectivos;
import clases.Usuarios;
import clases.Version;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.ArchivosFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("archivosController")
@ViewScoped
public class ArchivosController implements Serializable {

    private Archivos current;
    private DataModel items = null;
    @EJB
    private facade.ArchivosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ArchivosController() {
    }

    public Archivos getSelected() {
        if (current == null) {
            current = new Archivos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ArchivosFacade getFacade() {
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
        current = (Archivos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Archivos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivosUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Archivos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivosDeleted"));
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

    public Archivos getArchivos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Archivos.class)
    public static class ArchivosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArchivosController controller = (ArchivosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "archivosController");
            return controller.getArchivos(getKey(value));
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
            if (object instanceof Archivos) {
                Archivos o = (Archivos) object;
                return getStringKey(o.getArchivoId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Archivos.class.getName());
            }
        }
    }
    //CODIGO PERSONAL
    @EJB
    private facade.VersionFacade ejbVersionFacade;
    private Archivos archivoSelect;
    private Colectivos colectivoActual = ColectivosController.getColectivoActual();
    private Usuarios usuarioActual = UsuariosController.getUsuarioActual();
    private DataModel itemsUsuario = null;

    public DataModel getItemsUsuario() {
        return itemsUsuario = new ListDataModel((List) getUsuarioActual().getArchivosCollection());
    }

    public Colectivos getColectivoActual() {
        return colectivoActual;
    }
    
    public Usuarios getUsuarioActual() {
        return usuarioActual;
    }

    public Archivos getArchivoSelect() {
        return archivoSelect;
    }

    public void setArchivoSelect(Archivos archivoSelect) {
        this.archivoSelect = archivoSelect;
    }

    public void setArchivoSelectVista() {
        setArchivoSelect(null);
    }

    public DataModel getItems() {
//        if (items == null) {
//            items = getPagination().createPageDataModel();
//        }
        return items = new ListDataModel((List) getColectivoActual().getArchivosCollection());
    }

    public String prepareCreate() {
        current = new Archivos();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            asignarTodo();
            getFacade().create(current);
            Version version = new Version();
            version.setVersionArchivoId(current);
            version.setVersionFecha(new Date());
            version.setVersionNumero(1);
            version.setVersionUsrId(getUsuarioActual());
            ejbVersionFacade.create(version);
            List<Version> listaV = new ArrayList<>();
            listaV.add(version);
            current.setVersionCollection(listaV);
            if (!getColectivoActual().getArchivosCollection().isEmpty()) {
                getColectivoActual().getArchivosCollection().add(current);
            } else {
                List<Archivos> lista = new ArrayList<>();
                lista.add(current);
                getColectivoActual().setArchivosCollection(lista);
            }
            setArchivoSelect(current);
            VersionController.setArchivoSelect(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void asignarTodo() {
        current.setArchivoUsrId(getUsuarioActual());
        current.setArchivoColectivoId(getColectivoActual());
        current.setArchivoVisitas(0);
    }

    public void rowSelect() {
        JsfUtil.addSuccessMessage("Row select");
        VersionController.setArchivoSelect(getArchivoSelect());
    }
    //Subir Archivos
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

    public void prueba() {
        JsfUtil.addSuccessMessage("entra");
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException, URISyntaxException {
            setIn(event.getFile().getInputstream());
            setFile(event.getFile());
            TransferFile(16);
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

    public String ruta() throws UnsupportedEncodingException {
        String rutaca = ArchivosController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        rutaca = URLDecoder.decode(rutaca, "utf-8");
        String[] base = rutaca.split("/");
        String direccion = "";
        for (int i = 0; i < base.length - 6; i++) {
            direccion = direccion + base[i] + "/";
        }
        return direccion;
    }

    public void TransferFile(int usrId) {
        try {
            String direccion = ruta() + "web/" + "Usuarios/" + "Privado/" + usrId;
            //Crear carpeta de usuarios
            File folder = new File(direccion);
            if (!folder.exists()) {
                folder.mkdirs(); // esto crea la carpeta java, independientemente que exista el path completo, si no existe crea toda la ruta necesaria                 
            }
            JsfUtil.addSuccessMessage("Después de folder:" + ruta());
            OutputStream out = new FileOutputStream(new File(direccion + "/" + getFile().getFileName()));
            int read;
            byte[] bytes = new byte[(int) getFile().getSize()];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();

            JsfUtil.addSuccessMessage("Direccion " + direccion);
        } catch (IOException e) {
            JsfUtil.addErrorMessage("Error al subir el archivo");
        }
    }
}

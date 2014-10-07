package controllers;

import clases.Noticias;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.NoticiasFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Date;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named("noticiasController")
@ViewScoped
public class NoticiasController implements Serializable {

    private Noticias current;
    private DataModel items = null;
    @EJB
    private facade.NoticiasFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public NoticiasController() {
    }

    public Noticias getSelected() {
        if (current == null) {
            current = new Noticias();
            selectedItemIndex = -1;
        }
        return current;
    }

    private NoticiasFacade getFacade() {
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
        current = (Noticias) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public void prepareCreate() {
        current = new Noticias();

    }

    public void create() {
        try {
            current.setNoticiaUsrId(UsuariosController.getUsuarioActual());
            current.setNoticiaImagen("i");
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Noticia creada con éxito");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String prepareEdit() {
        current = (Noticias) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Noticia actualizada");
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Noticias) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("NoticiasDeleted"));
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

        return items = new ListDataModel(ejbFacade.findAll());
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

    public Noticias getNoticias(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Noticias.class)
    public static class NoticiasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            NoticiasController controller = (NoticiasController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "noticiasController");
            return controller.getNoticias(getKey(value));
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
            if (object instanceof Noticias) {
                Noticias o = (Noticias) object;
                return getStringKey(o.getNoticiaId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Noticias.class.getName());
            }
        }
    }
    //MI CÓDIGO
    private Noticias noticiaSelect;

    public Noticias getNoticiaSelect() {
        return noticiaSelect;
    }

    public void setNoticiaSelect(Noticias noticiaSelect) {
        this.noticiaSelect = noticiaSelect;
    }

    public void eliminarNoticia(Noticias noticia) {
        getFacade().remove(noticia);
        JsfUtil.addSuccessMessage("Noticia eliminada");
    }

    public void editarNoticia(Noticias noticia) {
        this.noticiaSelect = noticia;

    }

    public void editar() {
       try {
            ejbFacade.edit(noticiaSelect);
            JsfUtil.addSuccessMessage("Noticia Editada");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al editar la noticia: " + e);
        }
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

    public void handleFileUploadNoticiaFoto(FileUploadEvent event) throws IOException, URISyntaxException { //Cambiar foto
        try {
            current.setNoticiaUsrId(UsuariosController.getUsuarioActual());
            current.setNoticiaImagen("i");
            current.setNoticiaFecha(this.fecha);
            create();
            setIn(event.getFile().getInputstream());
            setFile(event.getFile());
            TransferFile(current.getNoticiaId(), "noticias");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error: " + e);
        }
    }

    public void handleFilereUploadNoticiaFoto(FileUploadEvent event) throws IOException, URISyntaxException { //Cambiar foto
        try {
            //noticiaSelect.setNoticiaFecha(this.fecha);
            ejbFacade.edit(noticiaSelect);
            setIn(event.getFile().getInputstream());
            setFile(event.getFile());
            TransferFile(current.getNoticiaId(), "noticias");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al cargar el archivo: " + e);
        }
    }

    public String TransferFile(int nombreArchivo, String directorioTipo) {
        try {
            String direccion = ruta() + directorioTipo;
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

    public String ruta() throws UnsupportedEncodingException { //Retorna la direccion de un archivo para ubicar una ruta
        String rutaca = ArchivosController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        rutaca = URLDecoder.decode(rutaca, "utf-8");
        String[] base = rutaca.split("/");
        String direccion = "";
        for (int i = 0; i < base.length - 6; i++) {
            direccion = direccion + base[i] + "/";
        }
        return direccion + "web/" + "Usuarios/";
    }
    //Variables para guardar antes de subir el archivo
    private String titulo, descripcion, fuente;
    private Date fecha;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void leerMas(Noticias noticia){
        this.noticiaSelect= noticia;
    }
}

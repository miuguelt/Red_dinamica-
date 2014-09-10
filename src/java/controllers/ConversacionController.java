package controllers;

import clases.Conversacion;
import clases.Mensaje;
import clases.Usuarios;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import facade.ConversacionFacade;
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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;

@Named("conversacionController")
@SessionScoped
public class ConversacionController implements Serializable {

    private static Conversacion current;
    private DataModel items = null;
    @EJB
    private facade.ConversacionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ConversacionController() {
    }

    public Conversacion getSelected() {
        if (current == null) {
            current = new Conversacion();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ConversacionFacade getFacade() {
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
        current = (Conversacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Conversacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConversacionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Conversacion) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConversacionDeleted"));
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

    public Conversacion getConversacion(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Conversacion.class)
    public static class ConversacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConversacionController controller = (ConversacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "conversacionController");
            return controller.getConversacion(getKey(value));
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
            if (object instanceof Conversacion) {
                Conversacion o = (Conversacion) object;
                return getStringKey(o.getConvId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Conversacion.class.getName());
            }
        }
    }
    //CODIGO PERSONAL
    private String textoMsj;
    private Mensaje nuevo;
    private Usuarios usrDestino;
    private Usuarios usrActual;
    private String ms;
    private Conversacion conv_select;
    private boolean existe_conv = false;
    private int cont_msj = 0;
    private List<Mensaje> nuevos;
    private List<Conversacion> listaConversacion;
    
    @PostConstruct
    public void init() {
//      cargarConversaciones();
    }
    
    public List<Conversacion> getListaConversacion() {
        return listaConversacion;
    }

    public void setListaConversacion(List<Conversacion> listaConversacion) {
        this.listaConversacion = listaConversacion;
    }

    public static Conversacion getCurrent() {
        return current;
    }

    public static void setCurrent(Conversacion current) {
        ConversacionController.current = current;
    }

    public void setUsrActual(Usuarios usrActual) {
        this.usrActual = usrActual;
    }

    public void setTextoMsj(String textoMsj) {
        this.textoMsj = textoMsj;
    }

    public void setNuevo(Mensaje nuevo) {
        this.nuevo = nuevo;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public void setConv_select(Conversacion conv_select) {
        this.conv_select = conv_select;
    }

    public void setExiste_conv(boolean existe_conv) {
        this.existe_conv = existe_conv;
    }

    public void setCont_msj(int cont_msj) {
        this.cont_msj = cont_msj;
    }

    public String getTextoMsj() {
        return textoMsj;
    }

    public Mensaje getNuevo() {
        return nuevo;
    }

    public Usuarios getUsrDestino() {
        return usrDestino;
    }

    public String getMs() {
        return ms;
    }

    public Conversacion getConv_select() {
        return conv_select;
    }

    public boolean isExiste_conv() {
        return existe_conv;
    }

    public int getCont_msj() {
        return cont_msj;
    }

    public List<Mensaje> getNuevos() {
        return nuevos;
    }

    public Usuarios getUsrActual() {
        return usrActual;
    }

    public void setUsrDestino(Usuarios usrDestino) throws IOException {
        try {
            this.usrDestino = usrDestino;
            setUsrActual(UsuariosController.getUsurioActual());
            setCurrent(null);
            cargarConversacion();
        } catch (IOException e) {
            JsfUtil.addErrorMessage("Error al asignar el usuario destino o al cargar la conversación");
        }
    }

    public void enviarMsj() {
        try {
            setExiste_conv(true);
            Date fechaActual = new Date();
            Mensaje mensaje = new Mensaje();
            mensaje.setMsjTexto(textoMsj);
            mensaje.setMsjConversacion(current);
            mensaje.setMsjFecha(fechaActual);
            mensaje.setMsjLeido(false);
            mensaje.setMsjRemitente(getUsrActual());
            mensaje.setMsjDestinatario(usrDestino);
            current.getMensajeCollection().add(mensaje);
            update();
            setConv_select(current);
            //JsfUtil.addSuccessMessage("Mensaje enviado!");

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error al crear la conversación: " + e + "  Localización: " + e.getLocalizedMessage() + ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
        JsfUtil.addSuccessMessage("Mi Mensaje");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String prepareCreate() {
        current = new Conversacion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConversacionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items = new ListDataModel(listaConversacion);
    }
    
    public void conversacioPru(){
        JsfUtil.addSuccessMessage("entra"+getUsrDestino());
    }
    
    public void cargarConversaciones() {
        try {
            if (listaConversacion == null) {
                setUsrActual(UsuariosController.getUsurioActual());
                listaConversacion = new ArrayList<>((List<Conversacion>) getUsrActual().getConversacionCollection());
                listaConversacion.addAll((List<Conversacion>) getUsrActual().getConversacionCollection1());
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al cargar las conversaciones del usuario... " + e);
        }
    }
    

    public void cargarConversacion() throws IOException {
        try {            
            for (int i = 0; i < getListaConversacion().size(); i++) {
                if ((getListaConversacion().get(i).getConvUsr1Id().getUsrId() == usrDestino.getUsrId()) || (getListaConversacion().get(i).getConvUsr2Id().getUsrId() == usrDestino.getUsrId())) {
                    setConv_select(getListaConversacion().get(i));
//                    setCurrent(getListaConversacion().get(i));
                    JsfUtil.addSuccessMessage("for Exites conv: " + getConv_select());
                } else {
                    JsfUtil.addSuccessMessage("for No existe conversacion");
                }
            }
            if (getCurrent() != null) { //Si la conversacion existe
                JsfUtil.addSuccessMessage("La conversacion existe");
                JsfUtil.addSuccessMessage("MEnsajes: " + new ArrayList<>(getCurrent().getMensajeCollection()));
                if (!getCurrent().getMensajeCollection().isEmpty()) { //Si hay mensages
                    JsfUtil.addSuccessMessage("Hay mensajes");
                    MensajeController.setMensaje_List((List<Mensaje>) getCurrent().getMensajeCollection());
                } else { //Si no hay mensages
                    JsfUtil.addSuccessMessage("No hay mensajes");
                    MensajeController.setMensaje_List(null);
                }
            } else { //Si la conversacion no existe
                MensajeController.setMensaje_List(null);
                JsfUtil.addSuccessMessage("La conversacion NO existe");
                current = new Conversacion();
                current.setConvAsunto(" ");
                current.setConvUltimo(new Date());
                current.setConvNumero(0);
                current.setConvUsr1Id(getUsrActual());
                current.setConvUsr2Id(getUsrDestino());
                getFacade().create(current);
                listaConversacion.add(current);
                items = new ListDataModel(getListaConversacion());
                JsfUtil.addSuccessMessage("Conversación nueva creada");
            }
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/red_dinamica/faces/web/conversacion/List.xhtml");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al cargar las conversaciones... : " + e + " Localize: " + e.getLocalizedMessage() + "  cause:   " + e.getCause());
        }
    }

    public void RowSelect1(SelectEvent event) {
        JsfUtil.addSuccessMessage("rowSelect1");
    }
    public void RowSelect(SelectEvent event) {
        try {
            JsfUtil.addSuccessMessage("Row select");
//            setConv_select((Conversacion) (event.getObject()));            
            setCurrent(getConv_select());
            this.usrDestino = getCurrent().getConvUsr2Id();
            setUsrActual(UsuariosController.getUsurioActual());
            JsfUtil.addSuccessMessage("Conversacion row Select: " + current.getConvUsr2Id().getUsrNombres());
//            cargarConversacion();
//            if (current.getConvUsr2Id().getUsrId() == getUsrActual().getUsrId()) {
//                setUsrDestino(current.getConvUsr1Id());
//            } else {
//                setUsrDestino(current.getConvUsr2Id());
//            }
            if (!getCurrent().getMensajeCollection().isEmpty()) { //Si la conversacion tiene mensajes
                JsfUtil.addSuccessMessage("Conversacion teiene mensajes");
                MensajeController.setMensaje_List((List<Mensaje>) getCurrent().getMensajeCollection());
            } else { //Si la conversacion No tiene mensajes
                JsfUtil.addSuccessMessage("Conversacion no tiene mensajes");
                MensajeController.setMensaje_List(null);
            }
            JsfUtil.addSuccessMessage("Colection isEmpty: " + current.getMensajeCollection().isEmpty());
            //leerMensajes();
//            JsfUtil.addSuccessMessage("Conversación seleccionada: " + ((Conversacion) event.getObject()).getConvId());
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al seleccionar la columna");
        }
    }
}

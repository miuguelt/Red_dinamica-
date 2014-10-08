package controllers;

import bean.Mail;
import clases.Agenda;
import clases.Conferencias;
import clases.Eventos;
import clases.Novedades;
import clases.Ponencias;
import clases.Usuarios;
import controllers.util.PaginationHelper;
import facade.ConferenciasFacade;
import facade.EventosFacade;
import facade.PonenciasFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import controllers.util.JsfUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.faces.context.FacesContext;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.jsp.tagext.TryCatchFinally;

@Named("eventosController")
@ViewScoped
public class EventosController implements Serializable {

    private Eventos current;
    private DataModel items = null;
    @EJB
    private facade.EventosFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public EventosController() {
    }

    public Eventos getSelected() {
        if (current == null) {
            current = new Eventos();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EventosFacade getFacade() {
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
        current = (Eventos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareEdit() {
        current = (Eventos) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String destroy() {
        current = (Eventos) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventosDeleted"));
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

    public Eventos getEventos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Eventos.class)
    public static class EventosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EventosController controller = (EventosController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eventosController");
            return controller.getEventos(getKey(value));
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
            if (object instanceof Eventos) {
                Eventos o = (Eventos) object;
                return getStringKey(o.getEventoId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Eventos.class.getName());
            }
        }
    }
    //////Mi codigo
    @EJB
    private facade.NovedadesFacade novedadEjbFacade;
    @EJB
    private facade.PonenciasFacade ponenciasEjbFacade;
    @EJB
    private facade.AgendaFacade agendaEjbFacade;
    @EJB
    private facade.ConferenciasFacade conferenciasEjbFacade;
    private Usuarios usuarioActual = UsuariosController.getUsuarioActual();
    private Usuarios usuarioSelect;
    private Usuarios usuarioEvaluador;
    private Ponencias ponencia;
    private Conferencias conferencia;
    private Agenda agenda = new Agenda();
    private String titulo;
    private String contenido;
    private boolean ponente = false;
    private boolean asistente = false;
    private boolean conferencista = false;
    private boolean admin = false;
    private Eventos eventoSelect = new Eventos();
    private DataModel itemsAsistes = null;
    private DataModel itemsAdmins = null;
    private DataModel itemsConferencistas = null;
    private DataModel itemsPonentes = null;
    private DataModel itemsAgenda = null;
    private DataModel itemsNovedades = null;
    private DataModel itemsEvaluarPonentes = null;
    private List<Ponencias> soyEvaluador;
    private List<Ponencias> soyEvaluador1;

    @PostConstruct
    public void init() {
        try {
            soyEvaluador = (List<Ponencias>) getUsuarioActual().getPonenciasCollection2();
            soyEvaluador1 = (List<Ponencias>) getUsuarioActual().getPonenciasCollection1();
//            soyEvaluador.addAll(soyEvaluador1);
        } catch (Exception e) {
        }
    }

    public void setSoyEvaluador(List<Ponencias> soyEvaluador) {
        this.soyEvaluador = soyEvaluador;
    }

    public DataModel getItemsEvaluarPonentes() {
        try {
            return itemsEvaluarPonentes = new ListDataModel(soyEvaluador);
        } catch (Exception e) {
            return itemsEvaluarPonentes = null;
        }
    }

    public DataModel getItemsNovedades() {
        return itemsNovedades = new ListDataModel((List) getEventoSelect().getNovedadesCollection());
    }

    public DataModel getItemsAgenda() {
        return itemsAgenda = new ListDataModel((List) getEventoSelect().getAgendaCollection());
    }

    public DataModel getItemsConferencistas() {
        return itemsConferencistas = new ListDataModel((List) getEventoSelect().getConferenciasCollection());
    }

    public DataModel getItemsPonentes() {
        return itemsPonentes = new ListDataModel((List) getEventoSelect().getPonenciasCollection());
    }

    public DataModel getItemsAsistes() {
        return itemsAsistes = new ListDataModel((List) getEventoSelect().getUsuariosCollection1());
    }

    public DataModel getItemsAdmins() {
        return itemsAdmins = new ListDataModel((List) getEventoSelect().getUsuariosCollection());
    }

    public PonenciasFacade getPonenciaEjbFacade() {
        return ponenciasEjbFacade;
    }

    public boolean isConferencista() {
        try {
            prepareEditConferencia();
            if (getEventoSelect().getConferenciasCollection().contains(getConferencia())) {
                return conferencista = true;
            } else {
                return conferencista = false;
            }
        } catch (Exception e) {
            return conferencista = false;
        }
    }

    public boolean isAsistente() {
        try {
            if (getEventoSelect().getUsuariosCollection1().contains(getUsuarioActual()) || isConferencista() || isPonente()) {
                return asistente = true;
            } else {
                return asistente = false;
            }
        } catch (Exception e) {
            return asistente = false;
        }
    }

    public boolean isPonente() {
        try {
            prepareCreatePonencia();
            if (getEventoSelect().getPonenciasCollection().contains(getPonencia())) {
                return ponente = true;
            } else {
                return ponente = false;
            }
        } catch (Exception e) {
            return ponente = false;
        }
    }

    public boolean isAdmin() {
        try {
            if (getEventoSelect().getUsuariosCollection().contains(getUsuarioActual())) {
                return admin = true;
            } else {
                return admin = false;
            }
        } catch (Exception e) {
            return admin = false;
        }
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }

    public Conferencias getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencias conferencia) {
        this.conferencia = conferencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String Titulo) {
        this.titulo = Titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Ponencias getPonencia() {
        return ponencia;
    }

    public void setPonencia(Ponencias ponencia) {
        this.ponencia = ponencia;
    }

    public ConferenciasFacade getConferenciasEjbFacade() {
        return conferenciasEjbFacade;
    }

    public Usuarios getUsuarioEvaluador() {
        return usuarioEvaluador;
    }

    public void setUsuarioEvaluador(Usuarios usuarioEvaluador) {
        this.usuarioEvaluador = usuarioEvaluador;
    }

    public Usuarios getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuarios usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public Usuarios getUsuarioActual() {
        return usuarioActual;
    }

    public Eventos getEventoSelect() {
        return eventoSelect;
    }

    public void setEventoSelect(Eventos eventoSelect) {
        this.eventoSelect = eventoSelect;
    }

    public void prepareListaEvaluar() {
        try {
            soyEvaluador.addAll(soyEvaluador1);
            soyEvaluador1.clear();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Inicie sesión");
        }
    }

    public void prepareEditConferencia() {
        conferencia = new Conferencias();
        getConferencia().setUsuarios(getUsuarioActual());
        getConferencia().setEventos(getEventoSelect());
        getConferencia().setConferenciasPK(new clases.ConferenciasPK());
        getConferencia().getConferenciasPK().setConferenciaUsrId(getConferencia().getUsuarios().getUsrId());
        getConferencia().getConferenciasPK().setConferenciaEventoId(getConferencia().getEventos().getEventoId());
    }

    public void prepareCreateAgendaConferencia() {
        setAgenda(new Agenda());
        getAgenda().setAgendaPK(new clases.AgendaPK());
        getAgenda().setUsuarios(getConferencia().getUsuarios());
        getAgenda().setEventos(getEventoSelect());
        getAgenda().setAgendaActividad(getConferencia().getConferenciaTitulo());
    }

    public void prepareCreateAgendaPonente() {
        setAgenda(new Agenda());
        getAgenda().setAgendaPK(new clases.AgendaPK());
        getAgenda().setUsuarios(getPonencia().getUsuarios());
        getAgenda().setEventos(getEventoSelect());
        getAgenda().setAgendaActividad(getPonencia().getPonenciaTitulo());
    }

    public void prepareCreatePonencia() {
        ponencia = new Ponencias();
        ponencia.setUsuarios(getUsuarioActual());
        ponencia.setEventos(getEventoSelect());
        ponencia.setPonenciasPK(new clases.PonenciasPK());
        ponencia.getPonenciasPK().setPonenciasPonenteId(ponencia.getUsuarios().getUsrId());
        ponencia.getPonenciasPK().setPonenciasEventoId(ponencia.getEventos().getEventoId());
    }

    public String prepareCreate() {
        current = new Eventos();
        selectedItemIndex = -1;
        return "Create";
    }

    public void update() {
        try {
            JsfUtil.addSuccessMessage("Entra");
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventosUpdated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventosCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public void createNovedad() {
        try {
            Novedades novedad = new Novedades();
            novedad.setNovedadTitulo(getTitulo());
            novedad.setNovedadContenido(getContenido());
            setTitulo("");
            setContenido("");
            novedad.setNovedadEventoId(getEventoSelect());
            novedadEjbFacade.create(novedad);
            getEventoSelect().getNovedadesCollection().add(novedad);
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage("Novedad Creada");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al crear novedad");
        }
    }

    public void createAgenda() {
        try {
            getAgenda().getAgendaPK().setAgendaEventoId(getAgenda().getEventos().getEventoId());
            getAgenda().getAgendaPK().setAgendaUsrId(getAgenda().getUsuarios().getUsrId());
            agendaEjbFacade.create(getAgenda());
            getEventoSelect().getAgendaCollection().add(getAgenda());
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage("Agendado con éxito");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ya fue agendado " + agenda);
        }
    }

    public void editPonencia() {
        try {
            getPonenciaEjbFacade().edit(ponencia);
            JsfUtil.addSuccessMessage("Ponencia evaluada");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("error al calificar");
        }
    }

    public void createPonencia() {
        try {
            ponencia.setPonenciaTitulo(getTitulo());
            getPonenciaEjbFacade().create(ponencia);
            getEventoSelect().getPonenciasCollection().add(ponencia);
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PonenciasCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Seleccione " + getEventoSelect());
            if (getEventoSelect().getEventoId() == null) {
                JsfUtil.addErrorMessage("Seleccione un evento");
            } else {
                JsfUtil.addErrorMessage("Ya es ponente este Evento");
            }
        }
    }

    public void editConferencias() {
        try {
            getConferencia().setConferenciaAceptada(true);
            getConferencia().setConferenciaTitulo(getTitulo());
            getConferenciasEjbFacade().edit(getConferencia());
            getEventoSelect().getConferenciasCollection().add(getConferencia());
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PonenciasCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void createInvitarConferencia() {
        try {
            conferencia = new Conferencias();
            getConferencia().setConferenciaAceptada(false);
            getConferencia().setUsuarios(getUsuarioSelect());
            getConferencia().setEventos(getEventoSelect());
            getConferencia().setConferenciasPK(new clases.ConferenciasPK());
            getConferencia().getConferenciasPK().setConferenciaUsrId(getConferencia().getUsuarios().getUsrId());
            getConferencia().getConferenciasPK().setConferenciaEventoId(getConferencia().getEventos().getEventoId());
            getConferenciasEjbFacade().create(getConferencia());
            getEventoSelect().getConferenciasCollection().add(getConferencia());
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PonenciasCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ya fue invitado");
        }
    }

    public void createAsistente() {
        try {
            getEventoSelect().getUsuariosCollection1().add(getUsuarioActual());
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventosCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Usted ya es asistente a este evento");
        }
    }

    public void createAdministrador() {
        try {
            getEventoSelect().getUsuariosCollection().add(getUsuarioSelect());
            getFacade().edit(getEventoSelect());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EventosCreated"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("El usuairo seleccionado ya es Administrador de este evento");
        }
    }

    public void enviarCorreoAdministrador() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Mail mail = new Mail();
        mail.setTo(usuarioSelect.getUsrEmail()); //para
        mail.setSubject("Red Dianmica Administrador"); //asunto
        mail.setMessage("Ha sido asigando como administrador en el evento " + getEventoSelect().getEventoTitulo()
                + " que se realizará del " + format.format(getEventoSelect().getEventoInicio())
                + " al " + format.format(getEventoSelect().getEventoFin())
                + " http://localhost:8080/red_dinamica/faces/web/eventos/index.xhtml"); //Mensaje
        mail.sendMail();
    }
    private int evaluador;

    public void setEvaluador(int evaluador) {
        this.evaluador = evaluador;
    }

    public void setEvaluar(Ponencias ponencia, int evaluador) {
        setEvaluador(evaluador);
        setPonencia(ponencia);
    }

    public void asignarEvaluador() {
        if (evaluador == 0) {
            ponencia.setPonenciasEvaluadorId(getUsuarioEvaluador());
        } else {
            ponencia.setPonenciasEvaluador1Id(getUsuarioEvaluador());
        }
        getPonenciaEjbFacade().edit(ponencia);
        JsfUtil.addSuccessMessage("Evaluador Asignado correctamente");
    }

    public void rowSelect() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/red_dinamica/faces/web/eventos/index.xhtml");
//        } catch (IOException ex) {
//            Logger.getLogger(EventosController.class.getName()).log(Level.SEVERE, null, ex);
//        }
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

    public void handleFileUploadConferencia(FileUploadEvent event) throws IOException, URISyntaxException { //Subir afiche de Evento
        try {
            editConferencias();
            setTitulo("");
            setIn(event.getFile().getInputstream());
            setFile(event.getFile());
            TransferFile("" + getUsuarioActual().getUsrId(), getEventoSelect().getEventoId() + "/conferencias");
            JsfUtil.addSuccessMessage("Ponencia Subida");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Inicie sesion para poder participar del evento");
        }
    }

    public void handleFileUploadPonencia(FileUploadEvent event) throws IOException, URISyntaxException { //Subir afiche de Evento
        try {
            createPonencia();
            setTitulo("");
            setIn(event.getFile().getInputstream());
            setFile(event.getFile());
            TransferFile("" + getUsuarioActual().getUsrId(), getEventoSelect().getEventoId() + "/ponencias");
            JsfUtil.addSuccessMessage("Ponencia Subida");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Inicie sesion para poder participar del evento");
        }
    }

    public void handleFileUploadAfiche(FileUploadEvent event) throws IOException, URISyntaxException { //Subir afiche de Evento
        setIn(event.getFile().getInputstream());
        setFile(event.getFile());
        TransferFile("afiche", "" + getEventoSelect().getEventoId());
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

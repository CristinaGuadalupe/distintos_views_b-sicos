package guzmancom.cristina.interfazdeusuarioviewsbasicosregusr;

import java.util.Date;

public class usuario {
    private String username;
    private String password;
    private String nombrePila;
    private String tecnologias;
    private String genero;
    private boolean notificaciones;
    private boolean publicidad;
    private String ies_origen;
    private Date fechaHoraNacimiento;

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nombrePila='" + nombrePila + '\'' +
                ", tecnologias='" + tecnologias + '\'' +
                ", genero='" + genero + '\'' +
                ", notificaciones=" + notificaciones +
                ", publicidad=" + publicidad +
                ", ies_origen='" + ies_origen + '\'' +
                ", fechaHoraNacimiento=" + fechaHoraNacimiento +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombrePila() {
        return nombrePila;
    }

    public void setNombrePila(String nombrePila) {
        this.nombrePila = nombrePila;
    }

    public String getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(String tecnologias) {
        this.tecnologias = tecnologias;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(boolean notificaciones) {
        this.notificaciones = notificaciones;
    }

    public boolean isPublicidad() {
        return publicidad;
    }

    public void setPublicidad(boolean publicidad) {
        this.publicidad = publicidad;
    }

    public String getIes_origen() {
        return ies_origen;
    }

    public void setIes_origen(String ies_origen) {
        this.ies_origen = ies_origen;
    }

    public Date getFechaHoraNacimiento() {
        return fechaHoraNacimiento;
    }

    public void setFechaHoraNacimiento(Date fechaHoraNacimiento) {
        this.fechaHoraNacimiento = fechaHoraNacimiento;
    }
}



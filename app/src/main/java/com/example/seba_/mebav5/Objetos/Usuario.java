package com.example.seba_.mebav5.Objetos;

/**
 * Created by seba_ on 16-10-2017.
 */

public class Usuario {
    private String IDusu;
    private String Nombreusu;
    private String Passwordusu;
    private String Cumpleaños;
    private String fotousu;
    private String Mail;

    public Usuario(){

    }

    public Usuario(String IDusu, String nombreusu, String passwordusu, String cumpleaños, String fotousu, String mail) {
        this.IDusu = IDusu;
        this.Nombreusu = nombreusu;
        this.Passwordusu = passwordusu;
        this.Cumpleaños = cumpleaños;
        this.fotousu = fotousu;
        this.Mail = mail;
    }

    public String getIDusu() {
        return IDusu;
    }

    public void setIDusu(String IDusu) {
        this.IDusu = IDusu;
    }

    public String getNombreusu() {
        return Nombreusu;
    }

    public void setNombreusu(String nombreusu) {
        this.Nombreusu = nombreusu;
    }

    public String getPasswordusu() {
        return Passwordusu;
    }

    public void setPasswordusu(String passwordusu) {
        this.Passwordusu = passwordusu;
    }

    public String getCumpleaños() {
        return Cumpleaños;
    }

    public void setCumpleaños(String cumpleaños) {
        this.Cumpleaños = cumpleaños;
    }

    public String getFotousu() {
        return fotousu;
    }

    public void setFotousu(String fotousu) {
        this.fotousu = fotousu;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        this.Mail = mail;
    }
}

package com.example.lazymessages;

public class Messages {

    public String titre;
    public String destinataire;
    public String contenu;
    public String date;

    public Messages(String titre, String destinataire, String contenu, String date){
        this.titre = titre;
        this.destinataire = destinataire;
        this.contenu = contenu;
        this.date = date;
    }



    public String getTitre(){return titre;}
    public String getDestinataire(){return destinataire;}
    public String getContenu(){return contenu;}
    public String getDate(){return date;}

    public void setTitre(){this.titre = titre;}
    public void setDestinataire(){this.destinataire = destinataire;}
    public void setContenu(){this.contenu = contenu;}
    public void setDate(){this.date = date;}

}

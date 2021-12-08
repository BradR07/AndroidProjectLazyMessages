package com.example.lazymessages;

/**
 * Classe de l'objet Messages
 */
public class Mails {

    public String objet;
    public String destinataire;
    public String contenu;
    public String date;

    /**
     * @param objet titre du message
     * @param destinataire numéro de téléphone du destinatiare
     * @param contenu contenu du message
     * @param date date a laquelle envoyer le message
     */
    public Mails(String objet, String destinataire, String contenu, String date){
        this.objet = objet;
        this.destinataire = destinataire;
        this.contenu = contenu;
        this.date = date;
    }

    public String getObjet(){
        return objet;
    }

    public String getDestinataire(){
        return destinataire;
    }

    public String getContenu(){
        return contenu;
    }

    public String getDate(){
        return date;
    }

    public void setObjet(){
        this.objet = objet;
    }

    public void setDestinataire(){
        this.destinataire = destinataire;
    }

    public void setContenu(){
        this.contenu = contenu;
    }

    public void setDate(){
        this.date = date;
    }

//    public void deleteMessage(){
//        this.titre = null;
//        this.destinataire = null;
//        this.contenu = null;
//        this.date = null;
//    }

}

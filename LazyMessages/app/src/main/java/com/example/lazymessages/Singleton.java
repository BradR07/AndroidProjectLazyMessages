package com.example.lazymessages;

import com.example.lazymessages.createMail.MailEntity;

public class Singleton {
    private MailEntity mailSingleton;

    private static Singleton instanceSingleton;



    private Singleton(MailEntity monMail){
        mailSingleton.id=monMail.id;
        System.out.println(monMail.id);
        mailSingleton.objet=monMail.objet;
        mailSingleton.destinataire=monMail.destinataire;
        mailSingleton.date=monMail.date;
        mailSingleton.contenu=monMail.contenu;

    }

    public static Singleton getInstanceSingleton(MailEntity monMail){
        if (instanceSingleton==null){
            instanceSingleton=new Singleton(monMail);
        }

        return instanceSingleton;
    }

    public static Singleton getInstanceSingleton(){
        return instanceSingleton;
    }

    public MailEntity getMonMail() {
        return mailSingleton;
    }
}

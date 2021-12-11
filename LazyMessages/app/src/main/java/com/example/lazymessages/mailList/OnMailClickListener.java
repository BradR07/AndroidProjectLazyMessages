package com.example.lazymessages.mailList;

import com.example.lazymessages.createMail.MailEntity;

/**
 * Interface ayant des fonctions listener sur chaque mail
 */
public interface OnMailClickListener {
    void onMailClick(MailEntity m);
    void deleteMailClicked(MailEntity m);
}
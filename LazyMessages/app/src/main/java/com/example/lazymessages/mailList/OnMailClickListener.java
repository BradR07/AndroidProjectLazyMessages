package com.example.lazymessages.mailList;

import com.example.lazymessages.createMail.Mails;

/**
 * Interface ayant des fonctions listener sur chaque mail
 */
public interface OnMailClickListener {
    void onMailClick(Mails m);
    void deleteMailClicked(Mails m);
}

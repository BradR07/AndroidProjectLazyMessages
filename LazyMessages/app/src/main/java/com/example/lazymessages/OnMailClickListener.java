package com.example.lazymessages;

/**
 * Interface ayant des fonctions listener sur chaque mail
 */
public interface OnMailClickListener {
    void onMailClick(Mails m);
    void deleteMailClicked(Mails m);
}

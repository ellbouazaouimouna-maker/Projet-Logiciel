/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mainapp;

import models.*;
import services.*;
import views.Connexion;

public class main {

    public static void main(
            String[] args){

        java.awt.EventQueue
        .invokeLater(() -> {

            new Connexion()
            .setVisible(true);

        });


        Adherent a =
        new Adherent(
        1,
        "Mouna",
        "EL",
        "mouna@gmail.com");


        GestionAdherent g =
        new GestionAdherent();

        g.ajouter(a);

        a.afficherInfos();

    }

}
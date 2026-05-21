/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import models.Adherent;

public class GestionAdherent {

    ArrayList<Adherent>
            liste=
            new ArrayList<>();


    public void ajouter(
            Adherent a){

        liste.add(a);

        System.out.println(
        "Adherent ajoute");

    }

}
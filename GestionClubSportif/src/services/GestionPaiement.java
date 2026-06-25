/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import models.Paiement;

public class GestionPaiement {

    ArrayList<Paiement>
            listePaiements =
            new ArrayList<>();


    // Ajouter paiement

    public void ajouterPaiement(
            Paiement p){

        listePaiements.add(p);

        System.out.println(
        "Paiement enregistré");

    }


    // Afficher paiements

    public void afficherPaiements(){

        for(Paiement p :
                listePaiements){

            System.out.println(
            p.getMontant());

        }

    }

}
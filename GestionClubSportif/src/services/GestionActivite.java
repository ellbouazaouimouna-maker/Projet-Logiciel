/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.ArrayList;
import models.Activite;

public class GestionActivite {

    ArrayList<Activite> listeActivites =
            new ArrayList<>();


    // Ajouter activité
    public void ajouterActivite(
            Activite a){

        listeActivites.add(a);

        System.out.println(
        "Activité ajoutée");

    }


    // Afficher activités
    public void afficherActivites(){

        for(Activite a :
                listeActivites){

            System.out.println(
            a.getNom());

        }

    }


    // Supprimer activité
    public void supprimerActivite(
            Activite a){

        listeActivites.remove(a);

        System.out.println(
        "Activité supprimée");

    }

}

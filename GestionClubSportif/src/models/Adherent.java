/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;

public class Adherent
extends utilisateur{

    private boolean archive;

    private ArrayList<Activite>
            activites;

    public Adherent(
            int id,
            String nom,
            String prenom,
            String email){

        super(
        id,
        nom,
        prenom,
        email);

        archive=false;

        activites=
        new ArrayList<>();

    }

    public void ajouterActivite(
            Activite a){

        activites.add(a);

    }

}
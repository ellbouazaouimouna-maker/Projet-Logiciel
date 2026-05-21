/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
public class Activite {

    private String nom;
    private double tarif;

    public Activite(
            String nom,
            double tarif){

        this.nom=nom;
        this.tarif=tarif;

    }

    public String getNom(){

        return nom;

    }

}
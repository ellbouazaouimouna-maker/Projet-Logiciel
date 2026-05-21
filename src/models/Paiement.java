/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
public class Paiement {

    private double Montant;

    private boolean paye;

    public double getMontant(){

        return Montant;

    }
    
    public void payer(){

        paye=true;

        System.out.println(
        "Paiement valide");

    }

}

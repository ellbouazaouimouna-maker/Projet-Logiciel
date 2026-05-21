/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

public class utilisateur {

    protected int id;
    protected String nom;
    protected String prenom;
    protected String email;

    public utilisateur(
            int id,
            String nom,
            String prenom,
            String email){

        this.id=id;
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;

    }

    public void afficherInfos(){

        System.out.println(
        "Nom : "+nom+
        "\nPrenom : "+prenom+
        "\nEmail : "+email);

    }

}
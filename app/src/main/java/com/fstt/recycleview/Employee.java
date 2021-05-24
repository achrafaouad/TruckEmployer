package com.fstt.recycleview;

import java.util.Date;
import java.util.HashMap;

public class Employee {
    private String nom;
    private String prenom;
    private String telephone;
    private String email;

    public Employee() {
    }

    public Employee(String nom, String prenom, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

}

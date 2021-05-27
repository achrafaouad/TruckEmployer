package ensa.application01.toolbar;

public class Employee {
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String arrive_position;
    private String current_position;
    private String start_position;
    private String id;
    private String tache;

    public Employee() {
    }

    public String getArrive_position() {
        return arrive_position;
    }

    public String getCurrent_position() {
        return current_position;
    }

    public String getStart_position() {
        return start_position;
    }

    public String getId() {
        return id;
    }

    public String getTache() {
        return tache;
    }

    public Employee(String nom, String prenom, String telephone, String email) {
        this.first_name = nom;
        this.last_name = prenom;
        this.phone = telephone;
        this.email = email;
    }

    public Employee(String first_name, String last_name, String phone, String email, String arrive_position, String current_position, String start_position, String id, String tache) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.arrive_position = arrive_position;
        this.current_position = current_position;
        this.start_position = start_position;
        this.id = id;
        this.tache = tache;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

}

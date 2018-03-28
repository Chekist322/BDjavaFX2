package main.model;

public class Doctor {
    int idDoctor;
    String name;
    String surname;
    int profession;

    public Doctor(int idDoctor, String name, String surname, int profession) {
        this.idDoctor = idDoctor;
        this.name = name;
        this.surname = surname;
        this.profession = profession;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }
}

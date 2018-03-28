package main.model;

public class Doctor {
    int id;
    String name;
    String surname;
    int profession;

    public Doctor(int id, String name, String surname, int profession) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

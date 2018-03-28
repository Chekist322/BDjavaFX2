package main.model;

public class PatientCard {
    int id;
    int patient;
    int illness;

    public PatientCard(int id, int patient, int illness) {
        this.id = id;
        this.patient = patient;
        this.illness = illness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getIllness() {
        return illness;
    }

    public void setIllness(int illness) {
        this.illness = illness;
    }
}

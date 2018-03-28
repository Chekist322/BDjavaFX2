package main.model;

public class Event {
    int id;
    String dateTime;
    int patient;
    int doctor;
    int procedure;

    public Event(int id, String dateTime, int patient, int doctor, int procedure) {
        this.id = id;
        this.dateTime = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.procedure = procedure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getDoctor() {
        return doctor;
    }

    public void setDoctor(int doctor) {
        this.doctor = doctor;
    }

    public int getProcedure() {
        return procedure;
    }

    public void setProcedure(int procedure) {
        this.procedure = procedure;
    }
}

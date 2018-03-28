package main.model;

public class Event {
    int idEvent;
    String time;
    int patient;
    int doctor;
    int procedure;

    public Event(int id, String dateTime, int patient, int doctor, int procedure) {
        this.idEvent = id;
        this.time = dateTime;
        this.patient = patient;
        this.doctor = doctor;
        this.procedure = procedure;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

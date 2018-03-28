package main.model;

public class Profession {
    int id;
    String name;
    int payment;

    public Profession(int id, String name, int payment) {
        this.id = id;
        this.name = name;
        this.payment = payment;
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

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }
}

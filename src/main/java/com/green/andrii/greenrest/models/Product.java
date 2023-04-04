package com.green.andrii.greenrest.models;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private double amount;

    @Column(name = "packing")
    @Enumerated(EnumType.ORDINAL)
    private Packing packing;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "status")
    private String status;

    public Product() {}

    public Product(String name, double amount, Packing packing, String status) {
        this.name = name;
        this.amount = amount;
        this.packing = packing;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Packing getPacking() { return packing; }
    public void setPacking(Packing packing) { this.packing = packing; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", packing=" + packing +
                ", status='" + status + '\'' +
                '}';
    }
}

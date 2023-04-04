package com.green.andrii.greenrest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.models.Packing;

public class ProductDTO {
    private int id;
    private String name;
    private double amount;
    private String packing;
    @JsonIgnore
    private int client;
    private String status;



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Packing getPacking() { return Packing.getPackingByText(this.packing); }
    public void setPacking(Packing packing) { this.packing = packing.getKey(); }

    public Client getClient() {
        Client cl = new Client();
        cl.setId(this.client);
        return cl;
    }
    public void setClient(Client client) { this.client = client.getId(); }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", packing=" + packing +
                ", client=" + client +
                ", status='" + status + '\'' +
                '}';
    }
}

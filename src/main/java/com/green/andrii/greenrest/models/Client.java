package com.green.andrii.greenrest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "manager")
    private String manager;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private String status;

    @Column(name = "date_complete")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateComplete;


    @ManyToOne
    @JoinColumn(name = "id_worker", referencedColumnName = "id")
    private Worker worker;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<Product> products;


    public Client() {}

    public Client(String name, Date date, String manager, String comment, String status) {
        this.name = name;
        this.date = date;
        this.manager = manager;
        this.comment = comment;
        this.status = status;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) { this.comment = comment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDateComplete() { return dateComplete; }
    public void setDateComplete(Date dateComplete) { this.dateComplete = dateComplete; }
    public Worker getWorker() { return worker; }
    public void setWorker(Worker worker) { this.worker = worker; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", manager='" + manager + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                ", product count='" + products.size() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name) && Objects.equals(date, client.date) && Objects.equals(manager, client.manager) && Objects.equals(comment, client.comment) && Objects.equals(status, client.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, manager, comment, status);
    }
}

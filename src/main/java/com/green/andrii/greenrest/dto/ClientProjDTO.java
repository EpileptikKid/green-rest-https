package com.green.andrii.greenrest.dto;

import org.springframework.stereotype.Component;

@Component
public class ClientProjDTO {
    private String name;
    private int id;
    private String status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

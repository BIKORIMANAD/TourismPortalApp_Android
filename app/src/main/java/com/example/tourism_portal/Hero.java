package com.example.tourism_portal;

import java.io.Serializable;


class Hero {
    private int id;
    private String name, PhoneNumber,Email,Message,Created_at;

    public Hero(int id, String name, String PhoneNumber, String Email, String Message,String Created_at) {
        this.id = id;
        this.name = name;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.Message = Message;
        this.Created_at = Created_at;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getMessage() {
        return Message;
    }

    public String getCreated_at() {
        return Created_at;
    }

}

package com.dominikk.sidebar.model;

import com.dominikk.sidebar.adapter.IdTextInterface;

/**
 * Created by Dominik K on 2017-02-07.
 */

public class Person implements IdTextInterface {

    private int id;
    private String name;

    public Person() {}

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return this.name;
    }
}

package com.springbatch.pagination.domain;


public class Student {
    private String id;
    private String name;
    private String status;

    public String getId() {
        return id;
    }

    public Student setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Student setStatus(String status) {
        this.status = status;
        return this;
    }
}

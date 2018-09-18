package me.demo.springcloud.hystrix.cache;

import java.util.Random;

public class User {

    private String Id;

    private int randomNumber;

    public User(String id) {
        Id = id;

        randomNumber = (new Random()).nextInt(10000);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }
}

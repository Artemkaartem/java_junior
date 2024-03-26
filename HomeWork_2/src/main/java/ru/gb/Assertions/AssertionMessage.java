package ru.gb.Assertions;


public class AssertionMessage extends RuntimeException{

    public AssertionMessage(String message) {
        super(message);
    }
}
package org.example;

import org.example.BusinessLayer.Guest;
import org.example.BusinessLayer.Member;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        Member m = new Member("sh", "456");
        Guest g = m;
        System.out.println(g instanceof Member);
    }
}
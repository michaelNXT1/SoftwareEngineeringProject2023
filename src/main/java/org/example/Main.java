package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        port(8080);
        post("/echo", (req, res) -> {
            System.out.println("Got a new message: " + req.body());
            System.out.println(req.ip());
            String message = req.body();
            res.type("text/plain");
            return message;
        });
    }
}
package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String[] classes=new String[]{
            "FundDemander",
                "ProductSupplier",
                "Product",
                "PurchaseType",
                "Raffle",
                "Offer",
                "BuyItNow",
                "Auction",
                "Discount",
                "VisibleDiscount",
                "HiddenDiscount",
                "ConditionalDiscount",
                "Authenticator",
                "Guest",
                "Member",
                "StoreManager",
                "StoreOwner",
                "StoreFounder",
                "Market",
                "Purchase",
                "ShoppingCart",
                "ShoppingBag",
                "Store",
                "DiscountPolicy",
                "PurchasePolicy"
        };
        for(String s: classes){
            createClass(s);
        }
    }
    public static void createClass(String className){
        String packageName = "org.example.BusinessLayer";
        String directoryPath = "src/main/java/org/example/BusinessLayer"; // Replace with the actual path to the directory

        String fileName = className + ".java";
        String filePath = directoryPath + "/" + fileName;

        // Create the directory if it does not exist
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create the file
        File file = new File(filePath);

        try {
            FileWriter writer = new FileWriter(file);
            writer.write("package " + packageName + ";\n\n");
            writer.write("public class " + className + " {\n\n");
            writer.write("}\n");
            writer.flush();
            writer.close();
            System.out.println("Java class file created successfully at " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while creating the Java class file.");
            e.printStackTrace();
        }
    }
}
package org.example.logic;

import javax.persistence.Tuple;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileWriter {
    public static void writeResultToFile(List<Tuple> results, String fileName){
        try (PrintWriter writer = new PrintWriter(new java.io.FileWriter(fileName))) {
            for (Tuple result : results) {
                writer.print("Name: " + result.get(0));
                writer.print(", Surname: " + result.get(1));
                writer.print(", Email: " + result.get(2));
                writer.print(", Building ID: " + result.get(3));
                writer.print(", Building Address: " + result.get(4));
                writer.print(", Apartment Number: " + result.get(5));
                writer.println(", Area: " + result.get(6));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        //example of output to console as mentioned in task
    public static void printResultToConsole(List<Tuple> results){
        for (Tuple result : results) {
            System.out.print("Name: " + result.get(0));
            System.out.print(", Surname: " + result.get(1));
            System.out.print(", Email: " + result.get(2));
            System.out.print(", Building ID: " + result.get(3));
            System.out.print(", Building Address: " + result.get(4));
            System.out.print(", Apartment Number: " + result.get(5));
            System.out.println(", Area: " + result.get(6));
        }
    }
}

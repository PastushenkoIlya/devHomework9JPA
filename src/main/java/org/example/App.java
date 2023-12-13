package org.example;

import org.example.logic.FileWriter;
import org.example.logic.QueryRepository;

import javax.persistence.Tuple;
import java.util.List;
public class App {
    public static void main(String[] args) {
        QueryRepository queryRepository = new QueryRepository();
        List<Tuple> results = queryRepository.getMembers();
        FileWriter.printResultToConsole(results);
    }
}
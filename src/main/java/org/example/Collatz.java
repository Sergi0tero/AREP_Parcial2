package org.example;

import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.port;

public class Collatz {
    public static void main(String[] args) {
        port(getPort());
        get("collatz", (req, res) ->{
            System.out.println(req.queryParams());
            return getCollatz(Integer.parseInt(req.queryParams("value")));
        });
    }

    static String getCollatz(int initVal){
        ArrayList<Integer> collatz = new ArrayList<Integer>();
        collatz.add(initVal);
        System.out.println(collatz.get(0) + 2);
        int actVal = initVal;
        while (actVal != 1){
            if(actVal%2 == 0){
                collatz.add(actVal / 2);
            } else {
                collatz.add((3 * actVal) + 1);
            }
            actVal = collatz.get(collatz.size() - 1);
        }
        String resCollatz = "";
        for (int col : collatz){
            if (col != 1){
                resCollatz += col + " -> ";
            } else {
                resCollatz += col;
            }
        }
        return "{\n" +
                "\n" +
                "        \"operation\": \"collatzsequence\",\n" +
                "\n" +
                "            \"input\":  " + initVal + ",\n" +
                "\n" +
                "            \"output\":  " + resCollatz + "\n" +
                "\n" +
                "    }";
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.port;



public class WebServer {


    public static void main(String[] args) throws IOException {
        port(getPort());
        get("hello", (req, res) ->{
            return mainJS();
        });
        get("collatzsequence", (req, res) -> {
            System.out.println(req.queryParams("name"));
            return getCollatz(Integer.parseInt(req.queryParams("name")));
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

    private static String mainJS(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/collatzsequence?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "    </body>\n" +
                "</html>";
    }

//    public static String HTTPRes(String link) throws IOException {
//        System.out.println("url " + link);
//        URL obj = new URL(link);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//
//        //The following invocation perform the connection implicitly before getting the code
//        int responseCode = con.getResponseCode();
//        System.out.println("GET Response Code :: " + responseCode);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) { // success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//            return response.toString();
//        } else {
//            System.out.println("GET request not worked");
//        }
//        System.out.println("GET DONE");
//        return null;
//    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}

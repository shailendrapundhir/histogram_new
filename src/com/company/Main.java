package com.company;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter input CV PDF file path:");
        try {
            String path = bufferedReader.readLine();

            String text = getPDFText(path);
            HashMap<Character, Integer> charMap = new HashMap<>();

            for (char ch : text.toCharArray()) {
                if (charMap.containsKey(ch)) {
                    charMap.put(ch, charMap.get(ch) + 1);
                } else {
                    charMap.put(ch, 1);
                }
            }

            for (Character key : charMap.keySet())
                System.out.println("\'" + key + "\'" + ":" + getHistogramReading(charMap.get(key)));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Invalid input");
        }
    }

    private static String getHistogramReading(int times) {
        StringBuilder returnVal = new StringBuilder();
        for (int i = 0; i < times; i++) {
            returnVal.append("*");
        }

        return returnVal.toString();
    }

    private static String getPDFText(String path) {
        try {
            PdfReader reader = new PdfReader(path);
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < reader.getNumberOfPages(); i++) {
                text.append(PdfTextExtractor.getTextFromPage(reader, i + 1));
            }
            reader.close();
            return text.toString().replaceAll("[^A-Za-z0-9]", "");
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

}

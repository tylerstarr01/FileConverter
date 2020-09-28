/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author student
 */
public class Translate extends Thread implements ActionListener {

    private String output;
    public static String inLang;
    public static String outLang;

    public Translate() {
    }

    public String getOutput() {
        return this.output;
    }

    public static String translateString(String from, String to, String in) throws IOException {
        Scanner sc = new Scanner(in);
        String out = "";
        ArrayList<String> a = new ArrayList<>();
        while (sc.hasNext()) {
            a.add(sc.next());
        }

        outerMost:
        for (String s : a) {
            
            
            
            
            URL url = new URL("https://www.wordreference.com/" + from + to + "/" + s);
            
            
            
            
            URLConnection urlc = url.openConnection();
            urlc.addRequestProperty("User-Agent", "Chrome/4.76");
            urlc.getPermission();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if (line.contains("td class='ToWrd'") && line.contains(s) && line.contains("em class='tooltip POS2'")) {
                    String[] arr = line.split("<|>");
                    //System.out.println(arr.length);

                    for (int i = 0; i < arr.length - 1; i++) {
                        //System.out.println(arr[i]);
                        if (arr[i].equals("td class='ToWrd' ")) {
                            //System.out.println("   " + arr[i+1]);
                            out += arr[i + 1];
                            continue outerMost;
                        }
                    }

                }
            }
        }
        return out;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(translateString("en", "fr", "hello"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("pressed");
        try {
            if (GUI.fileContents != null) {
                System.out.println("in");
                GUI.fileContents = (translateString(GUI.il.getSelectedItem().toString(), GUI.ol.getSelectedItem().toString(), GUI.fileContents));
                GUI.contentsField.setText(GUI.fileContents);
            }
        } catch (IOException ex) {
            Logger.getLogger(Translate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

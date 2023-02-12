/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.copesadamoli.competizionerobot;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author FEDERICOCOPES
 */
public class CompetizioneRobot {

    private static String readStringFromFile(String filepath) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(filepath));
        return new String(content);
    }
    
    public static void main(String[] args) throws IOException {
        Set<String> squadre = new HashSet<>();
        int totalePunti;
        Gson gson = new Gson();
        String json;
        int count = 1;
        json = readStringFromFile("GareRobot.json");
        Gara[] gara = gson.fromJson(json, Gara[].class);
        //System.out.println("Tutte le gare disputate:");
        for (Gara g : gara) {
            squadre.add(g.squadra);
            count++;
        }
        System.out.println("\nNumero di squadre che hanno partecipato alla gara: " + squadre.size());

        int[] punteggiSquadre = new int[squadre.size()]; //possono esserci un numero di punteggi tante quante sono le squadre nel set 
        int[] tempiSquadre = new int[squadre.size()]; // array per mantenere i tempi delle squadre
        String[] nomiSquadre = squadre.toArray(new String[0]);//Copia del set creato prima in un array di stringhe
        for (Gara g : gara) {
            for (int i = 0; i < nomiSquadre.length; i++) {
                if (g.squadra.equals(nomiSquadre[i])) { //se è ugaule al nome della squadra aggiunge il punteggio
                    punteggiSquadre[i] += Integer.parseInt(g.punteggio_percorso) + Integer.parseInt(g.punteggio_difficolta);
                    tempiSquadre[i] += Integer.parseInt(g.tempo); // incrementa il tempo per la squadra corrispondente
                }
            }
        }
        System.out.println("\nRisultati per squadra:");
        for (int i = 0; i < nomiSquadre.length; i++) {
            System.out.println(nomiSquadre[i] + " ha realizzato " + punteggiSquadre[i] + " punti nel tempo di " + tempiSquadre[i] + " secondi. Uno score totale di: " + (punteggiSquadre[i] + tempiSquadre[i]) + " punti.");
        }
        
        int indiceMigliorSquadra = 0;
        int punteggioMigliorSquadra = punteggiSquadre[0];
        int indicePeggioreSquadra = 0;
        int punteggioPeggioreSquadra = punteggiSquadre[0];
        for (int i = 1; i < punteggiSquadre.length; i++) {
            if (punteggiSquadre[i] > punteggioMigliorSquadra) {
                indiceMigliorSquadra = i;
                punteggioMigliorSquadra = punteggiSquadre[i];
            }
            if (punteggiSquadre[i] < punteggioPeggioreSquadra) {
                indicePeggioreSquadra = i;
                punteggioPeggioreSquadra = punteggiSquadre[i];
            }
        }
        System.out.println("\nLa migliore squadra è: " + nomiSquadre[indiceMigliorSquadra] + " con un punteggio di " + punteggioMigliorSquadra + " punti.");
        System.out.println("La peggiore squadra è: " + nomiSquadre[indicePeggioreSquadra] + " con un punteggio di " + punteggioPeggioreSquadra + " punti.");
    }
}

package com.example.naloga2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.script.*;
import javafx.stage.FileChooser;
import net.objecthunter.exp4j.*;
import java.io.*;
import java.util.ArrayList;


public class HelloController{
    public String ukaz="";
    public TextArea hist;
    public TextArea dnevnik;
    public ArrayList<String> history= new ArrayList<String>();
    public TextField zaslon;
    public String racun="";
    public Label status;
    public void eval() throws ScriptException {
        if(racun.length()>=3 && !(racun.charAt(racun.length()-1)=='+' || racun.charAt(racun.length()-1)=='/'|| racun.charAt(racun.length()-1)=='*'|| racun.charAt(racun.length()-1)=='-')) {
            ExpressionBuilder eb = new ExpressionBuilder(racun);
            Expression ex = eb.build();
            String rez = String.valueOf((int) ex.evaluate());
            zaslon.setText(String.valueOf((int) ex.evaluate()));
            racun = racun + "=" + rez;
            if (history.size() < 3) {
                history.add(racun);
            } else {
                history.remove(0);
                history.add(racun);
            }
            ukaz = racun;
            addDiary();
            addDnevnik();
            racun = "";
        }
        else {
            zaslon.setText("ERROR");
        }
    }
    public void gumb(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        racun=racun+button.getText();
        System.out.println(racun);
        zaslon.setText(racun);

    }
    public void clear(ActionEvent actionEvent) {
        racun="";
        zaslon.setText("");
    }
    public void addDnevnik(){
        hist.appendText(ukaz+"\n");
    }
    public void addDiary(){
        dnevnik.setText("");
        for(int i = 0; i<history.size();i++){
            dnevnik.appendText(history.get(i)+"\n");
        }
    }
    public void odpri(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        File f = fc.showOpenDialog(null);
        if (f!=null) {
            ukaz=f.getName()+":";
            addDnevnik();
            BufferedReader br = new BufferedReader(new FileReader(f));
            while(br.ready()){
                ukaz=br.readLine();
                addDnevnik();
            }
            status.setText(f.getName()+" "+f.length()+"B");
        }
    }
    public void shrani(ActionEvent actionEvent) {
        FileChooser fc= new FileChooser();
        File f = fc.showSaveDialog(null);
        if (f!=null){
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
                bw.write(hist.getText());
                bw.close();
                status.setText("Shranil datoteko: "+ f.getName());
            }catch (Exception e){
                status.setText("nisem uspel zapisati datoteke: "+ f.getName());
            }
        }
        ukaz="Shranjevanje";
        status.setText(ukaz);
        addDnevnik();
    }

    public void izhod(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void pocisti(ActionEvent actionEvent) {
        zaslon.setText("");
        while(!history.isEmpty()){
            history.remove(0);
        }
        dnevnik.setText("");
        ukaz="Čiščenje";
        status.setText(ukaz);
        addDnevnik();
    }

    public void pomoc(ActionEvent actionEvent) {
        status.setText("Avtor: Jure Pavlovič");
        ukaz="Avtor: Jure Pavlovič";
        addDnevnik();
    }
}
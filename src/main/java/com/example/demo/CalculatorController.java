package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CalculatorController extends MenuBarController{

    @FXML
    private Label prevNum;
    @FXML
    private Label result;
    private double num1=0;
    private String used_operator="";
    private boolean check=true;
    private boolean used_dot=false;
    private boolean can_add=true;

    public double create(double num1, double num2, String operator){
        switch(operator){
            case "+": return num1+num2;

            case "-": return num1-num2;

            case "x": return num1*num2;

            case "÷":{
                if(num2==0) return -1;
                else return num1/num2;
            }

        }
        return 0.0;
    }
    public void Compute(ActionEvent e){

        if(check) {
            result.setText("");
            prevNum.setText("");
            check = false;
        }

        Button but=(Button)e.getSource();

        String val=but.getText();

        if(val.equals(",")){
            if(!used_dot) {
                if(result.getText().isEmpty()){
                    clearCalculator();
                    return;
                }
                val = ".";
                result.setText(result.getText() + val);
                prevNum.setText(prevNum.getText() + val);
                used_dot = true;
            }
            return;
        }

        String text=result.getText();

        if(text.length()==1) {
            if (text.charAt(0) == '0' && !val.equals(".")){
                return;
            }
        }
        if(can_add) {
            result.setText(result.getText() + val);
            prevNum.setText(prevNum.getText() + val);
        }
    }

    public void operatorCompute(ActionEvent e){
        Button button=(Button)e.getSource();
        String value=button.getText();

        if(!value.equals("=")){
            if(!used_operator.isEmpty()){
                return;
            }
            used_operator=value;
            num1=Double.parseDouble(result.getText());
            prevNum.setText(num1+" "+used_operator+" ");
            result.setText("");
            used_dot=false;
            can_add=true;
        }

        else{
            if(used_operator.isEmpty()){
                return;
            }

            double num2=Double.parseDouble(result.getText());
            double score=create(num1,num2,used_operator);

            if(used_operator.equals("÷") && num2==0) {
                clearCalculator();
            }
            else {
                result.setText(String.valueOf(score));
            }

            used_operator="";
            check=false;
            used_dot=true;
            can_add=false;
        }

    }
    public void clearCalculator(ActionEvent e){
        result.setText("0.0");
        prevNum.setText("");
        num1=0;
        check=true;
        used_operator="";
        used_dot=false;
        can_add=true;
    }

    public void clearCalculator(){
        result.setText("Error");
        prevNum.setText("");
        num1=0;
        check=true;
        used_operator="";
        used_dot=false;
        can_add=true;
    }

    public void plus_minus_sqrt(ActionEvent e){
        if(result.getText().isEmpty()){
            return;
        }

        Button button=(Button)e.getSource();
        String val=button.getText();
        num1 = Double.parseDouble(result.getText());

        if(val.equals("+/-")) {
            result.setText(String.valueOf(num1 * -1));
        }
        else{
            result.setText(String.valueOf(Math.sqrt(num1)));
        }
        prevNum.setText("");
        can_add=false;
    }

}

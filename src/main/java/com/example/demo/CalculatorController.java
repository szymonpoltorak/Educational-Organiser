package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class CalculatorController extends MenuBarController{

    @FXML
    private Label prevNum;
    @FXML
    private Label result;
    @FXML
    private Label labelM;
    @FXML
    private TextArea history;

    private double num1=0;
    private String usedOperator ="";
    private boolean check=true;
    private boolean usedDot =false;
    private boolean canAdd =true;
    private boolean canAddMrc =true;
    private double numRemembered =0;
    private boolean visibleLabelM =false;
    private boolean mrcClickBefore =false;

    public double create(double num1, double num2, String operator) {
        switch(operator){
            case "+": return num1+num2;

            case "-": return num1-num2;

            case "x": return num1*num2;

            case "÷":{
                if(num2==0) return -1;
                else return num1/num2;
            }
            case "x^y": return Math.pow(num1, num2);

        }
        return 0.0;
    }
    public void compute(ActionEvent e){

        if(check) {
            result.setText("");
            prevNum.setText("");
            check = false;
        }

        Button but=(Button)e.getSource();

        String val=but.getText();

        if(val.equals(",")){
            if(!usedDot) {
                if(result.getText().isEmpty()){
                    clearCalculator();
                    return;
                }
                val = ".";
                result.setText(result.getText() + val);
                prevNum.setText(prevNum.getText() + val);
                usedDot = true;
            }
            return;
        }

        String text=result.getText();

        if(text.length()==1) {
            if (text.charAt(0) == '0' && !val.equals(".")){
                return;
            }
        }
        if(canAdd) {
            result.setText(result.getText() + val);
            prevNum.setText(prevNum.getText() + val);
        }
        mrcClickBefore =false;
        canAddMrc =false;
    }

    public void operatorCompute(ActionEvent e){
        Button button=(Button)e.getSource();
        String value=button.getText();

        if(!value.equals("=")){
            if(!usedOperator.isEmpty()){
                return;
            }
            usedOperator =value;
            num1=Double.parseDouble(result.getText());
            prevNum.setText(num1+" "+ usedOperator +" ");
            result.setText("");
            usedDot =false;
            canAdd =true;
        }

        else{
            if(usedOperator.isEmpty()){
                return;
            }

            double num2=Double.parseDouble(result.getText());
            double score=create(num1,num2, usedOperator);

            if(usedOperator.equals("÷") && num2==0) {
                clearCalculator();
            }
            else {
                result.setText(String.valueOf(score));
                history.appendText(prevNum.getText() + " = ");
                history.appendText( result.getText() + "\n");
            }

            usedOperator ="";
            check=false;
            usedDot =true;
            canAdd =false;
            mrcClickBefore =false;
        }
        canAddMrc =true;
    }

    public void clearHistory(ActionEvent clear){
        history.setText("");
    }

    public void clearCalculator(ActionEvent e){
        result.setText("0.0");
        prevNum.setText("");
        num1=0;
        check=true;
        usedOperator ="";
        usedDot =false;
        canAdd =true;
        canAddMrc =true;
    }

    public void clearCalculator(){
        result.setText("Error");
        prevNum.setText("");
        num1=0;
        check=true;
        usedOperator ="";
        usedDot =false;
        canAdd =true;
        canAddMrc =true;
    }

    public void plusMinusSqrt(ActionEvent e){
        if(result.getText().isEmpty()){
            return;
        }

        Button button=(Button)e.getSource();
        String val=button.getText();
        num1 = Double.parseDouble(result.getText());

        if(val.equals("+/-")) {
            result.setText(String.valueOf(num1 * -1));
            history.appendText("- ("+num1 +")" + " = " + String.valueOf(num1 * -1) + "\n");
        }
        else{
            result.setText(String.valueOf(Math.sqrt(num1)));
            history.appendText("√ ("+num1+")" + " = " + String.valueOf(Math.sqrt(num1)) + "\n" );
        }

        usedDot =true;
        prevNum.setText("");
        canAdd =false;
    }
    public void calcScienceFunctions(ActionEvent e){
        if(result.getText().isEmpty()){
            return;
        }

        Button button=(Button)e.getSource();
        String val=button.getText();
        num1 = Double.parseDouble(result.getText());

        switch (val) {
            case "n!":
                if (num1 >= 0 && Math.ceil(num1) == Math.floor(num1)) {
                    int frac = 1;
                    int temp = (int) num1;
                    if (temp < 17) {
                        if (temp > 0) {
                            for (int i = 1; i <= temp; i++)
                                frac *= i;
                        }
                        result.setText(String.valueOf(frac));
                    } else {
                        result.setText("OVERFLOW");
                    }
                } else
                    result.setText("BLAD");
                break;
            case "log":
                result.setText(CalcUtils.getLogString(num1));
                break;
            case "|x|":
                result.setText(CalcUtils.getAbsString(num1));
                break;
            case "x^2":
                result.setText(CalcUtils.getPower2String(num1));
                break;
            case "sin":
                result.setText(String.valueOf(Math.sin(num1)));
                break;
            case "cos":
                result.setText(String.valueOf(Math.cos(num1)));
                break;
            case "tan":
                result.setText(String.valueOf(Math.tan(num1)));
                break;
            case "cot":
                result.setText(String.valueOf(Math.cos(num1) / Math.sin(num1)));
                break;
        }

        usedDot =true;
        prevNum.setText("");
        canAdd =false;
    }

    // TODO: rozwiazac problem z '.' przy wybieraniu MRC, przetestować dokładnie MRC, ustawic przyciski
    public void storageInMemoryM(ActionEvent e) {
        Button button=(Button)e.getSource();
        String val=button.getText();

        if(val.equals("MRC")) {
            if(mrcClickBefore){
                visibleLabelM = false;
                labelM.setText("");
                mrcClickBefore =false;
            }
            else if(canAddMrc){
                val = String.valueOf(numRemembered);

                if(check) {
                    result.setText("");
                    prevNum.setText("");
                    check = false;
                }

                if(canAdd) {
                    result.setText(result.getText() + val);
                    prevNum.setText(prevNum.getText() + val);
                    canAdd =false;
                }
                mrcClickBefore =true;
            }
        }
        if(val.equals("M-")) {
            numRemembered -= Double.parseDouble(result.getText());
            if(!visibleLabelM) {
                visibleLabelM = true;
                labelM.setText("M");
                mrcClickBefore =false;
            }
        }
        else if(val.equals("M+")) {
            numRemembered += Double.parseDouble(result.getText());
            if(!visibleLabelM) {
                visibleLabelM = true;
                labelM.setText("M");
                mrcClickBefore =false;
            }
        }

    }

}

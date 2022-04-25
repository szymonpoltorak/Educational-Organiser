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
    private Label label_M;

    private double num1=0;
    private String used_operator="";
    private boolean check=true;
    private boolean used_dot=false;
    private boolean can_add=true;
    private boolean can_add_mrc=true;
    private double num_remembered=0;
    private boolean visible_lable_M=false;
    private boolean mrc_click_before=false;
    @FXML
    private TextArea history;

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
        mrc_click_before=false;
        can_add_mrc=false;
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
                history.appendText(prevNum.getText() + " = ");
                history.appendText( result.getText() + "\n");
            }

            used_operator="";
            check=false;
            used_dot=true;
            can_add=false;
            mrc_click_before=false;
        }
        can_add_mrc=true;
    }

    public void ClearHistory(ActionEvent clear){
        history.setText("");
    }

    public void clearCalculator(ActionEvent e){
        result.setText("0.0");
        prevNum.setText("");
        num1=0;
        check=true;
        used_operator="";
        used_dot=false;
        can_add=true;
        can_add_mrc=true;
    }

    public void clearCalculator(){
        result.setText("Error");
        prevNum.setText("");
        num1=0;
        check=true;
        used_operator="";
        used_dot=false;
        can_add=true;
        can_add_mrc=true;
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
            history.appendText("- ("+num1 +")" + " = " + String.valueOf(num1 * -1) + "\n");
        }
        else{
            result.setText(String.valueOf(Math.sqrt(num1)));
            history.appendText("√ ("+num1+")" + " = " + String.valueOf(Math.sqrt(num1)) + "\n" );
        }

        used_dot=true;
        prevNum.setText("");
        can_add=false;
    }
    public void calc_science_functions(ActionEvent e){
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
                result.setText(String.valueOf(Math.log10(num1)));
                break;
            case "|x|":
                result.setText(String.valueOf(Math.abs(num1)));
                break;
            case "x^2":
                result.setText(String.valueOf(Math.pow(num1, 2)));
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

        used_dot=true;
        prevNum.setText("");
        can_add=false;
    }

    // TODO: rozwiazac problem z '.' przy wybieraniu MRC, przetestować dokładnie MRC, ustawic przyciski
    public void storage_in_memory_M(ActionEvent e) {
        Button button=(Button)e.getSource();
        String val=button.getText();

        if(val.equals("MRC")) {
            if(mrc_click_before){
                visible_lable_M = false;
                label_M.setText("");
                mrc_click_before=false;
            }
            else if(can_add_mrc){
                val = String.valueOf(num_remembered);

                if(check) {
                    result.setText("");
                    prevNum.setText("");
                    check = false;
                }

                if(can_add) {
                    result.setText(result.getText() + val);
                    prevNum.setText(prevNum.getText() + val);
                    can_add=false;
                }
                mrc_click_before=true;
            }
        }
        if(val.equals("M-")) {
            num_remembered -= Double.parseDouble(result.getText());
            if(!visible_lable_M) {
                visible_lable_M = true;
                label_M.setText("M");
                mrc_click_before=false;
            }
        }
        else if(val.equals("M+")) {
            num_remembered += Double.parseDouble(result.getText());
            if(!visible_lable_M) {
                visible_lable_M = true;
                label_M.setText("M");
                mrc_click_before=false;
            }
        }

    }

}

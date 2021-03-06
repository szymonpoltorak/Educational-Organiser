package pl.edu.pw.ee.organiser;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AverageController extends MenuBarController {
    @FXML
    private CheckBox IfArithmetic;
    @FXML
    private TextField Grade;
    private double grade;
    @FXML
    private TextField Weight;
    private double weight;
    @FXML
    private TextArea Grades;
    @FXML
    private TextArea Weights;
    @FXML
    private Label Results;
    private double result;
    private double sumGrade;
    private double sumWeight;

    public Double getResults() {
        return result;
    }
    public void setSumGrade(double sumGrade) {
        this.sumGrade = sumGrade;
    }

    public void setSumWeight(double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public void IfArithmeticOn(){
        if(IfArithmetic.isSelected()) {
            Weight.setEditable(false);
            Weight.setDisable(true);
            Weight.setText("1");
            weight = 1;
        }
        else {
            Weight.setEditable(true);
            Weight.setDisable(false);
            Weight.setText("");
        }
    }

    public void onAddClick(){
        if(Grade.getLength() >0 && Weight.getLength() >0)
            if (Grade.getText().matches("[0-9]+") && Weight.getText().matches("[0-9]+") && Double.parseDouble(Grade.getText()) > 0 && Double.parseDouble(Weight.getText()) > 0) {
                grade = Double.parseDouble(Grade.getText());
                weight = Double.parseDouble(Weight.getText());
                Grades.appendText(Grade.getText() + "\n");
                Weights.appendText(Weight.getText() + "\n");
                sumGrade += grade * weight;
                sumWeight += weight;
                Grade.setText("");

                if (!IfArithmetic.isSelected()) {
                    Weight.setText("");
                }
            }
    }

    public void onClearClick(){
        sumGrade = 0;
        sumWeight = 0;
        Weights.setText("");
        Grades.setText("");
        Results.setText("");
    }

    public void countResult(){
        result = roundTo2DecimalPlace( sumGrade/sumWeight );
    }

    public void onResultClick(){
        countResult();
        Results.setText(Double.toString(result));
    }

    public double roundTo2DecimalPlace(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

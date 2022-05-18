package com.example.demo;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TimerController extends MenuBarController {

    private  boolean canRun =true;
    private static int seriesCounter = 0;
    @FXML
    private TextField focusTime;

    @FXML
    private Label minute;

    @FXML
    private TextArea notification;
    @FXML
    private Label type;

    @FXML
    private TextField restTime;

    @FXML
    private Label second;

    @FXML
    private TextField series;

    class Timer extends AnimationTimer {
        private long timestamp;
        private long time = 0;
        private int currentSeconds;
        private int currentMinutes;
        private long fraction = 0;

        @Override
        public void start() {
            timestamp = System.currentTimeMillis() - fraction;
            super.start();

        }

        @Override
        public void stop() {
            super.stop();
            fraction = System.currentTimeMillis() - timestamp;

        }

        @Override
        public void handle(long now) {
            long newTime = System.currentTimeMillis();

            if (timestamp + 1000 <= newTime) {
                long deltaT = (newTime - timestamp) / 1000;
                time += deltaT;
                timestamp += 1000 * deltaT;
                currentSeconds = (int) (60 - time);

                if(currentSeconds != 0 ){
                    second.setText(Integer.toString(currentSeconds));
                }

                else {
                    time=0;
                    currentMinutes = Integer.parseInt(minute.getText()) - 1;
                    minute.setText(Integer.toString(currentMinutes));
                    if(currentMinutes==-1){
                        ++seriesCounter;
                        changeView();
                    }
                }

            }

        }

        public void reset() {
            time = 0;
            currentSeconds = 0;
            currentMinutes = 0;
            seriesCounter=0;

            setButtonSetHandler();
            super.stop();

        }

        public void changeView(){
            if(seriesCounter==Integer.parseInt(series.getText())){
                String currentHistory = notification.getText() + "You have completed the whole time! Congratulations";

                notification.setText(currentHistory);
                time=0;
                canRun=true;
                isFirstStart=true;

                minute.setText(focusTime.getText());
                second.setText("0");

                stop();
                return;
            }

            time=0;
            String notificationHistory = notification.getText() + "You have completed your focus time! Time to rest :)"+"\n";
            notification.setText(notificationHistory);

            isFirstStart=true;
            canRun=true;

            timer.stop();
            startTimerHandler();

        }

    }

    public void setButtonSetHandler(){

        if(!canRun && !isFirstStart){
            return;
        }

        int focus = Integer.parseInt(focusTime.getText());
        int rest = Integer.parseInt(restTime.getText());
        int seriesNum = Integer.parseInt(series.getText());

        if(focus <= 0 || rest <=0 || seriesNum <=0){
            throw new IllegalArgumentException("Illegal argument!");
        }

        minute.setText(Integer.toString(focus));
        second.setText("0");

        canRun = true;
        isFirstStart = true;

    }

    private final Timer timer = new Timer();
    private boolean isFirstStart=true;

    public void startTimerHandler(){
        if(seriesCounter%2==0) {

            if (canRun && isFirstStart) {
                canRun = false;
                type.setText("Focus time");

                int minutes = Integer.parseInt(minute.getText()) - 1;
                minute.setText(Integer.toString(minutes));
                String notificationHistory = notification.getText() + "Started counting! The timer will be in focus mode for: "+focusTime.getText()+" minutes"+"\n";
                notification.setText(notificationHistory);

                second.setText("59");
                timer.start();
                isFirstStart = false;

            } else {
                timer.start();
            }

        }

        else {
            if (canRun && isFirstStart) {
                canRun = false;
                type.setText("Rest time");
                int minutes = Integer.parseInt(restTime.getText()) - 1;

                minute.setText(Integer.toString(minutes));
                second.setText("59");

                timer.start();
                isFirstStart = false;
            } else {
                timer.start();
            }
        }
    }

    public void  resetTimerHandler(){
        canRun = true;
        isFirstStart=true;
        type.setText("Focus time");

        timer.reset();

    }

    public void saveButtonHandler(){
        if(isFirstStart){
            return;
        }

        String currentHistory = notification.getText() + "Saving button pressed at: " + minute.getText() +" minutes and " + second.getText() + " seconds\n";
        notification.setText(currentHistory);

    }

    public void clearButtonHandler(){
        notification.clear();
    }

    public void pauseButtonHandler(){
        if(canRun || isFirstStart){
            return;
        }

        canRun = true;
        timer.stop();

    }

}
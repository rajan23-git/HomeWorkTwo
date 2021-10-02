package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;
/*
Author Name: Pranav Rajan
Date Last Modified: October 1 2021
Program Description: draws GUI and codes game in which you shift square with number on it
into blank spot. If you shift all 15 quares into correct spots, you win the game.

 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String [][] correctNums; // 2d array of where correct location of numbers are on board
    public  Button[][] buttonGrid;// 2d array of button where numbers are displayed
    public Button reset;// button that  randomly redraws grid of buttons

    Button clickedOne;   // button that has been clicked by user

    /*
    method name: checkButtons
    param: none
    return value: none
    checks if number labels on buttons in correct location, highlights correct locations

     */
    public void checkButtons(){
        //nested for loop goes through button grid
        for(int row=0;row<buttonGrid.length;row++){
            for(int col=0;col<buttonGrid[0].length;col++){
                //if number label on button correct
                if((buttonGrid[row][col]).getText().toString().equals(correctNums[row][col])){
                    buttonGrid[row][col].setBackgroundColor(Color.GREEN);

                }
                //if number label on button incorrect

                else{
                    buttonGrid[row][col].setBackgroundColor(Color.RED);

                }
            }
        }

    }

    /*
    method name: randomizeBoard
    param: none
    return value: none
    randomly places text labels on different buttons in the board

     */
    public void randomizeBoard() {
        Random r = new Random();
        int low;
        int high;
        int index;

        ArrayList<String> numArray = new ArrayList<String>();
        //arraylist created with all numbers on the button Grid
        numArray.add("1");
        numArray.add("2");
        numArray.add("3");
        numArray.add("4");
        numArray.add("5");
        numArray.add("6");
        numArray.add("7");
        numArray.add("8");
        numArray.add("9");
        numArray.add("10");
        numArray.add("11");
        numArray.add("12");
        numArray.add("13");
        numArray.add("14");
        numArray.add("15");
        numArray.add("");
        //nested for loop walks through each button in grid
        //randomly generates a number from the numArray, and
        //puts that number as the text label for the current button
        for (int row = 0; row < buttonGrid.length; row++) {
            for (int col = 0; col < buttonGrid[0].length; col++) {
                low = 0;
                high = numArray.size();
                index = r.nextInt(high - low) + low;
                buttonGrid[row][col].setText(numArray.get(index));
                numArray.remove(index);

            }

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        correctNums=new String [4][4];
        int num=0;
        //nested for loop stores the correct numberw in the correct
        //locations of the correctNums array
        for(int row=0;row<correctNums.length;row++){
            for(int col=0;col<correctNums[0].length;col++){
                if(!(row==3&&col==3)) {
                    num++;
                    correctNums[row][col] = Integer.toString(num);
                }

                else{
                    correctNums[3][3]="";
                }
                System.out.print(correctNums[row][col] + " ");

            }
            System.out.println("");

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGrid= new Button [4][4];

        buttonGrid= new Button [4][4];
        //nested for loop finds each button in the XML layout and stores it in the button
        //grid while giving each button in the gird an OnClickListener
        for(int row=0;row<4;row++){
            for(int col=0;col<4;col++){
                String id="rc"+row+col;
                int resourceId=getResources().getIdentifier(id,"id",getPackageName());
                buttonGrid[row][col]=findViewById(resourceId);
                buttonGrid[row][col].setOnClickListener(this);
            }

        }
        //reset button found from XML and given OnClickListener
        reset=findViewById(R.id.reset);
        reset.setOnClickListener(this);

        randomizeBoard(); //randomize board with different number labels

        checkButtons(); //check if button configuration correct or not

    }
    /*
    method name: onClick
    param: View v
    return value:none
    actions performed whenever a button in the layout is pressed. Different
    actions executed for different buttons

     */

    public void onClick(View v) {
        if(v.getId()==R.id.reset){
            //if reset button clicked, randomize board
            randomizeBoard();

        }
        clickedOne=(Button)v;
        int rowCoordinate=0;
        int colCoordinate=0;
        //if the button clicked is neither labeled reset or blank, we locate
        //the clicked button on the buttonGrid
        if((!(clickedOne.getText().toString().equals("")))&&(!(clickedOne.getText().toString().equals("reset")))){
            String clickedText=clickedOne.getText().toString();
            for(int row=0;row<buttonGrid.length;row++){
                for(int col=0;col<buttonGrid[0].length;col++){
                    if(buttonGrid[row][col]==clickedOne){
                        rowCoordinate=row;
                        colCoordinate=col;

                    }

                }

            }
            // the coordinates of the button's 4 neighbors is set
            int left=colCoordinate-1;
            int right=colCoordinate+1;
            int top=rowCoordinate-1;
            int bottom =rowCoordinate+1;
            //if left neighbor is available and this left neighbor is blank
            //we switch the left neighbor with current button
            if(left>=0){
                if(buttonGrid[rowCoordinate][left].getText().toString().equals("")){
                    buttonGrid[rowCoordinate][left].setText(clickedText);
                    clickedOne.setText("");

                }
            }
            //if right neighbor is available and this right neighbor is blank
            //we switch the right neighbor with current button
            if(right< buttonGrid.length){
                if(buttonGrid[rowCoordinate][right].getText().toString().equals("")){
                    buttonGrid[rowCoordinate][right].setText(clickedText);
                    clickedOne.setText("");

                }
            }
            //if top neighbor is available and this top neighbor is blank
            //we switch the top neighbor with current button
            if(top>=0){
                if(buttonGrid[top][colCoordinate].getText().toString().equals("")){
                    buttonGrid[top][colCoordinate].setText(clickedText);
                    clickedOne.setText("");

                }
            }
            //if bottom neighbor is available and this bottom neighbor is blank
            //we switch the bottom neighbor with current button
            if(bottom< buttonGrid[0].length){
                if(buttonGrid[bottom][colCoordinate].getText().toString().equals("")){
                    buttonGrid[bottom][colCoordinate].setText(clickedText);
                    clickedOne.setText("");

                }
            }


        }

        checkButtons(); //after each button click, checks for correct and incorrect squares in grid
    }

}

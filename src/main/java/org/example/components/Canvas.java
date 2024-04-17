package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.example.shapes.*;
import static org.example.components._Constants.CANVAS_SIZE;

public class Canvas extends _ComponentJPanel {

    //private ArrayList<Shape> shapes; // List to store drawn shapes

    Canvas() {
        super(CANVAS_SIZE);
        setLayout(new BorderLayout());
        setBackground(Color.white);
        //shapes = new ArrayList<>();
    }

}

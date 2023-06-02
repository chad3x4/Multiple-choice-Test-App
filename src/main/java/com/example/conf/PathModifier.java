package com.example.conf;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class PathModifier {
    public static List<Object> addPath(String title) {
        List<Object> path = new ArrayList<>();
        Label splash = new Label("/");
        splash.setFont(new Font("System", 16));
        splash.setPadding(new Insets(2, 0, 0, 0));

        Hyperlink option = new Hyperlink(title);
        option.setFont(new Font("System", 16));
        option.setTextFill(Paint.valueOf("#da0d0d"));
        path.add(splash); path.add(option);
        return path;

    }
}

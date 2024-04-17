package org.example.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import static org.example.components._Constants.*;

public class EditWindow extends _ComponentJPanel {

    DefaultListModel<String> ShapeEditListModel;
    JList<String> ShapeEditList;
    
    EditWindow() {
        super(SHAPES_WINDOW_SIZE);

        ShapeEditListModel = new DefaultListModel<>();
        ShapeEditList = new JList<>(ShapeEditListModel);

        JPanel ShapeEditPanel = new JPanel(new BorderLayout());
        ShapeEditPanel.setLayout(new BoxLayout(ShapeEditPanel, BoxLayout.Y_AXIS));
        ShapeEditPanel.add(new JScrollPane(ShapeEditList), BorderLayout.CENTER);

        // 1) Canvas상에서 도형을 클릭했을 경우

        // 서버에서 해당 도형의 ID를 받아온 후 리스트에 추가

        // 2) 도형을 편집할 경우
        // -> Style
        // 기존 도형을 수정해서 Canvas에 새로 그리고 이전 도형은 삭제
        
    }
}

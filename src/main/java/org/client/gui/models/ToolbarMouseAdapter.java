package org.client.gui.models;

import org.client.gui.shape.Shape;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import static org.client.gui.Constants.*;

public class ToolbarMouseAdapter extends MouseAdapter {
    private final AppModel appModel = AppModel.getInstance();

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JToggleButton) {
            handleToggleButtonEvent((JToggleButton) e.getSource());
        } else {
            handleButtonEvent((JButton) e.getSource());
        }
    }

    private void handleButtonEvent(JButton source) {
        if (source.getName().equals(TOOLBAR_UNDO)) {
            appModel.undo();
        } else if (source.getName().equals(TOOLBAR_REDO)) {
            appModel.redo();
        } else if (source.getName().equals(TOOLBAR_SAVE)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setSelectedFile(new File("shapes.data"));
            chooser.setDialogTitle(SAVE_SHAPES);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setMultiSelectionEnabled(false);

            int choose = chooser.showOpenDialog(source);
            if (choose == JFileChooser.APPROVE_OPTION) {
                try {
                    appModel.save(chooser.getSelectedFile());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(source, SAVE_FAILED_MESSAGE, SAVE_FAILED, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (source.getName().equals(TOOLBAR_LOAD)) {
            int option = JOptionPane.showConfirmDialog(source, LOAD_FAILED_CONFIRM_MESSAGE, LOAD_FAILED_CONFIRM, JOptionPane.OK_CANCEL_OPTION);
            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle(LOAD_SHAPES);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setMultiSelectionEnabled(false);

            int choose = chooser.showOpenDialog(source);
            if (choose == JFileChooser.APPROVE_OPTION) {
                try {
                    appModel.load(chooser.getSelectedFile());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(source, LOAD_FAILED_MESSAGE, LOAD_FAILED, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void handleToggleButtonEvent(JToggleButton source) {
        if(!source.isSelected()){
            return;
        }
        appModel.setType(switch (source.getName()) {
            case TOOLBAR_LINE -> Shape.Type.LINE;
            case TOOLBAR_RECT -> Shape.Type.RECTANGLE;
            case TOOLBAR_OVAL -> Shape.Type.OVAL;
            case TOOLBAR_TEXT -> Shape.Type.TEXT;
            default -> throw new IllegalStateException("Unexpected value: " + source.getName());
        });
    }
}


package org.client.gui.models;

import javax.swing.*;
import java.util.Map;
import static org.client.gui.Constants.*;

public class ToolbarButtonController {
  private final AppModel appModel = AppModel.getInstance();
  private final Map<String, JButton> buttonMap;

  public ToolbarButtonController (Map<String, JButton> buttonMap) {
    this.buttonMap = buttonMap;
    appModel.addStackListener(AppModel.Listener.UNDO_AVAILABLE, this::setUndoButtonEnabled);
    appModel.addStackListener(AppModel.Listener.UNDO_UNAVAILABLE, this::setUndoButtonEnabled);
    appModel.addStackListener(AppModel.Listener.REDO_AVAILABLE, this::setRedoButtonEnabled);
    appModel.addStackListener(AppModel.Listener.REDO_UNAVAILABLE, this::setRedoButtonEnabled);
  }

  public Void setUndoButtonEnabled(boolean enabled) {
    JButton button = buttonMap.get(TOOLBAR_UNDO);
    if (button != null) {
      button.setEnabled(enabled);
    }
    return null;
  }

  public Void setRedoButtonEnabled(boolean enabled) {
    JButton button = buttonMap.get(TOOLBAR_REDO);
    if (button != null) {
      button.setEnabled(enabled);
    }
    return null;
  }
}

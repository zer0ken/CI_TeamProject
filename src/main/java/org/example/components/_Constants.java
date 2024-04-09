package org.example.components;

import java.awt.*;

public class _Constants {
    private _Constants() {
    }

    // string literal
    public static final String APP_TITLE = "앱 이름이 여기에 들어가야 한단다~";
    public static final String LOGIN_TITLE = "로그인";
    public static final String LOGIN_MESSAGE = "다른 사용자에게 표시할 이름을 입력하세요.";

    // size
    public static final int APP_WIDTH = 1400;
    public static final int APP_HEIGHT = 900;
    public static final int APP_SIDE_WIDTH = 200;
    public static final int APP_CENTER_WIDTH = APP_WIDTH - 2 * APP_SIDE_WIDTH;

    public static final int STYLE_WINDOW_HEIGHT = 300;
    public static final int TOOLBAR_HEIGHT = 30;
    public static final int CLIENTS_WINDOW_HEIGHT = 300;
    public static final Rectangle STYLE_WINDOW_BOUNDS =
            new Rectangle(0, 0, APP_SIDE_WIDTH, STYLE_WINDOW_HEIGHT);
    public static final Rectangle EDIT_WINDOW_BOUNDS =
            new Rectangle(0, STYLE_WINDOW_HEIGHT, APP_SIDE_WIDTH, APP_HEIGHT - STYLE_WINDOW_HEIGHT);
    public static final Rectangle TOOLBAR_BOUNDS =
            new Rectangle(APP_SIDE_WIDTH, 0, APP_CENTER_WIDTH, TOOLBAR_HEIGHT);
    public static final Rectangle CANVAS_BOUNDS =
            new Rectangle(APP_SIDE_WIDTH, TOOLBAR_HEIGHT, APP_CENTER_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT);
    public static final Rectangle CLIENTS_WINDOW_BOUNDS =
            new Rectangle(APP_SIDE_WIDTH + APP_CENTER_WIDTH, 0, APP_SIDE_WIDTH, CLIENTS_WINDOW_HEIGHT);
    public static final Rectangle SHAPES_WINDOW_BOUNDS =
            new Rectangle(APP_SIDE_WIDTH + APP_CENTER_WIDTH, CLIENTS_WINDOW_HEIGHT,
                    APP_SIDE_WIDTH, APP_HEIGHT - CLIENTS_WINDOW_HEIGHT);
}

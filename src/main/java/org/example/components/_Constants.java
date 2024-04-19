package org.example.components;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class _Constants {
    private _Constants() {
    }

    // string literal
    public static final String APP_TITLE = "앱 이름이 여기에 들어가야 한단다~";
    public static final String LOGIN_TITLE = "로그인";
    public static final String LOGIN_MESSAGE = "다른 사용자에게 표시할 이름을 입력하세요.";
    public static final String[] STYLE_LABELS = {
            "획 두께(px)", "획 색상", "채우기 색상", "텍스트 크기(px)", "텍스트 색상", "텍스트 내용" };
    public static final String STYLE_WINDOW_TITLE = "스타일 지정";
    public static final String STYLE_LINE_COLOR_TITLE = "획 색상을 선택하세요.";
    public static final String STYLE_FILL_COLOR_TITLE = "채우기 색상을 선택하세요.";
    public static final String STYLE_TEXT_COLOR_TITLE = "텍스트 색상을 선택하세요.";
    public static final String STYLE_DEFAULT_TEXT_CONTENT = "Text Content";
    public static final String CLIENTS_WINDOW_TITLE = "동시 접속자 명단";

    public static final String EDIT_WINDOW_TITLE = "스타일 수정";
    public static final String EDIT_APPLY_BUTTON = "스타일 변경";


    public static final String TOOLBAR_TITLE = "Toolbar";
    public static final String TOOLBAR_LINE = "직선";
    public static final String TOOLBAR_RECT = "사각형";
    public static final String TOOLBAR_OVAL = "원";
    public static final String TOOLBAR_TEXT = "텍스트";
    public static final String[] TOOLBAR_BUTTONS = {TOOLBAR_LINE, TOOLBAR_RECT, TOOLBAR_OVAL, TOOLBAR_TEXT};


    // size
    public static final int APP_WIDTH = 1400;
    public static final int APP_HEIGHT = 900;
    public static final int APP_LEFT_WIDTH = 200;
    public static final int APP_RIGHT_WIDTH = 300;
    public static final int APP_CENTER_WIDTH = APP_WIDTH - APP_LEFT_WIDTH - APP_RIGHT_WIDTH;

    public static final int STYLE_WINDOW_HEIGHT = 300;
    public static final int TOOLBAR_HEIGHT = 30;
    public static final int CLIENTS_WINDOW_HEIGHT = 300;

    public static final Dimension STYLE_WINDOW_SIZE = new Dimension(APP_LEFT_WIDTH, STYLE_WINDOW_HEIGHT);
    public static final Dimension EDIT_WINDOW_SIZE = new Dimension(APP_LEFT_WIDTH, APP_HEIGHT - STYLE_WINDOW_HEIGHT);
    public static final Dimension TOOLBAR_SIZE = new Dimension(APP_CENTER_WIDTH, TOOLBAR_HEIGHT);
    public static final Dimension CANVAS_SIZE = new Dimension(APP_CENTER_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT);
    public static final Dimension CLIENTS_WINDOW_SIZE = new Dimension(APP_RIGHT_WIDTH, CLIENTS_WINDOW_HEIGHT);
    public static final Dimension SHAPES_WINDOW_SIZE =new Dimension(APP_RIGHT_WIDTH, APP_HEIGHT - CLIENTS_WINDOW_HEIGHT);

    public static final int V_SPACE = 4;
    public static final int H_SPACE = 8;

    public static final int STYLE_ITEM_HEIGHT = 26;
    public static final int STYLE_SLOT_WIDTH = 64;
    public static final EmptyBorder STYLE_ITEM_BORDER = new EmptyBorder(V_SPACE, H_SPACE, V_SPACE, H_SPACE);
    public static final Dimension STYLE_ITEM_SIZE = new Dimension(APP_LEFT_WIDTH, STYLE_ITEM_HEIGHT);
    public static final Dimension STYLE_LARGE_ITEM_SIZE = new Dimension(APP_LEFT_WIDTH, STYLE_ITEM_HEIGHT*2);

}

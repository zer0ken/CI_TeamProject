package org.client.gui;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Constants {
    private Constants() {
    }

    // string literal
    public static final String APP_TITLE = "실시간 공유 화이트보드";
    public static final String LOGIN_TITLE = "로그인";
    public static final String LOGIN_MESSAGE = "다른 사용자에게 표시할 이름을 입력하세요.";
    public static final String LOGIN_MESSAGE_WITH_INVALID_NAME = "입력하신 이름은 이미 사용되고 있습니다.\n다른 사용자에게 표시할 이름을 입력하세요.";
    public static final String[] STYLE_LABELS = {
            "획 두께(px)", "획 색상", "채우기 색상", "텍스트 크기(px)", "텍스트 색상", "텍스트 내용"};
    public static final String STYLE_WINDOW_TITLE = "스타일 지정";
    public static final String STYLE_LINE_COLOR_TITLE = "획 색상을 선택하세요.";
    public static final String STYLE_FILL_COLOR_TITLE = "채우기 색상을 선택하세요.";
    public static final String STYLE_TEXT_COLOR_TITLE = "텍스트 색상을 선택하세요.";
    public static final String STYLE_DEFAULT_TEXT_CONTENT = "Text Content";
    public static final String[] STYLE_TOOL_TIPS = {
            "새로 그릴 도형의 획 두께를 설정합니다.",
            "새로 그릴 도형의 획 색상을 설정합니다.",
            "새로 그릴 도형의 채우기 색상을 설정합니다.",
            "새로 그릴 텍스트의 크기를 설정합니다.",
            "새로 그릴 텍스트의 색상을 설정합니다.",
            "새로 그릴 텍스트의 내용을 설정합니다."
    };

    public static final String CLIENTS_WINDOW_TITLE = "동시 접속자 명단";

    public static final String EDIT_WINDOW_TITLE = "스타일 변경";
    public static final String APPLY_TEXT_BUTTON_CONTENT = "[Tab] 키로 텍스트 적용";
    public static final String[] EDIT_TOOL_TIPS = {
            "도형의 획 두께를 수정합니다.",
            "도형의 획 색상을 수정합니다.",
            "도형의 채우기 색상을 수정합니다.",
            "텍스트의 크기를 수정합니다.",
            "텍스트의 색상을 수정합니다.",
            "수정할 텍스트의 내용을 입력합니다.",
            "수정된 텍스트를 적용합니다."
    };

    public static final String SHAPES_WINDOW_TITLE = "생성된 도형 목록";

    public static final String TOOLBAR_TITLE = "도구 모음";
    public static final String TOOLBAR_LINE = "직선 도구";
    public static final String TOOLBAR_RECT = "사각형 도구";
    public static final String TOOLBAR_OVAL = "원 도구";
    public static final String TOOLBAR_TEXT = "텍스트 도구";
    public static final String[] TOOLBAR_BUTTONS = { TOOLBAR_LINE, TOOLBAR_RECT, TOOLBAR_OVAL, TOOLBAR_TEXT };
    public static final String[] TOOLBAR_TOOL_TIPS = {
            "캔버스에 직선을 그립니다.",
            "캔버스에 사각형을 그립니다.",
            "캔버스에 원을 그립니다.",
            "캔버스에 텍스트를 그립니다."
    };
    public static final String[] TOOLBAR_ICONS = {
            "src/res/line_icon.png",
            "src/res/rectangle_icon.png",
            "src/res/oval_icon.png",
            "src/res/text_icon.png",
    };

    public static final String LINE = "직선";
    public static final String RECT = "사각형";
    public static final String OVAL = "원";
//    public static final String TEXT = "텍스트";

    // dimension
    public static final int APP_WIDTH = 1400;
    public static final int APP_HEIGHT = 900;
    public static final int APP_LEFT_WIDTH = 200;
    public static final int APP_RIGHT_WIDTH = 200;
    public static final int APP_CENTER_WIDTH = APP_WIDTH - APP_LEFT_WIDTH - APP_RIGHT_WIDTH;

    public static final int STYLE_WINDOW_HEIGHT = 300;
    public static final int TOOLBAR_HEIGHT = 40;
    public static final int CLIENTS_WINDOW_HEIGHT = 300;

    public static final Dimension STYLE_WINDOW_SIZE = new Dimension(APP_LEFT_WIDTH, STYLE_WINDOW_HEIGHT);
    public static final Dimension EDIT_WINDOW_SIZE = new Dimension(APP_LEFT_WIDTH, APP_HEIGHT - STYLE_WINDOW_HEIGHT - TOOLBAR_HEIGHT);
    public static final Dimension TOOLBAR_SIZE = new Dimension(APP_WIDTH, TOOLBAR_HEIGHT);
    public static final Dimension CANVAS_SIZE = new Dimension(APP_CENTER_WIDTH, APP_HEIGHT - TOOLBAR_HEIGHT);
    public static final Dimension CLIENTS_WINDOW_SIZE = new Dimension(APP_RIGHT_WIDTH, CLIENTS_WINDOW_HEIGHT);
    public static final Dimension SHAPES_WINDOW_SIZE = new Dimension(APP_RIGHT_WIDTH, APP_HEIGHT - CLIENTS_WINDOW_HEIGHT - TOOLBAR_HEIGHT);

    public static final int DEFAULT_LINE_WIDTH = 1;
    public static final int DEFAULT_TEXT_SIZE = 12;

    public static final EmptyBorder TITLED_PANEL_PADDING = new EmptyBorder(8, 12, 8, 12);

    public static final EmptyBorder DEFAULT_COMPONENT_PADDING = new EmptyBorder(8, 12, 0, 12);

    public static final Dimension X_LABELED_PANEL_SIZE = new Dimension(APP_LEFT_WIDTH, 30);
    public static final Dimension X_LABELED_COMPONENT_SIZE = new Dimension(64, 30);
    public static final Dimension Y_LABELED_PANEL_SIZE = new Dimension(APP_LEFT_WIDTH, 60);
    public static final Dimension Y_LABELED_COMPONENT_SIZE = new Dimension(APP_LEFT_WIDTH, 60);

    //others
    public static final Color DEFAULT_LINE_COLOR = Color.black;
    public static final Color DEFAULT_FILL_COLOR = Color.white;
    public static final Color DEFAULT_TEXT_COLOR = Color.black;
    public static final Color PANEL_SEPERATOR_COLOR = Color.lightGray;
}

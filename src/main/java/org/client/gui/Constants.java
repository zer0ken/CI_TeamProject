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

    public static final String STYLE_WINDOW_TITLE = "스타일 지정";
    public static final String STYLE_WINDOW_TOOLTIP = "새로 생성될 도형의 스타일을 지정합니다.";

    public static final String CLIENTS_WINDOW_TITLE = "접속 중인 사용자 목록";
    public static final String CLIENTS_WINDOW_TOOLTIP = "접속 중인 사용자 목록을 표시합니다.";

    public static final String EDIT_WINDOW_TITLE = "스타일 편집";
    public static final String EDIT_WINDOW_TOOLTIP = "선택된 도형의 스타일을 수정합니다.";

    public static final String SHAPES_WINDOW_TITLE = "생성된 도형 목록";
    public static final String SHAPES_WINDOW_TOOLTIP = "생성된 모든 도형의 목록을 표시합니다.";

    public static final String[] STYLE_LABELS = {
            "획 두께(px):", "획 색상:", "채우기 색상:", "텍스트 크기(px):", "텍스트 색상:", "텍스트 내용:"
    };
    public static final String STYLE_LINE_COLOR_TITLE = "획 색상을 선택하세요.";
    public static final String STYLE_FILL_COLOR_TITLE = "채우기 색상을 선택하세요.";
    public static final String STYLE_TEXT_COLOR_TITLE = "텍스트 색상을 선택하세요.";
    public static final String STYLE_DEFAULT_TEXT_CONTENT = "Text Content";

    public static final String[] STYLE_TOOLTIPS = {
            "새로 그릴 도형의 획 두께를 설정합니다.",
            "새로 그릴 도형의 획 색상을 설정합니다.",
            "새로 그릴 도형의 채우기 색상을 설정합니다.",
            "새로 그릴 텍스트의 크기를 설정합니다.",
            "새로 그릴 텍스트의 색상을 설정합니다.",
            "새로 그릴 텍스트의 내용을 설정합니다."
    };

    public static final String APPLY_TEXT_BUTTON_CONTENT = "[Tab] 키로 텍스트 적용";

    public static final String[] EDIT_TOOLTIPS = {
            "도형의 획 두께를 수정합니다.",
            "도형의 획 색상을 수정합니다.",
            "도형의 채우기 색상을 수정합니다.",
            "텍스트의 크기를 수정합니다.",
            "텍스트의 색상을 수정합니다.",
            "수정할 텍스트의 내용을 입력합니다.",
            "수정된 텍스트를 적용합니다."
    };

    public static final String TOOLBAR_TITLE = "도구 모음";

    public static final String TOOLBAR_LINE = "직선 도구";
    public static final String TOOLBAR_RECT = "사각형 도구";
    public static final String TOOLBAR_OVAL = "원 도구";
    public static final String TOOLBAR_TEXT = "텍스트 도구";
    public static final String TOOLBAR_DELETE = "삭제";
    public static final String TOOLBAR_UNDO = "undo";
    public static final String TOOLBAR_REDO = "redo";

    public static final String[] TOOLBAR_SHAPE_TOOLS = {
            TOOLBAR_LINE,
            TOOLBAR_RECT,
            TOOLBAR_OVAL,
            TOOLBAR_TEXT,
            TOOLBAR_DELETE,
            TOOLBAR_UNDO,
            TOOLBAR_REDO
    };
    public static final String[] TOOLBAR_SHAPE_TOOLTIPS = {
            "캔버스에 직선을 그립니다.",
            "캔버스에 사각형을 그립니다.",
            "캔버스에 원을 그립니다.",
            "캔버스에 텍스트를 그립니다.",
            "캔버스에서 도형을 삭제합니다.",
            "이전 행동을 되돌립니다.",
            "이전에 되돌린 행동을 다시 수행합니다."
    };
    public static final String[] TOOLBAR_SHAPE_ICONS = {
            "icon/line_icon.png",
            "icon/rectangle_icon.png",
            "icon/oval_icon.png",
            "icon/text_icon.png",
            "icon/remove_icon.png",
            "icon/undo_icon.png",
            "icon/redo_icon.png",
    };

    public static final String LINE = "직선";
    public static final String RECT = "사각형";
    public static final String OVAL = "원";

    public static final String[] EAST_TOOLBAR_TOOLS = {
            EDIT_WINDOW_TITLE + "창 열기",
            SHAPES_WINDOW_TITLE + "창 열기"
    };
    public static final String[] EAST_TOOLBAR_TOOLTIPS = {
            EDIT_WINDOW_TITLE,
            SHAPES_WINDOW_TITLE
    };
    public static final String[] EAST_TOOLBAR_ICONS = {
            "icon/clients_icon.png",
            "icon/shapes_icon.png"
    };

    public static final String[] WEST_TOOLBAR_TOOLS = {
            STYLE_WINDOW_TITLE + "창 열기",
            EDIT_WINDOW_TITLE + "창 열기"
    };
    public static final String[] WEST_TOOLBAR_TOOLTIPS = {
            STYLE_WINDOW_TITLE,
            EDIT_WINDOW_TITLE
    };
    public static final String[] WEST_TOOLBAR_ICONS = {
            "icon/style_icon.png",
            "icon/edit_icon.png"
    };

    // dimension
    public static final int APP_WIDTH = 1200;
    public static final int APP_HEIGHT = 800;

    public static final int WEST_PANEL_WIDTH = 186;
    public static final int EAST_PANEL_WIDTH = 186;

    public static final Dimension APP_MIN_SIZE = new Dimension(WEST_PANEL_WIDTH + EAST_PANEL_WIDTH + 300, 300);

    public static final int TOOLBAR_HEIGHT = 32;

    public static final Dimension STYLE_WINDOW_SIZE = new Dimension(WEST_PANEL_WIDTH, 0);
    public static final Dimension EDIT_WINDOW_SIZE = new Dimension(WEST_PANEL_WIDTH, 0);

    public static final Dimension CLIENTS_WINDOW_SIZE = new Dimension(EAST_PANEL_WIDTH, 0);
    public static final Dimension SHAPES_WINDOW_SIZE = new Dimension(EAST_PANEL_WIDTH, 0);

    public static final int DEFAULT_LINE_WIDTH = 1;
    public static final int DEFAULT_TEXT_SIZE = 12;

    public static final EmptyBorder DEFAULT_PANEL_PADDING = new EmptyBorder(6, 12, 6, 12);

    public static final Insets STYLE_PANEL_LABEL_INSET = new Insets(4, 0, 4, 0);
    public static final Dimension STYLE_PANEL_LABEL_SIZE = new Dimension(0, 22);
    public static final Dimension STYLE_PANEL_COMPONENT_SIZE = new Dimension(60, 22);

    public static final Dimension TOOLBAR_ICON_SIZE = new Dimension(TOOLBAR_HEIGHT - 12, TOOLBAR_HEIGHT - 12);

    //others
    public static final Color DEFAULT_LINE_COLOR = Color.black;
    public static final Color DEFAULT_FILL_COLOR = Color.white;
    public static final Color DEFAULT_TEXT_COLOR = Color.black;
}

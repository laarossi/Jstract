package node;

public enum Tag {

    HTML, DIV, HEAD, BODY, BASE, BR, FORM, UL, LI, DL, DT, TITLE, H1, H2,
    H3, H4, H5, H6, IFRAME, LABEL, LEGEND, EM, FOOTER, HEADER, NAV, META,
    SCRIPT, STYLE, TD, TR, TABLE, SPAN, B, U, I, P, A, INPUT;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

package document;

public enum HTML_PATTERN {

    CLASS("\\.(.+)"),
    ID("#(.+)"),
    OPEN_TAG("<[^>]*"),
    CLOSED_TAG("<[^>]*");

    private final String pattern;

    HTML_PATTERN(String pattern){
        this.pattern = pattern;
    }


    @Override
    public String toString() {
        return pattern;
    }

    public String getPattern() {
        return pattern;
    }
}

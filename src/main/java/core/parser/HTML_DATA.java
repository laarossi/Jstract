package core.parser;

public enum HTML_DATA {

    OPEN_TAG(1),AUTO_TAG(2),CLOSE_TAG(3);
    int id;
    HTML_DATA(int id){
        this.id = id;
    }

}

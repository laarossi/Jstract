package core.node;

public class TextNode extends Node{

    public String text;

    public TextNode(String text){
        this.text = text;
    }

    public String print(int i) {
        return "body : " + text;
    }

}

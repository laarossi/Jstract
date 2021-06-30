package core.node.document;

import core.node.Element;

import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder {

    List<Element> parents = new ArrayList<>();
    private String documentTitle;

    private DocumentBuilder(){

    }

    public static DocumentBuilder create(List<Element> tags){
        DocumentBuilder documentBuilder = new DocumentBuilder();
        documentBuilder.parents.addAll(tags);
        return documentBuilder;
    }

    public Document build(){
        Document document = new Document();
        document.init(this.parents);
        return document;
    }

}

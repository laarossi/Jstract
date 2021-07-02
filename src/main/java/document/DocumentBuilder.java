package document;

import node.Element;

import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder {

    List<Element> parents = new ArrayList<>();
    private String documentTitle;

    private DocumentBuilder(){ }

    public static DocumentBuilder create(List<Element> tags){
        DocumentBuilder documentBuilder = new DocumentBuilder();
        documentBuilder.parents.addAll(tags);
        return documentBuilder;
    }

    public static DocumentBuilder create(){
        return new DocumentBuilder();
    }

    public Document build(){
        Document document = new Document();
        if(parents.size() != 0) document.setDocElements(parents);
        return document;
    }

}

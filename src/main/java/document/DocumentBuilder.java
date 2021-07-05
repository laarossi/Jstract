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

    public DocumentBuilder title(String title){
        this.documentTitle = title;
        return this;
    }

    public Document build(){
        Document document = new Document();
        if(documentTitle != null){
            document.setTitle(documentTitle);
            document.setDocElements(parents);
        }else document.init(parents);
        return document;
    }

}

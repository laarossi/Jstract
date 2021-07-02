package document;

import node.Element;

import java.util.ArrayList;
import java.util.List;

public class Document {

    private List<Element> docElements = new ArrayList<>();
    private String title;
    private Element root;

    public void init(List<Element> docElements){
        this.setDocElements(docElements);
        if(docElements.size() == 1) this.root = docElements.get(0);
        if(title == null) this.title = get("title").text();
    }

    public List<Element> getDocElements() {
        return docElements;
    }

    public void setDocElements(List<Element> elements){
        this.docElements = elements;
    }

    public Element get(String pattern){
        return null;
    }

}

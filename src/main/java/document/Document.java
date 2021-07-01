package document;

import node.Element;

import java.util.ArrayList;
import java.util.List;

public class Document {

    public List<Element> docElements = new ArrayList<>();
    private String title;
    private Element root;

    public void init(List<Element> documentParentsTags){
        if(documentParentsTags.size() == 1) root = documentParentsTags.get(0);
        docElements.addAll(documentParentsTags);
    }


}

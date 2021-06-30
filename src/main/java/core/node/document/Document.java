package core.node.document;

import core.node.Element;

import java.util.List;

public class Document {

    private List<Element> docElements;
    private String title;
    private Element root;

    public void init(List<Element> documentParentsTags){
        if(documentParentsTags.size() == 1) root = documentParentsTags.get(0);
        for(Element tag : documentParentsTags){
            formatTag(tag);
        }
    }

    private void formatTag(Element element){
        String[] tag = element.tag.replaceAll(" +", " ").replaceAll("<|>", "").split(" ");
        element.tag = tag[0];
        for(int i = 1; i < tag.length; i++){
            element.setAttribute(tag[i]);
        }
    }


}

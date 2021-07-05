package document;

import node.Element;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Document {

    private CopyOnWriteArrayList<Element> docElements = new CopyOnWriteArrayList<>();
    private String title;
    private Element root;
    private CopyOnWriteArrayList<Element> searchList = new CopyOnWriteArrayList<>();

    public void init(List<Element> docElements) {
        setDocElements(docElements);
        if (docElements.size() == 1) root = docElements.get(0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Element> getDocElements() {
        return docElements;
    }

    public void setDocElements(List<Element> elements) {
        this.docElements = new CopyOnWriteArrayList<>(elements);
    }

    public List<Element> get(String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) return null;
        String[] patterns = pattern.trim().replaceAll("([^/\"'><]*)(\\.|#|\\[)", "$1<=>$2").replaceAll(" +", " ").split(" ");
        List<Element> comparators = new ArrayList<>();
        for (int i = 0; i < patterns.length; i++) {
            String p = patterns[i];
            List<String> subPatterns = Arrays.stream(p.split("<=>")).filter(value -> !value.trim().isEmpty()).collect(Collectors.toList());
            Element comparator = new Element();
            for (String spec : subPatterns) {
                if (spec.startsWith(".") || spec.startsWith("#")) {
                    if (spec.trim().startsWith(".")) {
                        comparator.setAttribute("class", spec.replace(".", ""));
                    } else comparator.setAttribute("id", spec.replace("#", ""));
                } else {
                    Pattern attrPattern = Pattern.compile("\\[(?<attr>[^=\\\"'\\/><]+)(=(?<value>.*)){0,1}\\]");
                    Matcher matcher = attrPattern.matcher(spec);
                    if (matcher.find()) {
                        String value = (matcher.group("value") != null && !matcher.group("value").trim().isEmpty()) ? matcher.group("value") : "true";
                        comparator.setAttribute(matcher.group("attr"), value);
                    } else comparator.setTag(spec);
                }
            }
            comparators.add(comparator);
        }
        for(Element comparator : comparators){
            for(Element parent : searchList.size() == 0 ? docElements : searchList){
                searchParent(comparator, parent);
            }
        }
        return searchList;
    }

    public void searchParent(Element comparator, Element element) {
        if(comparator.isEqual(element)){
            if(!searchList.contains(element)) searchList.add(element);
            else searchList.remove(element);
        }else{
            searchList.remove(element);
        }
        for(Element child : element.getChildren()){
            searchParent(comparator, child);
        }
    }

}

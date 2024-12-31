package JavaTechniques;

public class CaelanValueEntry {
    private Integer key;
    //private key field
    public CaelanValueEntry(){
        this.key = -1;
    }
    //constructor without argument, sets key value to -1 (null)
    public CaelanValueEntry(Integer key){
        this.key= key;
    }
    //constructor with Integer type argument
    public void setKey(Integer key){
        this.key = key;
    }
    //setter method
    public Integer getKey(){
        return key;
    }
    //getter method

}

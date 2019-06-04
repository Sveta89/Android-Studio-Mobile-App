package svetlanavictorino.mywgutracker;

public class Term {
    private String id;
    private String title;
    private String start;
    private String end;

    public Term(String id, String title, String start, String end){
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;

    }
    public Term(String title, String start, String end){
        this.title = title;
        this.start = start;
        this.end = end;

    }

    public Term(){

    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getStart(){
        return start;
    }
    public void setStart(String start){
        this.start = start;
    }

    public String getEnd(){
        return end;
    }
    public void setEnd(String end){
        this.end = end;
    }

    //Will be used by ArrayAdapter in the ListView

    @Override
    public String toString(){
        return title;
    }
}

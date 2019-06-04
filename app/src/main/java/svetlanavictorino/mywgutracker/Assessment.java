package svetlanavictorino.mywgutracker;

public class Assessment {
    private String id;
    private String courseId;
    private String title;
    private String type;
    private String dueDate;
    private Boolean dueDateCheckbox;

    public Assessment (String courseId, String title, String type, String dueDate, Boolean dueDateCheckbox){
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.dueDateCheckbox = dueDateCheckbox;
    }

    public Assessment(){

    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getCourseId(){
        return courseId;
    }
    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type= type;
    }

    public String getDueDate(){
        return dueDate;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getDueDateCheckbox(){
        return dueDateCheckbox;
    }
    public void setDueDateCheckbox(Boolean dueDateCheckbox) {
        this.dueDateCheckbox = dueDateCheckbox;
    }
}

package svetlanavictorino.mywgutracker;

public class Course {
    private String id;
    private String termId;
    private String code;
    private String title;
    private String startDate;
    private Boolean startCheckBox;
    private String endDate;
    private Boolean endCheckBox;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String mentor2Name;
    private String mentor2Phone;
    private String mentor2Email;
    private String status;
    private String notes;



    public Course(String termId, String code, String title, String startDate, Boolean startCheckBox, String endDate, Boolean endCheckBox, String mentorName, String mentorPhone, String mentorEmail, String status, String notes){
        this.termId = termId;
        this.code = code;
        this.title = title;
        this.startDate = startDate;
        this.startCheckBox = startCheckBox;
        this.endDate = endDate;
        this.endCheckBox = endCheckBox;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.status = status;
        this.notes = notes;
    }

    public Course(String termId, String code, String title, String startDate, Boolean startCheckBox, String endDate, Boolean endCheckBox, String mentorName, String mentorPhone, String mentorEmail, String mentor2Name, String mentor2Phone, String mentor2Email, String status, String notes){
        this.termId = termId;
        this.code = code;
        this.title = title;
        this.startDate = startDate;
        this.startCheckBox = startCheckBox;
        this.endDate = endDate;
        this.endCheckBox = endCheckBox;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
        this.mentor2Name = mentor2Name;
        this.mentor2Phone = mentor2Phone;
        this.mentor2Email = mentor2Email;
        this.status = status;
        this.notes = notes;
    }

    public Course(){

    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getTermId(){
        return termId;
    }
    public void setTermId(String termId){
        this.termId = termId;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getStartCheckBox(){
        return startCheckBox;
    }
    public void setStartCheckBox(Boolean startCheckBox) {
        this.startCheckBox = startCheckBox;
    }

    public String getEndDate(){
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getEndCheckBox(){
        return endCheckBox;
    }
    public void setEndCheckBox(Boolean endCheckBox) {
        this.endCheckBox = endCheckBox;
    }

    public String getMentorName(){
        return mentorName;
    }
    public void setMentorName(String name) {
        this.mentorName = name;
    }

    public String getMentorPhone(){
        return mentorPhone;
    }
    public void setMentorPhone(String phone) {
        this.mentorPhone = phone;
    }

    public String getMentorEmail(){
        return mentorEmail;
    }
    public void setMentorEmail(String email) {
        this.mentorEmail = email;
    }

    public String getMentor2Name(){
        return mentor2Name;
    }
    public void setMentor2Name(String name) {
        this.mentor2Name = name;
    }

    public String getMentor2Phone(){
        return mentor2Phone;
    }
    public void setMentor2Phone(String phone) {
        this.mentor2Phone = phone;
    }

    public String getMentor2Email(){
        return mentor2Email;
    }
    public void setMentor2Email(String email) {
        this.mentor2Email = email;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}

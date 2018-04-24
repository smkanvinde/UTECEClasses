public class Course {

    private String course;
    private double diff;

    public Course(String c, double dif) {
        this.course = c;
        this.diff = dif;
    }

    public String getCourse() {
        return this.course;
    }

    public double getDiff() {
        return this.diff;
    }
}
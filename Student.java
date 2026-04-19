public class Student{
  private  String name;
  private  int rollno;
  private  double gpa;
  private  String city;
   public Student(String n,int rn,double g,String c){//Constructor -to initialize the objects
    this.name=n;
    this.rollno=rn;
    this.gpa=g;
    this.city=c;
  }

    public String getName() {
        return name;
    }

    public int getRollno() {
        return rollno;
    }
  public double getGpa() {
    return gpa;
  }
  public String getCity() {
    return city;
  }
}
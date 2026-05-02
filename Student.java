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

    public void setCity(String city) {
        this.city = city;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setName(String name) {
        this.name = name;
    }
  public void setRollno(int rollno) {
    this.rollno = rollno;
  }
  public String toString(){
    return name+","+rollno+","+gpa+","+city;
  }
}
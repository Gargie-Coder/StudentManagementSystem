
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StudentManagementSystem {
    static List<Student> students=new ArrayList<>();
   //Method to write a line to the file
    public static void writeFile(String line){
     try(BufferedWriter bw=new BufferedWriter(new FileWriter("Student.txt",true));){//Try with resources to ensure proper closing of the writer
         bw.write(line);
         bw.newLine();
        }
      catch(Exception e){
        System.out.println("An error occurred while writing to the file.");
      } 
    }

//Method to read the file and populate the list of students
    public static void readFile(File f){
        try(BufferedReader br=new BufferedReader(new FileReader(f));){//Try with resources to ensure proper closing of the reader
            String line;
            while((line=br.readLine())!=null){
              if(line.trim().isEmpty()) continue; // skip empty lines
              if(line.split(",").length<4) continue; // skip invalid lines
              String[] data=line.split(",");
              String name=data[0];
              int rollno=Integer.parseInt(data[1]);
              double gpa=Double.parseDouble(data[2]);
              String city=data[3];
              students.add(new Student(name,rollno,gpa,city));
            }
            }
            catch(Exception e){
              System.out.println("File not found or an error occurred");
            }
}
   
    //Method to add a student to the list and write to the file
    public static void add(Scanner sc){
    System.out.println("Enter the name,roll no.,gpa and city of the new Student");
    String name=sc.nextLine();
    int rollno=sc.nextInt();
    for(Student s:students){
      if(s.getRollno()==rollno){
        System.out.println("A student with this roll no. already exists. Please use a unique roll no.");
        return;
      }
    }
    double gpa=sc.nextDouble();
    sc.nextLine();
    String city=sc.nextLine();
    Student s=new Student(name,rollno,gpa,city);
    students.add(s);
    String line=name+","+rollno+","+gpa+","+city;
    writeFile(line);
    }

    //Method to remove a student from the list and update the file
public static void remove(Scanner sc){
   System.out.println("Enter the roll no. of the student to be removed");
      int rollno=sc.nextInt();
    // Remove from list
    students.removeIf(s -> s.getRollno() == rollno);

    try{
        Path path = Paths.get("Student.txt");//Because Java’s modern file system (java.nio) works with Path objects, not raw strings.Reads file using its location
        List<String> lines = Files.readAllLines(path);
        lines.removeIf(line -> {
            if(line.trim().isEmpty()) return false; // skip empty lines

            String[] parts = line.split(",");
            if(parts.length < 2) return false; // skip invalid lines

            return parts[1].trim().equals(String.valueOf(rollno));
        });

        Files.write(path, lines);// Writes back to same location

    } catch(Exception e){
        e.printStackTrace(); 
    }
}
//Method to update a student’s information in the list and update the file
  public static void update(Scanner sc){
     System.out.println("Enter the roll no. of the student to be updated:");
     int rollno=sc.nextInt();
      Student studentToUpdate = null;
      for (Student s : students) {
          if (s.getRollno() == rollno) {
              studentToUpdate = s;
              break;
          }
      }
      if (studentToUpdate == null) {
          System.out.println("Student with roll no. " + rollno + " not found.");
          return;
      }
    System.out.println("Enter the new name, roll no., gpa and city of the Student");
    sc.nextLine(); // ✅ clear buffer first
    String name = sc.nextLine();
    if (!sc.hasNextInt()) throw new InputMismatchException();
    int newRollno = sc.nextInt();
    if (!sc.hasNextDouble()) throw new InputMismatchException();
    double gpa = sc.nextDouble();
    sc.nextLine(); // clear buffer
    String city = sc.nextLine();
    studentToUpdate.setName(name);
    studentToUpdate.setRollno(newRollno);
    studentToUpdate.setGpa(gpa);
    studentToUpdate.setCity(city);
  try {
      Path path=Paths.get("Student.txt");
      List<String> lines=Files.readAllLines(path);
      for (int i = 0; i < lines.size(); i++) {
          String line = lines.get(i);
          if(line.trim().isEmpty()) continue; // skip empty lines

          String[] parts = line.split(",");
          if(parts.length < 2) continue; // skip invalid lines

          if (parts[1].trim().equals(String.valueOf(rollno))) {
              lines.set(i, name + "," + newRollno + "," + gpa + "," + city);
              break;
          }
      }
        Files.write(path, lines);
    
} catch (InputMismatchException e) {
    System.out.println("Invalid input! Please enter correct data types.");
} 
catch (Exception e) {
    e.printStackTrace();
}
 }

 //Method to display students based on Names
 public static void displayByNames(){
  List<Student> sortStudents=new ArrayList<>(students);
  Collections.sort(sortStudents,(s1,s2)->s1.getName().compareToIgnoreCase(s2.getName()));
  System.out.printf("%-15s %-10s %-8s %-15s%n", 
                  "Name", "Roll No", "GPA", "City");
    System.out.println("-----------------------------------------------------");

    for(Student s : sortStudents){
        System.out.printf("%-15s %-10d %-8.2f %-15s%n",
            s.getName(),
            s.getRollno(),
            s.getGpa(),
            s.getCity()
        );
    }
 }

 //Method to display students based on decreasing Gpa
 public static void displayByGpa(){
  List<Student> sortedStudents = new ArrayList<>(students);
  Collections.sort(sortedStudents,(s1,s2)->Double.compare(s2.getGpa(),s1.getGpa()));
  System.out.printf("%-15s %-10s %-8s %-15s%n", 
                  "Name", "Roll No", "GPA", "City");
  System.out.println("-----------------------------------------------------");
  for(Student s:sortedStudents){
    System.out.printf("%-15s %-10d %-8.2f %-15s%n",
        s.getName(),
        s.getRollno(),
        s.getGpa(),
        s.getCity()
    );
  }
 }

//.......Main method drives the program, displays menu and handles user choices...............
  public static void main(String[] args)  {
    File f=new File("Student.txt");
    readFile(f);
    boolean exit=false;
    Scanner sc=new Scanner(System.in);
    int choice=0;
    while(!exit){
      System.out.println("Choose one of the options to make changes to the student data:-");
      System.out.println("1.Add Student\n2.Update Student\n3.Remove Student\n4.Show students according to the names in Ascending order\n5.Show students based on the decreasing Gpa\n6.Exit");
        System.out.println("Enter your choice");
        choice=sc.nextInt();
        sc.nextLine();
     
    switch(choice){
      case 1:
      add(sc);
      System.out.println("Added Successfully!");
      
      try {
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 2:
      update(sc);
      System.out.println("Student info updated");
      try {
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 3:
      remove(sc);
       System.out.println("Student Removed Successfully!");
      try {
       
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
       
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 4:
      System.out.println("Displaying students:");
      displayByNames();
      try {
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 5:
      System.out.println("Displaying students based on decreasing Gpa:");
      displayByGpa();
      try {
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 6:
      System.out.println("Thanks for using Student Management System!");
      exit=true;
      break;
      default:
      System.out.println("Invalid choice,please choose a valid choice");
     
    }


  }
    
  }
}

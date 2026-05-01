
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StudentManagementSystem {
    static List<Student> students=new ArrayList<>();
    public static void writeFile(String line){
     try{
        FileWriter fw=new FileWriter("Student.txt",true);
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(line);
        bw.newLine();
        bw.close();
      }
      catch(Exception e){
        System.out.println("An error occurred while writing to the file.");
      } 
    }


    public  static void readFile(File f){
        try{
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null){
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
      double gpa=sc.nextDouble();
      sc.nextLine();
      String city=sc.nextLine();
      Student s=new Student(name,rollno,gpa,city);
      students.add(s);
      String line=name+","+rollno+","+gpa+","+city;
      writeFile(line);
     }
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

        Files.write(path, lines);//👉 Writes back to same location

    } catch(Exception e){
        e.printStackTrace(); // VERY IMPORTANT
    }
}
// public static void update(sc){

// }


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
      System.out.println("Getting back to main menu...");
      try {
        Thread.sleep(1000);
        
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 2:
      System.out.println("Student info updated");
      System.out.println("Getting back to main menu...");
      try {
        Thread.sleep(1000);
        
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 3:
      remove(sc);
      try {
        System.out.println("Student Removed Successfully!");
         System.out.println("Getting back to main menu...");
        Thread.sleep(1000);
       
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 4:
      System.out.println("Displaying students:");
      try {
        Thread.sleep(1000);
        System.out.println("Getting back to main menu...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      break;
      case 5:
      System.out.println("Displaying students based on decreasing Gpa:");
      try {
        Thread.sleep(1000);
         System.out.println("Getting back to main menu...");
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

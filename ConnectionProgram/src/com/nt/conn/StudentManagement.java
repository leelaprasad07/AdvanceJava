package com.nt.conn;

import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Database connection
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:ORCL",
                "leela",
                "0707");

        int choice;

        do {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    PreparedStatement ps1 = con.prepareStatement(
                            "INSERT INTO student VALUES (?, ?, ?, ?)");

                    ps1.setInt(1, id);
                    ps1.setString(2, name);
                    ps1.setInt(3, age);
                    ps1.setString(4, course);

                    ps1.executeUpdate();

                    System.out.println("Student Added Successfully");
                    ps1.close();
                    break;

                case 2:
                    Statement st = con.createStatement();

                    ResultSet rs = st.executeQuery(
                    		"SELECT * FROM student ORDER BY id ASC");

                    while (rs.next()) {
                        System.out.println(
                                rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getInt("age") + " " +
                                rs.getString("course"));
                    }

                    rs.close();
                    st.close();
                    break;

                case 3:
                    System.out.print("Enter ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter New Age: ");
                    int newAge = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Course: ");
                    String newCourse = sc.nextLine();

                    PreparedStatement ps2 = con.prepareStatement(
                            "UPDATE student SET name=?, age=?, course=? WHERE id=?");

                    ps2.setString(1, newName);
                    ps2.setInt(2, newAge);
                    ps2.setString(3, newCourse);
                    ps2.setInt(4, updateId);

                    int updated = ps2.executeUpdate();

                    if (updated > 0)
                        System.out.println("Student Updated");
                    else
                        System.out.println("Student Not Found");

                    ps2.close();
                    break;

                case 4:
                    System.out.print("Enter ID: ");
                    int deleteId = sc.nextInt();

                    PreparedStatement ps3 = con.prepareStatement(
                            "DELETE FROM student WHERE id=?");

                    ps3.setInt(1, deleteId);

                    int deleted = ps3.executeUpdate();

                    if (deleted > 0)
                        System.out.println("Student Deleted");
                    else
                        System.out.println("Student Not Found");

                    ps3.close();
                    break;

                case 5:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 5);

        con.close();
        sc.close();
    }
}
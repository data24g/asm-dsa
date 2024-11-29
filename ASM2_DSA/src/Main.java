import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class StudentManager {
    private ArrayList<Main.Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void addStudent() {
        while (true) {
            try {
                System.out.print("Enter Student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                String name;
                while (true) {
                    System.out.print("Enter Student Name: ");
                    name = scanner.nextLine();
                    if (name.matches("[a-zA-Z ]+")) {  // Check for letters only
                        break;  // Exit loop if valid
                    } else {
                        System.out.println("Invalid name. Please enter letters only.");
                    }
                }

                double marks = -1; // Initialize to an invalid value
                while (marks < 0 || marks > 10) {
                    System.out.print("Enter Student Marks (0-10): ");
                    marks = scanner.nextDouble();
                    if (marks < 0 || marks > 10) {
                        System.out.println("Invalid input! Marks should be between 0 and 10.");
                    }
                }

                students.add(new Main.Student(id, name, marks));
                System.out.println("Student added successfully.");
                break;  // Exit loop if all input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter numeric values for ID and Marks.");
                scanner.nextLine();  // Consume invalid line
            }
        }
    }

    public void sortStudentsByMarks() {
        // Bubble Sort algorithm
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = 0; j < students.size() - 1 - i; j++) {
                if (students.get(j).marks < students.get(j + 1).marks) {
                    // Swap
                    Main.Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }
        System.out.println("Students sorted by marks:");
        displayStudents();
    }

    public void sortStudentsByName() {
        // Selection Sort algorithm
        for (int i = 0; i < students.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(j).name.compareToIgnoreCase(students.get(minIndex).name) < 0) {
                    minIndex = j;
                }
            }
            // Swap
            if (minIndex != i) {
                Main.Student temp = students.get(i);
                students.set(i, students.get(minIndex));
                students.set(minIndex, temp);
            }
        }
        System.out.println("Students sorted by name:");
        displayStudents();
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            students.forEach(System.out::println);
        }
    }

    public void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (Main.Student student : students) {
            if (student.id == id) {
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                student.name = newName;

                double newMarks = -1;
                while (newMarks < 0 || newMarks > 10) {
                    System.out.print("Enter new marks (0-10): ");
                    newMarks = scanner.nextDouble();
                    if (newMarks < 0 || newMarks > 10) {
                        System.out.println("Invalid input! Marks should be between 0 and 10.");
                    }
                }
                student.marks = newMarks;
                student.rank = student.assignRank(newMarks);  // Update rank
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    public void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        boolean removed = students.removeIf(student -> student.id == id);
        if (removed) {
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    public void findStudent() {
        System.out.print("Enter Student ID to find: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (Main.Student student : students) {
            if (student.id == id) {
                System.out.println("Student found: " + student);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. Sort Students by Marks");
            System.out.println("3. Sort Students by Name");
            System.out.println("4. Display Students");
            System.out.println("5. Update Student");
            System.out.println("6. Delete Student");
            System.out.println("7. Find Student");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            try {
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> manager.addStudent();
                    case 2 -> manager.sortStudentsByMarks();
                    case 3 -> manager.sortStudentsByName();
                    case 4 -> manager.displayStudents();
                    case 5 -> manager.updateStudent();
                    case 6 -> manager.deleteStudent();
                    case 7 -> manager.findStudent();
                    case 8 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice! Please select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.nextLine();  // Consume invalid line
                choice = 0; // Reset choice to stay in the loop
            }
        } while (choice != 8);

        scanner.close();
    }

    public static class Student {
        int id;
        String name;
        double marks;
        String rank;

        public Student(int id, String name, double marks) {
            this.id = id;
            this.name = name;
            this.marks = marks;
            this.rank = assignRank(marks);
        }

        public String assignRank(double marks) {
            if (marks < 5.0) return "Fail";
            if (marks < 6.5) return "Medium";
            if (marks < 7.5) return "Good";
            if (marks < 9.0) return "Very Good";
            return "Excellent";
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Marks: " + marks + ", Rank: " + rank;
        }
    }
}

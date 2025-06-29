import java.util.Scanner;

public class StudentGradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("STUDENT GRADE CALCULATOR");
        System.out.println("-----------------------");
        
        // Input student name
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        
        // Input number of subjects
        System.out.print("Enter number of subjects: ");
        int numSubjects = scanner.nextInt();
        
        // Validate number of subjects
        while (numSubjects <= 0) {
            System.out.println("Number of subjects must be positive. Please try again.");
            System.out.print("Enter number of subjects: ");
            numSubjects = scanner.nextInt();
        }
        
        // Array to store marks
        int[] marks = new int[numSubjects];
        int totalMarks = 0;
        
        // Input marks for each subject
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for Subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextInt();
            
            // Validate marks (0-100)
            while (marks[i] < 0 || marks[i] > 100) {
                System.out.println("Marks must be between 0 and 100. Please try again.");
                System.out.print("Enter marks for Subject " + (i + 1) + " (out of 100): ");
                marks[i] = scanner.nextInt();
            }
            
            totalMarks += marks[i];
        }
        
        // Calculate average
        double average = (double) totalMarks / numSubjects;
        
        // Determine grade
        String grade = calculateGrade(average);
        
        // Display results
        System.out.println("\nRESULTS FOR " + studentName.toUpperCase());
        System.out.println("--------------------------------");
        System.out.println("Total Marks: " + totalMarks + "/" + (numSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", average);
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }
    
    // Method to calculate grade based on average
    private static String calculateGrade(double average) {
        if (average >= 90) {
            return "A+ (Excellent)";
        } else if (average >= 80) {
            return "A (Very Good)";
        } else if (average >= 70) {
            return "B+ (Good)";
        } else if (average >= 60) {
            return "B (Above Average)";
        } else if (average >= 50) {
            return "C (Average)";
        } else if (average >= 40) {
            return "D (Below Average)";
        } else {
            return "F (Fail)";
        }
    }
}
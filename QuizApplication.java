import java.util.*;
import java.io.*;

public class QuizApplication {
    private static List<Question> questions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int score = 0;

    public static void main(String[] args) {
        loadQuestions(); // Load questions from file or initialize default questions
        startQuiz();
        displayResults();
    }

    private static void loadQuestions() {
        // Try to load questions from file first
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("questions.dat"))) {
            questions = (List<Question>) ois.readObject();
            System.out.println("Loaded " + questions.size() + " questions from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No question file found. Loading default questions.");
            initializeDefaultQuestions();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading questions. Loading default questions.");
            initializeDefaultQuestions();
        }
    }

    private static void initializeDefaultQuestions() {
        questions.add(new Question(
            "What is the capital of France?",
            Arrays.asList("London", "Paris", "Berlin", "Madrid"),
            1 // Paris is at index 1
        ));

        questions.add(new Question(
            "Which planet is known as the Red Planet?",
            Arrays.asList("Venus", "Mars", "Jupiter", "Saturn"),
            1 // Mars is at index 1
        ));

        questions.add(new Question(
            "What is 2 + 2?",
            Arrays.asList("3", "4", "5", "6"),
            1 // 4 is at index 1
        ));

        questions.add(new Question(
            "Which language runs in a web browser?",
            Arrays.asList("Java", "C", "Python", "JavaScript"),
            3 // JavaScript is at index 3
        ));

        questions.add(new Question(
            "What does HTML stand for?",
            Arrays.asList(
                "Hyper Text Markup Language",
                "Hyperlinks and Text Markup Language",
                "Home Tool Markup Language",
                "Hyper Transfer Markup Language"
            ),
            0 // First option is correct
        ));
    }

    private static void startQuiz() {
        System.out.println("\nWelcome to the Quiz Application!");
        System.out.println("You will be presented with " + questions.size() + " questions.");
        System.out.println("For each question, enter the number of the correct answer.\n");

        Collections.shuffle(questions); // Shuffle questions for variety

        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            displayQuestion(currentQuestion, i + 1);
            
            int userAnswer = getUserAnswer(currentQuestion.getOptions().size());
            checkAnswer(currentQuestion, userAnswer);
        }
    }

    private static void displayQuestion(Question question, int questionNumber) {
        System.out.println("\nQuestion " + questionNumber + ": " + question.getText());
        List<String> options = question.getOptions();
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    private static int getUserAnswer(int maxOption) {
        while (true) {
            System.out.print("\nYour answer (1-" + maxOption + "): ");
            try {
                int answer = scanner.nextInt();
                if (answer >= 1 && answer <= maxOption) {
                    return answer - 1; // Convert to 0-based index
                } else {
                    System.out.println("Please enter a number between 1 and " + maxOption);
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static void checkAnswer(Question question, int userAnswer) {
        if (userAnswer == question.getCorrectAnswerIndex()) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect! The correct answer was: " + 
                (question.getCorrectAnswerIndex() + 1) + ". " + 
                question.getOptions().get(question.getCorrectAnswerIndex()));
        }
    }

    private static void displayResults() {
        System.out.println("\nQuiz Completed!");
        System.out.println("Your score: " + score + " out of " + questions.size());
        double percentage = (double) score / questions.size() * 100;
        System.out.printf("Percentage: %.1f%%\n", percentage);
        
        if (percentage >= 80) {
            System.out.println("Excellent work!");
        } else if (percentage >= 60) {
            System.out.println("Good job!");
        } else if (percentage >= 40) {
            System.out.println("Not bad, but you can do better!");
        } else {
            System.out.println("Keep practicing!");
        }
    }
}

class Question implements Serializable {
    private String text;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String text, List<String> options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters
    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
import java.util.Scanner;

public class TemperatureConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("TEMPERATURE CONVERTER");
        System.out.println("---------------------");
        
        while (true) {
            // Display menu
            System.out.println("\nChoose conversion type:");
            System.out.println("1. Celsius to Fahrenheit");
            System.out.println("2. Fahrenheit to Celsius");
            System.out.println("3. Celsius to Kelvin");
            System.out.println("4. Kelvin to Celsius");
            System.out.println("5. Fahrenheit to Kelvin");
            System.out.println("6. Kelvin to Fahrenheit");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");
            
            int choice = scanner.nextInt();
            
            if (choice == 7) {
                System.out.println("Exiting program...");
                break;
            }
            
            if (choice < 1 || choice > 6) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
            
            System.out.print("Enter temperature to convert: ");
            double temperature = scanner.nextDouble();
            double convertedTemp = 0;
            String fromUnit = "";
            String toUnit = "";
            
            // Perform conversion based on user choice
            switch (choice) {
                case 1:
                    convertedTemp = celsiusToFahrenheit(temperature);
                    fromUnit = "Celsius";
                    toUnit = "Fahrenheit";
                    break;
                case 2:
                    convertedTemp = fahrenheitToCelsius(temperature);
                    fromUnit = "Fahrenheit";
                    toUnit = "Celsius";
                    break;
                case 3:
                    convertedTemp = celsiusToKelvin(temperature);
                    fromUnit = "Celsius";
                    toUnit = "Kelvin";
                    break;
                case 4:
                    convertedTemp = kelvinToCelsius(temperature);
                    fromUnit = "Kelvin";
                    toUnit = "Celsius";
                    break;
                case 5:
                    convertedTemp = fahrenheitToKelvin(temperature);
                    fromUnit = "Fahrenheit";
                    toUnit = "Kelvin";
                    break;
                case 6:
                    convertedTemp = kelvinToFahrenheit(temperature);
                    fromUnit = "Kelvin";
                    toUnit = "Fahrenheit";
                    break;
            }
            
            // Display result
            System.out.printf("\n%.2f° %s = %.2f° %s\n\n", temperature, fromUnit, convertedTemp, toUnit);
        }
        
        scanner.close();
    }
    
    // Conversion methods
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
    
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }
    
    public static double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }
    
    public static double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }
    
    public static double fahrenheitToKelvin(double fahrenheit) {
        return celsiusToKelvin(fahrenheitToCelsius(fahrenheit));
    }
    
    public static double kelvinToFahrenheit(double kelvin) {
        return celsiusToFahrenheit(kelvinToCelsius(kelvin));
    }
}
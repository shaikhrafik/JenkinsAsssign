package assignment6;

import java.util.Scanner;

public class CalculatorMain {
    public static void main(String[] args) {
        BasicCalculator calculator = new BasicCalculator();
        Scanner sc = new Scanner(System.in);

        while (true) {
            
            System.out.println("\n--- Calculator Menu ---");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = sc.nextInt();

            
            if (choice == 5) {
                System.out.println("Exit");
                break;
            }

            // Check for valid choice
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            try {
               
                System.out.print("Enter the first number: ");
                double num1 = sc.nextDouble();

                
                System.out.print("Enter the second number: ");
                double num2 = sc.nextDouble();

                
                double result = 0;
                switch (choice) {
                    case 1: 
                        result = calculator.add(num1, num2);
                        System.out.printf("Result: "+ num1+" " +"+"+" " +num2+"="+ result);
                        break;
                    case 2: 
                        result = calculator.subtract(num1, num2);
                        System.out.printf("Result: "+ num1+" " +"-"+" " +num2+"="+ result);
                        break;
                    case 3: 
                        result = calculator.multiply(num1, num2);
                        System.out.printf("Result: "+ num1+" " +"*"+" " +num2+"="+ result);
                        break;
                    case 4: 
                        result = calculator.divide(num1, num2);
                        System.out.printf("Result: "+ num1+" " +"/"+" " +num2+"="+ result);
                        break;
                }
            } 
            catch (ArithmeticException e) {
                System.out.println("Divide By Zero Exception: " + e.getMessage());
                System.exit(1);
            } 
            catch (Exception e) {
            	System.exit(1);
                System.out.println("Exception Occurred: " + e.getMessage());
            }
        }

       
        sc.close();
    }
}
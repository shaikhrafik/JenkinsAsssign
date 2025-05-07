package assignment6;

public class BasicCalculator {
   
    public double add(double num1, double num2) {
        return num1 + num2;
    }

    
    public double subtract(double num1, double num2) {
        return num1 - num2;
    }

   
    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    
    public double divide(double num1, double num2) throws ArithmeticException {
        if (num2 == 0) {
            throw new ArithmeticException("Division by zero is not allowed!");
        }
        return num1 / num2;
    }
}

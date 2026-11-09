public class Employee {
    String name;
    double startingSalary;
    double payRise;

    public Employee(String name, double startingSalary, double payRise){
        this.name = name;
        this.startingSalary = startingSalary;
        this.payRise = payRise;
    }

    double finalSalary() {
        return startingSalary + payRise;
    }

    void showData() {
        System.out.println("Name: " + name);
        System.out.println("Starting salary: " + startingSalary);
        System.out.println("Pay Rise: " + payRise);
        System.out.println("Final salary: " + finalSalary());
    }
}



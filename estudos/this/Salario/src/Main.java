//public class Main {
//    public static void main(String[] args) {
//        Employee empl1 = new Employee("Alexandre", 1500, 800);
//        empl1.showData();
//    }
//}

public class Main {
    void main(String[] args) {
    Employee empl1 = new Employee("Ana Paula", 2900, 2900);
    Employee empl2 = new Employee("Pedro Lucas", 2500, 1600);
    Employee empl3 = new Employee("Alexandre", 7000, 45000);

    Employee[] employees = {empl1, empl2, empl3}; //arrays

    String highestPaid = employees[0].name;
    double highestSalary = employees[0].finalSalary();
    int over5000 = 0;

    for (int i = 0; i < employees.length; i++) {
            employees[i].showData();

            if (employees[i].finalSalary() > highestSalary) {
                highestPaid = employees[i].name;
                highestSalary = employees[i].finalSalary();
            }

            if (employees[i].finalSalary() > 5000) {
                over5000++;
            }
            System.out.println();
        }
        System.out.printf("The highest-paid employee is: %s with $%.2f\n", highestPaid, highestSalary);
        System.out.printf("Number of employees earning more than $2000: %d", over5000);
    }
}

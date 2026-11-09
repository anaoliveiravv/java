import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Locale.setDefault(Locale.US);
        List<Student> students = new ArrayList<>();

        while (true) {

            System.out.print("Full Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Grade 1: ");
            double g1 = sc.nextDouble();

            System.out.print("Grade 2: ");
            double g2 = sc.nextDouble();

            sc.nextLine();

            System.out.print("Course: ");
            String course = sc.nextLine();

            Student student = new Student(name, age, g1, g2, course);
            students.add(student);

            char continuar;
            while (true) {
                System.out.print("Continue [Y/N]: ");
                continuar = sc.next().toLowerCase().charAt(0);
                sc.nextLine();

                if (continuar == 'y' || continuar == 'n') {
                    break;
                }

                System.out.println("Type 'y' or 'n'");
            }
            if (continuar == 'n') {
                break;
            }
        }

        System.out.println("==ALUNOS==");
        for (int i=0; i < students.size(); i++) {
            System.out.println((i+1) + "-" + students.get(i).getName());
        }
        System.out.print("Select a student: ");
        int stuSelected = sc.nextInt() -1;
        System.out.println();

        while (true) {
            System.out.println("==MENU==\n1- See average\n2- See situation\n3- Show round Average\n4- End program");
            System.out.println();
            System.out.print("Select an option: ");

            if (!sc.hasNextInt()) { //Serve pra perguntar ao Scanner: “o próximo valor que o usuário digitou é um número inteiro?”
                System.out.println("Invalid, enter a number.\n");
                sc.next(); // descarta o que foi digitado
                continue;
            }
            int option = sc.nextInt();

            if (option < 1 || option > 4) {
                System.out.println("Invalid, enter a number between the options\n");
                continue;
            }

            switch (option) {
                    case 1:
                        System.out.printf("Average: %.1f%n", students.get(stuSelected).calculateAverage()); //para definir quantas casas vem após a vírgula + quebra de linha (%n)
                        break;
                    case 2:
                        System.out.println("Situation: " + students.get(stuSelected).seeSituation());
                        break;
                    case 3:
                        System.out.println("Round Average: " + students.get(stuSelected).roundAverage());
                        break;
                    default:
                        sc.close();
                        return; //so sai do metodo (main), sem retornar nada
                }

            while (true) {
                System.out.print("Continue [Y/N]?: \n");
                char answer = sc.next().toLowerCase().charAt(0);
                if (answer == 'n') {
                    sc.close();
                    return;
                } else if (answer != 'y') {
                    System.out.println("Type 'y' or 'n' \n");
                    continue;
                }
                break;
            }

        }
    }
}
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Calculadora calc = new Calculadora();
        System.out.print("N1: ");
        calc.n1 = scanner.nextDouble();

        System.out.print("N2: ");
        calc.n2 = scanner.nextDouble();

        System.out.println("--OPERAÇÕES--\n1- Adição\n 2- Subtração\n 3- Multiplicação\n 4- Divisão");
        System.out.print("Escolha uma operação: ");
        int escolha = scanner.nextInt();
//        switch (escolha) {
//            case 1:
//                System.out.println(calc.somar());
//                break;
//            case 2:
//                System.out.println(calc.subtrair());
//                break;
//            case 3:
//                System.out.println(calc.multiplicar());
//                break;
//            case 4:
//                System.out.println(calc.dividir());
//                break;
//            default:
//                System.out.println("Opção Inválida");
//        }
//    }
//}

        if (escolha == 1) {
            System.out.println(calc.somar());
        } else if (escolha == 2) {
            System.out.println(calc.subtrair());
        } else if (escolha == 3) {
            System.out.println(calc.multiplicar());
        } else if (escolha == 4) {
            System.out.println(calc.dividir());
        } else {
            System.out.println("Opção inválida");
        }
    }
}


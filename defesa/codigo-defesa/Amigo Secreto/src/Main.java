
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Sorteio sorteio = new Sorteio();

        int opcao;

        do {
            System.out.println("\n=== AMIGO SECRETO ===");
            System.out.println("1 - Cadastrar participante");
            System.out.println("2 - Listar participantes");
            System.out.println("3 - Realizar sorteio");
            System.out.println("4 - Descobrir meu amigo secreto");
            System.out.println("0 - Sair");

            while (true) {
                System.out.print("Escolha: ");

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();

                    if (opcao >= 0 && opcao <= 4) {
                        break;
                    }
                } else {
                    scanner.nextLine();
                }
                System.out.println("Escolha um número válido do menu");
            }

            switch (opcao) {

                case 1:
                    while (true) {
                        String nome;
                        while (true) {
                            System.out.print("Nome: ");
                            nome = scanner.nextLine();

                            boolean valido = true;

                            if (nome.length() == 0) {
                                valido = false;
                            }

                            for (int i = 0; i < nome.length(); i++) {
                                char letra = nome.charAt(i);

                                if (!Character.isLetter(letra) && letra != ' ') {
                                    valido = false;
                                    break;
                                }
                            }

                            if (valido && !nome.trim().isEmpty()) {
                                break;
                            }

                            System.out.println("Nome inválido! Digite apenas letras.");
                        }

                        Pessoa pessoa = new Pessoa(nome);

                        System.out.println("Cadastre até 3 presentes:");


                        for (int i = 1; i <= 3; i++) {

                            while (true) {
                                System.out.print("Presente " + i + ": ");
                                String presente = scanner.nextLine();

                                boolean valido = true;

                                if (presente.length() == 0) {
                                    valido = false;
                                }

                                for (int j = 0; j < presente.length(); j++) {
                                    char letra = presente.charAt(j);

                                    if (!Character.isLetter(letra) && letra != ' ') {
                                        valido = false;
                                        break;
                                    }
                                }

                                if (valido && !presente.trim().isEmpty()) {
                                    pessoa.adicionarPresente(presente);
                                    break;
                                }

                                System.out.println("Presente inválido! Digite apenas letras.");
                            }
                        }


                        sorteio.cadastrar(pessoa);


                        char continuar;

                        while (true) {
                            System.out.print("Cadastrar mais pessoas? [s/n]: ");
                            continuar = scanner.nextLine().charAt(0);

                            if (continuar == 's' || continuar == 'n') {
                                break;
                            } else {
                                System.out.println("Digite apenas s ou n!");
                            }
                        }

                        if (continuar == 'n') {
                            break;
                        }

                    }
                    break;

                case 2:
                    sorteio.listar();
                    break;

                case 3:
                    sorteio.realizarSorteio();
                    break;

                case 4:
                    System.out.print("Digite seu nome: ");
                    String consulta = scanner.nextLine();

                    sorteio.descobrir(consulta);
                    break;

                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main extends JFrame {
    private static final Color BORDO = new Color(0x6C151E);
    private static final Color VERDE = new Color(0x0F3D3A);
    private static final Color CREME = new Color(0xF5DABF);
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FunctionalInterface private interface Acao { void executar() throws Exception; }

    private final Sorteio sorteio;
    private final DefaultListModel<Participante> participantes = new DefaultListModel<>();
    private final JList<Participante> lista = new JList<>(participantes);
    private final JTextField nome = new JTextField(), email = new JTextField();
    private final JLabel contador = new JLabel(), detalhes = new JLabel();
    private final JButton sortear = botao("Realizar sorteio", BORDO, CREME);

    public Main(Sorteio sorteio) {
        super("Amigo secreto");
        this.sorteio = sorteio;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(960, 760);
        setMinimumSize(new Dimension(760, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(CREME);
        setLayout(new GridBagLayout());

        JPanel tela = new JPanel();
        tela.setOpaque(false);
        tela.setPreferredSize(new Dimension(440, 640));
        tela.setBorder(new EmptyBorder(28, 30, 26, 30));
        tela.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Amigo secreto ✦");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 29));
        titulo.setForeground(BORDO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitulo = new JLabel("Organize sua troca de presentes");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitulo.setForeground(VERDE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        tela.add(titulo);
        tela.add(Box.createVerticalStrut(5));
        tela.add(subtitulo);
        tela.add(Box.createVerticalStrut(22));

        campoLabel(tela, "Nome");
        estilizarCampo(nome, "Nome do participante");
        tela.add(nome);
        tela.add(Box.createVerticalStrut(10));
        campoLabel(tela, "E-mail");
        estilizarCampo(email, "email@exemplo.com");
        tela.add(email);
        tela.add(Box.createVerticalStrut(12));

        JButton adicionar = botao("Adicionar participante", VERDE, CREME);
        adicionar.setAlignmentX(Component.CENTER_ALIGNMENT);
        adicionar.addActionListener(e -> executar(() -> {
            sorteio.adicionarParticipante(new Participante(nome.getText(), email.getText()));
            sorteio.salvar();
            nome.setText(""); email.setText(""); atualizarLista();
        }));
        tela.add(adicionar);
        tela.add(Box.createVerticalStrut(16));

        JPanel linhaLista = new JPanel(new BorderLayout());
        linhaLista.setOpaque(false);
        contador.setFont(new Font("SansSerif", Font.BOLD, 13));
        contador.setForeground(BORDO);
        linhaLista.add(contador, BorderLayout.WEST);
        tela.add(linhaLista);
        tela.add(Box.createVerticalStrut(6));

        lista.setVisibleRowCount(5);
        lista.setFixedCellHeight(34);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lista.setBackground(new Color(0xFFF7EF));
        lista.setSelectionBackground(new Color(0xE9C8AE));
        lista.setCellRenderer((list, participante, index, selecionado, foco) -> {
            JLabel item = new JLabel("  " + participante.getNome() + "   ·   " + participante.getEmail());
            item.setOpaque(true);
            item.setFont(new Font("SansSerif", Font.PLAIN, 12));
            item.setForeground(VERDE);
            item.setBackground(selecionado ? new Color(0xE9C8AE) : new Color(0xFFF7EF));
            item.setBorder(new EmptyBorder(4, 4, 4, 4));
            return item;
        });
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xE6D0BC)));
        scroll.setPreferredSize(new Dimension(380, 165));
        scroll.setMaximumSize(new Dimension(380, 165));
        tela.add(scroll);
        tela.add(Box.createVerticalStrut(8));

        JButton desejos = botao("Editar desejos da pessoa selecionada", Color.WHITE, VERDE);
        desejos.setAlignmentX(Component.CENTER_ALIGNMENT);
        desejos.addActionListener(e -> editarDesejos());
        tela.add(desejos);
        tela.add(Box.createVerticalStrut(10));

        detalhes.setForeground(VERDE);
        detalhes.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detalhes.setAlignmentX(Component.CENTER_ALIGNMENT);
        tela.add(detalhes);
        tela.add(Box.createVerticalStrut(8));

        JButton configurar = botao("Configurar valor e data", Color.WHITE, BORDO);
        configurar.setAlignmentX(Component.CENTER_ALIGNMENT);
        configurar.addActionListener(e -> configurarTroca());
        tela.add(configurar);
        tela.add(Box.createVerticalStrut(9));

        sortear.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortear.addActionListener(e -> executar(() -> {
            sorteio.realizarSorteio(); sorteio.salvar(); atualizarLista();
            JOptionPane.showMessageDialog(this, "Sorteio realizado! Cada pessoa consulta pelo próprio e-mail.");
        }));
        tela.add(sortear);
        tela.add(Box.createVerticalStrut(9));

        JButton consultar = botao("Consultar meu resultado", BORDO, CREME);
        consultar.setAlignmentX(Component.CENTER_ALIGNMENT);
        consultar.addActionListener(e -> consultarResultado());
        tela.add(consultar);

        add(new Cartao(tela));
        atualizarLista();
    }

    private void campoLabel(JPanel tela, String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(VERDE);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        tela.add(label);
        tela.add(Box.createVerticalStrut(4));
    }

    private void estilizarCampo(JTextField campo, String dica) {
        campo.setToolTipText(dica);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD8BCA3)), new EmptyBorder(8, 10, 8, 10)));
    }

    private void atualizarLista() {
        participantes.clear();
        for (Participante participante : sorteio.getParticipantes()) participantes.addElement(participante);
        contador.setText("Participantes  ·  " + participantes.size());
        detalhes.setText("Presente: " + dinheiro(sorteio.getValorMinimo()) + " a " + dinheiro(sorteio.getValorMaximo())
                + "     |     Troca: " + (sorteio.getDataTroca() == null ? "a definir" : sorteio.getDataTroca().format(DATA)));
        sortear.setEnabled(!sorteio.foiRealizado());
        sortear.setText(sorteio.foiRealizado() ? "Sorteio realizado" : "Realizar sorteio");
    }

    private void configurarTroca() {
        JTextField min = new JTextField(sorteio.getValorMinimo().toPlainString().replace('.', ','));
        JTextField max = new JTextField(sorteio.getValorMaximo().toPlainString().replace('.', ','));
        JTextField data = new JTextField(sorteio.getDataTroca() == null ? "" : sorteio.getDataTroca().format(DATA));
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.add(new JLabel("Valor mínimo (R$)")); form.add(min);
        form.add(new JLabel("Valor máximo (R$)")); form.add(max);
        form.add(new JLabel("Data da troca")); form.add(data);
        if (JOptionPane.showConfirmDialog(this, form, "Detalhes da troca", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
            executar(() -> {
                sorteio.configurar(parseDinheiro(min.getText()), parseDinheiro(max.getText()),
                        data.getText().isBlank() ? null : LocalDate.parse(data.getText().trim(), DATA));
                sorteio.salvar(); atualizarLista();
            });
    }

    private void editarDesejos() {
        Participante participante = lista.getSelectedValue();
        if (participante == null) { avisar("Selecione uma pessoa na lista."); return; }
        String[] atuais = participante.getDesejos();
        JTextField[] campos = {new JTextField(atuais[0]), new JTextField(atuais[1]), new JTextField(atuais[2])};
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        for (int i = 0; i < 3; i++) { form.add(new JLabel("Sugestão " + (i + 1))); form.add(campos[i]); }
        if (JOptionPane.showConfirmDialog(this, form, "Desejos de " + participante.getNome(), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
            executar(() -> { sorteio.atualizarDesejos(participante.getEmail(), new String[]{campos[0].getText(), campos[1].getText(), campos[2].getText()}); sorteio.salvar(); });
    }

    private void consultarResultado() {
        String emailConsulta = JOptionPane.showInputDialog(this, "Digite seu e-mail para ver seu resultado:", "Consultar resultado", JOptionPane.QUESTION_MESSAGE);
        if (emailConsulta == null || emailConsulta.isBlank()) return;
        Participante amigo = sorteio.consultarResultado(emailConsulta);
        if (amigo == null) { avisar(sorteio.foiRealizado() ? "Resultado não encontrado." : "Realize o sorteio primeiro."); return; }
        StringBuilder texto = new StringBuilder("Você tirou: ").append(amigo.getNome()).append("\n\nSugestões:");
        boolean temDesejos = false;
        for (String desejo : amigo.getDesejos()) if (!desejo.isBlank()) { texto.append("\n• ").append(desejo); temDesejos = true; }
        if (!temDesejos) texto.append("\nAinda não informou sugestões.");
        String chave = emailConsulta.trim().toLowerCase(Locale.ROOT);
        JCheckBox comprado = new JCheckBox("Já comprei o presente", sorteio.presenteComprado(chave));
        JPanel resultado = new JPanel(new BorderLayout(0, 12));
        resultado.add(new JLabel("<html>" + texto.toString().replace("\n", "<br>") + "</html>"), BorderLayout.CENTER);
        resultado.add(comprado, BorderLayout.SOUTH);
        JOptionPane.showMessageDialog(this, resultado, "Seu amigo secreto", JOptionPane.PLAIN_MESSAGE);
        executar(() -> { sorteio.confirmarPresente(chave, comprado.isSelected()); sorteio.salvar(); });
    }

    private BigDecimal parseDinheiro(String valor) {
        String limpo = valor.trim().replace("R$", "").replace(" ", "");
        return new BigDecimal(limpo.contains(",") ? limpo.replace(".", "").replace(',', '.') : limpo);
    }

    private String dinheiro(BigDecimal valor) { return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(valor); }
    private void executar(Acao acao) { try { acao.executar(); } catch (Exception ex) { avisar(ex.getMessage() == null ? "Não foi possível concluir a ação." : ex.getMessage()); } }
    private void avisar(String mensagem) { JOptionPane.showMessageDialog(this, mensagem, "Amigo secreto", JOptionPane.INFORMATION_MESSAGE); }

    private JButton botao(String texto, Color fundo, Color frente) {
        JButton botao = new JButton(texto);
        botao.setBackground(fundo); botao.setForeground(frente); botao.setFocusPainted(false);
        botao.setFont(new Font("SansSerif", Font.BOLD, 13)); botao.setBorder(new EmptyBorder(9, 12, 9, 12));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return botao;
    }

    private static final class Cartao extends JPanel {
        Cartao(JPanel conteudo) { setOpaque(false); setLayout(new BorderLayout()); add(conteudo); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 5; i > 0; i--) { g2.setColor(new Color(70, 30, 20, 5)); g2.fillRoundRect(2, i + 2, getWidth() - 4, getHeight() - 7, 28, 28); }
            g2.setColor(new Color(0xFFF9F2)); g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 8, 28, 28);
            g2.dispose(); super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { new Main(Sorteio.carregar()).setVisible(true); }
            catch (IOException | RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível carregar os dados: " + e.getMessage());
                new Main(new Sorteio()).setVisible(true);
            }
        });
    }
}

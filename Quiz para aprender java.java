import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class JavaQuizGUI {
    private JFrame frame;
    private JLabel questionLabel, feedbackLabel, scoreLabel, errorLabel, hintsLabel;
    private JButton[] answerButtons;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private int errors = 0;
    private final int maxQuestions = 60;
    private final ArrayList<String[]> questions = new ArrayList<>();
    private final ArrayList<String> explanations = new ArrayList<>();
    private final ArrayList<String[]> hints = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaQuizGUI::new);
    }

    public JavaQuizGUI() {
        createQuestions();
        Collections.shuffle(questions);
        frame = new JFrame("Quiz de Java - Estudos e Prática");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setLayout(new BorderLayout());

        questionLabel = createLabel("", Color.WHITE, 18, SwingConstants.CENTER);
        feedbackLabel = createLabel("", Color.YELLOW, 16, SwingConstants.CENTER);
        scoreLabel = createLabel("Acertos: 0", Color.GREEN, 16, SwingConstants.LEFT);
        errorLabel = createLabel("Erros: 0", Color.RED, 16, SwingConstants.LEFT);
        hintsLabel = createLabel("Dicas: ", Color.CYAN, 14, SwingConstants.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.DARK_GRAY);
        panel.add(questionLabel, BorderLayout.NORTH);
        panel.add(hintsLabel, BorderLayout.CENTER);
        panel.add(feedbackLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.setLayout(new GridLayout(2, 2));
        answerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = createAnswerButton();
            buttonPanel.add(answerButtons[i]);
        }
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        scorePanel.setLayout(new GridLayout(1, 2));
        scorePanel.add(scoreLabel);
        scorePanel.add(errorLabel);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(scorePanel, BorderLayout.SOUTH);

        loadQuestion();
        frame.setVisible(true);
    }

    private JLabel createLabel(String text, Color color, int fontSize, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(color);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        return label;
    }

    private JButton createAnswerButton() {
        JButton button = new JButton();
        button.setForeground(Color.WHITE);
        button.setBackground(Color.GRAY);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.addActionListener(new AnswerButtonListener());
        return button;
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            String[] question = questions.get(currentQuestionIndex);
            questionLabel.setText("Pergunta " + (currentQuestionIndex + 1) + ": " + question[0]);
            for (int i = 0; i < 4; i++) {
                answerButtons[i].setText(question[i + 2]);
            }
            hintsLabel.setText("<html>Dicas:<br>1. " + hints.get(currentQuestionIndex)[0] +
                    "<br>2. " + hints.get(currentQuestionIndex)[1] +
                    "<br>3. " + hints.get(currentQuestionIndex)[2] + "</html>");
            feedbackLabel.setText("");
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(String answer) {
        String[] question = questions.get(currentQuestionIndex);
        String correctAnswer = question[1];
        if (answer.equals(correctAnswer)) {
            feedbackLabel.setText("Parabéns! Você acertou!");
            score++;
            scoreLabel.setText("Acertos: " + score);
        } else {
            feedbackLabel.setText("Você errou! A resposta correta é: " + correctAnswer + ".");
            errors++;
            errorLabel.setText("Erros: " + errors);
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < maxQuestions) {
            loadQuestion();
        } else {
            endQuiz();
        }
    }

    private void endQuiz() {
        String message = "Fim do quiz! Sua pontuação final: " + score + " acertos e " + errors + " erros.";
        int option = JOptionPane.showConfirmDialog(frame, message + "\nDeseja tentar novamente?", "Resultado do Quiz", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            currentQuestionIndex = 0;
            score = 0;
            errors = 0;
            scoreLabel.setText("Acertos: 0");
            errorLabel.setText("Erros: 0");
            Collections.shuffle(questions);
            loadQuestion();
        } else {
            System.exit(0);
        }
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            checkAnswer(clickedButton.getText());
        }
    }

    private void createQuestions() {
        // Questões e dicas de Java
        questions.add(new String[]{"Qual palavra-chave define uma constante em Java?", "C", "A. var", "B. static", "C. final", "D. const"});
        explanations.add("A palavra-chave 'final' define uma constante.");
        hints.add(new String[]{"É usada para evitar alterações.", "A variável não pode ser modificada.", "É uma palavra de 5 letras."});
        questions.add(new String[]{"Qual método é usado para iniciar a execução de um programa Java?", "B", "A. start()", "B. main()", "C. run()", "D. execute()"});
        explanations.add("O método main() é o ponto de entrada de execução.");
        hints.add(new String[]{"É um método obrigatório em Java.", "Define o ponto de início.", "É chamado automaticamente pela JVM."});
        questions.add(new String[]{"Qual tipo de dado é usado para números inteiros?", "A", "A. int", "B. float", "C. boolean", "D. char"});
        explanations.add("O tipo 'int' armazena números inteiros.");
        hints.add(new String[]{"Usado para armazenar números sem decimais.", "É um tipo de dado básico.", "Começa com a letra 'i'."});
        questions.add(new String[]{"Como definimos um comentário de linha única?", "D", "A. /**/", "B. #", "C. --", "D. //"});
        explanations.add("Os comentários de linha única são iniciados com '//'.");
        hints.add(new String[]{"Está no início da linha.", "Possui dois caracteres iguais.", "É usado antes do comentário."});
        questions.add(new String[]{"Qual operador é usado para comparar igualdade entre valores?", "B", "A. =", "B. ==", "C. !=", "D. <>."});
        explanations.add("O operador == compara a igualdade entre valores.");
        hints.add(new String[]{"Usado para verificar se os valores são iguais.", "Possui dois caracteres iguais.", "É uma comparação lógica."});
        questions.add(new String[]{"Qual operador é usado para atribuição em Java?", "A", "A. =", "B. == ", "C. !=", "D. =>"});
        hints.add(new String[]{"Usado para definir valores.", "É um operador de atribuição.", "Apenas um caractere."});

        questions.add(new String[]{"Qual a extensão de um arquivo Java?", "C", "A. .jav", "B. .javascript", "C. .java", "D. .j"});
        hints.add(new String[]{"Tem quatro letras.", "É padrão para Java.", "Começa com 'j'."});

        questions.add(new String[]{"Qual estrutura de dados permite duplicatas?", "D", "A. Set", "B. Map", "C. Tuple", "D. List"});
        hints.add(new String[]{"Pode conter elementos repetidos.", "É uma coleção ordenada.", "Tem quatro letras."});

        questions.add(new String[]{"Qual método converte uma string para um inteiro?", "B", "A. Integer.valueOf()", "B. Integer.parseInt()", "C. String.toInt()", "D. String.parse()"});
        hints.add(new String[]{"É um método da classe Integer.", "Aceita uma string como argumento.", "Retorna um valor inteiro."});

        questions.add(new String[]{"Qual o valor padrão de uma variável booleana?", "A", "A. false", "B. true", "C. null", "D. 0"});
        hints.add(new String[]{"É um valor lógico.", "Pode ser true ou false.", "Começa com 'f'."});

// Questões e dicas adicionais
        questions.add(new String[]{"Qual operador é usado para atribuição em Java?", "A", "A. =", "B. == ", "C. !=", "D. =>"});
        explanations.add("O operador '=' é usado para atribuição.");
        hints.add(new String[]{"Usado para definir valores.", "É um operador de atribuição.", "Apenas um caractere."});

        questions.add(new String[]{"Qual a extensão de um arquivo Java?", "C", "A. .jav", "B. .javascript", "C. .java", "D. .j"});
        explanations.add("Arquivos Java têm a extensão '.java'.");
        hints.add(new String[]{"Tem quatro letras.", "É padrão para Java.", "Começa com 'j'."});

        questions.add(new String[]{"Qual estrutura de dados permite duplicatas?", "D", "A. Set", "B. Map", "C. Tuple", "D. List"});
        explanations.add("List permite duplicatas.");
        hints.add(new String[]{"Pode conter elementos repetidos.", "É uma coleção ordenada.", "Tem quatro letras."});

        questions.add(new String[]{"Qual método converte uma string para um inteiro?", "B", "A. Integer.valueOf()", "B. Integer.parseInt()", "C. String.toInt()", "D. String.parse()"});
        explanations.add("Integer.parseInt() converte uma string para int.");
        hints.add(new String[]{"É um método da classe Integer.", "Aceita uma string como argumento.", "Retorna um valor inteiro."});

        questions.add(new String[]{"Qual o valor padrão de uma variável booleana?", "A", "A. false", "B. true", "C. null", "D. 0"});
        explanations.add("O valor padrão de boolean é false.");
        hints.add(new String[]{"É um valor lógico.", "Pode ser true ou false.", "Começa com 'f'."});
        
            // Questões e dicas de Java
            questions.add(new String[]{"Qual palavra-chave define uma constante em Java?", "C", "A. var", "B. static", "C. final", "D. const"});
            questions.add(new String[]{"Quem é o criador da linguagem Java?", "B", "A. James Gosling", "B. Bill Gates", "C. Linus Torvalds", "D. Guido van Rossum"});
            questions.add(new String[]{"Qual método é usado para iniciar a execução de um programa Java?", "B", "A. start()", "B. main()", "C. run()", "D. execute()"});
            questions.add(new String[]{"Qual tipo de dado é usado para números inteiros?", "A", "A. int", "B. float", "C. boolean", "D. char"});
            questions.add(new String[]{"Como definimos um comentário de linha única?", "D", "A. /**/", "B. #", "C. --", "D. //"});
            questions.add(new String[]{"Qual operador é usado para comparar igualdade entre valores?", "B", "A. =", "B. ==", "C. !=", "D. <>."});
            questions.add(new String[]{"Qual operador é usado para atribuição em Java?", "A", "A. =", "B. == ", "C. !=", "D. =>"});
            questions.add(new String[]{"Qual a extensão de um arquivo Java?", "C", "A. .jav", "B. .javascript", "C. .java", "D. .j"});
            questions.add(new String[]{"Qual estrutura de dados permite duplicatas?", "D", "A. Set", "B. Map", "C. Tuple", "D. List"});
            questions.add(new String[]{"Qual método converte uma string para um inteiro?", "B", "A. Integer.valueOf()", "B. Integer.parseInt()", "C. String.toInt()", "D. String.parse()"});
            questions.add(new String[]{"Qual o valor padrão de uma variável booleana?", "A", "A. false", "B. true", "C. null", "D. 0"});
            questions.add(new String[]{"Qual a palavra-chave para criar uma classe em Java?", "C", "A. def", "B. function", "C. class", "D. new"});
            questions.add(new String[]{"Qual estrutura de controle é usada para repetir um bloco de código?", "A", "A. for", "B. if", "C. switch", "D. try"});
            questions.add(new String[]{"Qual é a saída de System.out.println(5 + 5)?", "B", "A. 55", "B. 10", "C. 5", "D. 0"});
            questions.add(new String[]{"Como se declara uma variável em Java?", "C", "A. var x;", "B. x = 5;", "C. int x;", "D. x: int;"});
            questions.add(new String[]{"Qual é a principal função do método 'toString'?", "D", "A. Converter para inteiro", "B. Criar um objeto", "C. Comparar objetos", "D. Retornar uma representação em string"});
            questions.add(new String[]{"Qual é o operador de incremento em Java?", "C", "A. ++", "B. +1", "C. += 1", "D. +"});
            questions.add(new String[]{"Qual é a palavra-chave usada para herança?", "B", "A. implements", "B. extends", "C. inherits", "D. derived"});
            questions.add(new String[]{"Qual é a estrutura de dados que não permite duplicatas?", "A", "A. Set", "B. List", "C. Array", "D. Map"});
            questions.add(new String[]{"Qual é o valor padrão de uma variável do tipo int?", "C", "A. 1", "B. null", "C. 0", "D. -1"});
            questions.add(new String[]{"Qual é o método para adicionar um elemento a uma lista?", "B", "A. insert()", "B. add()", "C. push()", "D. append()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para encapsulamento?", "D", "A. public", "B. private", "C. protected", "D. all of the above"});
            questions.add(new String[]{"Qual é a estrutura de controle que permite múltiplas condições?", "C", "A. if", "B. while", "C. switch", "D. for"});
            questions.add(new String[]{"Qual é o método para remover um elemento de uma lista?", "A", "A. remove()", "B. delete()", "C. discard()", "D. clear()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar um objeto?", "C", "A. new", "B. create", "C. instantiate", "D. object"});
            questions.add(new String[]{"Qual é o operador usado para negação lógica?", "D", "A. !", "B. ~", "C. &", "D. not"});
            questions.add(new String[]{"Qual é a estrutura de dados que armazena pares chave-valor?", "B", "A. List", "B. Map", "C. Set", "D. Array"});
            questions.add(new String[]{"Qual é a palavra-chave usada para finalizar um loop?", "A", "A. break", "B. stop", "C. exit", "D. end"});
            questions.add(new String[]{"Qual é o método usado para comparar duas strings?", "B", "A. equals()", "B. compareTo()", "C. match()", "D. compare()"});
            questions.add(new String[]{"Como se define uma classe abstrata?", "C", "A. abstract class MyClass", "B. class MyClass abstract", "C. abstract MyClass", "D. MyClass abstract class"});
            questions.add(new String[]{"Qual é o método usado para fazer uma pausa na execução?", "D", "A. sleep()", "B. wait()", "C. pause()", "D. Thread.sleep()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para tratar exceções?", "B", "A. try", "B. catch", "C. throw", "D. finally"});
            questions.add(new String[]{"Qual é a estrutura que permite executar um bloco de código se uma condição for falsa?", "A", "A. else", "B. if", "C. switch", "D. for"});
            questions.add(new String[]{"Qual é o método usado para converter um inteiro em string?", "C", "A. String.valueOf()", "B. Integer.toString()", "C. String.format()", "D. String.convert()"});
            questions.add(new String[]{"Qual é o valor padrão de uma variável do tipo double?", "D", "A. 0", "B. 1", "C. null", "D. 0.0"});
            questions.add(new String[]{"Qual é a estrutura que permite executar um bloco de código várias vezes?", "B", "A. if", "B. loop", "C. switch", "D. case"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma interface?", "C", "A. class", "B. abstract", "C. interface", "D. implements"});
            questions.add(new String[]{"Qual é o método usado para ordenar uma lista?", "B", "A. sort()", "B. Collections.sort()", "C. order()", "D. arrange()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar um método estático?", "D", "A. final", "B. abstract", "C. static", "D. void"});
            questions.add(new String[]{"Qual é o método usado para adicionar um elemento a um ArrayList?", "A", "A. add()", "B. insert()", "C. append()", "D. push()"});
            questions.add(new String[]{"Qual é o operador usado para acessar membros de uma classe?", "B", "A. .", "B. ->", "C. ::", "D. #"});
            questions.add(new String[]{"Qual é a estrutura de dados que armazena elementos em uma ordem específica?", "C", "A. Set", "B. Map", "C. List", "D. Array"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar um array?", "A", "A. new", "B. create", "C. array", "D. list"});
            questions.add(new String[]{"Qual é o método usado para obter o tamanho de um ArrayList?", "B", "A. length()", "B. size()", "C. count()", "D. getSize()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir um método abstrato?", "C", "A. abstract", "B. void", "C. abstract void", "D. function"});
            questions.add(new String[]{"Qual é a palavra-chave usada para encapsular dados?", "A", "A. private", "B. public", "C. protected", "D. default"});
            questions.add(new String[]{"Qual é o método usado para imprimir no console?", "B", "A. print()", "B. System.out.println()", "C. echo()", "D. display()"});
            questions.add(new String[]{"Qual é a estrutura de controle que permite executar um bloco de código baseado em uma condição?", "C", "A. loop", "B. switch", "C. if", "D. case"});
            questions.add(new String[]{"Qual é a palavra-chave que indica que um método pode ser sobrescrito?", "B", "A. final", "B. override", "C. static", "D. virtual"});
            questions.add(new String[]{"Qual é o método usado para verificar se um ArrayList contém um elemento?", "A", "A. contains()", "B. has()", "C. includes()", "D. exists()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar uma exceção personalizada?", "C", "A. throw", "B. catch", "C. throws", "D. exception"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar um bloco de código que pode lançar uma exceção?", "D", "A. try", "B. catch", "C. finally", "D. throws"});
            questions.add(new String[]{"Qual é o método usado para converter uma string em um número inteiro?", "B", "A. Integer.toString()", "B. Integer.parseInt()", "C. String.valueOf()", "D. String.convert()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que um método não pode ser sobrescrito?", "A", "A. final", "B. static", "C. abstract", "D. private"});
            questions.add(new String[]{"Qual é o método usado para remover um elemento de um ArrayList?", "B", "A. delete()", "B. remove()", "C. discard()", "D. clear()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma classe que não pode ser instanciada?", "C", "A. abstract", "B. static", "C. final", "D. sealed"});
            questions.add(new String[]{"Qual é a estrutura que permite armazenar objetos de diferentes tipos?", "B", "A. Array", "B. Object", "C. List", "D. Map"});
            questions.add(new String[]{"Qual é o operador usado para realizar uma operação lógica AND?", "A", "A. &&", "B. ||", "C. !", "D. &"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar uma classe interna?", "C", "A. inner", "B. local", "C. static", "D. nested"});
            questions.add(new String[]{"Qual é o método usado para obter o primeiro elemento de uma lista?", "B", "A. first()", "B. get(0)", "C. head()", "D. top()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir um método que não retorna valor?", "A", "A. void", "B. null", "C. empty", "D. none"});
            questions.add(new String[]{"Qual é o método usado para converter um objeto em uma string?", "C", "A. toString()", "B. convert()", "C. stringify()", "D. cast()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma variável que pode ser acessada de qualquer lugar?", "B", "A. local", "B. public", "C. private", "D. protected"});
            questions.add(new String[]{"Qual é o operador usado para realizar uma operação lógica OR?", "A", "A. ||", "B. &&", "C. !", "D. |"});
            questions.add(new String[]{"Qual é o método usado para converter um objeto em uma string?", "A", "A. toString()", "B. convert()", "C. stringValue()", "D. parseString()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que uma variável não pode ser alterada?", "B", "A. const", "B. final", "C. static", "D. immutable"});
            questions.add(new String[]{"Qual é a estrutura de controle que permite repetir um bloco de código enquanto uma condição for verdadeira?", "C", "A. for", "B. switch", "C. while", "D. do"});
            questions.add(new String[]{"Qual é o método usado para obter um elemento na posição específica de uma lista?", "B", "A. getElement()", "B. get(index)", "C. elementAt()", "D. at(index)"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que uma classe é uma subclasse?", "A", "A. extends", "B. inherits", "C. implements", "D. derived"});
            questions.add(new String[]{"Qual é o método usado para verificar se uma lista está vazia?", "B", "A. isEmpty()", "B. size() == 0", "C. empty()", "D. count() == 0"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar uma enumeração?", "C", "A. enum", "B. constants", "C. enum class", "D. type"});
            questions.add(new String[]{"Qual é o método usado para inverter uma lista?", "B", "A. reverse()", "B. Collections.reverse()", "C. flip()", "D. changeOrder()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma classe abstrata?", "A", "A. abstract", "B. interface", "C. class", "D. virtual"});
            questions.add(new String[]{"Qual é o método usado para adicionar um elemento em uma posição específica de uma lista?", "C", "A. insert()", "B. addAt()", "C. add(index, element)", "D. place()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que um método pode ser sobrescrito em uma subclasse?", "D", "A. override", "B. virtual", "C. abstract", "D. @Override"});
            questions.add(new String[]{"Qual é o método usado para fazer uma cópia de uma lista?", "B", "A. copy()", "B. new ArrayList<>(originalList)", "C. clone()", "D. duplicate()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma classe que não pode ser estendida?", "C", "A. final", "B. static", "C. sealed", "D. abstract"});
            questions.add(new String[]{"Qual é a estrutura de controle que permite executar um bloco de código até que uma condição seja verdadeira?", "A", "A. do-while", "B. while", "C. for", "D. if"});
            questions.add(new String[]{"Qual é o método usado para obter a última posição de um elemento em uma lista?", "B", "A. lastIndexOf()", "B. indexOf(element)", "C. findLast()", "D. getLast()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma classe que implementa uma interface?", "A", "A. implements", "B. extends", "C. interface", "D. derive"});
            questions.add(new String[]{"Qual é o método usado para transformar uma lista em um array?", "C", "A. toArray()", "B. toArrayList()", "C. toArray(new Type[0])", "D. convertToArray()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma variável que pode ser acessada somente dentro de uma classe?", "B", "A. public", "B. private", "C. protected", "D. default"});
            questions.add(new String[]{"Qual é o método usado para verificar se uma string contém uma substring?", "A", "A. contains()", "B. hasSubstring()", "C. includes()", "D. find()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir um método que não pode ser sobrescrito?", "C", "A. final", "B. static", "C. sealed", "D. constant"});
            questions.add(new String[]{"Qual é o método usado para concatenar duas strings?", "B", "A. add()", "B. concat()", "C. merge()", "D. join()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para criar uma classe que pode ser instanciada?", "D", "A. abstract", "B. final", "C. interface", "D. class"});
            questions.add(new String[]{"Qual é o método usado para obter o tamanho de um array?", "A", "A. length", "B. size()", "C. count()", "D. getSize()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para parar a execução de um loop?", "B", "A. exit", "B. break", "C. stop", "D. end"});
            questions.add(new String[]{"Qual é o método usado para transformar uma string em letras minúsculas?", "C", "A. lowerCase()", "B. toLower()", "C. toLowerCase()", "D. makeLower()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir um método que retorna um valor?", "A", "A. return", "B. void", "C. output", "D. result"});
            questions.add(new String[]{"Qual é o método usado para dividir uma string em partes?", "B", "A. split()", "B. divide()", "C. partition()", "D. cut()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que uma variável pode ser acessada fora da classe?", "A", "A. public", "B. private", "C. protected", "D. default"});
            questions.add(new String[]{"Qual é o método usado para obter o caractere em uma posição específica de uma string?", "C", "A. charAt()", "B. getChar()", "C. charAt(index)", "D. at(index)"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir uma classe que não pode ser instanciada diretamente?", "D", "A. abstract", "B. final", "C. interface", "D. sealed"});
            questions.add(new String[]{"Qual é o método usado para verificar se um número é par?", "B", "A. isEven()", "B. % 2 == 0", "C. checkEven()", "D. even()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para indicar que um método deve ser sobrescrito em uma subclasse?", "A", "A. @Override", "B. override", "C. virtual", "D. final"});
            questions.add(new String[]{"Qual é o método usado para transformar uma lista em um conjunto?", "C", "A. toSet()", "B. convertToSet()", "C. new HashSet<>(list)", "D. listToSet()"});
            questions.add(new String[]{"Qual é a palavra-chave usada para definir um método que pode lançar uma exceção?", "A", "A. throws", "B. catch", "C. try", "D. exception"});


// Mais 50 questões exemplo
        for (int i = 0; i < 50; i++) {
            questions.add(new String[]{"Pergunta de exemplo " + (i + 6), "B", "A. Resposta1", "B. Resposta2", "C. Resposta3", "D. Resposta4"});
            explanations.add("Explicação para exemplo " + (i + 6));
            hints.add(new String[]{"Dica 1 para exemplo " + (i + 6), "Dica 2 para exemplo " + (i + 6), "Dica 3 para exemplo " + (i + 6)});
        }


    }
}

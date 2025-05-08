package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class chatServer {
    // Define a porta padrão para o servidor. É uma boa prática usar constantes para esses valores.
    public static final int PORT = 4000;
    private ServerSocket serverSocket = null;
    // Endereço local do servidor. "127.0.0.1" ou "localhost" referem-se à própria máquina.
    private final String SERVER_ADDRESS = "127.0.0.1";

    // O método start inicializa o servidor, criando um ServerSocket e iniciando o loop de conexão.
    public void start() throws IOException {
        // Cria um ServerSocket que escuta por conexões na porta especificada.
        // Se a porta estiver em uso, uma IOException será lançada.
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta " + PORT);
        // Chama o método que mantém o servidor aceitando continuamente novas conexões de clientes.
        clientConnectionLoop();
    }

    // Este método fica em um loop infinito, aceitando novas conexões de clientes.
    private void clientConnectionLoop() throws IOException {
        while (true) {
            // O método accept() bloqueia a execução até que um cliente tente se conectar.
            // Quando um cliente se conecta, accept() retorna um objeto Socket representando a conexão com esse cliente.
            Socket clientSocket = serverSocket.accept();

            // Para lidar com múltiplos clientes simultaneamente, criamos uma nova thread para cada conexão.
            // Isso permite que o servidor continue aceitando novas conexões enquanto lida com os clientes já conectados.
            new Thread(() -> {
                try {
                    // Cria uma instância de ClienteSocket para encapsular a comunicação com o cliente.
                    ClienteSocket clienteSocketWrapper = new ClienteSocket(clientSocket);
                    // Chama o método que lida com a troca de mensagens com este cliente específico.
                    clienteMessageLoop(clienteSocketWrapper);
                } catch (IOException e) {
                    // Em caso de erro na comunicação com o cliente, podemos logar ou tomar outras ações.
                    e.printStackTrace();
                } finally {
                    // É importante fechar o socket do cliente quando a comunicação terminar ou ocorrer um erro.
                    try {
                        clientSocket.close();
                        System.out.println("Conexão com " + clientSocket.getRemoteSocketAddress() + " encerrada.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start(); // Inicia a nova thread, permitindo que o código dentro do bloco run() seja executado concorrentemente.
        }
    }

    // Este método lida com o loop de mensagens para um cliente específico.
    public void clienteMessageLoop(ClienteSocket clienteSocket) throws IOException {
        String msg;
        try {
            // Enquanto houver mensagens chegando do cliente (getMessage() não retornar null)...
            while ((msg = clienteSocket.getMessage()) != null) {
                // Se a mensagem for "sair" (ignorando a caixa alta/baixa), encerra a comunicação com este cliente.
                if ("sair".equalsIgnoreCase(msg)) {
                    System.out.printf("Cliente %s desconectou.\n", clienteSocket.getRemoteSocketAddress());
                    return; // Sai do loop de mensagens, e a thread do cliente terminará.
                }
                // Exibe a mensagem recebida do cliente no console do servidor.
                System.out.printf("Mensagem recebida do cliente %s: %s\n",
                        clienteSocket.getRemoteSocketAddress(), msg);
                // Aqui você pode adicionar lógica para processar a mensagem, como enviar para outros clientes.
                // Por exemplo, você poderia ter uma lista de todos os ClienteSocket conectados e iterar sobre ela
                // para enviar a mensagem para todos, exceto o remetente (para um chat em grupo).
            }
        } finally {
            // O bloco finally garante que o socket do cliente seja fechado, mesmo que ocorra uma exceção no try.
            clienteSocket.close();
            System.out.println("Conexão com " + clienteSocket.getRemoteSocketAddress() + " fechada (finally).");
        }
    }

    // Método principal que inicia o servidor.
    public static void main(String[] args) throws IOException {
        chatServer server = new chatServer();
        server.start();
        // Note que o servidor ficará rodando indefinidamente aqui, aceitando conexões.
        // Para parar o servidor, você geralmente precisaria interromper o processo manualmente.
    }
}
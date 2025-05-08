package thread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class chatCliente {

    // Define a porta para a qual o cliente tentará se conectar. Deve ser a mesma do servidor.
    public static final int PORT = 4000;
    private ClienteSocket clienteSocket = null;
    // Endereço do servidor ao qual o cliente tentará se conectar.
    private final String SERVER_ADDRESS = "127.0.0.1";

    private Scanner scanner; // Scanner para ler a entrada do usuário.

    public chatCliente() {
        scanner = new Scanner(System.in);
    }

    // O método start tenta conectar o cliente ao servidor e inicia o loop de mensagens.
    public void start() throws UnknownHostException, IOException {
        // Cria um novo socket para conectar ao servidor no endereço e porta especificados.
        // Se o servidor não estiver rodando ou o endereço/porta estiverem incorretos, uma IOException pode ser lançada.
        Socket socket = new Socket(SERVER_ADDRESS, chatServer.PORT);
        // Cria uma instância de ClienteSocket para facilitar a comunicação através do socket.
        clienteSocket = new ClienteSocket(socket);

        System.out.println("Cliente conectado ao servidor em "
                + SERVER_ADDRESS + ":" + chatServer.PORT);
        // Inicia o loop que permite ao usuário enviar mensagens para o servidor.
        messageLoop();

        // Após o loop de mensagens terminar (geralmente quando o cliente digita "exit"),
        // podemos fechar o socket do cliente. Isso é feito no bloco finally do método clienteMessageLoop no servidor.
    }

    // Este método permite que o usuário digite mensagens e as envie para o servidor.
    private void messageLoop() {
        String msg = null;
        do {
            System.out.println("Escreva sua mensagem ou 'sair' para sair:");
            // Lê a próxima linha digitada pelo usuário no console.
            msg = scanner.nextLine();
            // Envia a mensagem para o servidor através do ClienteSocket.
            clienteSocket.send(msg);
            // O loop continua até que o usuário digite "sair" (ignorando a caixa alta/baixa).
        } while (!msg.equalsIgnoreCase("sair"));

        // Quando o loop termina (o usuário digitou "sair"), podemos fechar o scanner.
        if (scanner != null) {
            scanner.close();
        }

        // O fechamento do socket do cliente é feito no método clienteMessageLoop do servidor
        // quando ele recebe a mensagem "sair" ou quando a conexão é interrompida.
        // No entanto, se quisermos fechar o socket explicitamente aqui também, podemos fazer:
        /*
        try {
            if (clienteSocket != null) {
                clienteSocket.close();
                System.out.println("Conexão com o servidor encerrada pelo cliente.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    // Método principal que inicia o cliente.
    public static void main(String[] args) {
        chatCliente cliente = new chatCliente();
        try {
            cliente.start();
        } catch (UnknownHostException e) {
            System.err.println("Erro ao conectar ao servidor: Host desconhecido.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro de I/O durante a conexão ou comunicação.");
            e.printStackTrace();
        } finally {
            System.out.println("Cliente encerrado!");
        }
    }
}
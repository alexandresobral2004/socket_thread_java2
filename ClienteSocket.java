package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClienteSocket {

    private PrintWriter out; // Objeto para enviar dados (mensagens) para o servidor.
    private Socket socket; // O socket de conexão com o servidor.
    private BufferedReader in; // Objeto para receber dados (mensagens) do servidor.

    // Construtor que recebe um objeto Socket já conectado.
    public ClienteSocket(Socket socket) throws IOException {
        this.socket = socket;
        
        // Imprime no console do servidor o endereço do cliente que se conectou.
        System.out.println("Cliente "
                + socket.getRemoteSocketAddress() + " conectou!");
        
        // Cria um BufferedReader para ler dados do InputStream do socket.
        // InputStreamReader é usado para converter os bytes do InputStream em caracteres.
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Cria um PrintWriter para escrever dados no OutputStream do socket.
        // O true como segundo argumento ativa o auto-flush, o que significa que o buffer é enviado
        // automaticamente após cada println().
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    // Retorna o endereço remoto (IP e porta) do socket conectado.
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    // Lê uma linha de texto (mensagem) do InputStream do socket.
    // Este método bloqueia até que uma linha seja lida (terminada por uma nova linha) ou o stream seja fechado.
    // Retorna a linha lida (sem a terminação de linha) ou null se o stream for fechado.
    public String getMessage() throws IOException {
        return in.readLine();
    }

    // Envia uma mensagem para o servidor através do OutputStream do socket.
    // println() escreve a string seguida por uma nova linha.
    // checkError() retorna true se houver algum erro pendente no PrintWriter.
    // Retornamos o inverso de checkError(), então true significa que não houve erro (a mensagem provavelmente foi enviada).
    public boolean send(String msg) {
        out.println(msg);
        return !out.checkError();
    }

    // Fecha os streams de entrada e saída e o socket.
    // É importante fechar os recursos para liberar conexões e evitar vazamentos de recursos.
    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
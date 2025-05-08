## Chat Simples Multi-Cliente em Java

Este projeto consiste em um chat de console simples que permite a múltiplos clientes se conectarem a um servidor e trocarem mensagens em tempo real. O projeto é composto por três arquivos Java: `chatServer.java`, `chatCliente.java` e `ClienteSocket.java`.

### Visão Geral dos Arquivos

1.  **`chatServer.java`**:
    * Este arquivo contém a classe `chatServer`, responsável por iniciar e gerenciar o servidor de chat.
    * O servidor escuta por conexões de clientes em uma porta específica (definida como `4000` por padrão).
    * Para cada novo cliente que se conecta, o servidor cria uma nova thread para lidar com a comunicação individualmente, permitindo que múltiplos clientes se conectem e interajam simultaneamente.
    * O servidor recebe mensagens dos clientes e as exibe no seu console. Atualmente, não há funcionalidade para retransmitir mensagens para outros clientes.
    * O servidor detecta quando um cliente envia a mensagem "sair" e encerra a comunicação com esse cliente.

2.  **`chatCliente.java`**:
    * Este arquivo contém a classe `chatCliente`, responsável por criar e gerenciar a aplicação cliente do chat.
    * O cliente se conecta ao servidor em um endereço e porta específicos (`127.0.0.1:4000` por padrão).
    * O cliente permite que o usuário digite mensagens no console e as envie para o servidor.
    * Se o usuário digitar "sair", o cliente envia essa mensagem para o servidor e encerra sua conexão.

3.  **`ClienteSocket.java`**:
    * Este arquivo contém a classe `ClienteSocket`, que encapsula a lógica de comunicação ponto a ponto entre o servidor e um cliente individual.
    * Ela recebe um objeto `Socket` já conectado e configura streams de entrada (`BufferedReader`) e saída (`PrintWriter`) para enviar e receber dados.
    * Possui métodos para obter o endereço remoto do cliente, ler mensagens recebidas (`getMessage`), enviar mensagens (`send`) e fechar a conexão (`close`).
    * Esta classe simplifica o tratamento da comunicação de rede tanto no servidor quanto no cliente.

### Funcionalidades Atuais

* **Conexão Múltipla de Clientes:** O servidor é capaz de aceitar conexões de múltiplos clientes simultaneamente.
* **Comunicação Unidirecional:** Os clientes podem enviar mensagens para o servidor, e o servidor exibe essas mensagens no seu console.
* **Encerramento de Conexão:** Os clientes podem encerrar a conexão enviando a mensagem "sair". O servidor também detecta essa mensagem e fecha a conexão correspondente.

### Como Executar

1.  **Pré-requisitos:** Certifique-se de ter o Java Development Kit (JDK) instalado em seu sistema.
2.  **Compilação:** Navegue até o diretório onde os arquivos `.java` estão salvos e compile-os usando o comando:
    ```bash
    javac chatServer.java chatCliente.java ClienteSocket.java
    ```
    Isso irá gerar os arquivos `.class` correspondentes.
3.  **Execução do Servidor:** Execute o servidor com o comando:
    ```bash
    java thread.chatServer
    ```
    Você verá a mensagem "Servidor iniciado na porta 4000" no console. O servidor agora está aguardando conexões de clientes.
4.  **Execução dos Clientes:** Para conectar um ou mais clientes, abra novos terminais ou prompts de comando e execute o cliente com o comando:
    ```bash
    java thread.chatCliente
    ```
    Você verá a mensagem "Cliente conectado ao servidor em 127.0.0.1:4000". Agora você pode digitar mensagens neste cliente, e elas aparecerão no console do servidor.
5.  **Encerrando a Conexão do Cliente:** No console do cliente, digite `sair` e pressione Enter. Isso enviará a mensagem para o servidor e encerrará a conexão deste cliente. Você verá uma mensagem de desconexão no console do servidor e no console do cliente.
6.  **Encerrando o Servidor:** Para encerrar o servidor, você geralmente precisará interromper o processo manualmente no terminal onde ele está sendo executado (por exemplo, usando `Ctrl+C`).

### Possíveis Melhorias e Próximos Passos

* **Chat em Grupo:** Implementar a lógica no servidor para retransmitir mensagens recebidas de um cliente para todos os outros clientes conectados, criando um verdadeiro chat em grupo.
* **Nomes de Usuário:** Permitir que os clientes escolham um nome de usuário ao se conectar e exibir esse nome nas mensagens.
* **Mensagens Privadas:** Adicionar a funcionalidade de enviar mensagens diretas para um usuário específico.
* **Interface Gráfica:** Desenvolver uma interface gráfica de usuário (GUI) para uma experiência mais amigável.
* **Tratamento de Erros:** Melhorar o tratamento de erros para lidar com desconexões inesperadas, falhas de rede, etc.
* **Segurança:** Considerar a implementação de mecanismos de segurança, como criptografia, para proteger a comunicação.

Este é um projeto básico para demonstrar a comunicação multi-cliente usando sockets em Java. Sinta-se à vontade para explorar e adicionar as melhorias sugeridas ou outras funcionalidades que você achar interessantes!

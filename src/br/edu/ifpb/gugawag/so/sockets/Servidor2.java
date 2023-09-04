package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class Servidor2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();
        NioService nio = new NioService();
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            if (mensagem.length() >= 6) {
                String comando = mensagem.substring(0, 6).toLowerCase();
                if (comando.equals("readir")) {
                    dos.writeUTF(nio.readdir().toString());
                    dos.flush();
                } else if (comando.equals("create")) {
                    String nome = mensagem.substring(7);
                    nio.createFile(nome);
                    dos.writeUTF("O arquivo de nome -> " + nome + " Criado!");
                    dos.flush();
                } else if (comando.equals("rename")) {
                    String nameRenameTo = mensagem.substring(7);
                    String[] nomes = nameRenameTo.split(" ");
                    nio.rename(nomes[0], nomes[1]);
                    dos.writeUTF("O arquivo de nome -> " + nomes + " Criado!");
                    dos.flush();
                } else if (comando.equals("delete")) {
                    String arqToRemove = mensagem.substring(7);
                    nio.remove(arqToRemove);
                    dos.writeUTF("O arquivo de nome -> " + arqToRemove + " Criado!");
                    dos.flush();
                } else {
                    System.out.println("Hello");
                }

                dos.writeUTF("Li sua mensagem: " + mensagem);
                dos.flush();
            }
        }

    }

}

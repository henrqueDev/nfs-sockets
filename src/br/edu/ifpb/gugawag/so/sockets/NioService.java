package br.edu.ifpb.gugawag.so.sockets;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NioService {

    private static String HOME = System.getProperty("user.home");
    private List<String> arquivos;

    public NioService() {
        this.arquivos = new ArrayList<String>();
    }

    public List<String> readdir() {
        return this.arquivos;
    }

    public Path createFile(String name) throws IOException {
        Path p = Paths.get(HOME + "/" + name);
        Path created = Files.createFile(p);
        this.arquivos.add(created.getFileName().toString());
        return created;
    }

    public void remove(String path) throws IOException {
        Path p = Paths.get(HOME + "/" + path);
        this.arquivos.remove(p.getFileName().toString());
        Files.delete(p);
    }

    public Path rename(String path, String newName) throws IOException {
        Path file1 = Paths.get(HOME + "/" + path);

        int i = this.arquivos.indexOf(file1.getFileName().toString());
        Path fileUpdated = Files.move(file1, file1.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
        this.arquivos.set(i, fileUpdated.getFileName().toString());
        return fileUpdated;
    }
}

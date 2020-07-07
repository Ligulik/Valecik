package pl.maciek_rychlinski;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Sorter {

    public Sorter() {
    }

    public void sort(String from, String entryFile, String to) throws IOException {


        System.out.println("I move this file to:   " + Paths.get(to + "/" + entryFile));
        Files.move(Paths.get(from + "/" + entryFile), Paths.get(to + "/" + entryFile), StandardCopyOption.REPLACE_EXISTING);
    }

}

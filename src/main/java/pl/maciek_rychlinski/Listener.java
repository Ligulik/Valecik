package pl.maciek_rychlinski;

import java.io.IOException;
import java.nio.file.*;

public class Listener {

    WatchService watchService;
    WatchKey watchKey;
    Sorter sorter = new Sorter();

    public void listen(Path pathToListen) throws IOException {

        watchService = FileSystems.getDefault().newWatchService();
        pathToListen.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
        String to;


        try {
            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {

//                    System.out.println("Zauważono zmiany");
//                    System.out.println(pathToListen.toString());
                    System.out.println(event.context().toString());

                    // switch for others options in future

                    switch (getExtension(event.context().toString())) {
                        case ("txt"):
                            to = Controller.txtCase;
                            break;
                        default:
                            to = "F:/";
                    }
                    sorter.sort(pathToListen.toString(), event.context().toString(), to);
                }
                watchKey.reset();
            }
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
        }

    }


    private String getExtension(String entry) {

        String extension = null;
        int i;

        i = entry.lastIndexOf(".");
        if (i > 0) {
            extension = entry.substring(i + 1);
            System.out.println("Rozrzeszenie:    " + extension);
        }
        return extension;
    }

}

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

//
                    System.out.println(event.context().toString());

                    // switch for others options in future

                    switch (getExtension(event.context().toString())) {
                        case ("txt"):
                            to = Controller.txtCase;
                            moveInfo(event.context().toString(),Controller.txtCase);
                            break;
                        case ("mp3"):
                            to=Controller.mp3Case;
                            moveInfo(event.context().toString(),Controller.mp3Case);
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




    // Help methods:


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


    private void moveInfo(String file, String localization){
        System.out.println("Plik "+file+" przeniesiono do "+localization);
    }
}

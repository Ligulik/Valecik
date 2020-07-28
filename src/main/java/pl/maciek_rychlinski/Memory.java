package pl.maciek_rychlinski;


import java.io.Serializable;

public class Memory implements Serializable {

    String path;
    String extension;

    public Memory(){

    }

    public Memory(String path, String extension){
        this.path=path;
        this.extension=extension;
    }

    public Memory(String path){
        this.path=path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


}

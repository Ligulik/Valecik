module pl.maciek_rychlinski {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.maciek_rychlinski to javafx.fxml;
    exports pl.maciek_rychlinski;
}
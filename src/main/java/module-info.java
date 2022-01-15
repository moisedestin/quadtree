module com.example.quadtreegraphical {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quadtreegraphical to javafx.fxml;
    exports com.example.quadtreegraphical;
}
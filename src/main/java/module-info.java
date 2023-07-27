module team.sema.dpa.digitalpatientenakte {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.orm.jcache;
    requires MaterialFX;
    requires lombok;
    requires java.naming;

    opens team.sema.dpa.digitalpatientenakte to javafx.fxml;

    exports team.sema.dpa.digitalpatientenakte;
    exports team.sema.dpa.digitalpatientenakte.dao;
    exports team.sema.dpa.digitalpatientenakte.dao.impl;
    exports team.sema.dpa.digitalpatientenakte.models;
    exports team.sema.dpa.digitalpatientenakte.services;
    exports team.sema.dpa.digitalpatientenakte.views;
    exports team.sema.dpa.digitalpatientenakte.views.factories;

    opens team.sema.dpa.digitalpatientenakte.views to javafx.fxml;
    opens team.sema.dpa.digitalpatientenakte.models to javafx.fxml, org.hibernate.orm.core;
    opens team.sema.dpa.digitalpatientenakte.views.factories to javafx.fxml;
}
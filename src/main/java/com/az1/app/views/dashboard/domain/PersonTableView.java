package com.az1.app.views.dashboard.domain;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.Arrays;
import java.util.List;

@Route("person-table")
public class PersonTableView extends VerticalLayout {

    public PersonTableView() {
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25)
        );

        GenericCustomTable<Person> table = new GenericCustomTable<>();
        table.addTextColumn("Name", Person::getName); // <-- LỖI NẰM TẠI DÒNG NÀY NẾU KHÔNG CÓ getName()
        table.addTextColumn("Age", p -> String.valueOf(p.getAge()));

        table.setItems(people);

        add(new H2("Person Table"));
        add(table);
    }
}
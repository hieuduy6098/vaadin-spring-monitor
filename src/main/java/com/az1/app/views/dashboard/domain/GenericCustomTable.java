package com.az1.app.views.dashboard.domain;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;

public class GenericCustomTable<T> extends Grid<T> {

    public GenericCustomTable() {
        setSelectionMode(SelectionMode.NONE);
        setAllRowsVisible(true);
    }

    public void addTextColumn(String header, java.util.function.Function<T, String> valueProvider) {
        addColumn(new ComponentRenderer<>(item -> new Span(valueProvider.apply(item))))
                .setHeader(header);
    }

    public void setItems(List<T> items) {
        super.setItems(items);
    }
}

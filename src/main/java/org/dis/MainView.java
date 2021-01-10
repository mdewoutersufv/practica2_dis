package org.dis;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.util.List;


@Route
public class MainView extends VerticalLayout {
    private Grid<Pelicula> grid = new Grid<>(Pelicula.class);
    private final PeliculaRepository repo;
    private final ActorRepository actorRepo;

    private TextField filterText = new TextField();

    public MainView(PeliculaRepository repo, ActorRepository actorRepo) {
        this.repo = repo;
        this.actorRepo = actorRepo;
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();

        add(filterText,grid);
        updateList(filterText);
    }

    private void configureGrid() {
        grid.addClassName("pelicula-grid");
        grid.setSizeFull();
        grid.setColumns( "peliculaId","titulo", "sinopsis", "enlace", "agno", "duracion");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private void updateList(TextField filterText) {
        if (StringUtils.isEmpty(filterText.getValue())) {
            grid.setItems(repo.findAll());
        }
        else {
            grid.setItems(repo.findByTituloStartsWithIgnoreCase(filterText.getValue()));
        }
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filtrar por título...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList(filterText));
    }


}
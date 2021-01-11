package org.dis;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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
    private Dialog detalles =  new Dialog();
    private VerticalLayout detallesLayout = new VerticalLayout();
    private HorizontalLayout detallesTitulo = new HorizontalLayout();
    private Text tituloValue = new Text("");
    private HorizontalLayout detallesSinopsis = new HorizontalLayout();
    private Text sinopsisValue = new Text("");
    private HorizontalLayout detallesGenero = new HorizontalLayout();
    private Text generoValue = new Text("");
    private HorizontalLayout detallesEnlace= new HorizontalLayout();
    private Text enlaceValue = new Text("");
    private HorizontalLayout detallesAgno = new HorizontalLayout();
    private Text agnoValue = new Text("");
    private HorizontalLayout detallesDuracion = new HorizontalLayout();
    private Text duracionValue = new Text("");

    public MainView(PeliculaRepository repo, ActorRepository actorRepo ) {
        this.repo = repo;
        this.actorRepo = actorRepo;
        addClassName("list-view");
        setSizeFull();
        configureFilter();
        configureGrid();
        configureDetalles();

        add(filterText,grid,detalles);
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

    private void configureDetalles( ){

        detalles.setCloseOnOutsideClick(false);
        detalles.setCloseOnEsc(false);
        detallesTitulo.add("Titulo: ");
        detallesTitulo.add(tituloValue);
        detallesSinopsis.add("Sinopsis: ");
        detallesSinopsis.add(sinopsisValue);
        detallesGenero.add("Genero: ");
        detallesGenero.add(generoValue);
        detallesEnlace.add("Enlace: ");
        detallesEnlace.add(enlaceValue);
        detallesAgno.add("Agno: ");
        detallesAgno.add(agnoValue);
        detallesDuracion.add("Duracion: ");
        detallesDuracion.add(duracionValue);

        detallesLayout.add(detallesTitulo,detallesSinopsis,detallesGenero,detallesEnlace,detallesAgno,detallesDuracion);

        detalles.add(detallesLayout);
        detalles.add(new Button("Cerrar", event -> {
            detalles.close();
        }));
    }


}

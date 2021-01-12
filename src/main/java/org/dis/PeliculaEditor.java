package org.dis;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in MainView.
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX.
 */
@SpringComponent
@UIScope
public class PeliculaEditor extends VerticalLayout implements KeyNotifier {

   private final PeliculaRepository repository;
   private final ActorRepository actorRepo;

    /**
     * The currently edited pelicula
     */
    private Pelicula pelicula;

    /* Fields to edit properties in Pelicula entity */
    TextField titulo = new TextField("Titulo");
    TextField sinopsis = new TextField("Sinopsis");
    TextField genero = new TextField("Genero");
    TextField enlace = new TextField("Enlace");
    IntegerField agno = new IntegerField("Agno");
    IntegerField duracion = new IntegerField("Duracion");
    Paragraph reparto = new Paragraph("Reparto");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Pelicula> binder = new Binder<>(Pelicula.class);
    private ChangeHandler changeHandler;

    @Autowired
    public PeliculaEditor(PeliculaRepository repository, ActorRepository actorRepo) {
        this.repository = repository;
        this.actorRepo = actorRepo;

        add(titulo, sinopsis, genero, enlace, agno, duracion,reparto, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editPelicula(pelicula));
        setVisible(false);


    }

    void delete() {
        repository.delete(pelicula);
        changeHandler.onChange();
    }

    void save() {
        repository.save(pelicula);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editPelicula(Pelicula c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getPeliculaId() != null;
        if (persisted) {
            // Find fresh entity for editing
            pelicula = repository.findById(c.getPeliculaId()).get();
        }
        else {
            pelicula = c;
        }
        cancel.setVisible(persisted);

        // Bind pelicula properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(pelicula);

        setVisible(true);

        // Focus first name initially
        titulo.focus();

    }





    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
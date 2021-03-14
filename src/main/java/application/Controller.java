package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    public Controller(NotesService notesService) {
        this.notesService = notesService;
    }

    private final NotesService notesService;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes(@RequestParam(required = false, name = "query") String query) {
        if (query == null) {
            return new ResponseEntity<>(notesService.findAll(), HttpStatus.OK);
        } else return new ResponseEntity<>(notesService.getTargetNotes(query), HttpStatus.OK);

    }

    @PostMapping("/notes")
    public ResponseEntity<Note> addNote(@RequestBody Note note) {
        return new ResponseEntity<>(notesService.addNewNote(note), HttpStatus.CREATED);
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<Note> getCurrentNote(@PathVariable("id") long id) {
        Note optNote = notesService.findOneNote(id);
        if (optNote == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optNote, HttpStatus.OK);
        }
    }

    @PutMapping("/notes/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") long id, @RequestBody Note note) {
        Note optNote = notesService.updateNote(id, note);
        if (optNote == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(optNote, HttpStatus.OK);
        }
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity deleteNote(@PathVariable("id") long id) {
        notesService.deleteNote(id);
        return new ResponseEntity(null, HttpStatus.OK);
    }

}

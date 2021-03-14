package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Value("${notes.N}")
    int notesNum;

    private final NotesRepository notesRepository;

    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        List<Note> allNotes = notesRepository.findAll();
        for (Note n : allNotes) {
            Note tmpNote = n;
            if (tmpNote.getTitle().isEmpty()) {
                tmpNote.setTitle(tmpNote.getContent().substring(0, notesNum));
            }
            notes.add(tmpNote);
        }
        return notes;
}

    public Note addNewNote(Note note) {
        if (note.getTitle() == null) {
            note.setTitle("");
        }
        return notesRepository.save(note);
    }

    public Note findOneNote(long id) {
        Optional<Note> optNote = notesRepository.findById(id);
        if (optNote.isPresent()) {
            Note tmpNote = optNote.get();
            if (tmpNote.getTitle().isEmpty()) {
                tmpNote.setTitle(tmpNote.getContent().substring(0, notesNum));
            }
            return tmpNote;
        } else {
            return null;
        }
    }

    public Note updateNote(long id, Note note) {
        Optional<Note> n = notesRepository.findById(id);
        if (n.isPresent()) {
            Note newNote = n.get();
            newNote.setContent(note.getContent());
            newNote.setTitle(note.getTitle());
            notesRepository.save(newNote);
            return newNote;
        } else {
            return null;
        }
    }

    public void deleteNote(long id) {
        notesRepository.deleteById(id);
    }


    public List<Note> getTargetNotes(String query) {
        List<Note> notes = new ArrayList<>();
        List<Note> allNotes = notesRepository.findAll();
        for (Note n : allNotes) {
            if (n.getTitle().contains(query) || n.getContent().contains(query)) {
                Note tmpNote = n;
                if (tmpNote.getTitle().isEmpty()) {
                    tmpNote.setTitle(tmpNote.getContent().substring(0, notesNum));
                }
                notes.add(tmpNote);
            }
        }
        return notes;
    }
}

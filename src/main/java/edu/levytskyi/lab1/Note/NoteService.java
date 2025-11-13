package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class NoteService
 @version 1.0.0
 @since 30.09.2025 - 22.47
*/

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

  private final NoteRepository repository;


  private final List<Note> notes = new ArrayList<>();

  @PostConstruct
  void init() {

    repository.deleteAll();


    notes.add(Note.builder()
        .id("1")
        .title("Перша нотатка")
        .content("Це зміст першої нотатки.")
        .createdDate(LocalDateTime.now())
        .createdBy("system")
        .build());

    notes.add(Note.builder()
        .id("2")
        .title("Сходити в магазин")
        .content("Купити хліб, молоко, яйця.")
        .createdDate(LocalDateTime.now().plusHours(1))
        .createdBy("admin")
        .build());

    notes.add(Note.builder()
        .id("3")
        .title("План на завтра")
        .content("Зробити лабораторну з безпеки.")
        .createdDate(LocalDateTime.now().plusHours(5))
        .createdBy("user")
        .build());

    repository.saveAll(notes);
  }

  public List<Note> getAll() {
    return repository.findAll();
  }

  public Note getById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void deleteById(String id) {
    repository.deleteById(id);
  }

  public Note create(Note note) {
    // При створенні Spring Security + Auditing автоматично заповнять createdBy/createdDate
    return repository.save(note);
  }

  public Note update(Note note) {

    return repository.findById(note.getId())
        .map(existingNote -> {

          existingNote.setTitle(note.getTitle());
          existingNote.setContent(note.getContent());

          return repository.save(existingNote);
        })
        .orElse(null);
  }
}
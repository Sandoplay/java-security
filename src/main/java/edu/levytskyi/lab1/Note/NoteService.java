package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class NoteService
 @version 1.0.0
 @since 30.09.2025 - 22.47
*/

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

  private final NoteRepository repository;

  private List<Note> notes = new ArrayList<>();

  @PostConstruct
  void init() {
    notes.add(new Note("1", "Перша нотатка", "Це зміст першої нотатки.", LocalDateTime.of(2025, 9, 30, 9, 15)));
    notes.add(new Note("2", "Сходити в магазин", "Купити хліб, молоко, яйця.", LocalDateTime.of(2025, 9, 30, 14, 30)));
    notes.add(new Note("3", "План на завтра", "Зробити лабораторну з безпеки.", LocalDateTime.of(2025, 9, 30, 21, 0)));
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
    return repository.save(note);
  }

  public Note update(Note note) {
    return repository.save(note);
  }
}
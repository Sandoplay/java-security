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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    // Ініціалізуємо тестові дані з "захардкодженими" авторами
    notes.add(Note.builder()
        .id("1")
        .title("Перша нотатка")
        .content("Зміст 1")
        .createdDate(LocalDateTime.now())
        .createdBy("system")
        .build());

    notes.add(Note.builder()
        .id("2")
        .title("Адмінська нотатка")
        .content("Важлива інфа")
        .createdDate(LocalDateTime.now())
        .createdBy("admin")
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

  // --- РУЧНИЙ АУДИТ: CREATE ---
  public Note create(Note note) {
    note.setCreatedDate(LocalDateTime.now());
    note.setCreatedBy(getCurrentUsername()); // Записуємо поточного юзера
    // При створенні можна також заповнити lastModified
    note.setLastModifiedDate(LocalDateTime.now());
    note.setLastModifiedBy(getCurrentUsername());

    return repository.save(note);
  }

  // --- РУЧНИЙ АУДИТ: UPDATE ---
  public Note update(Note note) {
    Note existing = repository.findById(note.getId()).orElse(null);
    if (existing != null) {
      // Оновлюємо контент
      existing.setTitle(note.getTitle());
      existing.setContent(note.getContent());

      // Руками ставимо дату і автора зміни
      existing.setLastModifiedDate(LocalDateTime.now());
      existing.setLastModifiedBy(getCurrentUsername());

      return repository.save(existing);
    }
    return null;
  }

  // Допоміжний метод для отримання логіна
  private String getCurrentUsername() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null && auth.isAuthenticated()) {
      return auth.getName();
    }
    return "anonymous";
  }
}
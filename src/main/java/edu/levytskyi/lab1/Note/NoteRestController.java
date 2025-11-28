package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class NoteRestController
 @version 1.0.0
 @since 30.09.2025 - 22.54
*/

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NoteRestController {

  private final NoteService noteService;

  // Публічний доступ
  @GetMapping
  public List<Note> getAll() {
    return noteService.getAll();
  }

  // Доступно будь-якому аутентифікованому користувачу
  @GetMapping("/{id}")
  public ResponseEntity<Note> getById(@PathVariable String id) {
    Note note = noteService.getById(id);
    if (note != null) {
      return ResponseEntity.ok(note);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')") // БУЛО: hasRole('ADMIN')
  public Note create(@RequestBody Note note) {
    return noteService.create(note);
  }

  @PutMapping
  @PreAuthorize("hasAuthority('ADMIN')") // БУЛО: hasRole('ADMIN')
  public Note update(@RequestBody Note note) {
    return noteService.update(note);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')") // БУЛО: hasRole('ADMIN')
  public void deleteById(@PathVariable String id) {
    noteService.deleteById(id);
  }

  @GetMapping("/new")
  public String getNewPage() {
    return "This is a new page!";
  }

  @GetMapping("/helloUser")
  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')") // БУЛО: hasAnyRole
  public String helloUser() {
    return "Hello, User!";
  }

  @GetMapping("/helloAdmin")
  @PreAuthorize("hasAuthority('ADMIN')") // БУЛО: hasRole
  public String helloAdmin() {
    return "Hello, Admin!";
  }

  // Публічний доступ
  @GetMapping("/helloUnknown")
  public String helloUnknown() {
    return "Hello, Unknown!";
  }
}
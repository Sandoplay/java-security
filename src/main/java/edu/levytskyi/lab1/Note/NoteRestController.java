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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@AllArgsConstructor
public class NoteRestController {

  private final NoteService noteService;

  @GetMapping
  public List<Note> getAll() {
    return noteService.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Note> getById(@PathVariable String id) {
    Note note = noteService.getById(id);
    if (note != null) {
      return ResponseEntity.ok(note);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public Note create(@RequestBody Note note) {
    return noteService.create(note);
  }

  @PutMapping
  public Note update(@RequestBody Note note) {
    return noteService.update(note);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable String id) {
    noteService.deleteById(id);
  }
}
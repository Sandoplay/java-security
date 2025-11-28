package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class Note
 @version 1.0.0
 @since 30.09.2025 - 22.45
*/


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
@Data
@EqualsAndHashCode(callSuper = true) // Важливо для Lombok при наслідуванні
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note extends AuditMetaData { // <--- Додали extends

  @Id
  private String id;
  private String title;
  private String content;

}
package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class Note
 @version 1.0.0
 @since 30.09.2025 - 22.45
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

  @Id
  private String id;
  private String title;
  private String content;
  private LocalDateTime createdAt;
}

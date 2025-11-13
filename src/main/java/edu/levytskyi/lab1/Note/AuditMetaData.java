package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class AuditMetaData
 @version 1.0.0
 @since 13.11.2025 - 16.42
*/

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public abstract class AuditMetaData {

  @CreatedDate
  private LocalDateTime createdDate;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  @LastModifiedBy
  private String lastModifiedBy;
}
package edu.levytskyi.lab1.Note;

/*
 @author Sandoplay
 @project lab1
 @class NoteRepository
 @version 1.0.0
 @since 30.09.2025 - 22.46
*/

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
}
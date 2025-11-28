package edu.levytskyi.lab1;

/*
 @author Sandoplay
 @project lab1
 @class AccessTests
 @version 1.0.0
 @since 28.11.2025 - 18.07
*/

import edu.levytskyi.lab1.Note.Note;
import edu.levytskyi.lab1.Note.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AccessTests {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockitoBean
  private NoteService noteService;

  private MockMvc mockMvc;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setup() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
  }

  // --- 1. Анонімний доступ до публічного ресурсу ---
  @Test
  @WithAnonymousUser
  public void whenAnonymousGetPublicHello_thenStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloUnknown"))
        .andExpect(status().isOk());
  }

  // --- 2. Анонімний доступ до захищеного ресурсу ---
  @Test
  @WithAnonymousUser
  public void whenAnonymousGetProtectedResource_thenStatusUnauthorized() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloUser"))
        .andExpect(status().isUnauthorized());
  }

  // --- 3. Доступ USER до ресурсу для USER/ADMIN ---
  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void whenUserGetHelloUser_thenStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloUser"))
        .andExpect(status().isOk());
  }

  // --- 4. Спроба доступу USER до ресурсу ADMIN ---
  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void whenUserGetHelloAdmin_thenStatusForbidden() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloAdmin"))
        .andExpect(status().isForbidden());
  }

  // --- 5. Доступ ADMIN до ресурсу ADMIN ---
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void whenAdminGetHelloAdmin_thenStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloAdmin"))
        .andExpect(status().isOk());
  }

  // --- 6. Доступ ADMIN до спільного ресурсу ---
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void whenAdminGetHelloUser_thenStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/notes/helloUser"))
        .andExpect(status().isOk());
  }

  // --- 7. Спроба створення запису (POST) користувачем USER ---
  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void whenUserPostNote_thenStatusForbidden() throws Exception {
    Note note = new Note();
    note.setTitle("Hacker Note");

    mockMvc.perform(post("/api/v1/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(note)))
        .andExpect(status().isForbidden());
  }

  // --- 8. Створення запису (POST) адміністратором ---
  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void whenAdminPostNote_thenStatusOk() throws Exception {
    Note note = new Note();
    note.setTitle("Admin Note");

    mockMvc.perform(post("/api/v1/notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(note)))
        .andExpect(status().isOk());
  }

  // --- 9. Спроба видалення (DELETE) користувачем SUPERADMIN ---
  @Test
  @WithMockUser(username = "superadmin", roles = {"SUPERADMIN"})
  public void whenSuperAdminDeleteNote_thenStatusForbidden() throws Exception {
    mockMvc.perform(delete("/api/v1/notes/1"))
        .andExpect(status().isForbidden());
  }

  // --- 10. Анонімний доступ до списку всіх нотаток ---
  @Test
  @WithAnonymousUser
  public void whenAnonymousGetAllNotes_thenStatusOk() throws Exception {
    mockMvc.perform(get("/api/v1/notes"))
        .andExpect(status().isOk());
  }
}
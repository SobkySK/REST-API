package me.patriksobol.bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Transactional
public class MvcPersonTests {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void createPerson() throws Exception {
        this.mockMvc.perform(post("/save")
                        .content(getResourceContent("NewPerson.json"))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person successfully saved!"));
    }

    @Test
    public void tryToCreateDuplicate() throws Exception {
        this.mockMvc.perform(post("/save")
                        .content(getResourceContent("NewPerson.json"))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person successfully saved!"));

        this.mockMvc.perform(post("/save")
                        .content(getResourceContent("NewPerson.json"))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest()).andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person already exists!"));
    }

    @Test
    public void findById() throws Exception {
        this.mockMvc.perform(post("/save")
                        .content(getResourceContent("NewPerson.json"))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person successfully saved!"));

        this.mockMvc.perform(get("/find/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void findById_UserDoesNotExist() throws Exception {
        this.mockMvc.perform(post("/save")
                        .content(getResourceContent("NewPerson.json"))
                        .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content()
                        .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person successfully saved!"));

        this.mockMvc.perform(get("/find/{id}", 42))
                .andDo(print())
                .andExpect(status().isNotFound()).andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Person with id 42 does not exist!"));
    }

    protected String getResourceContent(final String fileName) throws URISyntaxException, IOException {
        final URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalStateException("File does not exist!");
        }
        final Path path = Paths.get(resource.toURI());
        final StringBuilder data = new StringBuilder();
        final Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();

        return data.toString();
    }
}

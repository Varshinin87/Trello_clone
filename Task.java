package syr.edu.TrelloClone.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Document(collection="Task")
public class Task {
        @Id
        private String id;
        private String state;
        private String assignedTo;
        private String description;
        private List<String> comments;
        private LocalDateTime createdTime;
}

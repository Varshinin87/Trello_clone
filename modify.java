package syr.edu.TrelloClone.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
@Getter
@Setter
public class Modify {
    @Id
    private String id;
    private String taskId;
    private Task task;
}

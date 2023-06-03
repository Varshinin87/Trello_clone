package syr.edu.TrelloClone.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import syr.edu.TrelloClone.model.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
}

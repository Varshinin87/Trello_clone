package syr.edu.TrelloClone.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import syr.edu.TrelloClone.Repository.ModifyRepository;
import syr.edu.TrelloClone.Repository.TaskRepository;
import syr.edu.TrelloClone.model.Modify;
import syr.edu.TrelloClone.model.Task;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class TaskController {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ModifyRepository modifyRepository;

// The function createTask() is used to create a new task in the TrelloClone Application.
    @PostMapping("/createTask")
    public String createTask(@RequestBody Task task) {
        {
            String id = UUID.randomUUID().toString();
            task.setId(id);
            task.setCreatedTime(LocalDateTime.now());
            try {
                repository.save(task);
                return task.getId();
            } catch (Exception e) {
                return "Failure";
            }
        }
    }

/* The function modifyTask() is called when any new change is made in a particular task. This task is identified using its id.
   For any particular task we can modify either its state, assignedTo, description or comment values. */
    @PutMapping("/modifyTask/{id}")
    public String modifyTask(@PathVariable String id, @RequestBody Task task) {
        Optional<Task> optionalTask = repository.findById(id);

        Task oldTask = null;
        Task newTask = new Task();
        if (optionalTask.isPresent()) {
            oldTask = optionalTask.get();
        } else {
            return "Failure";
        }

        if (task.getState() != null) {
            oldTask.setState(task.getState());

        }

        if (task.getAssignedTo() != null) {
            oldTask.setAssignedTo(task.getAssignedTo());
        }

        if (task.getDescription() != null) {
            oldTask.setDescription(task.getDescription());
        }

        if (task.getComments() != null){
            List<String> commentsList = new ArrayList<String>();

            if (oldTask.getComments() != null)
                commentsList.addAll(oldTask.getComments());

            commentsList.addAll(task.getComments());

            oldTask.setComments(commentsList);
        }

        oldTask.setId(id);


        try {
            repository.save(oldTask);
            modifiedList(id,oldTask);
            return "Success";
        }catch(Exception e) {
            return "Failure";
        }
    }

// The function modifiedList() is called within the function modify() to save the modifications made to particular task in the database.
    @PutMapping("/postModifiedList/{id}")
    public String modifiedList(@PathVariable String taskId, @RequestBody Task task) {

        {
            Modify modify = new Modify();
            String id = UUID.randomUUID().toString();
            modify.setId(id);
            modify.setTaskId(taskId);
            modify.setTask(task);
            try {
                modifyRepository.save(modify);
                return modify.getId();
            } catch (Exception e) {
                return "Failure";
            }
        }
    }

// The function getModifiedList() is called to display the history of modifications done for a particular task using taskId.
    @GetMapping("/getModifiedList/{id}")
    public ArrayList<Modify> getModifiedList(@PathVariable String id) {
        try{
            ArrayList<Modify> listOfSpecific = new ArrayList<Modify>();
            List<Modify> allData = modifyRepository.findAll();
            for(int i=0;i<allData.size();i++){
                   if( allData.get(i).getTaskId().equals(id)){
                       listOfSpecific.add(allData.get(i));
                   }
            }
            return listOfSpecific;
        }catch(Exception e) {
            return null;
        }
    }

// The function deleteTask() is called to delete a task from the database.
    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable String id) {
        try{
            Optional<Task> optionalTask = repository.findById(id);

            if (optionalTask.isPresent()) {
                repository.findAll();
                return "Success";
            } else {
                return "Failure";
            }

        }catch(Exception e) {
            return "failure";
        }
    }

// The function showBoard is called to display all the task present in the application.
    @GetMapping("/showBoard")
    public List<Task> getTasks(){
        return repository.findAll();
    }


}

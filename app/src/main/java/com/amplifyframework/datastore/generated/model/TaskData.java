package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the TaskData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "TaskData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class TaskData implements Model {
  public static final QueryField ID = field("TaskData", "id");
  public static final QueryField NAME = field("TaskData", "name");
  public static final QueryField DESCRIPTION = field("TaskData", "description");
  public static final QueryField IMAGE = field("TaskData", "image");
  public static final QueryField TASK_COLOR = field("TaskData", "taskColor");
  public static final QueryField PRIORITY = field("TaskData", "priority");
  public static final QueryField TIME = field("TaskData", "time");
  public static final QueryField DEADLINE = field("TaskData", "deadline");
  public static final QueryField COMPLETED = field("TaskData", "completed");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="Int", isRequired = true) Integer taskColor;
  private final @ModelField(targetType="Int", isRequired = true) Integer priority;
  private final @ModelField(targetType="String") String time;
  private final @ModelField(targetType="Int") Integer deadline;
  private final @ModelField(targetType="Boolean") Boolean completed;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getImage() {
      return image;
  }
  
  public Integer getTaskColor() {
      return taskColor;
  }
  
  public Integer getPriority() {
      return priority;
  }
  
  public String getTime() {
      return time;
  }
  
  public Integer getDeadline() {
      return deadline;
  }
  
  public Boolean getCompleted() {
      return completed;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private TaskData(String id, String name, String description, String image, Integer taskColor, Integer priority, String time, Integer deadline, Boolean completed) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.image = image;
    this.taskColor = taskColor;
    this.priority = priority;
    this.time = time;
    this.deadline = deadline;
    this.completed = completed;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      TaskData taskData = (TaskData) obj;
      return ObjectsCompat.equals(getId(), taskData.getId()) &&
              ObjectsCompat.equals(getName(), taskData.getName()) &&
              ObjectsCompat.equals(getDescription(), taskData.getDescription()) &&
              ObjectsCompat.equals(getImage(), taskData.getImage()) &&
              ObjectsCompat.equals(getTaskColor(), taskData.getTaskColor()) &&
              ObjectsCompat.equals(getPriority(), taskData.getPriority()) &&
              ObjectsCompat.equals(getTime(), taskData.getTime()) &&
              ObjectsCompat.equals(getDeadline(), taskData.getDeadline()) &&
              ObjectsCompat.equals(getCompleted(), taskData.getCompleted()) &&
              ObjectsCompat.equals(getCreatedAt(), taskData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), taskData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getImage())
      .append(getTaskColor())
      .append(getPriority())
      .append(getTime())
      .append(getDeadline())
      .append(getCompleted())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("TaskData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("taskColor=" + String.valueOf(getTaskColor()) + ", ")
      .append("priority=" + String.valueOf(getPriority()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
      .append("deadline=" + String.valueOf(getDeadline()) + ", ")
      .append("completed=" + String.valueOf(getCompleted()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static TaskData justId(String id) {
    return new TaskData(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      image,
      taskColor,
      priority,
      time,
      deadline,
      completed);
  }
  public interface NameStep {
    DescriptionStep name(String name);
  }
  

  public interface DescriptionStep {
    TaskColorStep description(String description);
  }
  

  public interface TaskColorStep {
    PriorityStep taskColor(Integer taskColor);
  }
  

  public interface PriorityStep {
    BuildStep priority(Integer priority);
  }
  

  public interface BuildStep {
    TaskData build();
    BuildStep id(String id);
    BuildStep image(String image);
    BuildStep time(String time);
    BuildStep deadline(Integer deadline);
    BuildStep completed(Boolean completed);
  }
  

  public static class Builder implements NameStep, DescriptionStep, TaskColorStep, PriorityStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private Integer taskColor;
    private Integer priority;
    private String image;
    private String time;
    private Integer deadline;
    private Boolean completed;
    @Override
     public TaskData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new TaskData(
          id,
          name,
          description,
          image,
          taskColor,
          priority,
          time,
          deadline,
          completed);
    }
    
    @Override
     public DescriptionStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public TaskColorStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public PriorityStep taskColor(Integer taskColor) {
        Objects.requireNonNull(taskColor);
        this.taskColor = taskColor;
        return this;
    }
    
    @Override
     public BuildStep priority(Integer priority) {
        Objects.requireNonNull(priority);
        this.priority = priority;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        this.time = time;
        return this;
    }
    
    @Override
     public BuildStep deadline(Integer deadline) {
        this.deadline = deadline;
        return this;
    }
    
    @Override
     public BuildStep completed(Boolean completed) {
        this.completed = completed;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String image, Integer taskColor, Integer priority, String time, Integer deadline, Boolean completed) {
      super.id(id);
      super.name(name)
        .description(description)
        .taskColor(taskColor)
        .priority(priority)
        .image(image)
        .time(time)
        .deadline(deadline)
        .completed(completed);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder taskColor(Integer taskColor) {
      return (CopyOfBuilder) super.taskColor(taskColor);
    }
    
    @Override
     public CopyOfBuilder priority(Integer priority) {
      return (CopyOfBuilder) super.priority(priority);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
    
    @Override
     public CopyOfBuilder deadline(Integer deadline) {
      return (CopyOfBuilder) super.deadline(deadline);
    }
    
    @Override
     public CopyOfBuilder completed(Boolean completed) {
      return (CopyOfBuilder) super.completed(completed);
    }
  }
  
}

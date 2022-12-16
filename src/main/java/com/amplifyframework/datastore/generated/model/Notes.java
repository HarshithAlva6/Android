package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Notes type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Notes", type = Model.Type.USER, version = 1)
public final class Notes implements Model {
  public static final QueryField ID = field("Notes", "id");
  public static final QueryField DESCRIPTION = field("Notes", "description");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String description;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Notes(String id, String description) {
    this.id = id;
    this.description = description;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Notes notes = (Notes) obj;
      return ObjectsCompat.equals(getId(), notes.getId()) &&
              ObjectsCompat.equals(getDescription(), notes.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), notes.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), notes.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Notes {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
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
  public static Notes justId(String id) {
    return new Notes(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      description);
  }
  public interface BuildStep {
    Notes build();
    BuildStep id(String id);
    BuildStep description(String description);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String description;
    @Override
     public Notes build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Notes(
          id,
          description);
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
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
    private CopyOfBuilder(String id, String description) {
      super.id(id);
      super.description(description);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
  }
  
}

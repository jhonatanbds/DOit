package edu.ufcg.jhonatanbds.converter;


import edu.ufcg.jhonatanbds.entity.ToDo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class ToDoWriterConverter implements Converter<ToDo, DBObject> {

    @Override
    public DBObject convert(final ToDo toDo) {
        final DBObject dbObject = new BasicDBObject();
        dbObject.put("name", toDo.getName());
        dbObject.put("observation", toDo.getObservation());
        dbObject.put("completed", toDo.isCompleted());
        dbObject.put("priority", toDo.getPriority());
        dbObject.put("category", toDo.getCategory());
        dbObject.put("associatedList", toDo.getAssociatedList());
        dbObject.put("associatedListId", toDo.getAssociatedListId());
        dbObject.removeField("_class");
        return dbObject;
    }

}

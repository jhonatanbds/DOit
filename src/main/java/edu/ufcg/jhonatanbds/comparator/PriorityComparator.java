package edu.ufcg.jhonatanbds.comparator;

import edu.ufcg.jhonatanbds.entity.ToDo;

import java.util.Comparator;

/**
 * Created by Jhonatan on 10/02/2017.
 */
public class PriorityComparator implements Comparator<ToDo> {

    @Override
    public int compare(ToDo o1, ToDo o2) {
        if (o2.getPriority() == null)
            return -1;
        if (o1.getPriority() == null)
            return 1;
        if (o2.getPriority().equals("HIGH"))
            return 1;
        if (o2.getPriority().equals("LOW"))
            return -1;
        if (o2.getPriority().equals("MEDIUM")) {
            if (o1.getPriority().equals("HIGH"))
                return -1;
            if (o1.getPriority().equals("LOW"))
                return 1;
            else return 0;
        }
        else return 0;
    }
}

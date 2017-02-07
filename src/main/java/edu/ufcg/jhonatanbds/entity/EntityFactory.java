package edu.ufcg.jhonatanbds.entity;

import edu.ufcg.jhonatanbds.entity.category.*;
import edu.ufcg.jhonatanbds.entity.priority.HighPriority;
import edu.ufcg.jhonatanbds.entity.priority.LowPriority;
import edu.ufcg.jhonatanbds.entity.priority.MediumPriority;
import edu.ufcg.jhonatanbds.entity.priority.Priority;

/**
 * Created by Jhonatan on 03/02/2017.
 */
public class EntityFactory {

    public Category makeCategory(String category) {
        switch (category){
            case "TRABALHO":
                return new WorkCategory();
            case "LAZER":
                return new RecreationCategory();
            case "COMPROMISSO":
                return new CommitmentCategory();
            default:
                return new StandardCategory();
        }
    }

    public Priority makePriority(String priority) {
        switch (priority){
            case "ALTA":
                return new HighPriority();
            case "BAIXA":
                return new LowPriority();
            default:
                return new MediumPriority();
        }
    }
}

package fr.istic.aco.editor.core;

import fr.istic.aco.editor.commands.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Invoker class is responsible for managing commands and executing them.
 * It holds a map of commands identified by a string ID, and it interacts with the Engine to manipulate text.
 */
public class Invoker {
   private Map< String, Command> commands;
   private String text = "";
   private int beginIndex;
   private int endIndex;
   private Engine engine;

    public Invoker(Engine engine) {
       commands = new HashMap<>();
       this.engine = engine;
   }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addCommand(String id, Command command){
       commands.put(id, command);
   }

    /** calls the command corresponding to the id and execute it
     *
     * @param id
     */
   public void playCommand(String id){
       if (commands.containsKey(id)) {
           Command command = commands.get(id);
           command.execute();
       } else {
           throw new IllegalArgumentException("Unknown command. Type 'h' for help.");
       }
   }

}

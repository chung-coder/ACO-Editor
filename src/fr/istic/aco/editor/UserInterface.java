package fr.istic.aco.editor;
import fr.istic.aco.editor.commands.*;
import fr.istic.aco.editor.core.*;

import java.util.Scanner;

/**
 * Main class to run the editor
 */
public class UserInterface {
    public static void main(String[] args) {
        // Engine and Invoker setup
        Engine engine = new EngineImpl();
        Invoker invoker = new Invoker(engine);
        Recorder recorder = new Recorder();
        UndoManager undoManager = new UndoManager(engine);

        invoker.addCommand("i", new InsertTextCommand(engine, invoker, recorder, undoManager));
        invoker.addCommand("v", new PasteTextCommand(engine, recorder, undoManager));
        invoker.addCommand("x", new CutSelectedCommand(engine, recorder, undoManager));
        invoker.addCommand("d", new DeleteTextCommand(engine, recorder, undoManager));
        invoker.addCommand("m", new MoveSelection(engine, invoker, recorder, undoManager));
        invoker.addCommand("c", new CopyTextCommand(engine, recorder, undoManager));
        invoker.addCommand("s", new StartCommand(recorder));
        invoker.addCommand("e", new StopCommand(recorder));
        invoker.addCommand("r", new ReplayCommand(recorder));
        invoker.addCommand("1", new UndoCommand(undoManager));
        invoker.addCommand("2", new RedoCommand(undoManager));

        // Command-line interface
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the Java Mini Editor!");
        printHelp();

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine().toLowerCase();

            if (command.equals("i")) {
                System.out.println("Enter insert text: ");
                String inputText = scanner.nextLine();
                if(inputText == null || inputText.trim().isEmpty()){
                    System.out.println("Invalid input. No text entered. Please provide input.");
                    continue;
                } else {
                    invoker.setText(inputText);
                    invoker.playCommand(command);
                    displayState(engine, "Text inserted successfully!");
                }

            } else if (command.equals("m")) {
                System.out.println("Enter start index and end index: [start] [end] (e.g., 0 3):");
                String indicesInput = scanner.nextLine();
                String[] indices = indicesInput.split(" ");

                if (indices.length != 2) {
                    System.out.println("Invalid input. Please enter two indices separated by a space.");
                    continue;
                }

                try {
                    int newBeginIndex = Integer.parseInt(indices[0]);
                    int newEndIndex = Integer.parseInt(indices[1]);

                    if (newBeginIndex < 0 || newEndIndex > engine.getBufferContents().length() || newBeginIndex > newEndIndex) {
                        System.out.println("Invalid indices. Ensure they are within range and start <= end.");
                        continue;
                    }
                    invoker.setBeginIndex(newBeginIndex);
                    invoker.setEndIndex(newEndIndex);
                    invoker.playCommand("m");
                    displayState(engine, "Selection moved successfully!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric indices.");
                }
            } else if (command.equals("h")) {
                printHelp();
            } else if (command.equals("q")) {
                System.out.println("Are you sure you want to exit? (y/n)");
                String exitConfirmation = scanner.nextLine().toLowerCase();
                if (exitConfirmation.equals("y")) {
                    System.out.println("Goodbye!");
                    return;
                }
            } else {
                try {
                    invoker.playCommand(command);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if(invoker.getCommands().containsKey(command)){
                    displayState(engine, "");
                }
            }
        }
    }

    private static void printHelp() {
        System.out.println("i: Insert text");
        System.out.println("d: Delete selected text");
        System.out.println("c: Copy selected text");
        System.out.println("x: Cut selected text");
        System.out.println("p: Paste clipboard content");
        System.out.println("m: Move selection (e.g., specify indices)");
        System.out.println("s: Recording Start");
        System.out.println("e: Recording End");
        System.out.println("r: Replaying");
        System.out.println("1: Undo");
        System.out.println("2: Redo");
        System.out.println("h: Display this help menu");
        System.out.println("q: Quit the editor");
    }

    private static void displayState(Engine engine, String message) {
        System.out.println(message);
        System.out.println("------------------------------");
        if(message.equals("Selection moved successfully!")){
            System.out.println("Selected Text: " + engine.getBufferContents().substring(engine.getSelection().getBeginIndex(), engine.getSelection().getEndIndex()));
        }
        System.out.println("Current Buffer Contents: " + engine.getBufferContents());
        System.out.println("Current Clipboard Contents: " + engine.getClipboardContents());
        System.out.println("------------------------------");
    }
}

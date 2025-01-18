# Java Mini Text Editor
This project was completed by Ting-Chen Yen and DIAGNE Mame Absa as part of the course 2024 Object-Oriented Analysis and Design (ACO) in the M1 Informatique CNI program at the University of Rennes ISTIC under the supervision of Adrian Le Roch.

## Overview
We have provided the list of the editor's features, supported commands. 
For a comprehensive guide, please refer to [full API documentation](https://chung-coder.github.io/ACO-Editor/).
You can also explore [UML diagram](https://github.com/chung-coder/ACO-Editor/blob/main/UML.pdf) for an architectural overview.

PDF prints of the documentation can be found [here](https://github.com/chung-coder/ACO-Editor/blob/main/ACO%20REPORT.pdf).
## Features
- Modular Backend:
    - **Command Line Interface**: Interactive prompt-based interface for command execution.
    - **Command Execution**: Centralized execution of commands via the `Invoker`.
    - **Undo and Redo**: Reverse or reapply the last commands using `UndoManager`.
    - **Command Recording and Replay**: Record and replay sequences of commands using the `Recorder`.
    - **State Management**: Uses `Memento` to track and restore states of the editor and its commands.
- Teat
    - Over 50 Unit tests for all the features of the editor using JUnit 5.
    - Exception handling is tested for all the features of the editor.
    - Coverage Report Summary


  | Package                     | Class (%)   | Method (%)   | Branch (%)   | Line (%)   |
  	|-----------------------------|-------------|--------------|--------------|------------|
  | **All Classes**             | 90.9% (20/22) | 94.3% (100/106) | 51.7% (30/58) | 75.3% (247/328) |
  | `fr.istic.aco.editor.commands` | 100% (11/11) | 100% (34/34)  | 100% (2/2)   | 100% (91/91)  |
  | `fr.istic.aco.editor.core`  | 100% (6/6)   | 100% (49/49)  | 100% (28/28) | 100% (131/131) |
  | `fr.istic.aco.editor.memento` | 100% (3/3)   | 100% (17/17)  | -            | 100% (25/25)  |


## Supported Commands
| Command | Description|
|---------|----------------------------------------|
| `i` | Insert text at the cursor position.|
| `d` | Delete the selected text.|
| `c` | Copy the selected text to the clipboard.|
| `x` | Cut the selected text and save it to the clipboard.|
| `v` | Paste the clipboard contents at the cursor position.|
| `m` | Move the selection range by providing start and end indices.|
| `s` | Start recording a sequence of commands.|
| `e` | Stop recording the command sequence.|
| `r` | Replay the recorded commands.|
| `1` | Undo the most recent command.|
| `2` | Redo the last undone command.|
| `h` | Display the help menu.
| `q` | Quit the editor (confirmation required).


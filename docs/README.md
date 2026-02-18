# Spark User Guide

![Spark Screenshot](Ui.png)

Spark is a task management chatbot that helps you manage todos, deadlines, and events quickly using typed commands.
It supports a simple GUI where you type commands and Spark replies in the chat window.


---

## Command summary

| Command | Format |
|---|---|
| List tasks | `list` |
| Add todo | `todo <description>` |
| Add deadline | `deadline <description> /by <yyyy-MM-dd HHmm>` |
| Add event | `event <description> /from <start> /to <end>` |
| Mark task | `mark <taskNumber>` |
| Unmark task | `unmark <taskNumber>` |
| Delete task | `delete <taskNumber>` |
| Find tasks | `find <keyword>` |
| Exit | `bye` |

---


## Listing tasks

Shows all tasks currently stored.

Format: `list`

Example:
`list`

Expected outcome:
- Spark prints all tasks with indices.

---
## Adding a todo

Adds a todo task.

Format: `todo <description>`

Example:
`todo read book`

Expected outcome:
- A todo is added to the list and shown in the response.

---

## Adding a deadline

Adds a deadline task with a date-time.

Format:
`deadline <description> /by <yyyy-MM-dd HHmm>`

Example:
`deadline return book /by 2025-01-25 1200`

Expected outcome:
- A deadline task is added.
- Spark displays the deadline in a friendlier format (example: `Jan 25 2025, 12:00PM`).

---
## Adding an event

Adds an event task with a start and end.

Format:
`event <description> /from <start> /to <end>`

Example:
`event project meeting /from Mon 2pm /to Mon 4pm`

Expected outcome:
- An event is added with its from-to timing.

---

## Marking a task as done

Marks a task as completed.

Format:
`mark <taskNumber>`

Example:
`mark 1`

Expected outcome:
- Task 1 becomes done and shows `[X]`.

---

## Unmarking a task

Marks a task as not done.

Format:
`unmark <taskNumber>`

Example:
`unmark 1`

Expected outcome:
- Task 1 becomes not done and shows `[ ]`.

---

## Deleting a task

Deletes a task from the list.

Format:
`delete <taskNumber>`

Example:
`delete 2`

Expected outcome:
- Task 2 is removed and Spark shows the deleted task.

---

## Finding tasks

Finds tasks whose description contains a keyword (case-insensitive is recommended).

Format:
`find <keyword>`

Example:
`find book`

Expected outcome:
- Spark lists matching tasks only.

---

## Saving and loading

Spark automatically saves tasks after commands that change the list (add, mark, unmark, delete).
When Spark starts, it loads saved tasks from disk.

If loading fails (corrupted save file), Spark will start with an empty list and show one warning message.

---

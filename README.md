# NoteMe - A Note-Taking App

**NoteMe** is an Android-based note-taking application that allows users to create, view, edit, and delete notes. The app features a simple and intuitive interface with options to customize the background color of each note. Users can search through their notes and manage them easily using a built-in recycler view.

![image](https://github.com/user-attachments/assets/9bbf63e3-585b-4f33-8ea2-d01c056cebb4)
![image](https://github.com/user-attachments/assets/8fe202f5-94f7-4f43-9fdb-f708f03c5486)

---

## Features

- **Create, View, Edit, and Delete Notes**: Users can create new notes, view existing notes, edit, and delete them as needed.
- **Customizable Note Colors**: Each note can be assigned a background color from a set of predefined options (Yellow, Pink, Green).
- **Search Functionality**: Notes can be easily searched by title, subtitle, or description. The list of notes is dynamically updated with each character added in the search bar.
- **User-Friendly Interface**: The app uses a clean and straightforward design with clear input fields, validation and warnings and an easy-to-navigate toolbar.

---

## Installation

### Prerequisites

- Android Studio
- An Android device or emulator for testing

### Steps to Install

1. Clone the repository:
   ```bash
   git clone https://github.com/MaherMuhtadi/NoteMe.git
   ```

2. Open the project in Android Studio:
   - Launch Android Studio.
   - Click on **Open an existing project**.
   - Navigate to the folder where you cloned the repository and open it.

3. Build the project:
   - Click on **Build** in the top menu bar.
   - Select **Make Project** to build the app.

4. Run the project:
   - Connect an Android device or use an emulator.
   - Click on the green **Run** button in Android Studio to launch the app.

---

## Usage

1. **Create a New Note**:
   - Tap on the floating action button (FAB) located at the bottom-right corner of the screen.
   - Enter the title, subtitle, and description of the note.
   - Choose a background color from the color selection at the bottom of the screen.
   - Tap on **Save** to store the note.

2. **Edit an Existing Note**:
   - Tap on any note from the list to open it for editing.
   - Modify the title, subtitle, description, and color as needed.
   - Tap on **Save** to update the note.

3. **Delete a Note**:
   - Tap on the delete icon on the top right of any note in the list to remove the note from your collection.
   - A confirmation dialog will appear before the note is deleted.

4. **Search Notes**:
   - Use the search bar at the top of the main screen to search through your notes by title, subtitle, or description.

---

## File Structure

- **`MainActivity.java`**: The main screen of the app where all notes are listed.
- **`NoteEditor.java`**: The screen for creating and editing notes.
- **`NoteViewHolder.java`**: A ViewHolder for displaying individual notes in a RecyclerView.
- **`NotesAdapter.java`**: The adapter for binding data to the RecyclerView.
- **`Note.java`**: The model class representing a note.
- **`NoteColorChanger.java`**: A helper class for managing the background color selection of notes.
- **`DatabaseHelper.java`**: A singleton class for handling database operations such as adding, deleting, and updating notes.

---

## Libraries Used

- **AndroidX RecyclerView**: For displaying the list of notes.
- **Google Material Components**: For the floating action button and other UI elements.
- **SQLite**: For local storage of notes.

# Media Manager (Virtual File System Simulator)

**Media Manager** is a Java desktop application that simulates a file system. Developed using **Swing** for the graphical interface, it processes text-based file paths (e.g., `music/rock/metallica/song.mp3`), parses them, and constructs a dynamic hierarchical object structure in memory (Directories and Media Files).

The application offers real-time statistics, live editing capabilities, and ensures data persistence between sessions.

---

## 📋 Key Features

### 1. Dynamic Path Parsing
* Transforms raw text strings into complex object structures (`Directory` and `MediaFile` entities).
* **Automatic File Recognition:** Identifies file types based on extensions (MP3, WAV, JPG, PNG, MP4) and categorizes them.
* **Hierarchy Construction:** Automatically builds parent-child relationships for directories using the **Composite Pattern**.

### 2. Data Manipulation (CRUD & Editing)
* **Add:** Entering a new path instantly creates the necessary folder structure and file objects.
* **Delete:** Removing a path automatically cleans up the memory and updates deletion statistics.
* **Smart Auto-Save:** The editing area features a **Debouncing mechanism (400ms)**. It waits for the user to stop typing before automatically saving changes and rebuilding the file model, ensuring a smooth user experience without freezing the UI.

### 3. Advanced Statistics
* Tracks the number of files and directories created and deleted in real-time.
* Provides a detailed breakdown by file type (e.g., count of `.jpg` vs `.mp3` files).
* Dedicated "Statistics" window for detailed reporting.

### 4. Data Persistence
* Application state is automatically saved to text files upon exit.
* Data is reloaded automatically at the next startup.
    * Saved Paths: `src/resource/paths.txt`
    * Saved Stats: `src/resource/statistics.txt`

---

## 🛠️ Architecture & Technologies

The project follows the **MVC (Model-View-Controller)** principles and a **Layered Architecture**:

* **Language:** Java (JDK 8+)
* **GUI Framework:** Java Swing (JFrame, JPanel, JTextArea, Event Listeners)
* **Design Patterns:**
    * **Composite:** Used for the recursive structure of directories.
    * **Dependency Injection:** Services are injected into other components (e.g., `DirectoryService` into `SourceServiceImpl`).
    * **Observer:** Used for UI event handling (MouseListener, DocumentListener).

### Project Structure
```text
src/
├── exception/               # Custom Exceptions (FilesLoadingException, etc.)
├── gui/                     # User Interface (GUI.java)
├── model/                   # Data Entities (Directory, MediaFile, Statistic)
├── resource/                # Text files for persistence (paths.txt)
├── service/                 # Service Interfaces
└── service/implementation/  # Business Logic Implementations
````

## 🚀 How to Run

Prerequisites

- Java Development Kit (JDK): Version 8 or higher.
- An IDE (IntelliJ IDEA, Eclipse) or a terminal.

Installation & Execution
Clone the repository:
````
git clone [https://github.com/your-username/media-manager.git](https://github.com/your-username/media-manager.git)
````
Verify Resource Folder: Ensure the src/resource directory exists. It is recommended to have empty paths.txt and statistics.txt files inside it to ensure smooth initial loading.
Compile and Run (via Terminal): Navigate to the src folder:
````
javac Main.java
java Main
````
Alternatively, open the project in IntelliJ IDEA or Eclipse and run the Main class.

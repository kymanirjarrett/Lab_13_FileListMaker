import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class FileListMaker
{
    private static ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        char choice;
        do {
            displayList();
            displayMenu();
            choice = scanner.next().charAt(0);
            scanner.nextLine(); // Consume the newline character

            switch (Character.toUpperCase(choice)) {
                case 'A':
                    addItem();
                    break;
                case 'D':
                    deleteItem();
                    break;
                case 'V': // Changed from 'P' to 'V'
                    printList();
                    break;
                case 'O':
                    openList();
                    break;
                case 'S':
                    saveList();
                    break;
                case 'C':
                    clearList();
                    break;
                case 'Q':
                    quitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 'Q');
    }

    private static void displayList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Current List:");
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Menu Options:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("V - View the list");
        System.out.println("O - Open a list from disk");
        System.out.println("S - Save the list to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit");
        System.out.print("Enter your choice: ");
    }

    private static void addItem() {
        System.out.print("Enter an item to add to the list: ");
        String item = scanner.nextLine();
        if (!item.isEmpty()) {
            list.add(item);
            needsToBeSaved = true;
            System.out.println("Item added to the list.");
        } else {
            System.out.println("Item cannot be empty. Try again.");
        }
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }

        displayList();
        System.out.print("Enter the number of the item to delete (0 to cancel): ");
        int choice = scanner.nextInt();

        if (choice > 0 && choice <= list.size()) {
            list.remove(choice - 1);
            needsToBeSaved = true;
            System.out.println("Item deleted from the list.");
        } else if (choice != 0) {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void printList() {
        displayList();
    }

    private static void openList() {
        if (needsToBeSaved) {
            System.out.println("Warning: There is an unsaved list in memory.");
            System.out.print("Do you want to save the current list before opening a new one? (Y/N): ");
            char saveChoice = scanner.next().charAt(0);
            if (Character.toUpperCase(saveChoice) == 'Y') {
                saveList();
            }
        }

        list.clear();

        System.out.print("Enter the filename to open: ");
        String filename = scanner.next();
        scanner.nextLine(); // Consume the newline character

        try (BufferedReader reader = new BufferedReader(new FileReader(filename + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            System.out.println("List loaded from " + filename + ".txt");
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error reading the file. Please make sure it exists.");
        }
    }

    private static void saveList() {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }

        System.out.print("Enter the filename to save: ");
        String filename = scanner.next();
        scanner.nextLine(); // Consume the newline character

        try (PrintWriter writer = new PrintWriter(filename + ".txt")) {
            for (String item : list) {
                writer.println(item);
            }
            System.out.println("List saved to " + filename + ".txt");
            needsToBeSaved = false;
        } catch (FileNotFoundException e) {
            System.out.println("Error creating or writing to the file.");
        }
    }

    private static void clearList() {
        if (!list.isEmpty()) {
            System.out.print("Are you sure you want to clear the list? (Y/N): ");
            char clearChoice = scanner.next().charAt(0);
            if (Character.toUpperCase(clearChoice) == 'Y') {
                list.clear();
                needsToBeSaved = true;
                System.out.println("List cleared.");
            }
        } else {
            System.out.println("The list is already empty.");
        }
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            System.out.print("There is an unsaved list in memory. Do you want to save it before quitting? (Y/N): ");
            char saveChoice = scanner.next().charAt(0);
            if (Character.toUpperCase(saveChoice) == 'Y') {
                saveList();
            }
        }

        System.out.println("Exiting the program. Goodbye!");
        System.exit(0);
    }
}
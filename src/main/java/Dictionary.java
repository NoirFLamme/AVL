import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
    public static void main(String[] args) throws IOException {
        AVL dictionary = new AVL();

        System.out.println("1 - Load dictionary");
        System.out.println("2 - Get dictionary size");
        System.out.println("3 - Insert a word");
        System.out.println("4 - Search for a word");
        System.out.println("5 - Remove a word");
        System.out.println("6 - Batch look-up");
        System.out.println("7 - Batch remove");
        System.out.println("Please insert the number of the command required!");
        while (true)
        {

            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            String word;
            switch (option)
            {
                case "1":
                    BufferedReader reader = new BufferedReader(new FileReader("src/main/java/dictionary.txt"));
                    while ((word = reader.readLine()) != null) {
                         dictionary.insert(word);
                    }
                    reader.close();
                    System.out.println("Dictionary Loaded!");
                    break;
                case "2":
                    dictionary.size = 0;
                    dictionary.getSize(dictionary.root);
                    System.out.println("Size: " + dictionary.size);
                    break;
                case "3":
                    word = scanner.nextLine();
                    dictionary.insert(word);
                    System.out.println("Insertion done!");
                    break;
                case "4":
                    word = scanner.nextLine();
                    dictionary.search(dictionary.root, word);
                    break;
                case "5":
                    word = scanner.nextLine();
                    dictionary.root = dictionary.delete(dictionary.root, word);
                    System.out.println("Deletion done!");
                    break;
                case "6":
                    int found = 0;
                    int check = 0;
                    reader = new BufferedReader(new FileReader("src/main/java/queries.txt"));
                    while ((word = reader.readLine()) != null) {
                        check = dictionary.search(dictionary.root, word);
                        if (check == 1)
                            found += 1;
                    }
                    System.out.println("Entries found: " + found);
                    reader.close();
                    break;
                case "7":
                    reader = new BufferedReader(new FileReader("src/main/java/deletions.txt"));
                    while ((word = reader.readLine()) != null) {
                        dictionary.root = dictionary.delete(dictionary.root, word);
                    }
                    reader.close();
                    System.out.println("Batch deletion done!");
                    break;

                default:
                    System.out.println("Invalid");
                    break;

            }
        }
    }
}

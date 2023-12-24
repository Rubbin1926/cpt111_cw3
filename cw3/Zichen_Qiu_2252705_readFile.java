package cw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class Zichen_Qiu_2252705_readFile {

    private String title = "";
    private Map<String, Integer> map = new HashMap<>();
    private List<Integer> sortedInteger = new ArrayList<>();
    private List<String> sortedString = new ArrayList<>();


    private Map<String, Integer> dataStorage(String dataPath) throws IOException {
        String fullEntity = ""; // Stores the full entity name
        int sum = 0;

        File file = new File(dataPath);
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist."); // Throw FileNotFoundException if the file doesn't exist
        }

        try (Scanner input = new Scanner(file)) {
            int i = 0;
            while (input.hasNextLine()) {
                String name = input.nextLine();
                if (i >= 2) {
                    String[] parts = name.split(" "); // Split the line by spaces
                    try {
                        int num = Integer.parseInt(parts[parts.length - 1]); // Parse the last part as an integer
                        String nameAll = String.join(" ", Arrays.copyOf(parts, parts.length - 1)); // Join the remaining parts as the entity name
                        map.put(nameAll, num);
                        sum += num;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Error parsing number: " + e.getMessage()); // Throw IllegalArgumentException if there is an error parsing the number
                    }
                }
                if (i == 1) {
                    fullEntity = name; // Store the second line as the full entity name
                }
                if (i == 0) {
                    this.title = name; // Store the first line as the title
                }
                i++;
            }
        } catch (IOException ioe) {
            throw new IOException("Error reading file: " + ioe.getMessage()); // Throw IOException if there is an error reading the file
        }

        map.put(fullEntity, sum);
        return map;
    }

    private void execution(Map<String, Integer> map1) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(map1.entrySet());

        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            sortedString.add(entry.getKey());
            sortedInteger.add(entry.getValue());
        }
    }

    public Zichen_Qiu_2252705_readFile(String dataPath) {
        try {
            this.map = dataStorage(dataPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Integer> map1 = new HashMap<>(map);
        execution(map1);
    }

    public Map<String,Integer> getMap(){
        return this.map;
    }

    public String getTitle(){
        return this.title;
    }

    public List<Integer> getSortedInteger(){
        return this.sortedInteger;
    }

    public List<String> getSortedString(){
        return this.sortedString;
    }

    
}


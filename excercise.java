import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fence {

    public static void main(String[] args) throws IOException {

        //parsing a CSV file into Scanner class constructor
        Scanner sc = new Scanner(new File("F:/development/test.csv"));
        Scanner staticSc = sc;
        sc.useDelimiter(",");//sets the delimiter pattern
        int i = 0;
        HashMap<String,Double> mapFile0 = new HashMap<>();
        HashMap<String,String> mapFile1 = new HashMap<>();
        while (sc.hasNext())  //returns a boolean value
        {
//            System.out.println(sc.next());  //find and returns the next complete token from this scanner
            String line = sc.nextLine();
            String[] employee = line.split(",");
            if(mapFile0.containsKey(employee[2])){
                mapFile0.replace(employee[2],mapFile0.get(employee[2])+Double.parseDouble(employee[3]));
            }else{
                mapFile1.put(employee[2],employee[4]);
                mapFile0.put(employee[2], Double.parseDouble(employee[3]));
            }

            i++;
        }

        List<String[]> dataLines = new ArrayList<>();
        String[] file0Data = new String[2];

        for(String key: mapFile0.keySet()) {
            file0Data[0] = key;
            file0Data[1] = String.valueOf((mapFile0.get(key)/i));
            dataLines.add(file0Data);
            file0Data = new String[2];
            mapFile0.replace(key,(mapFile0.get(key)/i));
        }

        List<String[]> dataLines2 = new ArrayList<>();
        String[] file1Data = new String[2];

        for(String key: mapFile1.keySet()) {
            file1Data[0] = key;
            file1Data[1] = mapFile1.get(key);
            dataLines2.add(file1Data);
            file1Data = new String[2];
            mapFile1.replace(key,mapFile1.get(key));
        }

        givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines,0);
        givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines2,1);

        sc.close();  //closes the scanner

    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(Fence::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    public static void givenDataArray_whenConvertToCSV_thenOutputCreated(List<String[]> dataLines,int fileNum) throws IOException {
        File csvOutputFile = new File("F:/development/"+fileNum+"_input_example.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(Fence::convertToCSV)
                    .forEach(pw::println);
        }
        assert (csvOutputFile.exists());
    }
    }

package energiequiz.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for writing data to files.
 */

public class FileWriter {

  /**
   * Writes data to a CSV file.
   *
   * @param dataLines the data to write to the file
   * @throws FileNotFoundException if the file cannot be found
   */

  public static void writeScoresToCsv(List<String[]> dataLines, Path path)
      throws FileNotFoundException {
    File csvOutpufFile = new File(path.toString());
    try (PrintWriter pw = new PrintWriter(csvOutpufFile)) {
      dataLines.stream()
          .sorted(Comparator.comparingDouble(d -> -Double.parseDouble(d[1])))
          .map(d -> convertToCsv(d)).forEach(d -> pw.println(d));
    }
  }

  /**
   * Converts a String array to a CSV-formatted string.
   *
   * @param data the String array to convert
   * @return the CSV-formatted string
   */

  private static String convertToCsv(String[] data) {
    return Stream.of(data).collect(Collectors.joining(","));
  }
}

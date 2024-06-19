package energiequiz.io;


import static energiequiz.model.Constants.HEIGHT_DIFFERENCE;
import static energiequiz.model.Constants.JSONPATH;
import static energiequiz.model.Constants.SCORE_PATH;

import energiequiz.model.Asset;
import energiequiz.model.Device;
import energiequiz.model.LeaderboardEntry;
import energiequiz.model.Question;
import energiequiz.model.QuestionType;
import energiequiz.model.Room;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Utility class for reading data from files and resources.
 */

public class FileReader {

  public FileReader() {
  }

  /**
   * Reads questions from a JSON file.
   *
   * @param jsonPath The path of the JSON file
   * @return a list of Question objects parsed from the JSON file
   * @throws RuntimeException if an IO error occurs while reading the file
   */

  public List<Question> readQuestions(String jsonPath, Locale locale) {
    List<Question> result = new ArrayList<Question>();
    String jsonString;
    try {
      jsonString = new String(
          Files.readAllBytes(Paths.get(jsonPath + "questions_" + locale + ".json")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    JSONObject obj = new JSONObject(jsonString);
    JSONArray arr = obj.getJSONArray("questions");
    for (int i = 0; i < arr.length(); i++) {
      String answer1 = arr.getJSONObject(i).getString("answer1");
      String answer2 = arr.getJSONObject(i).getString("answer2");
      String answer3 = arr.getJSONObject(i).getString("answer3");
      List<String> answers = new ArrayList<>();
      answers.add(answer1);
      answers.add(answer2);
      answers.add(answer3);
      int type = arr.getJSONObject(i).getInt("type");
      QuestionType qtype = QuestionType.Choice;
      switch (type) {
        case 0 -> {
          qtype = QuestionType.Choice;
        }
        case 1 -> {
          qtype = QuestionType.Sorting;
        }
        default -> {

        }
      }
      String question = arr.getJSONObject(i).getString("question");
      String explanation1 = arr.getJSONObject(i).getString("explanation1");
      String explanation2 = arr.getJSONObject(i).getString("explanation2");
      String explanation3 = arr.getJSONObject(i).getString("explanation3");
      String explanation4 = arr.getJSONObject(i).getString("explanation");
      List<String> explanations = new ArrayList<>();
      explanations.add(explanation1);
      explanations.add(explanation2);
      explanations.add(explanation3);
      explanations.add(explanation4);
      String correctAnswer = arr.getJSONObject(i).getString("correct");
      Question q = new Question(question, explanations, answers, correctAnswer, qtype);
      result.add(q);
    }
    return result;
  }

  /**
   * Reads questions from the base path JSON file.
   *
   * @return a list of Question objects parsed from the JSON file
   * @throws RuntimeException if an IO error occurs while reading the file
   */

  public List<Question> readQuestions(Locale locale) {
    return readQuestions(JSONPATH, locale);
  }

  /**
   * Reads rooms from a JSON file.
   *
   * @param jsonPath The path of the JSON file
   * @return a list of Room objects parsed from the JSON file
   * @throws RuntimeException if an IO error occurs while reading the file
   */

  public List<Room> readRooms(String jsonPath) {
    List<Room> result = new ArrayList<Room>();
    String jsonString;
    try {
      jsonString = new String(Files.readAllBytes(Paths.get(jsonPath + "rooms.json")));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    JSONObject obj = new JSONObject(jsonString);
    JSONArray arr = obj.getJSONArray("rooms");
    for (int i = 0; i < arr.length(); i++) {
      String name = arr.getJSONObject(i).getString("name");
      String background = arr.getJSONObject(i).getString("background");
      JSONArray jsonAssets = arr.getJSONObject(i).getJSONArray("assets");
      Asset[] assets = new Asset[jsonAssets.length()];
      for (int j = 0; j < jsonAssets.length(); j++) {
        Asset asset;
        String filename = jsonAssets.getJSONObject(j).getString("filename");
        double posxDouble = (double) jsonAssets.getJSONObject(j).getInt("posX");
        var posFive = ((int) posxDouble) % 5;
        int posX = (int) posxDouble;
        double posyDouble = (double) jsonAssets.getJSONObject(j).getInt("posY");
        var posyFive = ((int) posyDouble + HEIGHT_DIFFERENCE) % 5;
        int posY = (int) posyDouble; //-posYFive
        int width = jsonAssets.getJSONObject(j).getInt("width");
        int height = jsonAssets.getJSONObject(j).getInt("height");
        if (jsonAssets.getJSONObject(j).getBoolean("device")) {
          double energy = jsonAssets.getJSONObject(j).getDouble("energy");
          asset = new Device(filename, posX, posY, width, height, energy);
        } else {
          asset = new Asset(filename, posX, posY, width, height);
        }

        assets[j] = asset;
      }
      Room r = new Room(name, background);
      r.setAssets(assets);
      result.add(r);
    }
    return result;
  }

  /**
   * Reads rooms from the base path JSON file.
   *
   * @return a list of Room objects parsed from the JSON file
   * @throws RuntimeException if an IO error occurs while reading the file
   */

  public List<Room> readRooms() {
    return readRooms(JSONPATH);
  }

  /**
   * Reads data from a CSV file containing scores.
   *
   * @param scorePath The path of the score file
   * @return a list of string arrays representing the CSV data
   * @throws IOException if an IO error occurs while reading the file
   */

  public static List<LeaderboardEntry> readScoreCsv(Path scorePath) throws IOException {
    List<LeaderboardEntry> dataLines = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(scorePath)) {
      if (reader == null) {
        throw new FileNotFoundException("This file does not exist");
      }
      String line;
      int index = 1;
      while ((line = reader.readLine()) != null) {
        String[] splitScore = line.split(",");
        dataLines.add(new LeaderboardEntry(String.valueOf(index), splitScore[0], splitScore[1]));
        index++;
      }
    }
    return dataLines;
  }

  /**
   * Reads data from the base path CSV file containing scores.
   *
   * @return a list of string arrays representing the CSV data
   * @throws IOException if an IO error occurs while reading the file
   */

  public static List<LeaderboardEntry> readScoreCsv() throws IOException {
    return readScoreCsv(SCORE_PATH);
  }

  /**
   * Reads data from a CSV file containing names.
   *
   * @param path the path to the CSV file
   * @return a list of strings representing the names read from the CSV file
   * @throws IOException if an IO error occurs while reading the file
   */

  public static List<String> readNamesCsv(Path path) throws IOException {
    List<String[]> dataLines = new ArrayList<>();
    List<String> data = new ArrayList<>();
    try (BufferedReader reader = Files.newBufferedReader(path)) {
      if (reader == null) {
        throw new FileNotFoundException("The file: " + path + " does not exist");
      }
      String line;
      while ((line = reader.readLine()) != null) {
        dataLines.add(line.split(","));
      }
      for (String[] strings : dataLines
      ) {
        for (String string : strings
        ) {
          data.add(string);
        }
      }
    }
    return data;
  }
}

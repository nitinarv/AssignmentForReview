package res.truecaller.sample.com.truecallersample.model;

import java.util.HashMap;

/**
 * Created by nitinraj.arvind on 7/6/2015.
 *
 * We may need to consider making our own hash map in order to do this right
 */
public class ResultsDetails {

    Character tenthCharacter = null;
    String allTenthCharacter = null;
    HashMap<String, String> wordCountMap = null;


    public ResultsDetails(HashMap<String, String> wordCountMap, String allTenthCharacter, Character tenthCharacter) {
        this.wordCountMap = wordCountMap;
        this.allTenthCharacter = allTenthCharacter;
        this.tenthCharacter = tenthCharacter;
    }


    public Character getTenthCharacter() {
        return tenthCharacter;
    }

    public void setTenthCharacter(Character tenthCharacter) {
        this.tenthCharacter = tenthCharacter;
    }

    public String getAllTenthCharacter() {
        return allTenthCharacter;
    }

    public void setAllTenthCharacter(String allTenthCharacter) {
        this.allTenthCharacter = allTenthCharacter;
    }

    public HashMap<String, String> getWordCountMap() {
        return wordCountMap;
    }

    public void setWordCountMap(HashMap<String, String> wordCountMap) {
        this.wordCountMap = wordCountMap;
    }


    @Override
    public String toString() {
        return "ResultsDetails{" +
                "tenthCharacter=" + tenthCharacter +
                ", allTenthCharacter='" + allTenthCharacter + '\'' +
                ", wordCountMap=" + wordCountMap +
                '}';
    }
}

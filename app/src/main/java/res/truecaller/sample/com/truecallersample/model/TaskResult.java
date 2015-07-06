package res.truecaller.sample.com.truecallersample.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by  nitinraj.arvind on 06/07/15.
 */
public class TaskResult {

    Character firstTenthElement;
    List<Character> allTenthElement;
    HashMap<String, Integer> UniqueWordCount;
    Date startTime;
    Date endTime;

    String firstTenthElementString;
    String allTenthElementString;
    String uniqueWordCountString;

    public String getFirstTenthElementString() {
        return firstTenthElementString;
    }

    public void setFirstTenthElementString(String firstTenthElementString) {
        this.firstTenthElementString = firstTenthElementString;
    }

    public String getAllTenthElementString() {
        return allTenthElementString;
    }

    public void setAllTenthElementString(String allTenthElementString) {
        this.allTenthElementString = allTenthElementString;
    }

    public String getUniqueWordCountString() {
        return uniqueWordCountString;
    }

    public void setUniqueWordCountString(String uniqueWordCountString) {
        this.uniqueWordCountString = uniqueWordCountString;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Character getFirstTenthElement() {
        return firstTenthElement;
    }

    public void setFirstTenthElement(Character firstTenthElement) {
        this.firstTenthElement = firstTenthElement;
    }

    public List<Character> getAllTenthElement() {
        return allTenthElement;
    }

    public void setAllTenthElement(List<Character> allTenthElement) {
        this.allTenthElement = allTenthElement;
    }

    public HashMap<String, Integer> getUniqueWordCount() {
        return UniqueWordCount;
    }

    public void setUniqueWordCount(HashMap<String, Integer> uniqueWordCount) {
        UniqueWordCount = uniqueWordCount;
    }
}

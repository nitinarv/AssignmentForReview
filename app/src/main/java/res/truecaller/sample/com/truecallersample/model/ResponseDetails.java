package res.truecaller.sample.com.truecallersample.model;

/**
 * Created by nitinraj.arvind on 7/6/2015.
 * Simple pojo to hold result
 */
public class ResponseDetails {

    int responseCode = -1;
    String reponseString = null;

    public ResponseDetails(int responseCode, String reponseString) {
        this.reponseString = reponseString;
        this.responseCode = responseCode;
    }

    public String getReponseString() {
        return reponseString;
    }

    public void setReponseString(String reponseString) {
        this.reponseString = reponseString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }


    @Override
    public String toString() {
        return "ResponseDetails{" +
                "responseCode=" + responseCode +
                ", reponseString='" + reponseString + '\'' +
                '}';
    }
}

package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faqs {
    private int id;
    private String question;
    private String answer;

    private Faqs(int id, String question, String answer){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public static Faqs getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String question = rs.getString("question");
        String answer = rs.getString("answer");

        return new Faqs(id, question, answer);
    }


    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}

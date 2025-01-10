package graph;
import java.util.Date;

public class Message {
    public final byte[] data;
    public final String asText;
    public final double asDouble;
    public final Date date;

    public Message(byte[] data) {
        this.data = data;
        this.asText = new String(data);
        this.asDouble = convertToDouble(this.asText);
        this.date = new Date();
    }

    public Message(String asText) {
        this(asText.getBytes());
    }

    public Message(double asDouble) {
        this(Double.toString(asDouble));
    }

    public double convertToDouble(String s){
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public


}

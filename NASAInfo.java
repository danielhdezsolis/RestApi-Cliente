import java.net.*;
import java.io.*;
import org.json.*;

public class NASAInfo {
    private String apiKey;

    public NASAInfo(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMarsImages() {
        try {
            URL url = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=" + apiKey);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            JSONObject jsonObject = new JSONObject(content.toString());
            JSONArray photosArray = jsonObject.getJSONArray("photos");

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < photosArray.length(); i++) {
                JSONObject photo = photosArray.getJSONObject(i);
                result.append("ID: ").append(photo.getInt("id")).append("\n"); // Cambiado a getInt()
                result.append("Sol: ").append(photo.getInt("sol")).append("\n"); // Cambiado a getInt()
                result.append("Camera: ").append(photo.getJSONObject("camera").getString("full_name")).append("\n");
                result.append("Earth Date: ").append(photo.getString("earth_date")).append("\n");
                result.append("Image URL: ").append(photo.getString("img_src")).append("\n\n");
            }

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener la información de la API";
        }
    }

    @Override
    public String toString() {
        return getMarsImages();
    }

    public static void main(String[] args) {
        String apiKey = "W4nq6jtC6hLyckMgjcLOoBvrBvEkSm7hI0vaCOFC";
        NASAInfo nasaInfo = new NASAInfo(apiKey);
        System.out.println(nasaInfo);
    }
}

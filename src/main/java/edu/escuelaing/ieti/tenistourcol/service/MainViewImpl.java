package edu.escuelaing.ieti.tenistourcol.service;

import edu.escuelaing.ieti.tenistourcol.exception.NotFoundException;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MainViewImpl implements MainView {

    @Override
    public Response getMatches(String date) {
        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String date = simpleDateFormat.format(new Date());
            URL url = new URL("https://tennis-live-data.p.rapidapi.com/matches-by-date/"+date);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setRequestProperty("x-rapidapi-key", "b4a4a43c03msh3cc76158bd46315p13bea5jsn1c4be9ab9d20");
            con.setRequestProperty("x-rapidapi-host", "tennis-live-data.p.rapidapi.com");
            con.setRequestProperty("useQueryString", "true");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
//                System.out.println(response.toString());

            return new SuccessResponse(new Date(), 200, "Se encontraron los partidos de hoy", response.toString());
        } catch (IOException e) {
            throw new NotFoundException("No se encontró ningún partido");
        }
    }
}

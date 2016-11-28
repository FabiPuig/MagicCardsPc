package sample;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 20464654j on 04/11/16.
 */
public class HttpUtils {

    public static String get( String dataUrl ) throws IOException{

        URL url = new URL(dataUrl);
        String resposta = null;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/4.76");


        try{
            InputStream in = new BufferedInputStream( connection.getInputStream() );
            resposta = llegirStream( in );
        }finally {
            connection.disconnect();
        }

        return resposta;

    }

    private static String llegirStream( InputStream in ) throws IOException {
        InputStreamReader isr = new InputStreamReader( in );
        BufferedReader br = new BufferedReader( isr);
        String linea;
        StringBuilder resposta = new StringBuilder();
        while( ( linea = br.readLine()) != null  ){
            resposta.append( linea );
            resposta.append("\r");
        }

        br.close();
        return resposta.toString();
    }

}

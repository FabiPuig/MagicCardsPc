package sample;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by 20464654j on 04/11/16.
 */
public class CridaApi {

    private final String url_base = "https://api.magicthegathering.io/v1/cards";

    /** Crida cartes sense filtres
     *
     * @param quantitat
     * @return
     */
    ArrayList<Carta> cartesGeneral( int quantitat ){

        String url = url_base + "?pageSize=" + quantitat;

        return crida( url );
    }

    /** Crida cartes filtrades per rarity
     *
     * @param quantitat
     * @param rarity
     * @return
     */
    ArrayList<Carta> cartesRarity( int quantitat, String rarity ){
        rarity = rarity.replace(" ", "_");

        String url = url_base + "?pageSize=" + quantitat + "&rarity=" + rarity;

        return crida( url );

    }

    ArrayList<Carta> cartesColor( int quantitat, String colors){

        String url = url_base + "?pageSize=" + quantitat + "&colors=" + colors;
        return crida( url );
    }

    ArrayList<Carta> cartesRarityColor( int quantitat, String rarity, String colors){
        rarity = rarity.replace(" ", "_");

        String url = url_base + "?pageSize=" + quantitat + "&rarity=" + rarity + "&colors=" + colors;
        System.out.println(url);
        return crida( url );

    }

    private ArrayList<Carta> crida( String url ){

        try{
            String respostaJSON = HttpUtils.get( url );
            return tractaJson( respostaJSON );
        }catch( IOException e ){
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Carta> tractaJson( String infoJSON ){

        ArrayList<Carta> cartes = new ArrayList<>();
        try{
            JSONObject data = new JSONObject(infoJSON);
            JSONArray cartesJSON = data.getJSONArray("cards");

            for (int i = 0; i < cartesJSON.length(); i++) {

                JSONObject cartaJSON = cartesJSON.getJSONObject( i );

                Carta c = new Carta();
                c.setName( cartaJSON.getString("name") );
                c.setType( cartaJSON.getString("type") );

                if( cartaJSON.has("colors") ){
                    String colores = "";
                    for (int j = 0; j < cartaJSON.getJSONArray("colors").length() ; j++) {
                        colores = colores + cartaJSON.getJSONArray("colors").getString( j ) + " ";
                    }
                    c.setColor( colores );
                }else{
                    c.setColor( null );
                }
                c.setRarity( cartaJSON.getString("rarity") );
                if( cartaJSON.has("imageUrl") ){
                    c.setImageUrl( cartaJSON.getString("imageUrl") );
                }else{
                    c.setImageUrl( null );
                }
                if( cartaJSON.has("text") ){
                    c.setText( cartaJSON.getString("text") );
                }else{
                    c.setText( null );
                }


                cartes.add( c );
            }
        }catch( JSONException e){
            e.printStackTrace();
        }

        return cartes;
    }
}

package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;


public class Controller{

    public TextField tvNameDetail;
    public TextField tvTypeDetail;
    public TextField tvRarityDetail;
    public ImageView ivDetail;
    public ListView lvColorsDetail;
    public Text tTextDetail;
    @FXML
    private ComboBox cmbRarity;
    @FXML
    private CheckBox cbAll;
    @FXML
    private CheckBox cbRed;
    @FXML
    private CheckBox cbBlue;
    @FXML
    private CheckBox cbGreen;
    @FXML
    private CheckBox cbWhite;
    @FXML
    private CheckBox cbBlack;
    @FXML
    private ListView<Carta> lvCartes;

    private String colors;

    public void initialize() {

        lvCartes.setCellFactory((list) -> {
            return new ListCell<Carta>() {
                private ImageView imageview= new ImageView();
                @Override
                protected void updateItem(Carta item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        Image img = new Image(item.getImageUrl(),50,50,true,true,true);
                        imageview.setImage(img);
                        imageview.setCache(true);
                        setText(item.getName() );
                        setGraphic(imageview);
                    }
                }
            };
        });

        cbAll.setSelected( true );

        ObservableList<String> rarity =
                FXCollections.observableArrayList(
                        "All",
                        "Common",
                        "Uncommon",
                        "Rare",
                        "Mythic Rare",
                        "Special",
                        "Basic Land"
                );
        cmbRarity.setItems( rarity );
        cmbRarity.setValue("All");

    }

    public void refresh(){

        if( !cbAll.isSelected() && !cbRed.isSelected() && !cbBlue.isSelected() && !cbGreen.isSelected() &&
                !cbWhite.isSelected() && !cbBlack.isSelected() ){
            cbAll.setSelected( true );
        }

        CridaApi api = new CridaApi();

        ArrayList<Carta> cartes = new ArrayList<>();

        String rarity = (String) cmbRarity.getValue();



        if( cbAll.isSelected() &&  rarity.equalsIgnoreCase("All") ) {
            cartes = api.cartesGeneral(100);
        }else if( cbAll.isSelected() && !rarity.equalsIgnoreCase("All") ){
            cartes = api.cartesRarity( 100, rarity );
        } else if( !cbAll.isSelected() && rarity.equalsIgnoreCase("All") ){
            System.out.println(colors);
            colors = getColors();
            cartes = api.cartesColor( 100, colors);
        }else if(!cbAll.isSelected() && !rarity.equalsIgnoreCase("All") ){
            colors = getColors();
            cartes = api.cartesRarityColor( 100, rarity, colors);
        }

        ObservableList<Carta> items =  FXCollections.observableArrayList (
                 cartes );

        lvCartes.setItems( items );

    }

    public void cbAllAct() {

        cbRed.setSelected( false );
        cbBlue.setSelected( false );
        cbGreen.setSelected( false );
        cbWhite.setSelected( false );
        cbBlack.setSelected( false );
    }

    public void cbRedAct() {
        desactivaCbAll();
    }

    public void cbBlueAct() {
        desactivaCbAll();
    }

    public void cbGreenAct() {
        desactivaCbAll();
    }

    public void cbWhiteAct() {
        desactivaCbAll();
    }

    public void cbBlackAct() {
        desactivaCbAll();
    }

    private void desactivaCbAll(){
        cbAll.setSelected( false );
    }

    private String getColors(){

        colors = "";

        if( cbRed.isSelected() ){
            colors = colors + "Red,";
        }
        if( cbBlue.isSelected() ){
            colors = colors + "Blue,";
        }
        if( cbGreen.isSelected() ){
            colors = colors + "Green,";
        }
        if( cbWhite.isSelected() ){
            colors = colors + "White,";
        }
        if( cbBlack.isSelected() ){
            colors = colors + "Black,";
        }

        return colors.substring(0, (colors.length() - 1) );
    }

    public void verDatos(MouseEvent mouseEvent) {
        Carta c = lvCartes.getSelectionModel().getSelectedItem();

        tvNameDetail.setText( c.getName() );
        tvTypeDetail.setText( c.getType() );
        tvRarityDetail.setText( c.getRarity() );
        Image image = new Image(c.getImageUrl(),600,600,true,true,true);
        ivDetail.setImage( image );


        if( c.getColor() != null){
            String[] colors = c.getColor().split(" ");
            int ROW_HEIGHT = 24;
            ObservableList<String> col = FXCollections.observableArrayList( colors );
            lvColorsDetail.setItems( col );
            lvColorsDetail.setPrefHeight( col.size() * ROW_HEIGHT + 2);
        }

        //tTextDetail.setText( c.getText() );

    }
}

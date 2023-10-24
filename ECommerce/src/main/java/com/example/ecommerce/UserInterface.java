package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {
    GridPane loginPage;
    HBox headerBar, footerBar;
    Button signInButton, cart, buyNow, addToCart, order;
    Button placeOrderBtn = new Button("Place order");
    Label welcomeLabel;
    VBox body, productPage;
    Customer loginCustomer;

    com.example.ecommerce.ProductList ProductList = new ProductList();

    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        // Set size of the panel
        root.setPrefSize(800,400); // (width, height)
        root.setTop(headerBar); // set headerBar to top

        // Body
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        // Add Product-Page to Body
        productPage  = ProductList.getAllProducts();
        body.getChildren().add(productPage);

        // Footer
        root.setBottom(footerBar);

        return root;
    }

    // Constructor
    UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }

    private void createLoginPage (){
        // Text-Label
        Text userIDLabel = new Text("User ID ");
        Text passwordLabel = new Text("password");


        /// Input- Field(Text-field)
        TextField userID = new TextField();
        userID.setPromptText("Enter your User-ID");
        PasswordField password = new PasswordField(); // Hide the password entered by the user
        password.setPromptText("Enter the password");

        Label message = new Label("");
        // Button
        Button loginButton = new Button("Login");

        loginPage = new GridPane();
        //loginPage.setStyle(" -fx-background-color:grey;");
        loginPage.setAlignment(Pos.CENTER);
        // Add paddings
        loginPage.setHgap(34.0);
        loginPage.setVgap(20.0);

        // add the fields to gridPane (column first and then row)
        loginPage.add(userIDLabel, 0, 0);
        loginPage.add(userID, 1, 0);
        loginPage.add(passwordLabel, 0, 1);
        loginPage.add(password, 1, 1);

        // Add button to the page
        loginPage.add(loginButton,1,2);
        loginPage.add(message, 0 ,2);

        // EventHandler for Login Button
        loginButton.setOnAction(actionEvent -> {
            String name = userID.getText();
            String pass = password.getText();

            // Login with the provided name and id
            LogIn login = new LogIn();
            loginCustomer = login.customerLogin(name,pass);

            if(loginCustomer != null){
                message.setText("Welcome "+loginCustomer.getName());
                welcomeLabel.setText("Welcome " + loginCustomer.getName());
                headerBar.getChildren().add(welcomeLabel);
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.getChildren().addAll(buyNow, addToCart);

                // Order Page
                /*order = new Button("Orders");
                headerBar.getChildren().add(order);

                order.setOnAction(actionEvent1 -> {
                    body.getChildren().clear();
                    productPage = ProductList.getUserOrders(loginCustomer.getId());
                    body.getChildren().add(productPage);
                });*/
            }
            else{
                message.setText("Invalid Credentials");
            }
        });
    }


    // ################################## HEADER_BAR ################################## //
    private void createHeaderBar() {
        // Graphical Image-Logo
        Button homeButton = new Button();
        Image image = new Image("E:\\Projects\\ECommerce\\src\\logo.png"); // path depends on Project Location
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);


        // SearchBar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Here...");
        searchBar.setPrefWidth(180);

        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLabel = new Label();

        cart = new Button("Cart");

        headerBar = new HBox(20);
        headerBar.setPadding(new Insets(10));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar, searchButton, signInButton, cart); //orderButton

        signInButton.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(loginPage);
            headerBar.getChildren().remove(signInButton);
            footerBar.getChildren().clear();
        });

        cart.setOnAction(actionEvent -> {
            body.getChildren().clear();
            VBox productPage = ProductList.getProductsInCart(itemsInCart);
            productPage.setAlignment(Pos.CENTER);
            productPage.setSpacing(10);
            productPage.getChildren().add(placeOrderBtn);
            body.getChildren().add(productPage);
            footerBar.setVisible(false);
        });

        placeOrderBtn.setOnAction(actionEvent -> {
            // list of products and a customer

            if(itemsInCart == null){
                // please select a prodUct first to place order
                showDialogue("please add some  product in card to place order");
                return;
            }

            if(loginCustomer == null){
                showDialogue("Please login to place order");
                return;
            }

            int count = Order.placeMultipleOrder(loginCustomer, itemsInCart);
            if(count > 0){
                showDialogue(count+" orders placed successfully !");
            }
            else{
                showDialogue("order failed");
            }
        });

        homeButton.setOnAction(actionEvent -> {
            body.getChildren().clear();
            body.getChildren().add(productPage);
            footerBar.setVisible(true);
            if(loginCustomer == null && !headerBar.getChildren().contains(signInButton)){
                headerBar.getChildren().add(signInButton);
            }
        });

        // Event Handler for Search Button
        searchButton.setOnAction(actionEvent -> {
            String item = searchBar.getText();

            if(item.isEmpty()){
                showDialogue("Please Enter a Key Word to Search");
                body.getChildren().clear();
                productPage = ProductList.getAllProducts();
                body.getChildren().add(productPage);
                System.out.println("Empty Search");
            }else {
                body.getChildren().clear();
                productPage = ProductList.searchedProduct(item);
                body.getChildren().add(productPage);
            }
        });

    }

    // ################################## FOOTER_BAR ################################## //
    private void createFooterBar() {

        buyNow = new Button("Buy Now");
        addToCart = new Button("Add To Cart");

        footerBar = new HBox(20);
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNow, addToCart);


        buyNow.setOnAction(actionEvent -> {
            Product Product = ProductList.getSelectedProduct();

            if(Product == null){
                // please select a product first to place order
                showDialogue("please select a product first to place order");
                return;
            }

            if(loginCustomer == null){
                showDialogue("Please login to place order");
                return;
            }

            boolean status = Order.placeOrder(loginCustomer, Product);
            if(status){
                showDialogue("order placed successfully");
            }
            else{
                showDialogue("order failed");
            }
        });

        addToCart.setOnAction(actionEvent -> {
            Product Product = ProductList.getSelectedProduct();

            if(Product == null){
                // please select a product first to place order
                showDialogue("please select a product first to add in cart");
                return;
            }

            itemsInCart.add(Product);
            showDialogue("Item added to cart Successfully !");

        });
    }

     // MESSAGE-BOX
    private void showDialogue (String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}

package com.jadventure.game;

import java.util.List;
import java.util.Map;
import java.util.Random;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.monsters.Monster;
import com.jadventure.game.monsters.MonsterFactory;
import com.jadventure.game.navigation.Direction;
import com.jadventure.game.navigation.ILocation;
import com.jadventure.game.navigation.LocationType;
import com.jadventure.game.repository.ItemRepository;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

public class GameController {
    
    // Reference to the main application.
    private JAdventure jAdventure;
    private Player player;

    public GameController() {
    }
    
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem back;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem github;
    @FXML
    private Text locationTitle;
    @FXML
    private Text locationDesc;
    @FXML
    private ProgressBar health;
    @FXML
    private Text hpString;
    @FXML
    private ImageView up;
    @FXML
    private ImageView down;
    @FXML
    private ImageView north;
    @FXML
    private ImageView west;
    @FXML
    private ImageView south;
    @FXML
    private ImageView east;
    @FXML
    private TilePane minimap;
    @FXML
    private AnchorPane northTile;
    @FXML
    private AnchorPane westTile;
    @FXML
    private AnchorPane eastTile;
    @FXML
    private AnchorPane southTile;
    @FXML
    private AnchorPane middleTile;
    @FXML
    private ImageView playerImage;
    @FXML
    private ImageView stairsUp;
    @FXML
    private ImageView stairsDown;
    @FXML
    private ImageView itemMiddle;
    @FXML
    private ImageView monsterMiddle;
    @FXML
    private ImageView npcMiddle;
    @FXML
    private ImageView itemNorth;
    @FXML
    private ImageView monsterNorth;
    @FXML
    private ImageView npcNorth;
    @FXML
    private ImageView itemWest;
    @FXML
    private ImageView monsterWest;
    @FXML
    private ImageView npcWest;
    @FXML
    private ImageView itemEast;
    @FXML
    private ImageView monsterEast;
    @FXML
    private ImageView npcEast;
    @FXML
    private ImageView itemSouth;
    @FXML
    private ImageView monsterSouth;
    @FXML
    private ImageView npcSouth;
    @FXML
    private GridPane backpack;
    
    @FXML
    void goUp() throws DeathException {
        command_g("up");
    }
    
    @FXML
    void goDown() throws DeathException {
        command_g("down");
    }

    @FXML
    void goEast() throws DeathException {
        command_g("east");
    }
    
    @FXML
    void goWest() throws DeathException {
        command_g("west");
    }

    @FXML
    void goNorth() throws DeathException {
        command_g("north");
    }

    @FXML
    void goSouth() throws DeathException {
        command_g("south");
    }
    
    public void command_g(String arg) throws DeathException {
        ILocation location = player.getLocation();
        try {
            Direction direction = Direction.valueOf(arg.toUpperCase());
            Map<Direction, ILocation> exits = location.getExits();
            if (exits.containsKey(direction)) {
                ILocation newLocation = exits.get(Direction.valueOf(arg.toUpperCase()));
                if (!newLocation.getLocationType().equals(LocationType.WALL)) {
                    player.setLocation(newLocation);
                    if ("test".equals(player.getName())) {
                        QueueProvider.offer(player.getLocation().getCoordinate().toString());
                    }
                    player.getLocation().print();
                    Random random = new Random();
                    if (player.getLocation().getMonsters().size() == 0) {
                        MonsterFactory monsterFactory = new MonsterFactory();
                        int upperBound = random.nextInt(player.getLocation().getDangerRating() + 1);
                        for (int i = 0; i < upperBound; i++) {
                            Monster monster = monsterFactory.generateMonster(player);
                            player.getLocation().addMonster(monster);
                        }
                    }
                    if (player.getLocation().getItems().size() == 0) {
                        int chance = random.nextInt(100);
                        if (chance < 60) {
                            addItemToLocation();
                        }
                    }
                    if (random.nextDouble() < 0.5) {
                        List<Monster> monsters = player.getLocation().getMonsters();
                        if (monsters.size() > 0) {
                            int posMonster = random.nextInt(monsters.size());
                            String monster = monsters.get(posMonster).monsterType;
                            QueueProvider.offer("A " + monster + " is attacking you!");
                            player.attack(monster);
                        }
                    }
                } else {
                    QueueProvider.offer("You cannot walk through walls.");
                }
            } else {
                QueueProvider.offer("The is no exit that way.");
            }
        } catch (IllegalArgumentException ex) {
            QueueProvider.offer("That direction doesn't exist");
        } catch (NullPointerException ex) {
            QueueProvider.offer("That direction doesn't exist");
        } finally {
            jAdventure.loadGame(player);
        }
    }
    
    private void addItemToLocation() {
        ItemRepository itemRepo = GameBeans.getItemRepository();
        if (player.getHealth() < player.getHealthMax()/3) {
            player.getLocation().addItem(itemRepo.getRandomFood(player.getLevel()));
        } else {
            Random rand = new Random();
            int startIndex = rand.nextInt(3);
            switch (startIndex) {
                case 0:
                    player.getLocation().addItem(itemRepo.getRandomWeapon(player.getLevel()));
                    break;
                case 1:
                    player.getLocation().addItem(itemRepo.getRandomFood(player.getLevel()));
                    break;
                case 2:
                    player.getLocation().addItem(itemRepo.getRandomArmour(player.getLevel()));
                    break;
                case 3:
                    player.getLocation().addItem(itemRepo.getRandomPotion(player.getLevel()));
                    break;
             }
        }
    }
    
    public void loadState() {
        loadCurrentLocation();
        loadHealthBar();
        loadMinimap();
        loadBackpack();
    }
    
    public void loadCurrentLocation() {
        locationTitle.setText(player.getLocation().getTitle());
        locationDesc.setText(player.getLocation().getDescription());
    }
    
    public void loadHealthBar() {
        int currentHealth = player.getHealth();
        int maxHealth = player.getHealthMax();
        double healthPercentage = (double) currentHealth / (double) maxHealth;
        health.setProgress(healthPercentage);
        hpString.setText(currentHealth + "/" + maxHealth);
    }
    
    public void loadMinimap() {
        loadPlayerImage();
        lookCurrentLocation();
        loadPossibleExits();
    }
    
    public void loadPlayerImage() {
        String characterClass = "";
        if (player.getCurrentCharacterType().equals("Recruit")) {
            characterClass = "hero_idle-l_300";
            playerImage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        } else if (player.getCurrentCharacterType().equals("Sewer Rat")) {
            characterClass = "Rat_Idle";
            playerImage.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        playerImage.setImage(new Image("/com/jadventure/game/images/" + characterClass + ".gif"));;
    }
    
    public void loadPossibleExits() {
        Map<Direction, ILocation> exits = player.getLocation().getExits();
        if ((exits.get(Direction.NORTH) != null) && !(exits.get(Direction.NORTH).getLocationType().equals(LocationType.WALL))) {
            northTile.setStyle("-fx-background-color: saddlebrown;");
        } else {
            northTile.setStyle("-fx-background-color: gray;");
        }
        if ((exits.get(Direction.SOUTH) != null) && !(exits.get(Direction.SOUTH).getLocationType().equals(LocationType.WALL))) {
            southTile.setStyle("-fx-background-color: saddlebrown;");
        } else {
            southTile.setStyle("-fx-background-color: gray;");
        }
        if ((exits.get(Direction.EAST) != null) && !(exits.get(Direction.EAST).getLocationType().equals(LocationType.WALL))) {
            eastTile.setStyle("-fx-background-color: saddlebrown;");
        } else {
            eastTile.setStyle("-fx-background-color: gray;");
        }
        if ((exits.get(Direction.WEST) != null) && !(exits.get(Direction.WEST).getLocationType().equals(LocationType.WALL))) {
            westTile.setStyle("-fx-background-color: saddlebrown;");
        } else {
            westTile.setStyle("-fx-background-color: gray;");
        }
        if (exits.get(Direction.UP) != null) {
            stairsUp.setVisible(true);
        } else {
            stairsUp.setVisible(false);
        }
        if (exits.get(Direction.DOWN) != null) {
            stairsDown.setVisible(true);
        } else {
            stairsDown.setVisible(false);
        }
        
        for (Map.Entry<Direction, ILocation> entry : exits.entrySet()) {
            Direction key = entry.getKey();
            ILocation value = entry.getValue();
            if (key.equals(Direction.NORTH)) {
                if (!value.getItems().isEmpty()) {
                    itemNorth.setVisible(true);
                } else {
                    itemNorth.setVisible(false);
                }
                if (!value.getMonsters().isEmpty()) {
                    monsterNorth.setVisible(true);
                } else {
                    monsterNorth.setVisible(false);
                }
                if (!value.getNpcs().isEmpty()) {
                    npcNorth.setVisible(true);
                } else {
                    npcNorth.setVisible(false);
                }
            } else if (key.equals(Direction.WEST)) {
                if (!value.getItems().isEmpty()) {
                    itemWest.setVisible(true);
                } else {
                    itemWest.setVisible(false);
                }
                if (!value.getMonsters().isEmpty()) {
                    monsterWest.setVisible(true);
                } else {
                    monsterWest.setVisible(false);
                }
                if (!value.getNpcs().isEmpty()) {
                    npcWest.setVisible(true);
                } else {
                    npcWest.setVisible(false);
                }
            } else if (key.equals(Direction.EAST)) {
                if (!value.getItems().isEmpty()) {
                    itemEast.setVisible(true);
                } else {
                    itemEast.setVisible(false);
                }
                if (!value.getMonsters().isEmpty()) {
                    monsterEast.setVisible(true);
                } else {
                    monsterEast.setVisible(false);
                }
                if (!value.getNpcs().isEmpty()) {
                    npcEast.setVisible(true);
                } else {
                    npcEast.setVisible(false);
                }
            } else if (key.equals(Direction.SOUTH)) {
                if (!value.getItems().isEmpty()) {
                    itemSouth.setVisible(true);
                } else {
                    itemSouth.setVisible(false);
                }
                if (!value.getMonsters().isEmpty()) {
                    monsterSouth.setVisible(true);
                } else {
                    monsterSouth.setVisible(false);
                }
                if (!value.getNpcs().isEmpty()) {
                    npcSouth.setVisible(true);
                } else {
                    npcSouth.setVisible(false);
                }
            }
        }
    }
    
    public void lookCurrentLocation() {
        if (!player.getLocation().getItems().isEmpty()) {
            itemMiddle.setVisible(true);
        } else {
            itemMiddle.setVisible(false);
        }
        if (!player.getLocation().getMonsters().isEmpty()) {
            monsterMiddle.setVisible(true);
        } else {
            monsterMiddle.setVisible(false);
        }
        if (!player.getLocation().getNpcs().isEmpty()) {
            npcMiddle.setVisible(true);
        } else {
            npcMiddle.setVisible(false);
        }
    }
    
    public void loadBackpack() {
        List<ItemStack> items = player.getStorage().getItemStack();
        if (!items.isEmpty()) {
            for (int i=0; i<items.size(); i++) {
                Text itemName = new Text(items.get(i).getItem().getName());
                int amount = items.get(i).getAmount();
                Text itemAmount = new Text(String.valueOf(amount));
                backpack.add(itemName, 0, i);
                backpack.add(itemAmount, 1, i);
                backpack.setHalignment(itemName, HPos.CENTER);
                backpack.setHalignment(itemAmount, HPos.CENTER);
            }
        }
    }
    
    @FXML
    public void saveGame() {
        player.save();
    }
    
    @FXML
    public void goToMainMenu() {
        jAdventure.loadMainMenu();
    }
    
    @FXML
    public void goToGithub() {
        // Goes to https://github.com/Progether/JAdventure
    }
    
    @FXML
    public void quitGame() throws Exception {
        jAdventure.getPrimaryStage().fireEvent(
                new WindowEvent(jAdventure.getPrimaryStage(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param jAdventure
     */
    public void setMainApp(JAdventure jAdventure) {
        this.jAdventure = jAdventure;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
        loadState();
    }
}

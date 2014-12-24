package com.jadventure.game;

import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.ItemStack;
import com.jadventure.game.items.Item;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.repository.ItemRepository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Trading {
    int npcGold;
    int playerGold;
    NPC npc;
    Player player;
    ItemRepository itemRepo = GameBeans.getItemRepository();

    public Trading(NPC npc, Player player) {
        npcGold = npc.getGold();
        playerGold = player.getGold();
        this.npc = npc;
        this.player = player;
    }

    public void trade(boolean buy, boolean sell) {
        List<MenuItem> tradeList = new ArrayList<>();
        String buyCommand = "Buy from " + npc.getName();
        String sellCommand = "Sell to " + npc.getName();
        if (buy) {
            tradeList.add(new MenuItem(buyCommand, null));
        }
        if (sell) {
            tradeList.add(new MenuItem(sellCommand, null));
        }
        tradeList.add(new MenuItem("exit", null));
        Menus tradeMenu = new Menus();
        MenuItem response = tradeMenu.displayMenu(tradeList);
        String command = response.getCommand();
        if (command.equals(buyCommand) && buy) {
            playerBuy();
        } else if (command.equals(sellCommand) && sell) {
            playerSell();
        } else if (command.equals("exit")) {
            return;
        }
        trade(buy, sell);
    }
    
    public void playerBuy() {
        QueueProvider.offer(npc.getName() + "'s items:\n");
        QueueProvider.offer(npc.getStorage().displayWithValue());

        QueueProvider.offer("You have " + playerGold + " gold coins.\nWhat do you want to buy?");
        String itemName = QueueProvider.take();

        if (itemName.equals("exit")) {
            return;
        }
        
        List<ItemStack> itemList = npc.getStorage().getItems();
        Map<String, String> itemIds = new HashMap<>();
        Map<String, Integer> itemValues = new HashMap<>();
        Map<String, Item> itemIdtoItem = new HashMap<>();

        for (ItemStack itemStack : itemList) {
            Item item = itemStack.getItem();
            String name = item.getName();
            String id = item.getId();
            int value = item.getProperties().get("value");
            itemIds.put(name, id);
            itemValues.put(id, value);
            itemIdtoItem.put(id, item);
        }

        if (itemIds.containsKey(itemName)) {
            int itemValue = itemValues.get(itemIds.get(itemName));
            Item item = itemIdtoItem.get(itemIds.get(itemName));
            if (playerGold < itemValue) {
                QueueProvider.offer("You do not have enough money!");
                return;
            }
            player.addItemToStorage(item);
            player.setGold(playerGold - itemValue);
            npc.setGold(npcGold + itemValue);
            npc.removeItemFromStorage(item);
            playerGold = playerGold - itemValue;
            npcGold = npcGold + itemValue;
            QueueProvider.offer("You have bought a " + item.getName() + " for " + itemValue + " gold coins.");
            QueueProvider.offer("You now have " + playerGold + " gold coins remaining.");
        } else {
            QueueProvider.offer("Either this item doesn't exist or this character does not own that item");
        }
    }

    public void playerSell() {
        QueueProvider.offer(player.getName() + "'s items:\n");
        QueueProvider.offer(player.getStorage().displayWithValue());
        
        QueueProvider.offer("You have " + playerGold + " gold coins.\nWhat do you want to sell?");
        String itemName = QueueProvider.take();

        if (itemName.equals("exit")) {
            return;
        }
        
        List<ItemStack> itemList = npc.getStorage().getItems();
        Map<String, String> itemIds = new HashMap<>();
        Map<String, Integer> itemValues = new HashMap<>();
        Map<String, Item> itemIdtoItem = new HashMap<>();

        for (ItemStack itemStack : itemList) {
            Item item = itemStack.getItem();
            String name = item.getName();
            String id = item.getId();
            int value = item.getProperties().get("value");
            itemIds.put(name, id);
            itemValues.put(id, value);
            itemIdtoItem.put(id, item);
        }

        if (itemIds.containsKey(itemName)) {
            int itemValue = itemValues.get(itemIds.get(itemName));
            Item item = itemIdtoItem.get(itemIds.get(itemName));
            npc.addItemToStorage(item);
            npc.setGold(playerGold - itemValue);
            player.setGold(npcGold + itemValue);
            player.removeItemFromStorage(item);
            playerGold = playerGold + itemValue;
            npcGold = npcGold - itemValue;
            QueueProvider.offer("You have sold a " + item.getName() + " for " + itemValue + " gold coins.");
            QueueProvider.offer("You now have " + playerGold + " gold coins remaining.");
        } else {
            QueueProvider.offer("Either this item doesn't exist or this character does not own that item");
        }
    }
}

package com.jadventure.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jadventure.game.entities.Entity;
import com.jadventure.game.entities.NPC;
import com.jadventure.game.entities.Player;
import com.jadventure.game.items.Item;
import com.jadventure.game.menus.MenuItem;
import com.jadventure.game.menus.Menus;
import com.jadventure.game.repository.ItemRepository;

public class Trading {
    NPC npc;
    Player player;
    ItemRepository itemRepo = GameBeans.getItemRepository();

    public Trading(NPC npc, Player player) {
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
        tradeList.add(new MenuItem("Exit", null));
        Menus tradeMenu = new Menus();
        MenuItem response = tradeMenu.displayMenu(tradeList);
        String command = response.getCommand();
        if (command.equals(buyCommand) && buy) {
            playerBuy();
        } else if (command.equals(sellCommand) && sell) {
            playerSell();
        } else if (command.equals("Exit")) {
            return;
        }
        trade(buy, sell);
    }
    
    public void playerBuy() {
        QueueProvider.offer(npc.getName() + "'s items:\t" + npc.getName()  + "'s gold:" + npc.getGold() + "\n");
        QueueProvider.offer(npc.getStorage().displayWithValue(0, 0));

        QueueProvider.offer("You have " + player.getGold() + " gold coins.\nWhat do you want to buy?");
        String itemName = QueueProvider.take();

        if ("exit".equals(itemName) || "back".equals(itemName)) {
            return;
        }

        Item item = tradeItem(npc, player, itemName);
        if (item != null) {
            if (item != itemRepo.getItem("empty")) {
                QueueProvider.offer("You have bought a " + item.getName() + " for " + item.getProperties().get("value") + " gold coins.");
                QueueProvider.offer("You now have " + player.getGold() + " gold coins remaining.");
            }
            else {
                QueueProvider.offer("You do not have enough money!");
            }
        } else {
            QueueProvider.offer("Either this item doesn't exist or this character does not own that item");
        }
    }

    public void playerSell() {
        QueueProvider.offer(player.getName() + "'s items:\t" + npc.getName()  + "'s gold:" + npc.getGold() + "\n");
        QueueProvider.offer(player.getStorage().displayWithValue(player.getLuck(), player.getIntelligence()));
        
        QueueProvider.offer("You have " + player.getGold() + " gold coins.\nWhat do you want to sell?");
        String itemName = QueueProvider.take();
 
        if ("exit".equals(itemName) || "back".equals(itemName)) {
            return;
        }
        int goldBefore = player.getGold();
        Item item = tradeItem(player, npc, itemName);
        if (item != null) {
            if (item != itemRepo.getItem("empty")) {
                QueueProvider.offer("You have sold a " + item.getName() + " for " + (player.getGold() - goldBefore) + " gold coins.");
                QueueProvider.offer("You now have " + player.getGold() + " gold coins remaining.");
            }
            else {
                QueueProvider.offer(npc.getName() + " does not have enough money!");
            }
        } else {
            QueueProvider.offer("Either this item doesn't exist or this character does not own that item");
        }
    }

    private Item tradeItem(Entity seller, Entity buyer, String itemName) {
        List<Item> itemList = seller.getStorage().getItems();
        Map<String, String> itemIds = new HashMap<>();
        Map<String, Integer> itemValues = new HashMap<>();
        Map<String, Item> itemIdtoItem = new HashMap<>();

        for (Item item : itemList) {
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
            
            if(seller instanceof Player){
                itemValue = (int)((0.5+0.02*(seller.getIntelligence()+seller.getLuck()))*itemValue);
            }
            if (buyer.getGold() < itemValue) {
                return itemRepo.getItem("empty");
            }
            buyer.addItemToStorage(item);
            buyer.setGold(buyer.getGold() - itemValue);

            seller.setGold(seller.getGold() + itemValue);
            seller.removeItemFromStorage(item);
            return item;
        } else {
            return null;
        }
    }
}

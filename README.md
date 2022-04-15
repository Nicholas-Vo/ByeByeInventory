# ByeByeInventory
A Minecraft plugin which deletes player inventories upon death.

Admins can configure this to occur across specific worlds, and can exclude worn armor and the hotbar from deletion.
# Permissions

- byebyeinventory.lose - Any player given this permission will lose their inventory upon death!
- byebyeinventory.admin - Allows a player to use the /byebyeinventory and /byebyeinventory reload commands
- byebyeinventory.exempt - Exempt a player from losing their inventory on death

# Configuration

```
#### Dimensions
##
# To disable the plugin in a specific dimension, change the below settings.

enabled-in-overworld: true
enabled-in-nether: true
enabled-in-end: true

### Exclude the player hotbar
##
## Setting this to true will exclude any items in the player hotbar at the time of death
exclude-hotbar: false

### Exclude player armor
##
## Setting this to true will exclude any items stored in the armor inventory slots.
## This includes helmet, chestplate, leggings, boot, and off-hand inventory slots.
exclude-armor: false

### Excluded items
##
## Items in this list will not be removed upon death.
exclude-items: false
excluded-items-list:
  - COMMAND_BLOCK
  - EMERALD_ORE
  - DIAMOND

#### Log deaths to the console
##
## Will alert the console when a player has had their inventory voided by the plugin.
## The plugin will also print out how many items the player has lost.

log-death-to-console: true

### Broadcast message to server when a player's items are voided
##
## The message displayed to a player when their items are voided.
## This message will not display if the player has an empty inventory.

display-message-to-server: false
message-to-server: '&8[!] &c%playername% &rdied in the %dimension% and lost %count% item(s)!'

### Broadcast message to server when a player's items are voided
##
## The message displayed to a player when their items are voided.
## This message will not display if the player has an empty inventory.

display-message-to-player: true
message-to-player: 'You have died, and your inventory of &c%count% &ritem(s) have been destroyed.'

### Available keywords:
##
## %playername% -> Replaced with the player's username
## %newline% -> Creates a new line on the screen
## %count% -> Replaced with how many items were lost upon death
## %dimension% -> Replaced with the dimension (overworld, the end, or the nether)
```

# License

This plugin is licensed under the MIT License. For more information, please refer to LICENSE.

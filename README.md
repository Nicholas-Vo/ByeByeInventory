# ByeByeInventory [![Version](https://img.shields.io/badge/version-1.0.1-blue)](https://www.spigotmc.org/resources/picturelogin-continued.101216/)
A Minecraft plugin for Spigot servers which allows for deletion of player inventories upon death.

Admins can configure this to occur across specific worlds and can exclude worn armor, the off-hand slot, and the hotbar from deletion.

Obtaining the plugin
---
You can download the plugin over on Spigot: https://www.spigotmc.org/resources/byebyeinventory.101405/

# Permissions

- byebyeinventory.lose - Any player given this permission will lose their inventory upon death!
- byebyeinventory.admin - Allows a player to use the /byebyeinventory and /byebyeinventory reload commands
- byebyeinventory.exempt - Exempt a player from losing their inventory on death

By default, server operators must negate the byebyeinventory.exempt permission if they wish for the plugin to take effect, and players must be given the byebyeinventory.lose permission or the plugin will do nothing.

# Configuration

```
# ByeByeInventory by _NickV
# For support, please visit my Discord server: https://discord.gg/fGzb73sPmV

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
## This includes helmet, chestplate, leggings, and boot inventory slots.
exclude-armor: false

### Exclude player off-hand slot
##
## Setting this to true will exclude any item(s) stored in the off-hand inventory slot.
exclude-offhand: false

### Excluded item list
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
## This message will not display if the player has an empty inventory.

display-message-to-server: false

### The message to send
message-to-server: '&8[!] &c%playername% &rdied in the %dimension% and lost %count% item(s)!'

### Display message to the player when their items are voided
##

display-message-to-player: true

## The message displayed to a player when their items are voided.
## This message will not display if the player has an empty inventory
message-to-player: 'You have died, and your inventory of &c%count% &ritem(s) have been destroyed.'

### Available keywords:
##
## %playername% -> Replaced with the player's username
## %newline% -> Creates a new line on the screen
## %count% -> Replaced with how many items were lost upon death
## %dimension% -> Replaced with the dimension (overworld, end, nether, or the name of a custom dimension)
```

Support [![Discord](https://img.shields.io/badge/discord-Nick's%20Place-orange)](https://discord.gg/fGzb73sPmV)
---
You can reach out to me on the Spigot forums or here on GitHub, although the best way to reach me is on Discord.

License [![License](https://img.shields.io/github/license/Nicholas-Vo/PictureLogin-Continued)](https://github.com/Nicholas-Vo/ByeByeInventory/blob/master/LICENSE)
---
This plugin is licensed under the MIT License. For more information please refer to LICENSE.

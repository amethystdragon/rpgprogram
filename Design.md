# Design #

Communication pattern is implemented using the medator pattern where the clients talk to the server and the server updates the clients. Each message is sent as a command to the server by implementing the command pattern.

Map is implemented using the medator pattern as the DM or player updates the map ro a charecters position the changes can be oked by the DM (player moves only) and then sent to the other clients as a status update.

## Sample GUI Mockups ##

https://docs.google.com/drawings/d/1L_RCujy0b_NK7k2IXAbLbvptD3izLkpeZPIdao8hW1Q/edit
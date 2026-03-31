package pythonrunner.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import snekboxwrapper.SnekboxClient;
import org.bukkit.entity.Player;
import org.bukkit.Material;

public class CommandGraphPy implements CommandExecutor {

    private final SnekboxClient snekboxClient;
    private final String host;

    public CommandGraphPy(String URI){
        this.host = URI;
        this.snekboxClient = new SnekboxClient(URI);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player player) {
            Location origin = player.getLocation().getBlock().getLocation();
            origin.setPitch(0);
            origin.setYaw(0);
            World world = player.getWorld();

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta() == null || !(item.getItemMeta() instanceof BookMeta)) {
                player.sendMessage(ChatColor.GOLD + "You should be holding a book while running this command.");
                return true;
            }
            BookMeta bookMeta = (BookMeta) item.getItemMeta();
            String bookContents = String.join("", bookMeta.getPages());
            String output = snekboxClient.eval(bookContents);
            // Bukkit.broadcastMessage("Command output:\n" + output);
            output.lines().forEach(line -> {
                String[] parsedstr = line.split(",",3);
                if (parsedstr.length == 3) {
                    int x, y, z;
                    x = Integer.parseInt(parsedstr[0]);
                    y = Integer.parseInt(parsedstr[1]);
                    z = Integer.parseInt(parsedstr[2]);
                    // Bukkit.broadcastMessage(String.format("x = %d, y = %d, z = %d\n", x,y,z));
                    Location abspos = new Location(world, origin.getX()+x, origin.getY()+y, origin.getZ()+z);
                    // Bukkit.broadcastMessage(abspos.toString());

                    BlockData block = Material.WHITE_CONCRETE.createBlockData();
                    world.setBlockData(abspos, block);
                    // player.sendMessage("placed block at "+ abspos.toString());
                } else {
                    player.sendMessage("invalid coordinates " );
                }
            });
        }
        return true;
    }
}

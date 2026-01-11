package pythonrunner.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;

import snekboxwrapper.SnekboxClient;

public class CommandRunPy implements CommandExecutor {

  private final SnekboxClient snekboxClient;
  private final String host;
  public CommandRunPy(String URI){
    this.host = URI;
    this.snekboxClient = new SnekboxClient(URI);
  }


  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

    if (commandSender instanceof Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      if (item.getItemMeta() == null || !(item.getItemMeta() instanceof BookMeta)) {
        player.sendMessage(ChatColor.RED + "You should be holding a book while running this command.");
        return true;
      }
      BookMeta bookMeta = (BookMeta) item.getItemMeta();
      String bookContents = String.join("", bookMeta.getPages());
      // Bukkit.broadcastMessage(host +": "+ bookContents);
      String output = snekboxClient.eval(bookContents);
      Bukkit.broadcastMessage(ChatColor.BOLD + "" + ChatColor.RED + "OUTPUT: \n" + ChatColor.RESET + output);
    }
    return true;
  }
}

package me.soki.bunkers.Teams.Command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Game.GameMGR.isStarted;
import static me.soki.bunkers.Teams.TeamMGR.sendHelp;

public class TeamCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 0){
            sendHelp(p);

        }else {
            if (args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("hq")) {
                    HomeArgument.Home(sender, command, label, args);
            } else {
                if (!isStarted) {
                    switch (args[0]) {
                        case "invite":
                            InviteArgument.InviteArgument(sender, command, label, args);
                            break;
                        case "join":
                            JoinArgument.JoinArgument(sender, command, label, args);
                            break;
                        case "kick":
                            KickArgument.KickArgument(sender, command, label, args);
                            break;
                        case "leave":
                            LeaveArgument.LeaveArgument(sender, command, label, args);
                            break;
                        case "leader":
                            LeaderArgument.LeaderArgument(sender, command, label, args);
                            break;
                        case "forceadd":
                            ForceAddCommand.ForceAddCommand(sender, command, label, args);
                            break;
                        default:
                            sendHelp(p);
                            break;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "The game has already started.");
                }
            }
        }
        return true;
    }
}

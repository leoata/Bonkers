package me.soki.bunkers.Arena;

import me.soki.bunkers.Arena.Arguments.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.soki.bunkers.Arena.ArenaMGR.sendHelp;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0){
            sendHelp(p);
        }else{
            if (p.isOp()) {
                switch (args[0]) {
                    case "pos1":
                        Pos1Argument.Pos1(sender, command, label, args);
                        break;
                    case "pos2":
                        Pos2Argument.Pos2(sender, command, label, args);
                        break;
                    case "setcenter":
                        SetCenterArgument.SetCenter(sender, command, label, args);
                        break;
                    case "setspawn":
                        SetSpawnArgument.SetSpawn(sender, command, label, args);
                        break;
                    case "setclaimpos1":
                        SetClaimPos1Argument.SetClaimPos1(sender, command, label, args);
                        break;
                    case "setclaimpos2":
                        SetClaimPos2Argument.SetClaimPos2(sender, command, label, args);
                        break;
                    case "mineylevel":
                        MineYLevelArgument.MineYLevelArgument(sender, command, label, args);
                        break;
                    case "kothpos1":
                        KothPos1Argument.KothPos1(sender, command, label, args);
                        break;
                    case "kothpos2":
                        KothPos2Argument.KothPos2(sender, command, label, args);
                        break;
                    case "capzonepos1":
                        CapzonePos1Argument.CapzonePos1(sender, command, label, args);
                        break;
                    case "capzonepos2":
                        CapzonePos2Argument.CapzonePos2(sender, command, label, args);
                        break;
                    case "setrespawnpos1":
                        SetRespawnPos1Argument.SetRespawnPos1Argument(sender, command, label, args);
                        break;
                    case "setrespawnpos2":
                        SetRespawnPos2Argument.SetRespawnPos2Argument(sender, command, label, args);
                        break;


                }
            }else{
                p.sendMessage(ChatColor.RED + "No permission.");
            }
        }
        return true;
    }
}

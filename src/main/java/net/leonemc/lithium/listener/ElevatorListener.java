package net.leonemc.lithium.listener;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ElevatorListener implements Listener {
    private final String signTitle;

    public ElevatorListener() {
        this.signTitle = ChatColor.BLUE + ChatColor.BOLD.toString() + "[Elevator]";
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSignUpdate(final SignChangeEvent e) {
        boolean up;
        if (StringUtils.containsIgnoreCase(e.getLine(0), "Elevator")) {
            if (StringUtils.containsIgnoreCase(e.getLine(1), "Up")) {
                up = true;
            }
            else {
                if (!StringUtils.containsIgnoreCase(e.getLine(1), "Down")) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Incorrect usage: Up or Down");
                    return;
                }
                up = false;
            }
            e.setLine(0, this.signTitle);
            e.setLine(1, up ? "Up" : "Down");
            e.setLine(2, "");
            e.setLine(3, "");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            Block block = e.getClickedBlock();
            if (block.getState() instanceof Sign) {
                Sign sign = (Sign)block.getState();
                String[] lines = sign.getLines();
                if (lines[0].equals(this.signTitle)) {
                    boolean up;
                    if (lines[1].equalsIgnoreCase("Up")) {
                        up = true;
                    }
                    else {
                        if (!lines[1].equalsIgnoreCase("Down")) {
                            return;
                        }
                        up = false;
                    }
                    this.signClick(e.getPlayer(), sign.getLocation(), up);
                }
            }
        }
    }

    public boolean signClick(final Player player, final Location signLocation, final boolean up) {
        Block block = signLocation.getBlock();
        do {
            block = block.getRelative(up ? BlockFace.UP : BlockFace.DOWN);
            if (block.getY() > block.getWorld().getMaxHeight() || block.getY() <= 1) {
                player.sendMessage(ChatColor.RED + "Could not find a sign " + (up ? "above" : "below") + " to teleport you to.");
                return false;
            }
        } while (!this.isSign(block));
        boolean underSafe = this.isSafe(block.getRelative(BlockFace.DOWN));
        boolean overSafe = this.isSafe(block.getRelative(BlockFace.UP));
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            final Location location = player.getLocation().clone();
            location.setX(block.getX() + 0.5);
            location.setY((block.getY() + (underSafe ? -1 : 0)));
            location.setZ(block.getZ() + 0.5);
            location.setPitch(0.0f);
            player.teleport(location);
            return true;
        }
        if (!underSafe && !overSafe) {
            player.sendMessage(ChatColor.RED + "There is a block blocking the sign " + (up ? "above" : "below") + "!");
            return false;
        }
        Location location2 = player.getLocation().clone();
        location2.setX(block.getX() + 0.5);
        location2.setY(block.getY() + (underSafe ? -1 : 0));
        location2.setZ(block.getZ() + 0.5);
        location2.setPitch(0.0f);
        player.teleport(location2);
        return true;
    }

    private boolean isSign(final Block block) {
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            String[] lines = sign.getLines();
            return lines[0].equals(this.signTitle) && (lines[1].equalsIgnoreCase("Up") || lines[1].equalsIgnoreCase("Down"));
        }
        return false;
    }

    private boolean isSafe(final Block block) {
        return block != null && !block.getType().isSolid() && block.getType() != Material.GLASS && block.getType() != Material.STAINED_GLASS;
    }
}
package net.leonemc.lithium;

import com.google.common.reflect.ClassPath;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import litebans.api.Events;
import lombok.Getter;
import net.leonemc.api.command.CommandHandler;
import net.leonemc.lithium.banknotes.BanknoteHandler;
import net.leonemc.lithium.bounties.BountyHandler;
import net.leonemc.lithium.chatgames.ChatGameHandler;
import net.leonemc.lithium.chatgames.object.ChatGame;
import net.leonemc.lithium.chatgames.parameter.ChatGameParameter;
import net.leonemc.lithium.cosmetics.CosmeticHandler;
import net.leonemc.lithium.customitems.CustomItem;
import net.leonemc.lithium.customitems.CustomItemHandler;
import net.leonemc.lithium.customitems.param.CustomItemParameter;
import net.leonemc.lithium.dropparty.DropPartyHandler;
import net.leonemc.lithium.filter.FilterManager;
import net.leonemc.lithium.fishing.FishingHandler;
import net.leonemc.lithium.leaderboard.CachedLeaderboard;
import net.leonemc.lithium.leaderboard.LeaderboardUpdater;
import net.leonemc.lithium.listener.PotionListener;
import net.leonemc.lithium.modmode.ModModeHandler;
import net.leonemc.lithium.profiles.Profile;
import net.leonemc.lithium.profiles.ProfileManager;
import net.leonemc.lithium.profiles.task.ProfileSaveRunnable;
import net.leonemc.lithium.rename.RenameHandler;
import net.leonemc.lithium.reports.ReportHandler;
import net.leonemc.lithium.reports.hook.LitebansHook;
import net.leonemc.lithium.scoreboard.ScoreboardAdapter;
import net.leonemc.lithium.shop.ShopHandler;
import net.leonemc.lithium.showcase.ShowcaseHandler;
import net.leonemc.lithium.staffchat.StaffChatHandler;
import net.leonemc.lithium.storage.Data;
import net.leonemc.lithium.tags.TagHandler;
import net.leonemc.lithium.tags.objects.Tag;
import net.leonemc.lithium.tags.parameter.TagParameter;
import net.leonemc.lithium.task.*;
import net.leonemc.lithium.tips.TipsManager;
import net.leonemc.lithium.utils.Cooldown;
import net.leonemc.lithium.utils.Task;
import net.leonemc.lithium.utils.command.Command;
import net.leonemc.lithium.utils.command.CommandFramework;
import net.leonemc.lithium.utils.entity.corpses.Corpses;
import net.leonemc.lithium.utils.entity.corpses.nms.NMSCorpses;
import net.leonemc.lithium.utils.java.ClassUtil;
import net.leonemc.lithium.vouchers.VoucherHandler;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Getter
public class Lithium extends JavaPlugin {

    private static Lithium instance;
    private CommandFramework commandFramework;
    private final ClassUtil classUtil = new ClassUtil();

    //vault
    private Economy econ = null;
    private Permission perms = null;
    private Chat chat = null;


    private Data data;

    private ProfileManager profileManager;
    private ModModeHandler modModeHandler;
    private BountyHandler bountyHandler;
    private CachedLeaderboard cachedLeaderboard;
    private ReportHandler reportHandler;
    private FishingHandler fishingHandler;
    private ShopHandler shopHandler;

    private TipsManager tipsManager;
    private StaffChatHandler staffChatHandler;
    private FilterManager filterManager;
    private BanknoteHandler banknoteHandler;
    private VoucherHandler voucherHandler;
    private CosmeticHandler cosmeticHandler;
    private TagHandler tagHandler;
    private CustomItemHandler customItemHandler;
    private DropPartyHandler dropPartyHandler;
    private Corpses corpses;
    private ChatGameHandler chatGameHandler;
    private RenameHandler renameHandler;
    private ShowcaseHandler showcaseHandler;

    @Override
    public void onEnable() {
        instance = this;

        File playerDir = new File(getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "players");
        if (!playerDir.exists()) {
            getLogger().log(Level.INFO, "Successfully created the player data directory.");
            playerDir.mkdirs();
        }

        File tagDirectory = new File(getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator + "tags");
        if (!tagDirectory.exists()) {
            getLogger().log(Level.INFO, "Successfully created the tag data directory.");
            tagDirectory.mkdirs();
        }

        //do config stuff
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //setup vault
        setupChat();
        setupEconomy();
        setupPermissions();

        commandFramework = new CommandFramework(this);

        data = new Data();

        new Task(this);

        corpses = new NMSCorpses();

        profileManager = new ProfileManager();
        //modModeHandler = new ModModeHandler();
        bountyHandler = new BountyHandler();
        cachedLeaderboard = new CachedLeaderboard();
        reportHandler = new ReportHandler();
        fishingHandler = new FishingHandler();
        shopHandler = new ShopHandler();
        tipsManager = new TipsManager();
        staffChatHandler = new StaffChatHandler();
        filterManager = new FilterManager();
        banknoteHandler = new BanknoteHandler();
        voucherHandler = new VoucherHandler();
        cosmeticHandler = new CosmeticHandler();
        tagHandler = new TagHandler();
        customItemHandler = new CustomItemHandler();
        chatGameHandler = new ChatGameHandler();
        dropPartyHandler = new DropPartyHandler();
        renameHandler = new RenameHandler();
        showcaseHandler = new ShowcaseHandler();

        tipsManager.handleTip();

        Cooldown.createCooldown("report");
        Cooldown.createCooldown("combat");
        Cooldown.createCooldown("godapple");
        Cooldown.createCooldown("gapple");
        Cooldown.createCooldown("enderpearl");
        Cooldown.createCooldown("repair");
        Cooldown.createCooldown("renamer");

        // ability cooldowns
        Cooldown.createCooldown("dash");
        Cooldown.createCooldown("updraft");
        Cooldown.createCooldown("grapple");
        Cooldown.createCooldown("attack_dogs");

        //load listeners and commands
        classUtil.loadListenersFromPackage("net.leonemc.lithium");

        //Registers all commands that use the API command handler
        CommandHandler.INSTANCE.registerAll(this);
        CommandHandler.INSTANCE.registerParameterType(CustomItem.class, new CustomItemParameter());
        CommandHandler.INSTANCE.registerParameterType(Tag.class, new TagParameter());
        CommandHandler.INSTANCE.registerParameterType(ChatGame.class, new ChatGameParameter());


        try {
            ClassPath.from(this.getClass().getClassLoader()).getAllClasses().stream()
                    .filter(info -> info.getPackageName().startsWith("net.leonemc.lithium.commands"))
                    .forEach(info -> {
                        if (!info.getName().contains("$")) {
                            try {
                                commandFramework.registerCommands(info.load().newInstance());
                            } catch (InstantiationException | IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
        } catch (Exception e) {
            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("§cError whilst loading commands.");
            Bukkit.broadcastMessage(" ");
            e.printStackTrace();
            Bukkit.shutdown();
            return;
        }

        //save playerdata every minute
        new ProfileSaveRunnable().runTaskTimerAsynchronously(this, 20 * 60, 20 * 60);
        new ModModeTask().runTaskTimer(this, 20, 20);
        new LeaderboardUpdater().runTaskTimerAsynchronously(this, 0L, 900 * 20);
        new CombatTagTask().runTaskTimer(this, 20, 20);
        new AFKRegionTask().runTaskTimer(this, TimeUnit.MINUTES.toSeconds(30) * 20, TimeUnit.MINUTES.toSeconds(30) * 20);
        new LeaderboardRunnable().runTaskTimer(this, 10 * 20L, 10 * 20);
        new ItemClearTask().runTaskTimer(this, 0L, 120 * 20L);

        Assemble assemble = new Assemble(this, new ScoreboardAdapter());
        assemble.setTicks(5);
        assemble.setAssembleStyle(AssembleStyle.MODERN);

        //hook into litebans to auto solve reports
        Events.get().register(new LitebansHook());

        // setup the knockback values
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "knockback friction 1.87");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "knockback horizontal 0.685");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "knockback vertical 0.402");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "knockback extra-horizontal 0.196");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "knockback extra-vertical 0.0");
    }

    @Override
    public void onDisable() {
        profileManager.getProfiles().values().forEach(Profile::save);

        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasMetadata("modmode")).forEach(player -> modModeHandler.disableModMode(player));
    }

    public static Lithium getInstance() {
        return instance;
    }

    public void reload() {
        filterManager.getOtherFiltered().clear();
        filterManager.getMutableWords().clear();
        filterManager.getOtherFiltered().addAll(getConfig().getStringList("filtered-words.normal"));
        filterManager.getMutableWords().addAll(getConfig().getStringList("filtered-words.mutable"));
        getLogger().info("Reloaded the config.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}

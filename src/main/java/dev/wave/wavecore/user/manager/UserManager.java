package dev.wave.wavecore.user.manager;



import dev.wave.wavecore.file.gson.GsonUtil;
import dev.wave.wavecore.plugin.WavePlugin;
import dev.wave.wavecore.user.UserDataModule;
import dev.wave.wavecore.user.configuration.UserDataConfig;
import dev.wave.wavecore.user.user.User;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.*;

public class UserManager {

    public static final File FOLDER = new File(UserDataModule.getInstance().getDirectory(), "user");

    private final List<User> saveQueue;

    private final Map<UUID, User> userMap;

    public UserManager(WavePlugin instance) {
        this.userMap = new HashMap<>();
        this.saveQueue = new ArrayList<>();
        loadUsers();
        long delay = UserDataConfig.SAVE_INTERVAL.getInt() * 20L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, this::runSaveTask, delay, delay);
    }

    private void loadUsers() {

        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
            return;
        }

        if (FOLDER.listFiles() == null) {
            return;
        }

        for (File file : FOLDER.listFiles()) {
            String uuidString = file.getName().split("\\.")[0];
            UUID uuid;
            try {
                uuid = UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                continue;
            }

            userMap.put(uuid, GsonUtil.read(FOLDER, file.getName(), User.class));

        }

    }

    public Collection<User> getAllUsers() {
        return userMap.values();
    }

    public User getUser(UUID uuid) {
        User user = userMap.get(uuid);
        if (user == null) {
            user = new User(uuid);
            userMap.put(uuid, user);
        }
        if (!saveQueue.contains(user)) {
            saveQueue.add(user);
        }
        return user;
    }

    public void runSaveTask() {

        List<User> savedUsers = new ArrayList<>();

        for (User user : saveQueue) {
            savedUsers.add(user);
            user.save();
        }

        saveQueue.removeAll(savedUsers);

    }

}

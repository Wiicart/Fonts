package com.pedestriamc.fonts.users;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface UserUtil {

    /**
     * Saves a User to users.yml.
     *
     * @param user The user to be saved.
     */
    void saveUser(@NotNull User user);

    /**
     * Loads a User from the users.yml file.
     * Returns a new User with font DefaultFont if user not found.
     *
     * @param player The player of the User to load.
     * @return A User
     */
    User loadUser(@NotNull Player player);

    UserMap getUserMap();

    class UserMap {

        private final Map<UUID, User> userMap;

        protected UserMap() {
            userMap = new HashMap<>();
        }

        /**
         * Adds a User to the User HashMap.
         *
         * @param user The User to be added.
         */
        public void addUser(User user) {
            userMap.put(user.getUuid(), user);
        }

        /**
         * Removes a User from the User HashMap.
         *
         * @param uuid The UUID of the User to remove.
         */
        public void removeUser(UUID uuid) {
            userMap.remove(uuid);
        }

        public void removeUser(Player player) {
            userMap.remove(player.getUniqueId());
        }

        /**
         * Gets a User from the User HashMap.
         *
         * @param uuid The UUID of the User.
         * @return Returns a User if it exists, otherwise null.
         */
        public User getUser(UUID uuid) {
            return userMap.get(uuid);
        }

        /**
         * Gets a User from the User HashMap.
         *
         * @param player The Player the User should represent.
         * @return Returns a User if it exists, otherwise null.
         */
        public User getUser(Player player) {
            return userMap.get(player.getUniqueId());
        }

        public void clear(){ userMap.clear(); }

        public boolean isEmpty(){ return userMap.isEmpty(); }

    }

}

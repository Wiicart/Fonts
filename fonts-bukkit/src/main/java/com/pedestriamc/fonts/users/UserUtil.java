package com.pedestriamc.fonts.users;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserUtil {

    /**
     * Saves a User to users.yml.
     *
     * @param user The user to be saved.
     */
    void saveUser(@NotNull User user);

    /**
     * Loads a User from the users.yml file.
     * Returns a new User if no user is found.
     *
     * @param uuid The UUID of the User to load
     * @return A User
     */
    @NotNull User loadUser(@NotNull UUID uuid);

    /**
     * Calls {@link UserUtil#loadUser(UUID)} in an async context
     * @param uuid The UUID
     * @return A User
     */
    @NotNull CompletableFuture<User> loadUserAsync(@NotNull UUID uuid);

    /**
     * Gets a User. Loads from {@link UserUtil#loadUser(UUID)} if necessary.
     * @param uuid The UUID of the User
     * @return A User
     */
    @NotNull User getUser(@NotNull UUID uuid);

    void removeUser(@NotNull UUID uuid);

    boolean isEmpty();

}

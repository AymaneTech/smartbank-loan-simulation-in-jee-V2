package com.wora.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The `env` class provides access to environment variables loaded from a .env file.
 */
public class Env {
    private static final Map<String, String> envVariables = new HashMap<>();

    static {
        synchronized (Env.class) {
            if (envVariables.isEmpty()) {
                final String currentDir = System.getProperty("user.dir");
                final String filePath = currentDir + File.separator + ".env";

                try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("#")) {
                            continue;
                        }

                        final String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            final String key = parts[0].trim();
                            final String value = parts[1].trim();
                            envVariables.put(key, value);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error while loading the .env file", e);
                }
            }
        }
    }

    private Env() {
    }

    /**
     * Get the value of an environment variable by its key.
     *
     * @param key The key of the environment variable.
     * @return The value of the environment variable, or null if not found.
     */
    public static String get(String key) {
        return envVariables.get(key);
    }
}
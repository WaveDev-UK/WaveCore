package dev.wave.wavecore.util;


import org.bukkit.Bukkit;

// @author Hippo
// ALL CREDITS FOR BUKKIT VERSION GO TO HIPPO - HIS GITHUB: https://github.com/Hippo
public class BukkitVersion {

    private static BukkitVersion current;

    private final int major, minor, patch;

    public BukkitVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public BukkitVersion(int major, int minor) {
        this(major, minor, 0);
    }

    public boolean isGreaterThan(BukkitVersion version) {
        if (major > version.major) {
            return true;
        } else if (major == version.major) {
            if (minor > version.minor) {
                return true;
            } else if (minor == version.minor) {
                return patch > version.patch;
            }
        }
        return false;
    }

    public boolean isGreaterThanOrEqual(BukkitVersion version) {
        return this.equals(version) || this.isGreaterThan(version);
    }

    public boolean isLessThan(BukkitVersion version) {
        if (major < version.major) {
            return true;
        } else if (major == version.major) {
            if (minor < version.minor) {
                return true;
            } else if (minor == version.minor) {
                return patch < version.patch;
            }
        }
        return false;
    }

    public boolean isLessThanOrEqual(BukkitVersion version) {
        return this.equals(version) || this.isLessThan(version);
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPatch() {
        return patch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BukkitVersion that = (BukkitVersion) o;

        if (major != that.major) return false;
        if (minor != that.minor) return false;
        return patch == that.patch;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + patch;
        return result;
    }

    @Override
    public String toString() {
        return "BukkitVersion{" +
                "major=" + major +
                ", minor=" + minor +
                ", patch=" + patch +
                '}';
    }

    public static BukkitVersion getCurrent() {
        if (current == null) {
            String version = Bukkit.getBukkitVersion();
            String minecraftVersion = version.substring(0, version.indexOf('-'));
            String[] split = minecraftVersion.split("\\.");
            if (split.length < 2 || split.length > 3) {
                throw new IllegalStateException("Invalid Bukkit version: " + minecraftVersion);
            }
            int major = Integer.parseInt(split[0]);
            int minor = Integer.parseInt(split[1]);
            if (split.length == 3) {
                int patch = Integer.parseInt(split[2]);
                current = new BukkitVersion(major, minor, patch);
            } else {
                current = new BukkitVersion(major, minor);
            }
        }
        return current;
    }


}

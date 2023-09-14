package de.sakurajin.sakuralib.util;

import net.fabricmc.loader.api.Version;

public class VersionHelper {
    static public int compareMajor(Version v1, Version v2){
        int m1 = 0;
        int m2 = 0;
        try{
            m1 = Integer.parseInt(v1.getFriendlyString().split("\\.")[0]);
            m2 = Integer.parseInt(v2.getFriendlyString().split("\\.")[0]);
        }catch (Exception ignored){}

        return Integer.compare(m1, m2);
    }

    static public int compareMinor(Version v1, Version v2){
        int m1 = 0;
        int m2 = 0;
        try{
            m1 = Integer.parseInt(v1.getFriendlyString().split("\\.")[1]);
            m2 = Integer.parseInt(v2.getFriendlyString().split("\\.")[1]);
        }catch (Exception ignored){}

        return Integer.compare(m1, m2);
    }

    static public int comparePatch(Version v1, Version v2){
        int m1 = 0;
        int m2 = 0;
        try{
            m1 = Integer.parseInt(v1.getFriendlyString().split("\\.")[2]);
            m2 = Integer.parseInt(v2.getFriendlyString().split("\\.")[2]);
        }catch (Exception ignored){}

        return Integer.compare(m1, m2);
    }
}

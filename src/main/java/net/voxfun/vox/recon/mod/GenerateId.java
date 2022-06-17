package net.voxfun.vox.recon.mod;

import java.util.Random;

public class GenerateId {
    public static String random() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) { sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toLowerCase().charAt(new Random().nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toLowerCase().length()))); }
        return sb.toString();
    }
}

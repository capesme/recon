package net.voxfun.vox.recon.manager;

public class MaximumPlayerAmount {
    public static Integer maxPlayerAmount = new Integer(15);

    public MaximumPlayerAmount() {

        maxPlayerAmount = 15;
    }


    public static Integer get() {
        return maxPlayerAmount;
    }
}

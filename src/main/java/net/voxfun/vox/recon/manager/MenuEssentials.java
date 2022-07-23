package net.voxfun.vox.recon.manager;

public class MenuEssentials {
    private final GameManager gameManager;
    public MenuEssentials(GameManager gameManager) { this.gameManager = gameManager; }

    private static int xPosSize = 0;
    private static int yPosSize = 0;
    private static int menuPos = 0;

    public static int clacMenuPos(Integer xPosSize, Integer yPosSize) {
        int menuPos = ((xPosSize - 1) + ((yPosSize -1) * 9 ));
        return menuPos;
    }
}

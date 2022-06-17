package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.index;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.*;

public class MapManager {
    private GameManager gameManager;
    public static Map<String, Document> maps = new HashMap<>();
    public static Document selectedMap = null;
    public static List<Player> voted = new ArrayList<>();
    public MapManager(GameManager gameManager) {
        this.gameManager = gameManager;
        MongoCollection<Document> mapCollection = index.database.getCollection("recon_maps");
        mapCollection.find().forEach(m -> {
            if (m.getBoolean("disabled")) { return; }
            maps.putIfAbsent(m.get("mapName").toString(), m);
        });
        setMap();
    }

    public static Document getMap() { return selectedMap; }
    public static void setMap() {
        if (voted.size() == 0) {
            List<Document> values = new ArrayList<>(maps.values());
            selectedMap = values.get(new Random().nextInt(maps.size()));
        } else {
            Map<Document, Integer> allMaps = new HashMap<>();
            maps.forEach((name, doc) -> allMaps.put(doc, doc.getInteger("votes") != null ? doc.getInteger("votes") : 0));
            int mostVoted = Collections.max(allMaps.values());
            for (Map.Entry<Document, Integer> entry : allMaps.entrySet()) {
                if (entry.getValue().equals(mostVoted)) {
                    selectedMap = entry.getKey();
                    selectedMap.append("wasMapVotedFor", true);
                }
            }
        }
    }
    public static Map<String, Document> getMaps() { return maps; }
    public static void reloadMaps() {
        selectedMap = null;
        voted.clear();
        maps.clear();
        MongoCollection<Document> mapCollection = index.database.getCollection("recon_maps");
        mapCollection.find().forEach(m -> {
            if (m.getBoolean("disabled")) { return; }
            maps.putIfAbsent(m.get("mapName").toString(), m);
        });
    }
    public static Document voteMap(String name, Player player) {
        if (maps.get(name) == null || voted.contains(player)) return null;
        maps.get(name).append("votes", maps.get(name).getInteger("votes") != null ? maps.get(name).getInteger("votes") + 1 : 1);
        voted.add(player);
        return maps.get(name);
    }
}

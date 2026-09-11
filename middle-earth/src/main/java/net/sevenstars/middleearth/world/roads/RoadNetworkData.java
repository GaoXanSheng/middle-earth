package net.sevenstars.middleearth.world.roads;

import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;

import java.util.List;

/**
 * Pure data shared by world generation (ProceduralRoads) and the client map screen
 * (road overlay lines + POI markers). Must stay free of any server-only or client-only imports.
 * <p>
 * All coordinates are in "initial map pixels" (0..{@value MiddleEarthMapConfigs#REGION_SIZE} on each axis).
 * World coordinates = pixel * {@link #MAP_TO_WORLD} (32 blocks per pixel).
 */
public final class RoadNetworkData {
    public static final int MAP_TO_WORLD = MiddleEarthMapConfigs.FULL_MAP_SIZE / MiddleEarthMapConfigs.REGION_SIZE;
    // region Points of interest (37 faction spawns + Mount Doom landmark)
    public static final Poi HOBBITON = poi("shire.hobbiton", 933, 900);
    public static final Poi WILLOWBOTTOM = poi("shire.willowbottom", 981, 970);
    public static final Poi MINAS_TIRITH = poi("gondor.minas_tirith", 1945, 1785);
    public static final Poi ANORIEN = poi("gondor.anorien", 1930, 1735);
    public static final Poi ITHILIEN = poi("gondor.ithilien", 1975, 1700);
    public static final Poi LOSSARNACH = poi("gondor.lossarnach", 1895, 1792);
    public static final Poi RINGLO_VALE = poi("gondor.ringlo_vale", 1530, 1730);
    public static final Poi LAMEDON = poi("gondor.lamedon", 1625, 1800);
    public static final Poi LEBENNIN = poi("gondor.lebennin", 1715, 1955);
    public static final Poi PELARGIR = poi("gondor.pelargir", 1875, 1960);
    public static final Poi DOL_AMROTH = poi("gondor.dol_amroth", 1500, 1930);
    public static final Poi EDORAS = poi("rohan.edoras", 1525, 1600);
    public static final Poi WESTEMNET = poi("rohan.westemnet", 1525, 1525);
    public static final Poi EASTEMNET = poi("rohan.eastemnet", 1715, 1575);
    public static final Poi ALDBURG = poi("rohan.aldburg", 1600, 1660);
    public static final Poi HELMS_DEEP = poi("rohan.helms_deep", 1470, 1555);
    public static final Poi THE_WOLD = poi("rohan.the_wold", 1675, 1475);
    public static final Poi ORTHANC = poi("isengard.orthanc", 1402, 1467);
    public static final Poi CERIN_AMROTH = poi("lothlorien.cerin_amroth", 1614, 1215);
    public static final Poi MORIA_WEST_GATE = poi("moria.west_gate", 1465, 1143);
    public static final Poi MORIA_EAST_GATE = poi("moria.east_gate", 1522, 1143);
    public static final Poi GOBLIN_CAMP = poi("moria.goblin_camp", 1546, 1115);
    public static final Poi GORGOROTH = poi("mordor.gorgoroth", 2161, 1717);
    public static final Poi BLACK_GATES = poi("mordor.black_gates", 2010, 1608);
    public static final Poi DOL_GULDUR = poi("mordor.dol_guldur", 1793, 1210);
    public static final Poi MINAS_MORGUL = poi("mordor.minas_morgul", 2029, 1770);
    public static final Poi NURN = poi("mordor.nurn", 2345, 1915);
    public static final Poi DALE = poi("dale.capital", 2021, 727);
    public static final Poi ESGAROTH = poi("dale.esgaroth", 2007, 757);
    public static final Poi RAVENHILL = poi("longbeards.erebor.ravenhill", 2017, 722);
    public static final Poi IRON_HILLS = poi("longbeards.erebor.iron_hills", 2355, 725);
    public static final Poi IRON_HILLS_SPRING = poi("longbeards.erebor.iron_hills_spring", 2262, 782);
    public static final Poi ELVENKINGS_HALLS = poi("woodland_realm.elvenkings_halls", 1957, 766);
    public static final Poi GOBLIN_TOWN = poi("goblin_town.goblin_town", 1583, 869);
    public static final Poi GUNDABAD = poi("hobgoblin_tribes.gundabad.gundabad", 1595, 640);
    public static final Poi MOUNT_GRAM = poi("hobgoblin_tribes.gundabad.mount_gram", 1401, 686);
    public static final Poi GREY_MOUNTAINS = poi("hobgoblin_tribes.gundabad.grey_mountains", 1652, 640);
    /** Landmark, not a faction spawn: reuses the existing biome name key. */
    public static final Poi MOUNT_DOOM = new Poi("mount_doom", 2131.5f, 1715.2f, "biome.middle-earth.mount_doom");
    private static final List<Poi> POIS = List.of(HOBBITON, WILLOWBOTTOM, MINAS_TIRITH, ANORIEN, ITHILIEN,
            LOSSARNACH, RINGLO_VALE, LAMEDON, LEBENNIN, PELARGIR, DOL_AMROTH, EDORAS, WESTEMNET, EASTEMNET,
            ALDBURG, HELMS_DEEP, THE_WOLD, ORTHANC, CERIN_AMROTH, MORIA_WEST_GATE, MORIA_EAST_GATE, GOBLIN_CAMP,
            GORGOROTH, BLACK_GATES, DOL_GULDUR, MINAS_MORGUL, NURN, DALE, ESGAROTH, RAVENHILL, IRON_HILLS,
            IRON_HILLS_SPRING, ELVENKINGS_HALLS, GOBLIN_TOWN, GUNDABAD, MOUNT_GRAM, GREY_MOUNTAINS, MOUNT_DOOM);
    // region Road edges (control points hand-placed to skirt deep water and the worst ridges)
    private static final List<Edge> EDGES = List.of(
            // The Great East Road: The Shire -> Bree downs -> Trollshaws -> Moria West Gate
            new Edge(HOBBITON, MORIA_WEST_GATE, 1170, 1070, RoadStyle.SHIRE, 5),
            new Edge(HOBBITON, WILLOWBOTTOM, 970, 945, RoadStyle.SHIRE, 4),

            // Misty Mountains crossings
            new Edge(MORIA_WEST_GATE, MORIA_EAST_GATE, 1493, 1150, RoadStyle.DALE, 4),
            new Edge(MORIA_EAST_GATE, GOBLIN_CAMP, 1534, 1128, RoadStyle.DALE, 3),
            new Edge(GOBLIN_TOWN, MORIA_WEST_GATE, 1512, 1000, RoadStyle.DALE, 3),
            new Edge(GOBLIN_TOWN, GUNDABAD, 1590, 755, RoadStyle.DALE, 3),
            new Edge(GUNDABAD, MOUNT_GRAM, 1500, 652, RoadStyle.DALE, 3),
            new Edge(GUNDABAD, GREY_MOUNTAINS, 1625, 633, RoadStyle.DALE, 3),

            // Anduin -> Lothlorien -> Rohan
            new Edge(MORIA_EAST_GATE, CERIN_AMROTH, 1570, 1178, RoadStyle.WLR, 4),
            new Edge(CERIN_AMROTH, THE_WOLD, 1648, 1345, RoadStyle.ROHAN, 4),
            new Edge(CERIN_AMROTH, DOL_GULDUR, 1705, 1203, RoadStyle.WLR, 4),
            new Edge(THE_WOLD, EASTEMNET, 1692, 1522, RoadStyle.ROHAN, 4),
            new Edge(EASTEMNET, ALDBURG, 1662, 1622, RoadStyle.ROHAN, 4),
            new Edge(ALDBURG, EDORAS, 1560, 1626, RoadStyle.ROHAN, 4),
            new Edge(EDORAS, HELMS_DEEP, 1495, 1572, RoadStyle.ROHAN, 4),
            new Edge(EDORAS, WESTEMNET, 1522, 1562, RoadStyle.ROHAN, 4),
            new Edge(WESTEMNET, THE_WOLD, 1600, 1488, RoadStyle.ROHAN, 4),
            new Edge(EDORAS, ORTHANC, 1455, 1530, RoadStyle.ROHAN, 4),
            new Edge(EDORAS, MINAS_TIRITH, 1730, 1692, RoadStyle.GONDOR, 5),

            // Gondor
            new Edge(MINAS_TIRITH, ANORIEN, 1938, 1760, RoadStyle.GONDOR, 4),
            new Edge(MINAS_TIRITH, LOSSARNACH, 1918, 1800, RoadStyle.GONDOR, 4),
            new Edge(MINAS_TIRITH, ITHILIEN, 1963, 1740, RoadStyle.GONDOR, 4),
            new Edge(ITHILIEN, MINAS_MORGUL, 2002, 1740, RoadStyle.MORDOR, 4),
            new Edge(ITHILIEN, BLACK_GATES, 1998, 1652, RoadStyle.MORDOR, 4),
            new Edge(MINAS_TIRITH, PELARGIR, 1888, 1878, RoadStyle.GONDOR, 4),
            new Edge(PELARGIR, LEBENNIN, 1792, 1972, RoadStyle.GONDOR, 4),
            new Edge(LEBENNIN, DOL_AMROTH, 1600, 1962, RoadStyle.GONDOR, 4),
            new Edge(DOL_AMROTH, LAMEDON, 1558, 1862, RoadStyle.GONDOR, 4),
            new Edge(LAMEDON, RINGLO_VALE, 1572, 1762, RoadStyle.GONDOR, 4),
            new Edge(RINGLO_VALE, ANORIEN, 1730, 1718, RoadStyle.GONDOR, 4),

            // Mordor
            new Edge(BLACK_GATES, GORGOROTH, 2088, 1658, RoadStyle.MORDOR, 4),
            new Edge(GORGOROTH, MOUNT_DOOM, 2146, 1716, RoadStyle.MORDOR, 3),
            new Edge(GORGOROTH, MINAS_MORGUL, 2094, 1752, RoadStyle.MORDOR, 4),
            new Edge(GORGOROTH, NURN, 2252, 1830, RoadStyle.MORDOR, 3),

            // Rhovanion: Old Forest Road, Mirkwood, the North-east
            new Edge(DOL_GULDUR, ELVENKINGS_HALLS, 1882, 1000, RoadStyle.WLR, 4),
            new Edge(ELVENKINGS_HALLS, DALE, 1990, 744, RoadStyle.DALE, 4),
            new Edge(DALE, ESGAROTH, 2012, 744, RoadStyle.DALE, 4),
            new Edge(RAVENHILL, ESGAROTH, 2010, 740, RoadStyle.DALE, 3),
            new Edge(DALE, IRON_HILLS, 2190, 700, RoadStyle.DALE, 4),
            new Edge(IRON_HILLS_SPRING, IRON_HILLS, 2310, 752, RoadStyle.DALE, 3),
            new Edge(IRON_HILLS_SPRING, DALE, 2140, 762, RoadStyle.DALE, 4)
    );
    private RoadNetworkData() {}

    public static List<Poi> pois() {
        return POIS;
    }
    // endregion

    public static List<Edge> edges() {
        return EDGES;
    }

    public static float toWorldX(float mapPixelX) {
        return mapPixelX * MAP_TO_WORLD;
    }
    // endregion

    public static float toWorldZ(float mapPixelZ) {
        return mapPixelZ * MAP_TO_WORLD;
    }

    private static Poi poi(String id, float x, float z) {
        return new Poi(id, x, z, "spawn.middle-earth." + id);
    }

    public enum RoadStyle { SHIRE, GONDOR, ROHAN, DALE, MORDOR, WLR }

    /**
     * @param id      stable identifier, matching faction spawn ids where applicable
     * @param langKey existing translation key for display (faction spawns already have "spawn.middle-earth.*" keys)
     */
    public record Poi(String id, float x, float z, String langKey) {}

    /**
     * Quadratic bezier edge in map pixel space: from -> (controlX, controlZ) -> to.
     * @param width full-width road core in blocks (influence falloff extends beyond it)
     */
    public record Edge(Poi from, Poi to, float controlX, float controlZ, RoadStyle style, float width) {}
}

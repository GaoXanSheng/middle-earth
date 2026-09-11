package net.sevenstars.middleearth.gui.map;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.map.MapMarkerWidget;
import net.sevenstars.middleearth.gui.utils.widgets.map.MapWidget;
import net.sevenstars.middleearth.gui.utils.widgets.map.types.MapMarkerType;
import net.sevenstars.middleearth.world.roads.RoadNetworkData;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Draws the {@link RoadNetworkData} points of interest on the map screen as display-only markers.
 * Follows the FactionSelectionMapWidget per-frame compute + draw pattern: markers are never
 * registered as screen widgets, so they cannot be clicked; hovering only shows a tooltip.
 */
public final class MapPoiMarkers {
    /** Markers closer than this (screen pixels) are merged into one stacked marker. */
    private static final double GROUP_RADIUS = 15;
    /** South bound high enough to never pinch arrows inside a fullscreen map. */
    private static final double UNBOUNDED_SOUTH = 10000;

    private static final List<MapMarkerWidget> MARKERS = buildMarkers();
    private static final Component[] NAME_LINES = buildNameLines();
    private static final Vector2i[] CENTERS = buildCenters();
    private static final int[] GROUP_OF = new int[MARKERS.size()];
    private static final int[] GROUP_HEAD = new int[MARKERS.size()];
    private static final int[] MEMBERS = new int[MARKERS.size()];
    private static final boolean[] CONTENT_IS_STACKED = new boolean[MARKERS.size()];
    // Scratch vector reused for every conversion: getMapPointFromMapCoordinate mutates it
    private static final Vector2d SCRATCH = new Vector2d();

    private MapPoiMarkers() {}

    private static List<MapMarkerWidget> buildMarkers() {
        List<RoadNetworkData.Poi> pois = RoadNetworkData.pois();
        List<MapMarkerWidget> markers = new ArrayList<>();
        for (RoadNetworkData.Poi poi : pois) {
            MapMarkerWidget marker = new MapMarkerWidget("MapPoi_" + poi.id(), x -> {},
                    // East/West bounds come from the widget itself and North from its top edge;
                    // the tall South bound keeps arrows from pinning mid-screen in fullscreen
                    new Rectangle2D.Double(0, 0, 0, UNBOUNDED_SOUTH));
            marker.setType(MapMarkerType.DYNAMIC_SPAWN);
            marker.setContent(createIndividualContent(poi));
            markers.add(marker);
        }
        return List.copyOf(markers);
    }

    private static Component[] buildNameLines() {
        List<RoadNetworkData.Poi> pois = RoadNetworkData.pois();
        Component[] lines = new Component[pois.size()];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = Component.translatable(pois.get(i).langKey()).withStyle(ChatFormatting.GOLD);
        }
        return lines;
    }

    private static Vector2i[] buildCenters() {
        Vector2i[] centers = new Vector2i[MARKERS.size()];
        for (int i = 0; i < centers.length; i++) {
            centers[i] = new Vector2i();
        }
        return centers;
    }

    private static List<Component> createIndividualContent(RoadNetworkData.Poi poi) {
        return List.of(
                Component.translatable(poi.langKey()).withStyle(ChatFormatting.GOLD),
                Component.literal("X: %d, Z: %d".formatted(
                        Math.round(poi.x() * RoadNetworkData.MAP_TO_WORLD),
                        Math.round(poi.z() * RoadNetworkData.MAP_TO_WORLD))).withStyle(ChatFormatting.GRAY));
    }

    public static void draw(GuiGraphicsExtractor context, MapWidget mapWidget, int startX, int startY, int mouseX, int mouseY) {
        if (!ModClientConfigs.SHOW_MAP_MARKERS) return;

        // Refresh before drawing so marker hover states match the current frame
        ModWidget.updateMouse(mouseX, mouseY);

        Arrays.fill(GROUP_OF, -1);
        int groupCount = 0;
        for (int i = 0; i < MARKERS.size(); i++) {
            MapMarkerWidget marker = MARKERS.get(i);
            RoadNetworkData.Poi poi = RoadNetworkData.pois().get(i);
            marker.setType(MapMarkerType.DYNAMIC_SPAWN);
            // Poi coordinates are initial map pixels (0..3000), same space as dynamic spawn data
            SCRATCH.set(poi.x(), poi.z());
            marker.computeFromMapPosition(mapWidget, SCRATCH);
            CENTERS[i].set(marker.getCenterCoordinates());

            int group = -1;
            for (int j = 0; j < i && group == -1; j++) {
                if (GROUP_OF[j] != -1 && Math.round(CENTERS[i].distance(CENTERS[j])) <= GROUP_RADIUS) {
                    group = GROUP_OF[j];
                }
            }
            if (group == -1) {
                group = groupCount++;
                GROUP_HEAD[group] = i;
            } else {
                MapMarkerWidget head = MARKERS.get(GROUP_HEAD[group]);
                head.updateMarkerType(MapMarkerType.STACKED_SPAWNS);
                marker.activateButton(false);
            }
            GROUP_OF[i] = group;
        }

        for (int g = 0; g < groupCount; g++) {
            MapMarkerWidget head = MARKERS.get(GROUP_HEAD[g]);
            int count = 0;
            int averageX = 0;
            int averageY = 0;
            for (int i = 0; i < MARKERS.size(); i++) {
                if (GROUP_OF[i] != g) continue;
                MEMBERS[count++] = i;
                averageX += CENTERS[i].x;
                averageY += CENTERS[i].y;
            }
            averageX /= count;
            averageY /= count;

            if (count > 1) {
                List<Component> names = new ArrayList<>(count);
                for (int m = 0; m < count; m++) {
                    names.add(NAME_LINES[MEMBERS[m]]);
                }
                head.setContent(names);
                CONTENT_IS_STACKED[GROUP_HEAD[g]] = true;
            } else if (CONTENT_IS_STACKED[GROUP_HEAD[g]]) {
                // Marker left its stack: restore its own tooltip
                head.setContent(createIndividualContent(RoadNetworkData.pois().get(GROUP_HEAD[g])));
                CONTENT_IS_STACKED[GROUP_HEAD[g]] = false;
            }

            head.assignNewCenter(new Vector2i(averageX, averageY));
            head.draw(context);
        }
    }
}

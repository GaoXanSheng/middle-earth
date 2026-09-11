package net.sevenstars.middleearth.gui.utils.widgets.map;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.world.roads.RoadNetworkData;
import org.joml.Vector2d;

/**
 * Draws the road network (see {@link RoadNetworkData}) on top of the map widget.
 * Edges are sampled client-side in map pixel space, then each sample goes through
 * the widget's own map->screen mapping so roads stay glued to the map texture.
 */
public final class MapRoadOverlay {
    private static final int COLOR_SHIRE = 0xC8A08A5C;
    private static final int COLOR_GONDOR = 0xC89A8F78;
    private static final int COLOR_ROHAN = 0xC8A6935F;
    private static final int COLOR_DALE = 0xC88D8579;
    private static final int COLOR_MORDOR = 0xC85E5A54;
    private static final int COLOR_WLR = 0xC87E8B6A;

    /** Target distance between two bezier samples, in map pixels. */
    private static final float SAMPLE_STEP_PX = 6f;
    /** Extra margin around the viewport before an edge is culled. */
    private static final int CULL_SLACK_PX = 8;

    private MapRoadOverlay() {}

    public static void draw(GuiGraphicsExtractor context, MapWidget widget, int startX, int startY) {
        if (!ModClientConfigs.SHOW_MAP_ROADS) return;

        // Roads cross the whole world, so edges partially overlapping the widget are the norm:
        // clip every fill to the widget area or the lines paint past the map border
        context.enableScissor(startX, startY, startX + widget.getWidth(), startY + widget.getHeight());
        try {
            drawClipped(context, widget, startX, startY);
        } finally {
            context.disableScissor();
        }
    }

    private static void drawClipped(GuiGraphicsExtractor context, MapWidget widget, int startX, int startY) {
        // At low zoom only trunk roads stay visible to avoid clutter
        boolean trunkOnly = MapWidget.zoomLevel < 2;
        int thickness = MapWidget.zoomLevel >= 4 ? 2 : 1;

        int viewMinX = startX - CULL_SLACK_PX;
        int viewMinY = startY - CULL_SLACK_PX;
        int viewMaxX = startX + widget.getWidth() + CULL_SLACK_PX;
        int viewMaxY = startY + widget.getHeight() + CULL_SLACK_PX;

        // Scratch vector reused for every conversion: getMapPointFromMapCoordinate mutates it
        Vector2d point = new Vector2d();

        for (RoadNetworkData.Edge edge : RoadNetworkData.edges()) {
            if (trunkOnly && edge.width() < 5) continue;

            float p0x = edge.from().x();
            float p0z = edge.from().z();
            float p1x = edge.to().x();
            float p1z = edge.to().z();
            float cx = edge.controlX();
            float cz = edge.controlZ();

            // Cull: the curve stays inside the control polygon hull, so its bbox covers P0/C/P1
            float minPx = Math.min(p0x, Math.min(cx, p1x));
            float maxPx = Math.max(p0x, Math.max(cx, p1x));
            float minPz = Math.min(p0z, Math.min(cz, p1z));
            float maxPz = Math.max(p0z, Math.max(cz, p1z));

            point.set(minPx, minPz);
            widget.getMapPointFromMapCoordinate(point);
            float sxMin = (float) point.x;
            float syMin = (float) point.y;
            point.set(maxPx, maxPz);
            widget.getMapPointFromMapCoordinate(point);
            float sxMax = (float) point.x;
            float syMax = (float) point.y;

            if (sxMax < viewMinX || sxMin > viewMaxX || syMax < viewMinY || syMin > viewMaxY) continue;

            // Chord + control-net arc length estimate for a quadratic bezier
            float length = (dist(p0x, p0z, cx, cz) + dist(cx, cz, p1x, p1z) + dist(p0x, p0z, p1x, p1z)) / 2f;
            int samples = Math.max(1, (int) Math.ceil(length / SAMPLE_STEP_PX));

            point.set(p0x, p0z);
            widget.getMapPointFromMapCoordinate(point);
            float prevX = (float) point.x;
            float prevY = (float) point.y;
            int color = colorFor(edge.style());

            for (int i = 1; i <= samples; i++) {
                float t = i / (float) samples;
                float inv = 1f - t;
                point.set(inv * inv * p0x + 2 * inv * t * cx + t * t * p1x,
                        inv * inv * p0z + 2 * inv * t * cz + t * t * p1z);
                widget.getMapPointFromMapCoordinate(point);
                fillSegment(context, prevX, prevY, (float) point.x, (float) point.y, thickness, color);
                prevX = (float) point.x;
                prevY = (float) point.y;
            }
        }
    }

    /** Fills the segment rect plus its midpoint rect so diagonal spans stay visually continuous. */
    private static void fillSegment(GuiGraphicsExtractor context, float x1, float y1, float x2, float y2, int thickness, int color) {
        float midX = (x1 + x2) / 2f;
        float midY = (y1 + y2) / 2f;
        fillSpan(context, x1, y1, midX, midY, thickness, color);
        fillSpan(context, midX, midY, x2, y2, thickness, color);
    }

    private static void fillSpan(GuiGraphicsExtractor context, float x1, float y1, float x2, float y2, int thickness, int color) {
        int minX = (int) Math.min(x1, x2);
        int minY = (int) Math.min(y1, y2);
        int maxX = (int) Math.max(x1, x2);
        int maxY = (int) Math.max(y1, y2);
        context.fill(minX, minY, maxX + thickness, maxY + thickness, color);
    }

    private static int colorFor(RoadNetworkData.RoadStyle style) {
        return switch (style) {
            case SHIRE -> COLOR_SHIRE;
            case GONDOR -> COLOR_GONDOR;
            case ROHAN -> COLOR_ROHAN;
            case DALE -> COLOR_DALE;
            case MORDOR -> COLOR_MORDOR;
            case WLR -> COLOR_WLR;
        };
    }

    private static float dist(float x1, float z1, float x2, float z2) {
        float dx = x2 - x1;
        float dz = z2 - z1;
        return (float) Math.sqrt(dx * dx + dz * dz);
    }
}

package net.sevenstars.middleearth.world.chunkgen.map;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.map.MiddleEarthMapGeneration;
import org.joml.sampling.Convolution;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ImageUtils {
    private static HashMap<Integer, float[]> gaussianBlurKernel = new HashMap<>();
    private static float[] edgeKernel =
                    {-1f, -2f, -1f,
                     -2f,  12f, -2f,
                     -1f, -2f, -1f};
    private static final float GAUSSIAN_SIGMA = 3.81f;

    public static Random random = new Random();

    public static BufferedImage fetchResourceImage(String path) throws IOException {
        URL resource = ImageUtils.class.getClassLoader().getResource(path);
        BufferedImage img = ImageIO.read(resource);
        return img;
    }

    public static void saveImage(BufferedImage bufferedImage, String path, String fileName) throws Exception {
        new File(path).mkdirs();
        File f = new File(path + fileName);
        ImageIO.write(bufferedImage, "png", f);
    }

    public static BufferedImage[][] subdivide(BufferedImage parent) {
        BufferedImage[][] subidivedImages = new BufferedImage[2][2];
        int width = parent.getWidth();
        int height = parent.getHeight();

        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++){
                subidivedImages[x][y] = createChildFromParentImage(parent, width, x, y);
            }
        }

        return subidivedImages;
    }

    private static BufferedImage createChildFromParentImage(BufferedImage parent, int regionSize, int xIndex, int yIndex) {
        BufferedImage child = new BufferedImage(regionSize, regionSize, BufferedImage.TYPE_INT_ARGB);
        child = createVoids(child, parent, regionSize/2, xIndex, yIndex);
        child = fillVoidCenters(child);
        child = fillVoidEdges(child);
        return child;
    }

    private static BufferedImage createVoids(BufferedImage result, BufferedImage source, int size, int xIndex, int yIndex){
        for(int x = size * xIndex; x < size * (xIndex+1); x++) {
            for(int y = size * yIndex; y < size * (yIndex+1); y++) {
                result.setRGB((x - (size * xIndex)) * 2, (y - (size * yIndex)) * 2, source.getRGB(x, y));
            }
        }
        return result;
    }

    private static BufferedImage fillVoidCenters(BufferedImage result) {

        ArrayList<Integer> colorOccurences = new ArrayList<>();

        for(int x = 1; x < result.getWidth(); x += 2){
            for(int y = 1; y < result.getHeight(); y += 2){
                colorOccurences.add(result.getRGB(x - 1, y - 1));

                if(x  + 1 < result.getWidth()) {
                    colorOccurences.add(result.getRGB(x + 1, y - 1));
                }
                if(y  + 1 < result.getHeight()) {
                    colorOccurences.add(result.getRGB(x - 1, y + 1));
                }
                if(y  + 1 < result.getHeight() && x  + 1 < result.getWidth()) {
                    colorOccurences.add(result.getRGB(x + 1, y + 1));
                }

                try {
                    Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                    result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                    colorOccurences.clear();
                } catch (Exception exception) {
                    //MiddleEarth.LOGGER.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
                }
                colorOccurences.clear();

            }
        }
        return result;

    }

    private static BufferedImage fillVoidEdges(BufferedImage result) {
        ArrayList<Integer> colorOccurences = new ArrayList<>();

        for(int x = 0; x < result.getWidth(); x ++){
            for(int y = 0; y < result.getHeight(); y ++) {
                if(x % 2 == 1 && y % 2 == 1) continue;
                if(x % 2 == 0 && y % 2 == 0) continue;

                if(x % 2 == 1)
                    colorOccurences.add(result.getRGB(x - 1, y));
                if(x + 1 < result.getWidth())
                    colorOccurences.add(result.getRGB(x + 1, y));
                if(y % 2 == 1)
                    colorOccurences.add(result.getRGB(x, y - 1));
                if(y + 1 < result.getHeight())
                    colorOccurences.add(result.getRGB(x, y + 1));

                try {
                    Integer color = getMostOccuringColorFromBiomeList(colorOccurences);
                    result.setRGB(x, y, (color != null ) ? color : colorOccurences.get(0));
                } catch (Exception exception) {
                    //MiddleEarth.LOGGER.logError("ImageUtils::Can't find color at [%s,%s]".formatted(x,y));
                }
                colorOccurences.clear();
            }
        }
        return result;
    }

    private static Integer getMostOccuringColorFromBiomeList(ArrayList<Integer> list) throws Exception {
        if(list.isEmpty()){
            MiddleEarth.LOGGER.logError("ImageUtils::getMostCommonColor - List was empty!");
            return null;
        }
        Map<Integer, Integer> counts = new HashMap<>();

        int max = 0;
        for(int i = 0; i < list.size(); i++) {
            var value = counts.get(list.get(i));
            var expansionWeight = getExpansionWeight(list.get(i));
            if(value != null)
                expansionWeight += value;

            counts.put(list.get(i), expansionWeight);

            if(expansionWeight > max){
                max = expansionWeight;
            }
        }

        list.clear();

        int finalMax = max;
        counts.forEach((key, value) -> {
            if(value == finalMax)
                list.add(key);
        });

        if(list.size() == 1)
            return list.get(0);
        return list.get(random.nextInt(0, list.size()));
    }

    private static int getExpansionWeight(Integer integer) throws Exception{
        return MapBasedBiomePool.getBiomeByColor(integer).getBiomeData().biomeWeight[(MiddleEarthMapGeneration.CURRENT_ITERATION <= 1) ? 0 : 1];
    }

    /**
     * Convolves the image with a cached gaussian kernel. When crop is set, the input is expected
     * to include brushSize pixels of border on every side, which are cropped back off afterwards.
     */
    public static BufferedImage blur(BufferedImage image, int brushSize, boolean crop) {
        int width = image.getWidth();
        int height = image.getHeight();

        float[] blurKernel = gaussianBlurKernel.computeIfAbsent(brushSize, size -> {
            float[] kernel = new float[size*size];
            Convolution.gaussianKernel(size, size, GAUSSIAN_SIGMA, kernel);
            return kernel;
        });
        Kernel kernel = new Kernel(brushSize, brushSize, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage blurredImage = new BufferedImage(width, height, image.getType());
        op.filter(image, blurredImage);

        if(crop) {
            return blurredImage.getSubimage(brushSize, brushSize, width - brushSize*2, height - brushSize*2);
        } else {
            return blurredImage;
        }
    }

    private static final int EDGE_BRUSH_SIZE = 3;
    public static BufferedImage edge(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Kernel kernel = new Kernel(EDGE_BRUSH_SIZE, EDGE_BRUSH_SIZE, edgeKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        edgeImage = op.filter(image, edgeImage);

        return edgeImage;
    }
}

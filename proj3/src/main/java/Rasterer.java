import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private final double LONDPP =
            ((MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / MapServer.TILE_SIZE);
    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    private boolean validParam(Map<String, Double> params) {
        return params.containsKey("lrlon") && params.containsKey("ullon")
                && params.containsKey("w") && params.containsKey("h")
                && params.containsKey("lrlat") && params.containsKey("ullat");
    }
    private int getDepth(double needLonDPP) {
        int depth = 0;
        double initLonDPP = LONDPP;
        while (depth < 7 && initLonDPP > needLonDPP) {
            initLonDPP /= 2;
            depth++;
        }
        return depth;
    }

    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        if (!validParam(params)) {
            results.put("query_success", false);
            return results;
        }

        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double lrlat = params.get("lrlat");
        double ullat = params.get("ullat");



        int depth = getDepth((lrlon - ullon) / w);
        results.put("depth", depth);

        double wBlock = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (Math.pow(2, depth));
        double hBlock = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / (Math.pow(2, depth));
        int startX = (int) ((ullon - MapServer.ROOT_ULLON) / wBlock);
        int startY = (int) ((MapServer.ROOT_ULLAT - ullat) / hBlock);
        double rasterUlLon = MapServer.ROOT_ULLON + wBlock * startX;
        double rasterUlLat = MapServer.ROOT_ULLAT - hBlock * startY;

        int stepX = 1, stepY = 1;
        while (rasterUlLon + wBlock * stepX < lrlon) {
            ++stepX;
        }
        while (rasterUlLat - hBlock * stepY > lrlat) {
            ++stepY;
        }

        String[][] renderGrid = new String[stepY][stepX];
        for (int j = 0; j < stepY; ++j) {
            for (int i = 0; i < stepX; ++i) {
                int x = startX + i;
                int y = startY + j;
                renderGrid[j][i] = "d" + depth + "_" + "x" + x + "_" + "y" + y + ".png";
            }
        }
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterUlLon);
        results.put("raster_ul_lat", rasterUlLat);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + wBlock * (startX + stepX));
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - hBlock * (startY + stepY));
        results.put("query_success", true);
        return results;
    }
}

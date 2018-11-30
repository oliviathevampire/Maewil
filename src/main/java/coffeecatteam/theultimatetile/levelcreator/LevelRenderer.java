package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UICheckBox;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.levelcreator.fileFilter.FileFilterDirectories;
import coffeecatteam.theultimatetile.levelcreator.fileFilter.FileFilterExtension;
import coffeecatteam.theultimatetile.levelcreator.grid.Grid;
import coffeecatteam.theultimatetile.levelcreator.grid.GridTileSelect;
import coffeecatteam.theultimatetile.levelcreator.grid.GridWorldEditor;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {

    private static Tile SELECTED_TILE;

    private CreatorEngine creatorEngine;
    private int xWorldSize, minXWorldSize = 10, maxXWorldSize = 100;
    private int yWorldSize, minYWorldSize = 10, maxYWorldSize = 100;
    private int mouseX, mouseY;

    private AABB gridBounds;
    private int ogX, ogY, ogWorldGridSize = 10, worldGridSize = ogWorldGridSize, selectGridSize = 10;

    private List<Grid> grids = new ArrayList<>();
    private UIManager uiManager;

    private GridWorldEditor gridWorldEditorBG, gridWorldEditorFG;
    private UISlider zoomSlider;
    private UICheckBox fgCheckBox;

    public LevelRenderer(CreatorEngine creatorEngine, int xWorldSize, int yWorldSize) {
        this.creatorEngine = creatorEngine;
        setSize(xWorldSize, yWorldSize);

        Tiles.init(creatorEngine);
        SELECTED_TILE = Tiles.GRASS;

        ogX = creatorEngine.getWidth() / 4;
        ogY = creatorEngine.getHeight() / 4;
        gridBounds = new AABB(ogX, ogY, creatorEngine.getWidth() / 2, creatorEngine.getHeight() / 2);
        initGrids();

        this.uiManager = new UIManager(creatorEngine);
        creatorEngine.getMouseManager().setUiManager(uiManager);
        initUI();
    }

    private void initUI() {
        zoomSlider = new UISlider(creatorEngine, 185, 18, 200, 2);
        uiManager.addObject(zoomSlider);

        fgCheckBox = new UICheckBox(10, 50, true);
        uiManager.addObject(fgCheckBox);

        uiManager.addObject(new UIButton(creatorEngine, creatorEngine.getWidth() - 170, 10, "Save World", false, Assets.FONT_20, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save World");
                    chooser.setCurrentDirectory(new File("./export"));
                    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                    chooser.setMultiSelectionEnabled(true);

                    chooser.addChoosableFileFilter(new FileFilterExtension(".json", "JSON Files (*.json)"));

                    if (chooser.showSaveDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = chooser.getSelectedFile().getName();
                        String filePath = chooser.getSelectedFile().getParent();
                        String finalPath = (filePath + "/" + fileName + "/").replace(".json", "");

                        saveWorld(finalPath);
                    } else {
                        Logger.print("Canceling save...");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIButton(creatorEngine, creatorEngine.getWidth() - 158, 60, "Zip World", false, Assets.FONT_20, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Save/Zip World");
                    chooser.setCurrentDirectory(new File("./export"));
                    chooser.setDialogType(JFileChooser.SAVE_DIALOG);
                    chooser.setMultiSelectionEnabled(true);

                    chooser.addChoosableFileFilter(new FileFilterExtension(".json", "JSON Files (*.json)"));

                    if (chooser.showSaveDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = chooser.getSelectedFile().getName();
                        String filePath = chooser.getSelectedFile().getParent();
                        String finalPath = (filePath + "/" + fileName + "/").replace(".json", "");

                        zipWorld(finalPath, fileName);
                    } else {
                        Logger.print("Canceling save...");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIButton(creatorEngine, creatorEngine.getWidth() - 170, 110, "Load World", false, Assets.FONT_20, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Load World");
                    chooser.setCurrentDirectory(new File("./export"));
                    chooser.setFileFilter(new FileFilterDirectories());
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);

                    if (chooser.showOpenDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = chooser.getSelectedFile().getName();
                        String filePath = chooser.getSelectedFile().getParent();
                        String finalPath = (filePath + "/" + fileName + "/").replace(".json", "");

                        loadWorld(finalPath);
                    } else {
                        Logger.print("Canceling load...");
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader loader = new WorldJsonLoader(path.substring(0, path.length() - 1), creatorEngine);
        loader.load();
//        Tile[][] bgT = new Tile[xWorldSize][yWorldSize];
//        Tile[][] fgT = new Tile[xWorldSize][yWorldSize];
//        loader.loadTiles(xWorldSize, yWorldSize, path + "background.json", path + "foreground.json", bgT, fgT);
        xWorldSize = loader.getWidth();
        yWorldSize = loader.getHeight();

        gridWorldEditorBG.setGridFromArray(loader.getBg_tiles(), xWorldSize, yWorldSize);
        gridWorldEditorFG.setGridFromArray(loader.getFg_tiles(), xWorldSize, yWorldSize);
        Logger.print("World [" + path + "] loaded!");
    }

    private void saveWorld(String path) throws IOException {
        WorldJsonSaver saver = new WorldJsonSaver(null, null, null);
        File dir = new File(path);
        if (!dir.mkdirs())
            throw new IOException("Directory [" + dir + "] couldn't be made!");

        saver.saveTiles(xWorldSize, yWorldSize, gridWorldEditorBG.convertGridToArray(), gridWorldEditorFG.convertGridToArray(), path + "background", path + "foreground");
        Logger.print("World [" + dir + "] saved!");
    }

    private void zipWorld(String path, String fileName) throws IOException {
        saveWorld(path);
        File bg = new File(path + "background.json");
        File fg = new File(path + "foreground.json");

        String zipPath = path + fileName + ".zip";
        Utils.zipFiles(new File[]{bg, fg}, zipPath);
        Logger.print("World [" + zipPath + "] zipped!");
    }

    private void initGrids() {
        gridWorldEditorBG = new GridWorldEditor(creatorEngine, ogX, ogY, worldGridSize, ogX, ogY, xWorldSize, yWorldSize, false);
        gridWorldEditorFG = new GridWorldEditor(creatorEngine, ogX, ogY, worldGridSize, ogX, ogY, xWorldSize, yWorldSize, true);

        grids.add(new GridTileSelect(creatorEngine, ogX, ogY, selectGridSize, (int) (ogX + ((ogX * 2) / selectGridSize) * 10.5f), ogY, 4, selectGridSize));
    }

    public void tick() {
        uiManager.tick();
        mouseX = creatorEngine.getMouseManager().getMouseX();
        mouseY = creatorEngine.getMouseManager().getMouseY();

        gridWorldEditorBG.updateVars(gridBounds, mouseX, mouseY, SELECTED_TILE);
        worldGridSize = ogWorldGridSize + zoomSlider.getValue();
        gridWorldEditorBG.setGridSize(worldGridSize);

        gridWorldEditorFG.updateVars(gridBounds, mouseX, mouseY, SELECTED_TILE);
        gridWorldEditorFG.setGridSize(worldGridSize);

        if (fgCheckBox.isChecked()) {
            gridWorldEditorBG.setBeingUsed(false);
            gridWorldEditorBG.setShowRendered(true);

            gridWorldEditorFG.setBeingUsed(true);
            gridWorldEditorFG.setShowRendered(true);
        } else {
            gridWorldEditorBG.setBeingUsed(true);
            gridWorldEditorBG.setShowRendered(true);

            gridWorldEditorFG.setBeingUsed(false);
            gridWorldEditorFG.setShowRendered(false);
        }
        gridWorldEditorBG.tick();
        gridWorldEditorFG.tick();

        grids.forEach(Grid::tick);
    }

    public void render(Graphics2D g) {
        gridWorldEditorBG.render(g);
        Color tint = new Color(63, 63, 63, 127);
        g.setColor(tint);
        g.fillRect(0, 0, creatorEngine.getWidth(), creatorEngine.getHeight());
        gridWorldEditorFG.render(g);

        grids.forEach(grid -> grid.render(g));
    }

    public void postRender(Graphics2D g) {
        Font font = Assets.FONT_20;
        Text.drawString(g, "Zoom: x" + zoomSlider.getValue(), (int) zoomSlider.getX() + zoomSlider.getWidth() + 25, (int) zoomSlider.getY() + Text.getHeight(g, font), false, false, Color.white, font);

        Text.drawString(g, "Edit foreground", (int) (fgCheckBox.getX() + fgCheckBox.getWidth() + 5), (int) (fgCheckBox.getY() + fgCheckBox.getHeight() - 5), false, false, Color.white, font);
        uiManager.render(g);
    }

    public static Tile getSelectedTile() {
        return SELECTED_TILE;
    }

    public static void setSelectedTile(Tile selectedTile) {
        SELECTED_TILE = selectedTile;
    }

    public void setSize(int xWorldSize, int yWorldSize) {
        this.xWorldSize = xWorldSize;
        if (this.xWorldSize < minXWorldSize) this.xWorldSize = minXWorldSize;
        if (this.xWorldSize > maxXWorldSize) this.xWorldSize = maxXWorldSize;

        this.yWorldSize = yWorldSize;
        if (this.yWorldSize < minYWorldSize) this.yWorldSize = minYWorldSize;
        if (this.yWorldSize > maxYWorldSize) this.yWorldSize = maxYWorldSize;
    }
}

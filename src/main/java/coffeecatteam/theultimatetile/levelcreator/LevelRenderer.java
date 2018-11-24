package coffeecatteam.theultimatetile.levelcreator;

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
import coffeecatteam.theultimatetile.levelcreator.grid.Grid;
import coffeecatteam.theultimatetile.levelcreator.grid.GridTileSelect;
import coffeecatteam.theultimatetile.levelcreator.grid.GridWorldEditor;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.AABB;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {

    private static Tile SELECTED_TILE;

    private CreatorEngine creatorEngine;
    private int xWorldSize, yWorldSize, mouseX, mouseY;

    private AABB gridBounds;
    private int ogX, ogY, ogWorldGridSize = 10, worldGridSize = ogWorldGridSize, selectGridSize = 10;

    private List<Grid> grids = new ArrayList<>();
    private UIManager uiManager;

    private GridWorldEditor gridWorldEditorBG, gridWorldEditorFG;
    private UISlider zoomSlider;
    private UICheckBox fgCheckBox;

    public LevelRenderer(CreatorEngine creatorEngine, int xWorldSize, int yWorldSize, UIManager uiManager) {
        this.creatorEngine = creatorEngine;
        this.xWorldSize = xWorldSize;
        this.yWorldSize = yWorldSize;

        Tiles.init(creatorEngine);
        SELECTED_TILE = Tiles.GRASS;

        ogX = creatorEngine.getWidth() / 4;
        ogY = creatorEngine.getHeight() / 4;
        gridBounds = new AABB(ogX, ogY, creatorEngine.getWidth() / 2, creatorEngine.getHeight() / 2);
        initGrids();

        this.uiManager = uiManager;
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
                    JFileChooser exporter = new JFileChooser();
                    exporter.setDialogTitle("Save World");
                    exporter.setCurrentDirectory(new File("./export"));
                    exporter.setDialogType(JFileChooser.SAVE_DIALOG);
                    exporter.setMultiSelectionEnabled(true);

                    exporter.addChoosableFileFilter(new CustomFileFilter(".json", "JSON Files (*.json)"));

                    if (exporter.showSaveDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = exporter.getSelectedFile().getName();
                        String filePath = exporter.getSelectedFile().getParent();
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
                    JFileChooser exporter = new JFileChooser();
                    exporter.setDialogTitle("Save/Zip World");
                    exporter.setCurrentDirectory(new File("./export"));
                    exporter.setDialogType(JFileChooser.SAVE_DIALOG);
                    exporter.setMultiSelectionEnabled(true);

                    exporter.addChoosableFileFilter(new CustomFileFilter(".json", "JSON Files (*.json)"));

                    if (exporter.showSaveDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = exporter.getSelectedFile().getName();
                        String filePath = exporter.getSelectedFile().getParent();
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
                    JFileChooser exporter = new JFileChooser();
                    exporter.setDialogTitle("Load World");
                    exporter.setCurrentDirectory(new File("./export"));
                    exporter.setFileFilter(new DirFilter());
                    exporter.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    exporter.setAcceptAllFileFilterUsed(false);

                    if (exporter.showOpenDialog(creatorEngine.getFrame()) == JFileChooser.APPROVE_OPTION) {
                        String fileName = exporter.getSelectedFile().getName();
                        String filePath = exporter.getSelectedFile().getParent();
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

            class DirFilter extends FileFilter {
                public boolean accept(File f) {
                    return (f.isDirectory());
                }

                public String getDescription() {
                    return ("Directories");
                }
            }
        }));
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader loader = new WorldJsonLoader(null, creatorEngine);
        Tile[][] bgT = new Tile[xWorldSize][yWorldSize];
        Tile[][] fgT = new Tile[xWorldSize][yWorldSize];
        loader.loadTiles(xWorldSize, yWorldSize, path + "background.json", path + "foreground.json", bgT, fgT);

        gridWorldEditorBG.setGridFromArray(bgT, xWorldSize, yWorldSize);
        gridWorldEditorFG.setGridFromArray(fgT, xWorldSize, yWorldSize);
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

    public void render(Graphics g) {
        g.drawImage(Assets.MG_OVERLAY_INNER_MID, 0, 0, creatorEngine.getWidth(), creatorEngine.getHeight(), null);

        gridWorldEditorBG.render(g);
        Color tint = new Color(63, 63, 63, 127);
        g.setColor(tint);
        g.fillRect(0, 0, creatorEngine.getWidth(), creatorEngine.getHeight());
        gridWorldEditorFG.render(g);

        grids.forEach(grid -> grid.render(g));
    }

    public void postRender(Graphics g) {
        Font font = Assets.FONT_20;
        Text.drawString(g, "Zoom: x" + zoomSlider.getValue(), (int) zoomSlider.getX() + zoomSlider.getWidth() + 25, (int) zoomSlider.getY() + Text.getHeight(g, font), false, false, Color.white, font);

        Text.drawString(g, "Edit foreground", (int) (fgCheckBox.getX() + fgCheckBox.getWidth() + 5), (int) (fgCheckBox.getY() + fgCheckBox.getHeight() - 5), false, false, Color.white, font);
    }

    public static Tile getSelectedTile() {
        return SELECTED_TILE;
    }

    public static void setSelectedTile(Tile selectedTile) {
        SELECTED_TILE = selectedTile;
    }
}

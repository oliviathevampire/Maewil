package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.Tiles;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UICheckBox;
import coffeecatteam.theultimatetile.gfx.ui.UISlider;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonSaver;
import coffeecatteam.theultimatetile.levelcreator.grid.Grid;
import coffeecatteam.theultimatetile.levelcreator.grid.GridTileSelect;
import coffeecatteam.theultimatetile.levelcreator.grid.GridWorldEditor;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.utils.Logger;
import coffeecatteam.theultimatetile.utils.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelRenderer {

    private static Tile SELECTED_TILE;

    private CreatorEngine creatorEngine;
    private int xWorldSize, yWorldSize, mouseX, mouseY;

    private Rectangle gridBounds;
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
        gridBounds = new Rectangle(ogX, ogY, creatorEngine.getWidth() / 2, creatorEngine.getHeight() / 2);
        initGrids();

        this.uiManager = uiManager;
        initUI();
    }

    private void initUI() {
        zoomSlider = new UISlider(creatorEngine, 185, 18, 200, 2);
        uiManager.addObject(zoomSlider);

        fgCheckBox = new UICheckBox(10, 50, true);
        uiManager.addObject(fgCheckBox);

        int exportBtnWidth = 4 * 32;
        int exportBtnHeight = 32;
        uiManager.addObject(new UIButton(creatorEngine.getWidth() - exportBtnWidth - 10, 10, exportBtnWidth, exportBtnHeight, "Save World", false, Assets.FONT_20, new ClickListener() {
            @Override
            public void onClick() {
                WorldJsonSaver saver = new WorldJsonSaver(null, null, null);
                try {
                    javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    JFileChooser exporter = new JFileChooser();
                    exporter.setDialogTitle("Export World");
                    exporter.setCurrentDirectory(new File("./export"));
                    exporter.setDialogType(JFileChooser.SAVE_DIALOG);

                    exporter.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File file) {
                            String filename = file.getName();
                            return filename.endsWith(".json") || file.isDirectory();
                        }

                        @Override
                        public String getDescription() {
                            return "JSON Files (*.json)";
                        }
                    });

                    int userSelection = exporter.showSaveDialog(creatorEngine.getFrame());

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        String fileName = exporter.getSelectedFile().getName();
                        String filePath = exporter.getSelectedFile().getParent();
                        String finalPath = (filePath + "/" + fileName + "/").replace(".json", "");
                        Utils.createSaveFolder(finalPath);
                        saver.saveTiles(xWorldSize, yWorldSize, gridWorldEditorBG.convertGridToArray(), gridWorldEditorFG.convertGridToArray(), finalPath + "background", finalPath + "foreground");
                        Logger.print("Exported world!");
                    }
                } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));
    }

    private void initGrids() {
        gridWorldEditorBG = new GridWorldEditor(creatorEngine, ogX, ogY, worldGridSize, ogX, ogY, xWorldSize, yWorldSize, false);
        //grids.add(gridWorldEditorBG);
        gridWorldEditorFG = new GridWorldEditor(creatorEngine, ogX, ogY, worldGridSize, ogX, ogY, xWorldSize, yWorldSize, true);
        //grids.add(gridWorldEditorFG);

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

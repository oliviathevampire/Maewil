package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.*;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.objs.entities.Entities;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.net.URI;

/**
 * @author CoffeeCatRailway
 * Created: 17/04/2019
 */
public class StateUITesting extends State {

    private UICheckBox uiCheckBox;
    private UIButton uiButton;
    private UITextBox uiTextBox, uiTextBoxToolTip;
    private UIHyperlink uiHyperlink;
    private UISlider uiSlider;
    private UIList uiList;
    private UIDropDown uiDropDown;
    private UIInputBox uiInputBox;

    public StateUITesting(TutEngine tutEngine) {
        super(tutEngine, new Tile[]{Tiles.AIR}, new Tile[]{Tiles.AIR});

        uiManager.addObject(uiCheckBox = new UICheckBox(new Vector2D(10, 10), 60, true));

        uiManager.addObject(uiButton = new UIButton(tutEngine, new Vector2D(80, 10), "Test Button", new ClickListener() {
            @Override
            public void onClick() {
                if (uiButton.getText().equals("Test Button")) {
                    uiButton.setText("");
                    uiButton.setUseImage(true);
                } else {
                    uiButton.setText("Test Button");
                    uiButton.setUseImage(false);
                }
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));
        uiButton.setImage(new Animation(Assets.GUI_TITLE_SMALL));
        uiButton.setImgScale(0.04f);
        uiButton.setHasTooltip(true);

        uiButton.setTooltip(uiTextBoxToolTip = new UITextBox());
        uiTextBoxToolTip.addText("Test Box/Tooltip", Assets.FONTS.get("50-italic"), Color.red);
        uiTextBoxToolTip.addText("It has text! :)");

        String hyperText = "Hyperlink (Click Me!)";
        Font hyperFont = Assets.FONTS.get("45");
        uiManager.addObject(uiHyperlink = new UIHyperlink(new Vector2D(10, uiCheckBox.getHeight() + 60), Text.getHeight(hyperText, hyperFont), hyperText, hyperFont, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop.getDesktop().browse(new URI("https://coffeecatrailway.itch.io/the-ultimate-tile"));
                } catch (Exception e) {
                    TutLauncher.LOGGER.error(e);
                }
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(uiSlider = new UISlider(tutEngine, new Vector2D(10, uiHyperlink.getPosition().y + uiHyperlink.getHeight() + 20), TutLauncher.WIDTH - 40));
        uiSlider.setMinValue(-10);
        uiSlider.setMaxValue(10);

        uiManager.addObject(uiTextBox = new UITextBox(new Vector2D(100, uiSlider.getPosition().y + uiSlider.getHeight())));
        uiTextBox.addText("This be a text box!", Assets.FONTS.get("30"), Color.green);

        Vector2D listPos = new Vector2D(uiTextBox.getPosition().x + uiTextBox.getWidth() + 20d, uiTextBox.getPosition().y);
        uiManager.addObject(uiList = new UIList((float) listPos.x, (float) listPos.y, (float) (TutLauncher.WIDTH - listPos.x - 10), 5));
        uiList.add("Meow1", new ClickListener() {
            @Override
            public void onClick() {
                if (uiList.get(1).getLabel().equals("Meow2"))
                    uiList.get(1).setLabel("2woeM");
                else
                    uiList.get(1).setLabel("Meow2");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
        uiList.add("Meow2", new ClickListener() {
            @Override
            public void onClick() {
                uiList.swapTheme();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
        uiList.add("Meow3", new ClickListener() {
            @Override
            public void onClick() {
                uiList.remove(uiList.size() - 1);
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });

        ClickListener NEW_LIST_CLICK = new ClickListener() {
            @Override
            public void onClick() {
                int value = 1000000000;
                uiList.add("ListOBJ-" + NumberUtils.getRandomInt(-value, value), NumberUtils.getRandomBoolean() ? Tiles.AIR.getAnimation() : Tiles.WATER.getAnimation(), new ClickListener() {
                    @Override
                    public void onClick() {
                        uiCheckBox.setChecked(!uiCheckBox.isChecked());
                    }

                    @Override
                    public void update(GameContainer container, StateBasedGame game, int delta) {
                    }
                });
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        };
        uiList.add("Meow4", NEW_LIST_CLICK);

        uiManager.addObject(uiDropDown = new UIDropDown(10, (float) (uiTextBox.getPosition().y + uiTextBox.getHeight() + 10), 350, "Dropdown Menu"));
        uiDropDown.add("Item1");
        uiDropDown.add("Item2", Tiles.GLITCH.getAnimation());
        uiDropDown.add("Item3", Entities.PIG.getTextures().get("idle"), NEW_LIST_CLICK);
        uiDropDown.add("Item4", Entities.FOX.getTextures().get("idle"), new ClickListener() {
            @Override
            public void onClick() {
                uiDropDown.swapTheme();
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });

        uiManager.addObject(uiInputBox = new UIInputBox(new Vector2D(20 + uiDropDown.getWidth(), 500), 300));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        uiManager.update(container, game, delta);
        uiButton.setPosition(new Vector2D(TutLauncher.WIDTH - uiButton.getWidth() - 10, 10));
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        uiManager.render(container, game, g);

        if (uiCheckBox.isChecked())
            Assets.GUI_TITLE_SMALL.draw(uiCheckBox.getWidth() + 20, 10, 0.075f);
        Text.drawString(g, String.valueOf(uiSlider.getValue()), 10, (float) (uiSlider.getPosition().y + uiSlider.getHeight() + 40), false, Color.white, Assets.FONTS.get("35"));
        Text.drawString(g, uiInputBox.getText(), (float) uiInputBox.getPosition().x, (float) (uiInputBox.getPosition().y + uiInputBox.getHeight() + 10), false, Color.white, Assets.FONTS.get("35"));
    }
}

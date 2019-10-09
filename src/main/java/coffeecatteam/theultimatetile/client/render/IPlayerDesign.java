package coffeecatteam.theultimatetile.client.render;

import coffeecatteam.theultimatetile.utils.ApiInternal;
import coffeecatteam.theultimatetile.utils.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IPlayerDesign {

    @ApiInternal
    Identifier EYES = new Identifier("player.base.eyes");

    @ApiInternal
    List<Identifier> BASE = Arrays.asList(
            new Identifier("player.base.1"),
            new Identifier("player.base.2"),
            new Identifier("player.base.3"),
            new Identifier("player.base.4"),
            new Identifier("player.base.5")
    );

    List<Identifier> SHIRT = new ArrayList<>(Arrays.asList(
            new Identifier("player.shirt.1"),
            new Identifier("player.shirt.2"),
            new Identifier("player.shirt.3"),
            new Identifier("player.shirt.4"),
            null
    ));

    @ApiInternal
    List<Identifier> ARMS = Arrays.asList(
            new Identifier("player.arm.skin_1"),
            new Identifier("player.arm.skin_2"),
            new Identifier("player.arm.skin_3"),
            new Identifier("player.arm.skin_4"),
            new Identifier("player.arm.skin_5")
    );

    List<Identifier> SLEEVES = new ArrayList<>(Arrays.asList(
            new Identifier("player.sleeve.1"),
            new Identifier("player.sleeve.2"),
            new Identifier("player.sleeve.3"),
            new Identifier("player.sleeve.4"),
            null
    ));

    List<Identifier> PANTS = new ArrayList<>(Arrays.asList(
            new Identifier("player.pants.1"),
            new Identifier("player.pants.2"),
            null
    ));

    List<Identifier> FOOTWEAR = new ArrayList<>(Arrays.asList(
            new Identifier("player.footwear.1"),
            new Identifier("player.footwear.2"),
            new Identifier("player.footwear.3"),
            new Identifier("player.footwear.4"),
            new Identifier("player.footwear.5"),
            null
    ));

    List<Identifier> HAIR = new ArrayList<>(Arrays.asList(
            new Identifier("player.hair.1"),
            new Identifier("player.hair.2"),
            new Identifier("player.hair.3"),
            new Identifier("player.hair.4"),
            new Identifier("player.hair.5"),
            new Identifier("player.hair.6"),
            new Identifier("player.hair.7"),
            new Identifier("player.hair.8"),
            new Identifier("player.hair.9"),
            null
    ));

    List<Identifier> ACCESSORIES = new ArrayList<>(Arrays.asList(
            null,
            new Identifier("player.accessory.monocle_1"),
            new Identifier("player.accessory.monocle_2"),
            new Identifier("player.accessory.sunglasses_1"),
            new Identifier("player.accessory.sunglasses_2"),
            new Identifier("player.accessory.sunglasses_3"),
            new Identifier("player.accessory.glasses_1"),
            new Identifier("player.accessory.glasses_2"),
            new Identifier("player.accessory.glasses_3"),
            new Identifier("player.accessory.eyeliner")
    ));

    List<Identifier> EYEBROWS = new ArrayList<>(Arrays.asList(
            new Identifier("player.eyebrows.1"),
            new Identifier("player.eyebrows.2"),
            new Identifier("player.eyebrows.3"),
            new Identifier("player.eyebrows.4"),
            new Identifier("player.eyebrows.5"),
            new Identifier("player.eyebrows.6"),
            null
    ));

    List<Identifier> MOUTH = new ArrayList<>(Arrays.asList(
            null,
            new Identifier("player.mouth.1"),
            new Identifier("player.mouth.2"),
            new Identifier("player.mouth.3"),
            new Identifier("player.mouth.4"),
            new Identifier("player.mouth.5"),
            new Identifier("player.mouth.6"),
            new Identifier("player.mouth.7"),
            new Identifier("player.mouth.8"),
            new Identifier("player.mouth.9"),
            new Identifier("player.mouth.10")
    ));

    List<Identifier> BEARD = new ArrayList<>(Arrays.asList(
            null,
            new Identifier("player.beard.1"),
            new Identifier("player.beard.2")
    ));

    int getFavoriteColor();

    @ApiInternal
    void setFavoriteColor(int color);

    String getName();

    @ApiInternal
    void setName(String name);

    int getBase();

    @ApiInternal
    void setBase(int base);

    int getEyeColor();

    @ApiInternal
    void setEyeColor(int eyeColor);

    int getShirt();

    @ApiInternal
    void setShirt(int shirt);

    int getShirtColor();

    @ApiInternal
    void setShirtColor(int shirtColor);

    int getSleeves();

    @ApiInternal
    void setSleeves(int sleeves);

    int getSleevesColor();

    @ApiInternal
    void setSleevesColor(int sleevesColor);

    int getPants();

    @ApiInternal
    void setPants(int pants);

    int getPantsColor();

    @ApiInternal
    void setPantsColor(int pantsColor);

    int getFootwear();

    @ApiInternal
    void setFootwear(int footwear);

    int getFootwearColor();

    @ApiInternal
    void setFootwearColor(int footwearColor);

    int getHair();

    @ApiInternal
    void setHair(int hair);

    int getHairColor();

    @ApiInternal
    void setHairColor(int hairColor);

    int getAccessory();

    @ApiInternal
    void setAccessory(int accessory);

    int getEyebrows();

    @ApiInternal
    void setEyebrows(int eyebrows);

    int getMouth();

    @ApiInternal
    void setMouth(int mouth);

    int getBeard();

    @ApiInternal
    void setBeard(int beard);

    int getBeardColor();

    @ApiInternal
    void setBeardColor(int beardColor);

    int getEyebrowsColor();

    @ApiInternal
    void setEyebrowsColor(int eyebrowsColor);

    boolean isFemale();

    @ApiInternal
    void setFemale(boolean female);
}
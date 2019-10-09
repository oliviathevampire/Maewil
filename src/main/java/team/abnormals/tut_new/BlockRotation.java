package team.abnormals.tut_new;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum BlockRotation {
   NONE,
   CLOCKWISE_90,
   CLOCKWISE_180,
   COUNTERCLOCKWISE_90;

   public BlockRotation rotate(BlockRotation blockRotation_1) {
      switch(blockRotation_1) {
      case CLOCKWISE_180:
         switch(this) {
         case NONE:
            return CLOCKWISE_180;
         case CLOCKWISE_90:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_180:
            return NONE;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_90;
         }
      case COUNTERCLOCKWISE_90:
         switch(this) {
         case NONE:
            return COUNTERCLOCKWISE_90;
         case CLOCKWISE_90:
            return NONE;
         case CLOCKWISE_180:
            return CLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return CLOCKWISE_180;
         }
      case CLOCKWISE_90:
         switch(this) {
         case NONE:
            return CLOCKWISE_90;
         case CLOCKWISE_90:
            return CLOCKWISE_180;
         case CLOCKWISE_180:
            return COUNTERCLOCKWISE_90;
         case COUNTERCLOCKWISE_90:
            return NONE;
         }
      default:
         return this;
      }
   }

   public Direction rotate(Direction direction_1) {
      if (direction_1.getAxis() == Direction.Axis.Y) {
         return direction_1;
      } else {
         switch(this) {
         case CLOCKWISE_90:
            return direction_1.rotateYClockwise();
         case CLOCKWISE_180:
            return direction_1.getOpposite();
         case COUNTERCLOCKWISE_90:
            return direction_1.rotateYCounterclockwise();
         default:
            return direction_1;
         }
      }
   }

   public int rotate(int int_1, int int_2) {
      switch(this) {
      case CLOCKWISE_90:
         return (int_1 + int_2 / 4) % int_2;
      case CLOCKWISE_180:
         return (int_1 + int_2 / 2) % int_2;
      case COUNTERCLOCKWISE_90:
         return (int_1 + int_2 * 3 / 4) % int_2;
      default:
         return int_1;
      }
   }

   public static BlockRotation random(Random random_1) {
      BlockRotation[] blockRotations_1 = values();
      return blockRotations_1[random_1.nextInt(blockRotations_1.length)];
   }

   public static List<BlockRotation> randomRotationOrder(Random random_1) {
      List<BlockRotation> list_1 = Lists.newArrayList(values());
      Collections.shuffle(list_1, random_1);
      return list_1;
   }
}

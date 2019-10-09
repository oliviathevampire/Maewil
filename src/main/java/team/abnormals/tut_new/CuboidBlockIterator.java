package team.abnormals.tut_new;

public class CuboidBlockIterator {
   private final int startX;
   private final int startY;
   private final int startZ;
   private final int endX;
   private final int endY;
   private final int endZ;
   private int x;
   private int y;
   private int z;
   private boolean complete;

   public CuboidBlockIterator(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6) {
      this.startX = int_1;
      this.startY = int_2;
      this.startZ = int_3;
      this.endX = int_4;
      this.endY = int_5;
      this.endZ = int_6;
   }

   public boolean step() {
      if (!this.complete) {
         this.x = this.startX;
         this.y = this.startY;
         this.z = this.startZ;
         this.complete = true;
         return true;
      } else if (this.x == this.endX && this.y == this.endY && this.z == this.endZ) {
         return false;
      } else {
         if (this.x < this.endX) {
            ++this.x;
         } else if (this.y < this.endY) {
            this.x = this.startX;
            ++this.y;
         } else if (this.z < this.endZ) {
            this.x = this.startX;
            this.y = this.startY;
            ++this.z;
         }

         return true;
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public int method_20789() {
      int int_1 = 0;
      if (this.x == this.startX || this.x == this.endX) {
         ++int_1;
      }

      if (this.y == this.startY || this.y == this.endY) {
         ++int_1;
      }

      if (this.z == this.startZ || this.z == this.endZ) {
         ++int_1;
      }

      return int_1;
   }
}

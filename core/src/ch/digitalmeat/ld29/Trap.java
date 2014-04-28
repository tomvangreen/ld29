package ch.digitalmeat.ld29;

public class Trap {
   private boolean wasPressed;
   private boolean down;
   private boolean pressed;
   private boolean up;

   public void update(boolean isPressed) {
      up = false;
      down = false;
      if (isPressed && !wasPressed) {
         down = true;
      } else if (!isPressed && wasPressed) {
         up = true;
      }
      wasPressed = pressed;
      pressed = isPressed;
   }

   public boolean pressed() {
      return pressed;
   }

   public boolean down() {
      return down;
   }

   public boolean up() {
      return up;
   }
}

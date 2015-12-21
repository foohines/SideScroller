package src.game.mechanics;

/**
 * Created by Foster on 12/1/2015.
 */
public class HitBox extends Box {
        private Position position;
        private int cushion;
        private int offsetX2; //Horizontal distance from position to right edge of specialty box
        private int offsetY2; //Vertical distance from position to bottom edge of specialty box

        public HitBox(int width, int height, Position position, int cushion) {
            this.position = position;
            this.cushion = cushion;
            this.width = width - 1 - 2*cushion;
            this.height = height - 1 -2*cushion;

            offsetX2 = (int) width - cushion;
            offsetY2 = (int) height - cushion;

        }


        public int getX1(){
            return (int) position.getX() + cushion;
        }
        public int getY1(){
            return (int) position.getY() + cushion;
        }

        public int getX2() {
            return (int) position.getX() + offsetX2;
        }

        public int getY2() {
            return (int) position.getY() + offsetY2;

        }

}

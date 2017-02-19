package piotr.fr.nosnooze.dataobjects;

import piotr.fr.nosnooze.R;

/**
 * Created by Piotr on 08/03/2015.
 */
public class ColoredShape {

    public static enum Color {

        GREEN(0, "green", R.string.vert), BLUE(1, "blue", R.string.bleu),
        WHITE(2, "white", R.string.blanc), RED(3, "red", R.string.rouge);

        private int id;
        private String name;
        public int descritpionResource;

        private Color(int id, String name, int descritpionResource){
            this.id=id;
            this.name=name;
            this.descritpionResource=descritpionResource;
        }

        public static Color valueOf(int id){
            for(Color color:values()){
                if(color.id==id){
                    return color;
                }
            }
            return null;
        }

    }

    public static enum Shape {
        CIRCLE(0, "circle", R.string.cercle), SQUARE(1, "square", R.string.carre);

        private int id;
        private String name;
        public int descritpionResource;

        private Shape(int id, String name, int descritpionResource){
            this.id=id;
            this.name=name;
            this.descritpionResource=descritpionResource;
        }

        public static Shape valueOf(int id){
            for(Shape shape:values()){
                if(shape.id==id){
                    return shape;
                }
            }
            return null;
        }
    }

    public Color color;

    public Shape shape;

    public ColoredShape() {
        shake();
    }

    public ColoredShape(Color color, Shape shape) {
        this.color = color;
        this.shape = shape;
    }

    public void shake(){
        this.color = Color.valueOf(random(Color.values().length));
        this.shape = Shape.valueOf(random(Shape.values().length));
    }

    private int random(int seed){
        return (int) (Math.random()*seed);
    }

    public String getResourceName(){
        return color.name + "_" + shape.name;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ColoredShape){
            ColoredShape other = (ColoredShape)o;
            return other.color==color && other.shape==shape;
        }
        return false;
    }
}

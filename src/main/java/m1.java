public class m1 {

    public static void main(String[] args) {

        ElementsOnBoard el = new ElementsOnBoard();
        el.addRandomWall();
        el.addRandomWall();
        el.addRandomWall();
        SnakeModel model = new SnakeModel(0.0f, 0.0f);
        model.change(0);
        model.change(1);
        Functions f1 = new Functions();
        f1.isWallForSnake(el, model.getHeadXAndY());
        

    }
}

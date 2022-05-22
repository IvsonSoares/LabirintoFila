public class Celula {
    public int x;
    public int y;
    public boolean wall = false;
    public char character = ' ';
    public String movement = "";

    public Celula(int x, int y){
        this.x = x;
        this.y = y;
        isWall();
    }

    public void isWall(){
        double rand = Math.random();
        if(rand < 0.2){
            this.wall = true;
            this.character = '#';
        }
    }
    
}

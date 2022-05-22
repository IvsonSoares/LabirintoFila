import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class App {
    static int count = 0;
    public static void main(String[] args) throws Exception {

        Queue<Celula> fila = new LinkedList<>();

        int cols = 10;
        int rows = 10;

        Celula start = new Celula(0, 0);
        start.character = 'S';
        start.wall = false;

        Celula end = new Celula(8, 8);
        end.character = 'E';
        end.wall = false;


        ArrayList<ArrayList<Celula>> labirinto = setLabirinto(rows, cols, start, end);

        //Play
        while(gamePlay()){
            printLabirinto(rows, cols, labirinto);
            menuDirections(start, end, fila, labirinto);
        }
    }


    //Construindo o Labirinto
    public static ArrayList<ArrayList<Celula>> setLabirinto(int rows, int cols, Celula start, Celula end){
        ArrayList<ArrayList<Celula>> labirinto = new ArrayList<ArrayList<Celula>>();

        for(int i = 0; i < rows; i++){
            labirinto.add(new ArrayList<Celula>());
        }

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(i == start.x && j == start.y){
                    labirinto.get(i).add(start);
                } else if (i == end.x && j == end.y){
                    labirinto.get(i).add(end);
                } else {
                    labirinto.get(i).add(new Celula(i, j));
                }
            }
        }
        return labirinto;
    }

    //Menu
    public static void menuDirections(Celula start, Celula end, Queue<Celula> fila, ArrayList<ArrayList<Celula>> labirinto){
        System.out.println(String.format("""
Programe seus passos para chegar ate o 'E':
Comandos enfileirados: %s 
Comandos Disponiveis:            
-> direita - d
-> esquerda - a
-> cima - w
-> baixo - s
-> comecar - c
-> reiniciar - r""", fila.size()));

        Scanner entrada = new Scanner(System.in);
        char cmd = entrada.next().charAt(0);

        move(cmd, start, end, fila, labirinto);
    }

    public static void move(char cmd, Celula start, Celula end, Queue<Celula> fila, ArrayList<ArrayList<Celula>> labirinto){

        switch(cmd){
            case 'd':
                start.x++;
                addToQueue(fila, "right", labirinto.get(start.y).get(start.x));
                break;
            case 'a':
                start.x--;
                addToQueue(fila, "left", labirinto.get(start.y).get(start.x));
                break;
            case 'w':
                start.y--;
                addToQueue(fila, "up", labirinto.get(start.y).get(start.x));
                break;
            case 's':
                start.y++;
                addToQueue(fila, "down", labirinto.get(start.y).get(start.x));
                break;
            case 'c':
                printQueue(fila, labirinto);
                if(start.x == end.x && start.y == end.y){
                    System.out.println("You Win");
                } else {
                    System.out.println("You Lose");
                }
                System.exit(0);
                break;
            case 'r':
                //esvaziar a pilha de comando e voltar ao inicio
                start.x = 0;
                start.y = 0;
                fila.clear();
                System.out.println("Sua fila foi esvaziada!");
                App.count = 0;
                break;
            default:
                System.out.println("Entrada Invalida");
                break;    
        }
    }

    public static boolean gamePlay(){
        return true;
    }

    public static void addToQueue(Queue<Celula> fila, String movement, Celula elt){
        if(elt.wall == true){
          //Wall
          System.out.println(String.format("""
Comando: %s                   
Movimento invalido!
Melhor tentar novamente!""", movement));
          System.exit(0);        
        } else {
          //Not wall
          elt.movement = movement;
          fila.add(elt);
        }
        
    }

    public static void printQueue(Queue<Celula> fila, ArrayList<ArrayList<Celula>> labirinto){
        Iterator<Celula> iterator = fila.iterator();
        while(iterator.hasNext()) {
            App.count++;
            Celula element = iterator.next();
            element.character = '.';
            labirinto.get(element.x).set(element.y, element);
            System.out.println(String.format("Comando: %s - %s", App.count, element.movement));
            //rows 10
            //cols 10
            printLabirinto(labirinto.size(), labirinto.get(element.x).size(), labirinto);
        }
    }

    public static void printLabirinto(int rows, int cols, ArrayList<ArrayList<Celula>> labirinto){
        //Printando Labirinto Inicial
        System.out.println("\n===========LABIRINTO==============");
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                System.out.print(labirinto.get(i).get(j).character);
            }
            System.out.println();
        }
        System.out.println("=========================");
    }
}

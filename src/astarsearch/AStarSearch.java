package astarsearch;

import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Arrays;

public class AStarSearch {

    Node[][] board = new Node[15][15]; 
    Node StartNode;
    Node GoalNode;
    Node unpathableNode;
    
    public static void main(String[] args) {
        AStarSearch AStar = new AStarSearch();
        AStar.unpathableNodes();
        
        Scanner scnr = new Scanner(System.in);
        System.out.println("Please enter a starting node (i,e: 1,2)");
        String[] input = scnr.nextLine().split(",");
        String row = input[0];
        String column = input[1];
        int sRow = Integer.parseInt(row);
        int sColumn = Integer.parseInt(column);
        AStar.setStart(sRow, sColumn);
        
        System.out.println("Please enter a target node (i,e: 6,14)");
        String[] input2 = scnr.nextLine().split(",");
        String row2 = input2[0];
        String column2 = input2[1];
        int tRow = Integer.parseInt(row2);
        int tColumn = Integer.parseInt(column2);
        AStar.setGoal(tRow, tColumn);
        
        //Generate algorithm
        AStar.AStarSearch();
        
        //Testing
        //System.out.println(AStar.printBoard());
        //AStar.fTest();
    }
    //Testing method
    public void hTest() {
        System.out.println(calcH(StartNode));
    }
    
    //Testing method
    public void fTest() {
        StartNode.setG(calcG(StartNode));
        StartNode.setH(calcH(StartNode));
        StartNode.setF();
        System.out.println(StartNode.getF());
    }
    
//Constructor to generate board
    public AStarSearch() {
        for(int i =0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                 board[i][j] = new Node(i,j, 0);
            }
        }
    }
    
    //Generates unpathable nodes
    public void unpathableNodes() {
        for(int i =0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                 Random rand = new Random();
                 int value = rand.nextInt(100);
                 
                 if (value <= 10) {
                     board[i][j] = new Node(i,j, 1);
                 }
            }
        }
    }
    
    //Places Start Node
    public void setStart(int i, int j) { 
        StartNode = new Node(i,j, 2);
        board[i][j] = StartNode;
        
    }
    
    //Places Goal Node
    public void setGoal(int i, int j){
        GoalNode = new Node(i,j, 3);
        board[i][j] = GoalNode;
        
    }
    
    //A Star method
    public void AStarSearch() {
        Node currentNode = StartNode;
        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashMap<Node,Node> closedList = new HashMap<>();
        
        openList.add(StartNode);
        StartNode.setParent(null);
        StartNode.setG(calcG(StartNode));
        StartNode.setH(calcH(StartNode));
        StartNode.setF();
        
        //START LOOP
        while(!openList.isEmpty()) {
            if(currentNode.equals(GoalNode)) {
                System.out.println("Path found!");
                break;
            } else {
            //Find node with lowest F score
            for(Node testNode : openList) {   
                testNode.setG(calcG(testNode));
                testNode.setH(calcH(testNode));
                testNode.setF();
                testNode.setParent(currentNode);
                
                if (testNode.getF() < currentNode.getF()) { 
                    currentNode = testNode;         
                }
                
            }
                currentNode = openList.poll();
                closedList.put(currentNode, currentNode.getParent());
                
             
                int i = currentNode.getRow();
                int j = currentNode.getCol();
                
                
                //***********EDGES CHECKING/GENERATING NEIGHBORS************
  
                //CHECKING NON EDGE NODES!
                if(j != 0 && j != 15 && i != 0 && i != 15) {
                    Node N1 = board[i][j+1];
                    if(!closedList.containsKey(N1) && N1.getT() != 1 && !openList.contains(N1)) {
                        openList.add(N1);
                    }
                    
                    Node N2 = board[i][j-1];
                    if(!closedList.containsKey(N2) && N2.getT() != 1 && !openList.contains(N2)) {
                        openList.add(N2);
                    }
                    
                    Node N3 = board[i+1][j];
                    if(!closedList.containsKey(N3) && N3.getT() != 1 && !openList.contains(N3)) {
                        openList.add(N3);
                    }
                    
                    Node N4 = board[i-1][j];
                    if(!closedList.containsKey(N4) && N4.getT() != 1 && !openList.contains(N4)) {
                        openList.add(N4);
                    }
                }
                //Checking for Edges on col == 0
                else if (j == 0) {
                    //Node N1 = new Node(i,j+1, board[i][j+1].getT());
                    Node N1 = board[i][j+1];
                    if(!closedList.containsKey(N1) && N1.getT() != 1 && !openList.contains(N1)) {
                        openList.add(N1);
                    }
                    
                    //Checking for if R != 0
                    if(i != 0) {
                        Node N2 = board[i-1][j];
                            if(!closedList.containsKey(N2) && N2.getT() != 1 && !openList.contains(N2)) {
                            openList.add(N2);
                        }
                    }
                    
                    //Checking for if Row != 15
                    if(i != 15) {
                        Node N3 = board[i+1][j];
                            if(!closedList.containsKey(N3) && N3.getT() != 1 && !openList.contains(N3)) {
                            openList.add(N3);
                        }
                    }
                }
                
                //Checking for Edges on col == 15
                else if (j == 15) {
                    Node N1 = board[i][j-1];
                    if(!closedList.containsKey(N1) && N1.getT() != 1 && !openList.contains(N1)) {
                        openList.add(N1);
                    }
                    
                    //Checking for if R != 0
                    if(i != 0) {
                        Node N2 = board[i+1][j];
                            if(!closedList.containsKey(N2) && N2.getT() != 1 && !openList.contains(N2)) {
                            openList.add(N2);
                        }
                    }
                    
                    //Checking for if Row != 15
                    if(i != 15) {
                        Node N3 = board[i-1][j];
                            if(!closedList.containsKey(N3) && N3.getT() != 1 && !openList.contains(N3)) {
                            openList.add(N3);
                        }
                    }
                }
                
                //Checking for Edges on Row == 0
                else if (i == 0) {
                    Node N1 = board[i+1][j];
                    if(!closedList.containsKey(N1) && N1.getT() != 1 && !openList.contains(N1)) {
                        openList.add(N1);
                    }
                    
                    //Checking for if R != 0
                    if(j != 0) {
                        Node N2 = board[i][j-1];
                            if(!closedList.containsKey(N2) && N2.getT() != 1 && !openList.contains(N2)) {
                            openList.add(N2);
                        }
                    }
                    
                    //Checking for if Row != 15
                    if(j != 15) {
                        Node N3 = board[i][j+1];
                            if(!closedList.containsKey(N3) && N3.getT() != 1 && !openList.contains(N3)) {
                            openList.add(N3);
                        }
                    }
                }
                
                //Checking for Edges on Row == 15
                else if (i == 15) {
                    Node N1 = board[i-1][j];
                    if(!closedList.containsKey(N1) && N1.getT() != 1 && !openList.contains(N1)) {
                        openList.add(N1);
                    }
                    
                    //Checking for if R != 0
                    if(j != 0) {
                        Node N2 = board[i][j+1];
                            if(!closedList.containsKey(N2) && N2.getT() != 1 && !openList.contains(N2)) {
                            openList.add(N2);
                        }
                    }
                    
                    //Checking for if Row != 15
                    if(j != 15) {
                        Node N3 = board[i][j-1];
                            if(!closedList.containsKey(N3) && N3.getT() != 1 && !openList.contains(N3)) {
                            openList.add(N3);
                        }
                    }
                }
                
                //If you find neighbor again, compare g scores. If new g score is lower change scores and parent node
                
                
                
                //***********EDGES CHECKING/GENERATING NEIGHBORS COMPLETE************
            
                
            }       
            //Add currentnode to closedList
            System.out.println("\n*******BOARD******");
            System.out.println(printBoard());
            System.out.print("Current F value: " + currentNode.getF() + "\n");
            System.out.println("OpenList: " + Arrays.toString(openList.toArray()));
            //System.out.println("ClosedList: " + Arrays.toString(closedList));
            System.out.println("CurrentNode: " + currentNode.toString());
                
            openList.remove(currentNode);
            
        }
        
        
    }
    
    //Prints path from goal to start
    public static void printPath(Node goal) {
        
    }
    
    public int compareNode(Node x, Node y) {
        if (x.getF() > y.getF()) {
            return 1;
        } else if (x.getF() < y.getF()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    //Calculate H value
    //H is the cost from current node to goal node
    public int calcH(Node current) {
        int H = ((Math.abs(current.getRow() - GoalNode.getRow()) + Math.abs(current.getCol() - GoalNode.getCol())) *10);
        return H;
    }
    
    //Calculate G value
    //G is the cost of the path from start to current node
    public int calcG(Node x) {
        int G = ((Math.abs(StartNode.getRow() - x.getRow()) + Math.abs(StartNode.getCol() - x.getCol())) *10);
        return G;
    }
    
    
    //Printing method
    public String printBoard() {

        String test = "";
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                test += " " + board[i][j].getT();
            }
            test += "\n";
        }
        return test;
    }
}

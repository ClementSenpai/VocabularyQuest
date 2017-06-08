package com.nf28.ressource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by nmeignot on 08/06/2017.
 */
public class MapTemplate {

    public int[][] generateNextFloor(int floor){
        int map[][] = new int[11][11];
        boolean visitedmap[][] = new boolean[11][11];
        Stack<int[]> visited = new Stack<int[]>();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map.length;j++){
                if(j%2==0 && i%2==0)
                    map[i][j]=0;
                else
                    map[i][j]=1;
                visitedmap[i][j]=false;
            }

        }
        int EntryPositionHeight =0;
        int EntryPositionWidth = 0;
        do{
            EntryPositionHeight = new Random().nextInt(map.length);
            EntryPositionWidth = new Random().nextInt(map.length);

        }while(map[EntryPositionHeight][EntryPositionWidth] != 0);

        map[EntryPositionHeight][EntryPositionWidth] = 3;
        visited.add(new int[]{EntryPositionHeight,EntryPositionWidth});

        while (visited.size()>0){
            List <int[]>neighbors = getNeighbors(map,visitedmap,visited.peek());
            if(neighbors.size()>0) {
                int[] nextcell =neighbors.get(new Random().nextInt(neighbors.size()));
                int[] wall =breakwall(visited.peek(),nextcell);
                map[wall[0]][wall[1]]=0;
                visited.add(nextcell);
                visitedmap[nextcell[0]][nextcell[1]]=true;

            }else{
                visited.pop();

            }

        }
        addExit(map);
        addMonster(map,floor);
        return map;
    }

    public int[][] addMonster(int[][] map, int floor){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map.length;j++){
                if (map[i][j]==0){
                    int test =new Random().nextInt(100);
                    if (test <= 10+floor){
                        map[i][j]=2;
                    }
                }
            }
        }
        return map;
    }
    public int[][] addExit(int [][] map){
        int height=0;
        int width=0;
        do{
            height = new Random().nextInt(map.length);
            width = new Random().nextInt(map.length);
        }while(map[height][width] != 0);
        map[height][width]=4;
        return map;
    }
    public void aff(int[][] map){
        for(int i=0;i<map.length;i++){
            System.out.print("-");
        }
        System.out.println();
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map.length;j++){
                if(j==0)
                    System.out.print("-");
                if(map[i][j]==0)
                    System.out.print(" ");
                if(map[i][j]==3)
                    System.out.print("O");
                if(map[i][j]==4)
                    System.out.print("X");
                if(map[i][j]==2)
                    System.out.print("s");
                if(map[i][j]==1)
                    System.out.print("#");
                if(j==map.length-1)
                    System.out.print("-");
            }
            System.out.println();
        }
        for(int i=0;i<map.length;i++){
            System.out.print("-");
        }
        System.out.println();
        System.out.println();
        System.out.println();

    }
    List<int[] >getNeighbors(int[][] map,boolean[][] visitedmap,int[] current){
        int height = current[0];
        int width = current[1];
        List<int[] > neighbor = new ArrayList<int[]>();
        if(height-2>=0)
            if(!visitedmap[height-2][width])
                neighbor.add(new int[]{height-2,width});
        if(height +2<11)
            if(!visitedmap[height+2][width])
                neighbor.add(new int[]{height+2,width});
        if(width-2>=0)
            if(!visitedmap[height][width-2])
                neighbor.add(new int[]{height,width-2});
        if(width +2<11)
            if(!visitedmap[height][width+2])
                neighbor.add(new int[]{height,width+2});
        return neighbor;
    }

    public int[] breakwall(int[] a,int[] b) {
        if(a[0]>b[0])
            return new int[]{a[0]-1,a[1]};
        if(a[0]<b[0])
            return new int[]{a[0]+1,a[1]};
        if(a[1]>b[1])
            return new int[]{a[0],a[1]-1};
        if(a[1]<b[1])
            return new int[]{a[0],a[1]+1};
        return null;
    }

    public static void main(String[] args) {
        new MapTemplate().aff(new MapTemplate().generateNextFloor(0));
    }

    // 0 plain
    // 1 wall
    // 2 monster
    // 3 start
    // 4 nextfloor
}

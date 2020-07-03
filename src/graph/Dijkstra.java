package graph;

import java.util.Arrays;
import java.util.Scanner;

public class Dijkstra {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[][] allDistance = new int[n][n];
        for (int i = 0; i < n; i++) {
            int[] allDistanceFromI = allDistance[i];
            Arrays.fill(allDistanceFromI, Integer.MAX_VALUE);
            allDistanceFromI[i] = 0;
        }

        int pathCount = scanner.nextInt();
        for (int i = 0; i < pathCount; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int dis = scanner.nextInt();
            allDistance[x][y] = allDistance[y][x] = dis;
        }

        int start = scanner.nextInt();
        int end = scanner.nextInt();

        boolean[] isProcessed = new boolean[n];
        int[] distanceFromStart = Arrays.copyOf(allDistance[start], n);
        int[] result = new int[n];
        int[] prev = new int[n];

        isProcessed[start] = true;
        Arrays.fill(prev, start);
        prev[start] = -1;

        int pathEnd = 1;
        for (; pathEnd < n; pathEnd++) {
            int nearestPoint = -1;
            int minDistance = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (isProcessed[j]) {
                    continue;
                }

                int dis = distanceFromStart[j];
                if (dis < minDistance) {
                    minDistance = dis;
                    nearestPoint = j;
                }
            }

            result[nearestPoint] = minDistance;
            isProcessed[nearestPoint] = true;

            int p = nearestPoint;
            int distanceFromStartToP = distanceFromStart[p];
            int[] distanceFromP = allDistance[p];
            for (int j = 0; j < n; j++) {
                if (!isProcessed[j] && distanceFromP[j] < Integer.MAX_VALUE) {
                    int distanceByP = distanceFromStartToP + distanceFromP[j];
                    if (distanceByP < distanceFromStart[j]) {
                        distanceFromStart[j] = distanceByP;
                        prev[j] = p;
                    }
                }
            }
        }

        for (int i = 0; i < pathEnd; i++) {
            System.out.print(result[i]);
            System.out.print(' ');
        }
        System.out.println();

        int p = end;
        int from;
        System.out.print(p);
        while ((from = prev[p]) != -1) {
            System.out.print(" -> ");
            System.out.print(from);

            p = from;
        }
    }

}

package com.tcgogogo.flipgame;

import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by tcgogogo on 17/8/21.
 */
public class GameHelper {

    public static final int[] opr = {
        51200,58368,29184,12544,
        35968,20032,10016,4880,
        2248,1252,626,305,
        140,78,39,19
    };

    private static boolean[] vis = new boolean[1 << 17];

    static class NODE {
        int sta;
        int step;

        NODE(int sta, int step) {
            this.sta = sta;
            this.step = step;
        }
    }

    private static Queue<NODE> q = new LinkedList<>();

    public static int canWin(int state) {
        Arrays.fill(vis, false);
        while (!q.isEmpty()) {
            q.poll();
        }
        NODE cur = new NODE(state, 0);
        q.add(cur);
        vis[cur.sta] = true;
        while (!q.isEmpty()) {
            cur = q.poll();
            if (cur.sta == 0 || cur.sta == 0xffff) {
                return cur.step;
            }
            for (int i = 0; i < MainActivity.TOT; i ++) {
                NODE nxt = new NODE(cur.sta ^ opr[i], cur.step + 1);
                if (!vis[nxt.sta]) {
                    vis[nxt.sta] = true;
                    q.add(nxt);
                }
            }
        }
        return -1;
    }
}

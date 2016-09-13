package com.cci.projectx.core.service.impl;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 33303 on 2016/9/5.
 */
public class test {
    public static void main(String[] args) {
        //需要计算的坐标
        double p_x =106.352495;
        double p_y =29.431426;
        Point2D.Double point = new Point2D.Double(p_x, p_y);
        //需要计算的区域
        List<Point2D.Double> pointList = new ArrayList<>();
        Point2D.Double polygonPoint1 = new Point2D.Double(106.27258200000000000,29.76556700000000000);
        pointList.add(polygonPoint1);

        Point2D.Double polygonPoint3 = new Point2D.Double(106.27315700000000000, 29.38108300000000000);
        pointList.add(polygonPoint3);

        Point2D.Double polygonPoint2 = new Point2D.Double(106.86359500000000000,29.75252400000000000);
        pointList.add(polygonPoint2);

        Point2D.Double polygonPoint4 = new Point2D.Double(106.84577300000000000, 29.37806100000000000);
        pointList.add(polygonPoint4);

        System.out.println(checkWithJdkGeneralPath(point,pointList));
    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param point
     * @param polygon
     * @return
     */
    public static boolean checkWithJdkGeneralPath(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();

        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            p.lineTo(d.x, d.y);
        }
        p.lineTo(first.x, first.y);
        p.closePath();
        return p.contains(point);

    }
}

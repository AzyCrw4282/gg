/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team142.gg.server.model.GameMap;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
public class MovableElement extends PlaceableElement {

    @Getter
    @Setter
    private double speed;

    @JsonIgnore
    @Getter
    @Setter
    private boolean walkOnWater;

    public static final float BASE_ROTATE = (float) Math.toRadians(1.25);
    public static final float MAX_ROTATE = (float) (Math.PI * 2);

    public MovableElement(SpaceTimePoint point, String skin, double speed, int tag) {
        super(point, skin, tag);
        this.speed = speed;
    }

    public void rotateLeft(float radians) {
        getPoint().setRotation(getPoint().getRotation() - radians);
        if (getPoint().getRotation() < 0) {
            getPoint().setRotation(MAX_ROTATE - getPoint().getRotation());
        }

    }

    public void rotateLeft() {
        rotateLeft(BASE_ROTATE);
    }

    public void rotateRight() {
        if (getPoint().getRotation() >= (MAX_ROTATE - BASE_ROTATE)) {
            getPoint().setRotation(0);
        } else {
            getPoint().setRotation(getPoint().getRotation() + BASE_ROTATE);
        }
    }

    public boolean moveForward(GameMap map) {
        return move(map, getPoint().getRotation());
    }

    public boolean moveBackward(GameMap map) {
        return move(map, getReversedDirection());
    }

    private boolean move(GameMap map, double rotation) {
        boolean hasMoved = false;

        double coefficientX = Math.sin(rotation);
        double coefficientZ = Math.cos(rotation);

        double amountChangeX = coefficientX * getSpeed();
        double newX = getPoint().getX() + amountChangeX;

        double amountChangeZ = coefficientZ * getSpeed();
        double newZ = getPoint().getZ() + amountChangeZ;

        if(isCoordValidMove(amountChangeX, newX, getPoint().getZ(), map, SpaceTimePoint.X_COORD)) {
            changeX(newX);
            hasMoved = true;
        }

        if(isCoordValidMove(amountChangeZ, getPoint().getX(), newZ, map, SpaceTimePoint.Z_COORD)) {
            changeZ(newZ);
            hasMoved = true;
        }

        return hasMoved;
    }

    private double getReversedDirection() {
        double newRotation = getPoint().getRotation();
        newRotation = newRotation - Math.PI;
        if (newRotation < 0) {
            newRotation = MAX_ROTATE + newRotation;
        }
        return newRotation;
    }

    public void movementTick(GameMap map) {

        if (getPoint().getX() < 0) {
            getPoint().setX(0);
        }
        if (getPoint().getX() > (map.getMaxX() - 1) + 1) {
            getPoint().setX(map.getMaxX() - 1);
        }
        if (getPoint().getZ() < 0) {
            getPoint().setZ(0);
        }
        if (getPoint().getZ() > (map.getMaxZ() - 1) + 1) {
            getPoint().setZ(map.getMaxZ() - 1);
        }

    }

    private boolean isShootoverValid(double amt, double x, double z, GameMap map, short coordinateType) {
        if(coordinateType == SpaceTimePoint.Z_COORD) {
            return (((amt > 0) && (map.isShootover(x, z + 1))) || ((amt < 0 && map.isShootover(x, z))));
        } else if(coordinateType == SpaceTimePoint.X_COORD) {
            return ((amt > 0 && map.isShootover(x + 1, z)) || (amt < 0 && map.isShootover(x, z)));
        }
        return false;
    }

    private boolean isMovementValid(double amt, double x, double z, GameMap map, short coordinateType) {
        if(coordinateType == SpaceTimePoint.X_COORD) {
            return ((amt > 0) && map.isMovable(x + 1, z)) || ((amt < 0) && map.isMovable(x, z));
        } else if (coordinateType == SpaceTimePoint.Z_COORD)
            return (amt > 0 && map.isMovable(x, z + 1)) || (amt < 0 && map.isMovable(x, z));
        return false;
    }

    private boolean isCoordValidMove(double amt, double x, double z, GameMap map, short coordType) {
        return ((isWalkOnWater() && (isShootoverValid(amt, x, z, map, coordType))) ||
                ((isMovementValid(amt, x, z, map, coordType))));
    }

    //Check before changing...
    private void changeX(double newX) {
        getPoint().setX(newX);
    }

    //Check before changing...

    /**
     * Change the value of the Z-Co-ordinate.
     *
     * Check whether the move is valid before changing it with {@link #isCoordValidMove(double, double, double, GameMap, short) isCoordValidMove()} method.
     *
     * @param newZ The Z-Coordinate to move to.
     */
    private void changeZ(double newZ) {
        getPoint().setZ(newZ);
    }
}

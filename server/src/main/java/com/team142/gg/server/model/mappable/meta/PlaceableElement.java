/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.model.mappable.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author just1689
 */
@AllArgsConstructor
public class PlaceableElement {

    @Getter
    @Setter
    private SpaceTimePoint point;

    @Getter
    @Setter
    private String skin;

    @Getter
    private final int TAG;

}

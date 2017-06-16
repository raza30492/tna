/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jazasoft.tna;

/**
 *
 * @author razamd
 */
public class ApiUrls {
    /*User Resource*/
    public static final String ROOT_URL_USERS = "/api/users";
    public static final String URL_USERS_USER = "/{userId}";
    /*Buyer Resources*/
    public static final String ROOT_URL_BUYERS = "/api/buyers";
    public static final String URL_BUYERS_BUYER = "/{buyerId}";
    public static final String URL_BUYERS_BUYER_LABELS = "/{buyerId}/labels";
    public static final String URL_BUYERS_BUYER_LABELS_LABEL = "/{buyerId}/labels/{labelId}";


}

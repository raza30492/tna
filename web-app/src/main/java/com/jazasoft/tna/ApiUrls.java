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
    public static final String URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES="/{buyerId}/labels/{labelId}/activities";
    public static final String URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES_ACTIVITY=
            "/{buyerId}/labels/{labelId}/activities/ativityId";
    public static final String URL_BUYERS_BUYER_LABELS_LABEL_ORDERS="/{buyerId}/labels/{labelId}/orders";
    public static final String URL_BUYERS_BUYER_LABELS_LABEL_ORDERS_ORDER="/{buyerId}/labels/{labelId}/orders/{orderId}";

    /*Activity Names Resources*/
    public static final String ROOT_URL_ACTIVITYNAMES="/api/activitynames";
    public static final String URL_ACTIVITYNAMES_ACTIVITYNAME="/{activitynameId}";

    /*  OrderDetail Resources */

    public static final String ROOT_URL_ORDERDETAILS="/api/orderdetails";
    public static final String URL_ORDERDETAILS_ORDERDETAIL="/{orderdetailId}";

}

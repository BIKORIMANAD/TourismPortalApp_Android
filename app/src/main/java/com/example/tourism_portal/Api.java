package com.example.tourism_portal;



public class Api {

    private static final String ROOT_URL = "http://172.20.10.6/2019/TourismTripPortal/suggestion/v1/Api.php?apicall=";
// very nice
    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

}

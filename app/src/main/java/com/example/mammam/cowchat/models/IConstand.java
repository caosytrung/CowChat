package com.example.mammam.cowchat.models;

import android.os.Environment;

/**
 * Created by Mam  Mam on 12/12/2016.
 */

public interface IConstand {

    String DRAW = "DRAW";
    String LOGIN = "LOGIN";
    String SAVE_USER = "SAVE_USER";
    String SAVE_PASSWORD = "SAVE_PASSWORD";
    String WELCOME = "WELCOME";
    String EMAIL = "EMAIL";
    String PASSWORD = "PASSWORD";
    int RQ_GALL = 1;
    int RQ_CAMERA = 2;
    int RQ_FILE = 3;
    String USER = "USER";
    String INFORMATION_USER = "INFORMATION_USER";
    String AVATAR = "AVATAR";
    String CHAT_IMAGE = "CHAT_IMAGE";
    String DB_NAME = "User.sqlite";
    String DB_PATH_SUFF = "/databases/";
    String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    String DB_FIRST_NAME = "firstname";
    String DB_LAST_NAME = "lastname";
    String DB_EMAIL = "email";
    String DB_PASSWORD = "password";
    String DB_AVATAR = "avatar";
    String DB_DES = "description";
    String DB_GENDER = "gender";
    String DB_ID = "id";
    String LIST_FRIEND = "LIST_FRIEND";
    String LIST_DE_NGHI_KET_BAN = "LIST_DE_NGHI_KET_BAN";
    String LISt_GUI_DI_LOI_MOI = "LISt_GUI_DI_LOI_MOI";
    String BB = "Bạn bè";
    String DGLMKB = "Đã gửi kết bạn ";
    String KB = " + Kết bạn";
    String CNLMKB = "+Chấp nhận kết bạn";
    String STATUS = "STATUS";
    int TYPE_1 = 1;
    int TYPE_2 = 2;
    int TYPE_3 = 3;
    int TYPE_4 = 4;
    int TYPE_5 = 5;
    int TYPE_6 = 6;
    int TYPE_7 = 7;
    int TYPE_8 = 8;
    String LIST_ROOM = "LIST_ROOM";
    String IMAGE_SMS = "IMAGE_SMS";
    String IMAGE_ROOM = "IMAGE_ROOM";
    String ROOM_ID = "ROOM_ID";
    String FRIEND_ID = "FRIEND_ID";
    String LINK_AVATAR = "LINK_AVATAR";
    String ROOT_PATH = Environment.getExternalStorageDirectory().getPath()+"/Chat chit";
    String URL_DOWNLOAD = "URL_DOWNLOAD";
    String RECEIVER= "RECEIVER";
    int UPDATE_PROGRESS = 1996;
    String FILE_NAME = "FILE_NAME";
    String PROGRESS = "PROGRESS";
    String CONVERSATION = " CONVERSATION";
    String NUMBER_MEMBER= "NUMBER_MEMBER";
    String APPLICATION_KEY = "56a2ec27-42a2-40f8-a61f-fd531eea30b4";
    String APPLICATION_SECRET = "XDNCrn1s5UOFK3I0nKKJcQ==";
    String HOST = "sandbox.sinch.com";
    int SMALL_WIDTH = 4;
    int LARGE_WIDTH = 8;
    int BIG_WIDTH = 16;
    String WHITE_COLOR = "#FFFFFF";
    String BLACK_COLOR = "#000000";
    String RED_COLOR = "#F44336";
    String BLUE_COLOR= "#2196F3";
    String GREEN_COLOR = "#2E7D32";
    String YELLOW_COLOR = "#FFEB3B";
    String ORANGE_COLOR = "#FF9800";
    String GRAY_COLOR = "#9E9E9E";
    String GREEN_LIGH = "#76FF03";
    int DEFAULT_WIDTH_PEN_DRAW = 4;
    String DEFAULE_COLOR_PEN = "#000000";
    String DEFAULT_COLOR_BG_DRAW = "#FFEB3B";
    String KEY_DEFAULT_WIDTH_PEN_DRAW = "KEY_DEFAULT_WIDTH_PEN_DRAW";
    String KEY_DEFAULE_COLOR_PEN = "KEY_DEFAULE_COLOR_PEN";
    String KEY__DEFAULT_COLOR_BG_DRAW = "KEY__DEFAULT_COLOR_BG_DRAW";
}

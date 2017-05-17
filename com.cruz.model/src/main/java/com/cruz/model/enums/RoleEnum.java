package com.cruz.model.enums;

public enum RoleEnum {
AUTHOR("ROLE_AUTHOR"),
AJANS("ROLE_AJANS"),
ADMIN("ROLE_ADMIN");
 
String userRoleType;
 
private RoleEnum(String userRoleType){
    this.userRoleType = userRoleType;
}
 
public String getUserRoleType(){
    return userRoleType;
}
}

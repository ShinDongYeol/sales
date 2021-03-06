package com.sales.main.vo.place;

import lombok.Data;

@Data
public class PlaceVO {
     private int storeNo;
     private String storeId;
     private String targetDate;  // 대상일
     private String empId;
     private String placeUrl;  // 가게 사이트 주소
     private String placeName;  // 장소명
     private String category;  // 카테고리
     private String roadAddr;  // 새도로명 주소
     private String addr;  // 지번주소
     private String phone;  // 번호
     private int statusNo;
     private String statusName;
     private String x;  // 경도
     private String y; // 위도

}

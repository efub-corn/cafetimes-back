package com.efub.cafetimes.dto;

import com.efub.cafetimes.domain.Cafe;
import com.efub.cafetimes.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CafeResponseDto {
    private Long id;
    private User owner;
    private String ownerName;
    private String cafeName;
    private String address;
    private String businessInfo;
    private String image;
    private Boolean isConfirmed;
    private Integer subscriptionCount;

    @Builder
    public CafeResponseDto(Cafe entity, User owner, String ownerName){
        this.id = entity.getId(); //getter를 썼으니까 Cafe 도메인에 있는 변수명인 id를 가져온다 -> getId로 함수명
        this.owner = owner;
        this.ownerName = ownerName;
        this.cafeName = entity.getCafeName();
        this.address = entity.getAddress();
        this.businessInfo = entity.getBusinessInfo();
        this.image = entity.getImage();
        this.isConfirmed = entity.getIsConfirmed();
        this.subscriptionCount = entity.getSubscriptionCount();
    }

    public Cafe toEnity() {
        Cafe build = Cafe.builder()
                .owner(owner)
                .cafeName(cafeName)
                .address(address)
                .businessInfo(businessInfo)
                .image(image)
                .isConfirmed(isConfirmed)
                .subscriptionCount(subscriptionCount)
                .build();
        return build;
    }

    public void updateUser(User owner) {
        this.owner = owner;
    }
}

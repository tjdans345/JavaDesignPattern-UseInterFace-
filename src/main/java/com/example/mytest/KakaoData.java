package com.example.mytest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoData {

    private Integer errorCode;

    private String errorMessage;

    private Integer id;
    @SerializedName("connected_at")
    private String connectedAt;
    private Properties properties;
    @SerializedName("kakao_account")
    private KakaoAccount kakaoAccount;

    @Data
    public static class Properties {
        private String nickname;
    }

    @Data
    public static class KakaoAccount {
        @SerializedName("profile_needs_agreement")
        private Boolean profileNeedsAgreement;
        private Profile profile;
        @SerializedName("has_email")
        private Boolean hasEmail;
        @SerializedName("email_needs_agreement")
        private Boolean emailNeedsAgreement;
        @SerializedName("is_email_valid")
        private Boolean isEmailValid;
        @SerializedName("is_email_verified")
        private Boolean isEmailVerified;
        private String email;
        @SerializedName("has_age_range")
        private Boolean hasAgeRange;
        @SerializedName("age_range_needs_agreement")
        private Boolean ageRangeNeedsAgreement;
        @SerializedName("age_range")
        private String ageRange;
        @SerializedName("has_birthday")
        private Boolean hasBirthday;
        @SerializedName("birthday_needs_agreement")
        private Boolean birthdayNeedsAgreement;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("birthday_type")
        private String birthdayType;
        @SerializedName("has_gender")
        private Boolean hasGender;
        @SerializedName("gender_needs_agreement")
        private Boolean genderNeedsAgreement;
        private String gender;

        @Data
        public static class Profile {
            private String nickname;
            @SerializedName("thumbnail_image_url")
            private String thumbnailImageUrl;
            @SerializedName("profile_image_url")
            private String profileImageUrl;
            @SerializedName("is_default_image")
            private Boolean isDefaultImage;

        }

    }



}




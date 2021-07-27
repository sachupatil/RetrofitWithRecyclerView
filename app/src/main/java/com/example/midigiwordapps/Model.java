package com.example.midigiwordapps;

import com.google.gson.annotations.SerializedName;

public class Model {


        @SerializedName("name")
        private String name;

        @SerializedName("image")
        private String image;

        @SerializedName("phone")
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

    }

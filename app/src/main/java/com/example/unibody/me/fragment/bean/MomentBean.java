package com.example.unibody.me.fragment.bean;

import java.util.List;

public class MomentBean {

    private String username;
    private List<MomentsBean> moments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MomentsBean> getMoments() {
        return moments;
    }

    public void setMoments(List<MomentsBean> moments) {
        this.moments = moments;
    }

    public static class MomentsBean {
        private String id;
        private UserBean user;
        private String datetime;
        private String context;
        private List<String> imgUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public List<String> getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(List<String> imgUrl) {
            this.imgUrl = imgUrl;
        }

        public static class UserBean {
            private String username;
            private String gender;
            private String age;
            private String unversity;
            private String avatar_url;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getUnversity() {
                return unversity;
            }

            public void setUnversity(String unversity) {
                this.unversity = unversity;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }
        }
    }
}

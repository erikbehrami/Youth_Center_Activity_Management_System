    package model;

    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class Advertisement {
        private int id;
        private String sponsorName;
        private String adTitle;
        private String adImageUrl;

        private Advertisement(int id, String sponsorName, String adTitle, String adImageUrl) {
            this.id = id;
            this.sponsorName = sponsorName;
            this.adTitle = adTitle;
            this.adImageUrl = adImageUrl;
        }

        public static Advertisement getInstance(ResultSet resultSet) throws SQLException {
            int id = resultSet.getInt("id");
            String sponsorName = resultSet.getString("sponsorName");
            String adTitle = resultSet.getString("adTitle");
            String adImageUrl = resultSet.getString("adImageUrl");

            return new Advertisement(id, sponsorName, adTitle, adImageUrl);
        }

        public int getId() { return this.id; }

        public void setId(int id) { this.id = id; }

        public String getSponsorName() { return this.sponsorName; }

        public void setSponsorName(String sponsorName) { this.sponsorName = sponsorName; }

        public String getAdTitle() { return this.adTitle; }

        public void setAdTitle(String adTitle) { this.adTitle = adTitle; }

        public String getAdImageUrl() { return this.adImageUrl; }

        public void setAdImageUrl(String adImageUrl) { this.adImageUrl = adImageUrl; }

    }

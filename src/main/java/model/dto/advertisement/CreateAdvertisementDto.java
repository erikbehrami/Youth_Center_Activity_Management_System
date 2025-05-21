package model.dto.advertisement;

public class CreateAdvertisementDto {
    private String sponsorName;
    private String adTitle;
    private String adImageUrl;

    public CreateAdvertisementDto(String sponsorName, String adTitle, String adImageUrl) {
        this.sponsorName = sponsorName;
        this.adTitle = adTitle;
        this.adImageUrl = adImageUrl;
    }

    public String getSponsorName() { return this.sponsorName; }

    public void setSponsorName(String sponsorName) { this.sponsorName = sponsorName; }

    public String getAdTitle() { return this.adTitle; }

    public void setAdTitle(String adTitle) { this.adTitle = adTitle; }

    public String getAdImageUrl() { return this.adImageUrl; }

    public void setAdImageUrl(String adImageUrl) { this.adImageUrl = adImageUrl; }
}

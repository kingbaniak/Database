public class Ksiazka implements Functionality {
    private String Artist;
    private String Title;
    private String Info;


    @Override
    public void setArtist(String value) {
        Artist = value;
    }

    @Override
    public void setTitle(String value) {
        Title = value;
    }

    public String storeInfo() {
        Info = "Author: " + Artist + "\nTitle: " + Title;
        return Info;
    }


    public String getTitle() {
        return Title;
    }
}

package app.news.com.newsapp.API;

public class Category{
    public static final String BUSINESS = "business";
    public static final String ENTERTAINMENT = "entertainment";
    public static final String GAMING = "gaming";
    public static final String GENERAL = "general";
    public static final String MUSIC = "music";
    public static final String SCIENCE_AND_NATURE = "science-and-nature";
    public static final String SPORTS = "sport";
    public static final String TECHNOLOGY = "technology";

    public static String toTitle(String category){
        String str = category.replace("-", " ");
        return str.toUpperCase();
    }

    public static String[] getAllCategories(){
        return new String[]{BUSINESS, ENTERTAINMENT, GAMING, GENERAL, MUSIC, SCIENCE_AND_NATURE, SPORTS, TECHNOLOGY};
    }
}

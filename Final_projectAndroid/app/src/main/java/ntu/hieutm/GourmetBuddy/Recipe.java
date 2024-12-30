package ntu.hieutm.GourmetBuddy;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private int id;
    private String name;
    private String category;
    private String difficulty;
    private int time;
    private int servings;
    private String instructions;
    private String healthInfo;
    private String imageUrl;

    public Recipe(int id, String name, String category, String difficulty, int time, int servings, String instructions, String healthInfo, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.time = time;
        this.servings = servings;
        this.instructions = instructions;
        this.healthInfo = healthInfo;
        this.imageUrl = imageUrl;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getHealthInfo() {
        return healthInfo;
    }

    public void setHealthInfo(String healthInfo) {
        this.healthInfo = healthInfo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Triển khai Parcelable
    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        category = in.readString();
        difficulty = in.readString();
        time = in.readInt();
        servings = in.readInt();
        instructions = in.readString();
        healthInfo = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(difficulty);
        dest.writeInt(time);
        dest.writeInt(servings);
        dest.writeString(instructions);
        dest.writeString(healthInfo);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}

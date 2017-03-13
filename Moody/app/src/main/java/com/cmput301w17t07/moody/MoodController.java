package com.cmput301w17t07.moody;

import android.graphics.Bitmap;
import android.location.Location;
import android.media.Image;
import android.util.Log;

import java.util.Date;

/**
 * Created by mike on 2017-02-23.
 */

/**
 * MoodController class object. Controller that controls mood objects with regards to offline
 * functionality. The MoodController also acts as a communicator between Mood objects and
 * the ElasticMoodController.
 */

public class MoodController {

    private static Mood mood = null;

    /**
     * createMood method that will create a new Mood object if the mood message is determined
     * to be appropriate. Method will also provided the new Mood object with the appropriate image
     * ID for its associated MoodImage object if it is added by the user. This means that this
     * method also creates MoodImage objects.
     *
     * Also communicates with the ElasticMood controller to get it to add the Mood to the server.
     * For project part 5, we will implement an internet checker method to determine whether
     * online or offline code logic needs to be implemented
     * @param feeling           user's selected feeling
     * @param username          user's username
     * @param moodMessage       user's textual explanation for their mood
     * @param location          user's location
     * @param image             bitmap of user's attached image
     * @param socialSituation   user's socialSituation
     * @return                  a boolean value indicating whether the mood was created
     */
    public static Boolean createMood(String feeling, String username, String moodMessage,
                                     Location location, Bitmap image, String socialSituation){
        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }

        //todo implement internet check to decide whether to use offline or online functionality

        // ID that will link mood to its respective image
        String moodID = null;

        // checking to see if there is an image to add to the database
        if(image != null){
            // Creating a new image object that will be linked to the proper mood; greasy workaround
            // to prevent slow loading of timeline
            ElasticMoodController.AddImage addImage = new ElasticMoodController.AddImage();
            MoodImage newImage = new MoodImage();
            // encoding image
            newImage.encodeImage(image);

            //adding image to database
            addImage.execute(newImage);
            try {
                moodID = addImage.get().getId();
            } catch (Exception E){
                Log.i("Error", "Weird method resulted in error because method is weird and sucks");
            }

        }



        // ID to link mood to image
        System.out.println("test ID"+ moodID);

        Mood newMood = new Mood(feeling, username, moodMessage, location, moodID, socialSituation);

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        addMood.execute(newMood);

        return true;
    }

    /**
     * Method for checking the message for appropriate word and character count. Returns a boolean
     * value indicating whether the message complies to requirements.
     * @param moodMessage
     * @return
     */
    public static Boolean checkMoodMessage(String moodMessage){
        String wordCheck = moodMessage.trim();
        if(wordCheck.split("\\s+").length > 3){
            return false;
        }
        else if(moodMessage.length() > 20){
            return false;
        }else{
            return true;
        }
    }


    /**
     *  EditMood method that works almost identically to the createMood method. Editing of mood
     *  date's is currently not implemented. Will discuss more thoroughly with prof/TA about
     *  what is required for date editing, as it is a strange feature...
     *
     *  Noticeable difference of the EditMood method is that it is passed a Date parameter (as
     *  mentioned above), and Mood object. The Mood object passed in is the "oldMood" that will
     *  need to be deleted from the server, as the new "edited mood" will need to be added in its
     *  place.
     * @param feeling
     * @param username
     * @param moodMessage
     * @param location
     * @param image
     * @param socialSituation
     * @param date
     * @param oldMood
     * @return
     */
    public static Boolean editMood(String feeling, String username, String moodMessage,
                           Location location, Bitmap image, String socialSituation, Date date, Mood oldMood){

        if(!checkMoodMessage(moodMessage)){
            // if it returns false...
            return false;
        }

        // ID that will link mood to its respective image
//        String moodID = oldMood.getMoodImageID();
        String moodID = null;


        // checking to see if there is an image to add to the database
        if(image != null){
            // Creating a new image object that will be linked to the proper mood; greasy workaround
            // to prevent slow loading of timeline
            ElasticMoodController.AddImage addImage = new ElasticMoodController.AddImage();
            MoodImage newImage = new MoodImage();
            // encoding image. Compression of image also happens here.
            newImage.encodeImage(image);

            //adding image to database
            addImage.execute(newImage);
            try {
                moodID = addImage.get().getId();
            } catch (Exception E){
                Log.i("Error", "Weird method resulted in error because method is weird and sucks");
            }

        }
        else{
            moodID = null;
        }



        // ID to link mood to image
        System.out.println("EDIT test ID"+ moodID);

        Mood editMood = new Mood(feeling, username, moodMessage, location, moodID, socialSituation);
        editMood.setDate(oldMood.getDate());
//        editMood.setId(oldMood.getId());    Will need this if we end up implementing a method that updates instead of edit and delete

        ElasticMoodController.AddMood addMood = new ElasticMoodController.AddMood();
        ElasticMoodController.DeleteMood deleteMood = new ElasticMoodController.DeleteMood();

        addMood.execute(editMood);
        deleteMood.execute(oldMood.getId());

        return true;

    }


    public String getMoodMessage() {
        return mood.getMoodMessage();
    }

    public void setMoodMessage(String moodMessage) {
        mood.setMoodMessage(moodMessage);
    }

    public Date getDate() {
        return mood.getDate();
    }

    public void setDate(Date date) {
        mood.setDate(date);
    }

    public Location getLocation() {
        return mood.getLocation();
    }

    public void setLocation(Location location) {
        mood.setLocation(location);
    }

//    public Bitmap getMoodImage() {
//        return mood.getMoodImage();
//    }

    public void setMoodImage(String moodImage) {
        mood.setMoodImage(moodImage);
    }


    public String getSocialSituation() {
        return mood.getSocialSituation();
    }

    public void setSocialSituation(String socialSituation) {
        mood.setSocialSituation(socialSituation);
    }


    public String getFeeling() {
        return mood.getFeeling();
    }

    public void setFeeling(String feeling) {
        mood.setFeeling(feeling);
    }

    public String getUsername() {
        return mood.getUsername();
    }

    public void setUsername(String username) {
        mood.setUsername(username);
    }

}
